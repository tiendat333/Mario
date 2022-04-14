/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.tile.Tile;

/**
 *
 * @author tolet
 */
public class UltimateBeam extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    public Random random = new Random();
    
    public UltimateBeam(int x, int y, int width, int height, Id id, Handler handler, int facing) {
        super(x, y, width, height, id, handler);
        this.facing = facing;
        
        int dir = random.nextInt(2);
        
        switch(facing){
            case 0:
                setVelX(-6);
                break;
            case 1:
                setVelX(6);
                break;
        }
    }

    public void render(Graphics g) {
        if (facing == 1)
            g.drawImage(Game.ultimatebeam[frame].getBufferedImage(), x, y, width, height, null);
        else
            g.drawImage(Game.ultimatebeam[frame+4].getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        x += velX;
        y += velY;
        
        for(int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
            
            if (t.isSolid()){
               if (getBoundsLeft().intersects(t.getBounds()) || getBoundsRight().intersects(t.getBounds()))
                   die();
            }
        }
        frameDelay++;
        if (frameDelay >= 10){
            frame++;
            if (frame >= 4){
                frame = 0;
            }
            frameDelay = 0;
        }
        
        
    
    }
}
