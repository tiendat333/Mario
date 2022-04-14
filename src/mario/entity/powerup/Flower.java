/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.powerup;

import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;

/**
 *
 * @author tolet
 */
public class Flower extends Entity{
    
    public Flower(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }
    
    public void render(Graphics g) {
        g.drawImage(Game.flower.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public void tick() {
    
    }
    
}
