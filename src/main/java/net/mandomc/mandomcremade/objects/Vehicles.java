package net.mandomc.mandomcremade.objects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Vehicles {

    private Player pilot;
    private Entity seatOne;
    private Entity model;

    public Vehicles(){}

    public void setPilot(Player pilot){
        this.pilot = pilot;
    }

    public Player getPilot(){
        return pilot;
    }

    public void setSeatOne(Entity seatOne){
        this.seatOne = seatOne;
    }

    public Entity getSeatOne(){
        return seatOne;
    }

    public void setModel(Entity model){
        this.model = model;
    }

    public Entity getModel(){
        return model;
    }

}
