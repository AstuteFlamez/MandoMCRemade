package net.mandomc.mandomcremade.listeners;

import net.mandomc.mandomcremade.MandoMCRemade;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListEvents implements Listener {
    MandoMCRemade plugin;

    public ServerListEvents(MandoMCRemade plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if (MandoMCRemade.getInstance().getConfig().getBoolean("Maintenance")) {
            event.setMotd("\\u00A74\\u00A7lMandoMC Is \\u00A7r\\n\\u00A74\\u00A7lUndergoing Maintenance");
        } else {
            event.setMotd("&2&lMandoMC has just released!");
        }
    }

}
