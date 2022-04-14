/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.tile;

import java.awt.Color;
import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.mob.Plant;

/**
 *
 * @author tolet
 */
public class Pipe extends Tile{

    
    
    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) {
        super(x, y, width, height, solid, id, handler);
        this.facing = facing;
        
        if (plant){
            handler.addEntity(new Plant(getX(), getY() - 64,getWidth(),64, Id.plant, handler));
        }
    }

    public void render(Graphics g) {
        g.drawImage(Game.pipe.getBufferedImage(), x, y, width, height, null);
    
    }

    public void tick() {
        
    }
    
}
