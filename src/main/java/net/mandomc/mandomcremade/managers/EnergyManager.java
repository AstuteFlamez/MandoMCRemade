package net.mandomc.mandomcremade.managers;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import net.mandomc.mandomcremade.MandoMCRemade;
import net.mandomc.mandomcremade.objects.Energy;

public class EnergyManager implements Listener {
    private static final int FATIGUE_COOLDOWN_TICKS = 60;
    private static final double ENERGY_REGEN_RATE = 2.0;
    private static final double ENERGY_DEPLETION_AMOUNT = 10.0;
    private static EnergyManager instance;

    private final MandoMCRemade plugin;
    private final HashMap<UUID, Energy> playerEnergyMap = new HashMap<>();
    private final HashMap<UUID, Long> lastActionTime = new HashMap<>();

    public EnergyManager(MandoMCRemade plugin) {
        this.plugin = plugin;
        instance = this;
        startEnergyTasks();
    }

    public static EnergyManager getInstance() {
        return instance;
    }

    public void addEnergy(Player player, double initialEnergy) {
        playerEnergyMap.computeIfAbsent(player.getUniqueId(), id -> {
            Energy energy = new Energy(player, initialEnergy, plugin);
            setupScoreboard(player);
            return energy;
        });
    }

    public void removeEnergy(Player player) {
        playerEnergyMap.remove(player.getUniqueId());
    }

    private void startEnergyTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                playerEnergyMap.forEach((playerId, energy) -> {
                    Player player = Bukkit.getPlayer(playerId);
                    if (player != null && player.isOnline()) {
                        if (!energy.isFatigued() && energy.getEnergy() < Energy.MAX_ENERGY) {
                            energy.setEnergy(energy.getEnergy() + ENERGY_REGEN_RATE);
                        }
                        updateScoreboard(player);
                    }
                });
            }
        }.runTaskTimer(plugin, 0L, 20L); // 20 ticks interval
    }

    private void setupScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager != null) {
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("energy", "dummy",
                    ChatColor.YELLOW + "Energy: " + ChatColor.RED + (int) getEnergy(player).getEnergy());
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            player.setScoreboard(scoreboard);
        }
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("energy");
        if (objective != null) {
            objective.setDisplayName(ChatColor.YELLOW + "Energy: " + (int) getEnergy(player).getEnergy());
        } else {
            setupScoreboard(player);
        }
    }

    private Energy getEnergy(Player player) {
        return playerEnergyMap.getOrDefault(player.getUniqueId(), new Energy(player, 0.0, plugin));
    }

    private void applyPotionEffect(LivingEntity entity, PotionEffectType effectType, int durationTicks, int amplifier) {
        entity.addPotionEffect(new PotionEffect(effectType, durationTicks, amplifier));
    }

    private void applyFatigue(Player player) {
        Energy energy = getEnergy(player);
        if (energy.getEnergy() <= 0 && !energy.isFatigued()) {
            energy.setFatigued(true);
            player.setSprinting(false);
            player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 1.0f, 0.5f);
            applyPotionEffect(player, PotionEffectType.SLOWNESS, 100, 2);
            applyPotionEffect(player, PotionEffectType.NAUSEA, 100, 1);

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (getEnergy(player).getEnergy() <= 0) {
                    applyFatigue(player); // Reapply fatigue if energy is still zero
                } else {
                    energy.setFatigued(false);
                }
                updateScoreboard(player);
            }, FATIGUE_COOLDOWN_TICKS);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        addEnergy(event.getPlayer(), 25.0);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        removeEnergy(event.getPlayer());
    }

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (getEnergy(player).isFatigued() && !player.isSprinting()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHitByLightsaber(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && event.getDamager() instanceof LivingEntity attacker) {
            ItemStack item = attacker.getEquipment() != null ? attacker.getEquipment().getItemInMainHand() : null;
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                    && item.getItemMeta().getDisplayName().contains("Lightsaber")) {
                if (getEnergy(attacker).isFatigued()) {
                    event.setCancelled(true);
                    attacker.sendMessage(ChatColor.RED + "You are too fatigued to attack!");
                    applyPotionEffect(player, PotionEffectType.SLOWNESS, 100, 2);
                    applyPotionEffect(player, PotionEffectType.NAUSEA, 100, 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()
                    && item.getItemMeta().getDisplayName().contains("Lightsaber")) {
                Energy energy = getEnergy(player);
                long currentTime = System.currentTimeMillis();
                long lastTime = lastActionTime.getOrDefault(player.getUniqueId(), 0L);
                if (currentTime - lastTime >= 55) {
                    lastActionTime.put(player.getUniqueId(), currentTime);
                    double newEnergy = Math.max(0, energy.getEnergy() - ENERGY_DEPLETION_AMOUNT);
                    energy.setEnergy(newEnergy);
                    if (newEnergy == 0) applyFatigue(player);
                    updateScoreboard(player);
                }
            }
        }
    }
}
