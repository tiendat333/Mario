/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity;

import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;
;

/**
 *
 * @author tolet
 */
public class Coin extends Entity{
    
    private int frame = 0;
    private int frameDelay = 0;

    public Coin(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    

    public void render(Graphics g) {
        g.drawImage(Game.coin[frame].getBufferedImage(), x, y, width, height, null);
    }
    
    public void tick() {
        
            frameDelay++;
            if (frameDelay >= 30){
                frame++;
                if (frame > 1){
                    frame = 0;
                }
                frameDelay = 0;
            }
       
    }
    
}
