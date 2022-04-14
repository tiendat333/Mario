/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.tile;

import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.powerup.Flower;
import mario.entity.powerup.Mushroom;
import mario.entity.powerup.PowerStar;
import mario.graphic.Sprite;

/**
 *
 * @author tolet
 */
public class PowerUpBlock extends Tile{

    private Sprite powerUp;
    
    private boolean poppedUp = false;
    
    private int spriteY = getY();
    private int type;
    
    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, int type) {
        super(x, y, width, height, solid, id, handler);
        this.type = type;
        this.powerUp = powerUp;
    }

    public void render(Graphics g) {
        if (!poppedUp) 
            if (powerUp == Game.mushroom)
                g.drawImage(Game.mushroom.getBufferedImage(), x, spriteY, width, height, null);
            else if (powerUp == Game.lifeMushroom)
                g.drawImage(Game.lifeMushroom.getBufferedImage(), x, spriteY, width, height, null);
            else if (powerUp == Game.flower)
                g.drawImage(Game.flower.getBufferedImage(), x, spriteY, width, height, null);
            else if (powerUp == Game.star)
                g.drawImage(Game.star.getBufferedImage(), x, spriteY, width, height, null);
            
        if (!activated){
            g.drawImage(Game.powerUp.getBufferedImage(), x, y, width, height, null);
        }else 
            g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        if (activated && !poppedUp){
            spriteY--;
            if (spriteY <= y-height){
                if (powerUp == Game.mushroom || powerUp == Game.lifeMushroom)
                    handler.addEntity(new Mushroom(x,spriteY,width,height,Id.mushroom,handler,type));
                else if (powerUp == Game.flower)
                    handler.addEntity(new Flower(x,spriteY,width,height,Id.flower,handler));
                else if (powerUp == Game.star)
                    handler.addEntity(new PowerStar(x,spriteY,width,height,Id.star,handler));
                poppedUp = true; 
            }
        }
    }
    
}
