/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import mario.Game;
import static mario.Game.fireball;
import mario.Id;
import mario.entity.Entity;
import mario.entity.Fireball;
import mario.entity.mob.UltimateBeam;
import mario.tile.PowerUpBlock;
import mario.tile.Tile;
import states.PlayerState;

/**
 *
 * @author tolet
 */
public class KeyInput implements KeyListener{

    public static boolean stand = true;
    private boolean fire;
    public static boolean aPressed = false, dPressed = false;

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en:Game.handler.entity){
            if (en.getId() == Id.player){
                if (en.goingDownPipe) return;
                switch(key) {
                case KeyEvent.VK_W:                   
                    
                    for (int j = 0; j < Game.handler.tile.size(); j++){
                        Tile t = Game.handler.tile.get(j);
                        if (t.isSolid()){
                            if (en.getBoundsBottom().intersects(t.getBounds())){
                                if(!en.jumping){ 
                                en.jumping = true;
                                en.gravity = 10.0;
                                en.jumpcount++;
                                Game.jump.play();
                                }
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_S:
                    for (int j = 0; j < Game.handler.tile.size(); j++){
                        Tile t = Game.handler.tile.get(j);
                        if (t.getId() == Id.pipe){
                            if (en.getBoundsBottom().intersects(t.getBounds())){
                                if (!en.goingDownPipe)
                                    en.goingDownPipe = true;
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!en.nextWall)
                        en.setVelX(-6);      
                    en.facing = 0;
                    aPressed = true;
                    stand = false;
                            
                    break;
                case KeyEvent.VK_D:
                    if (!en.nextWall)
                        en.setVelX(6);                
                    en.facing = 1;
                    dPressed = true;
                    stand = false;
                    break;                 
                case KeyEvent.VK_Q:
                    en.die();
                    break;     
                case KeyEvent.VK_E:
                    Game.handler.addEntity(new Fireball(64*19,64*10,64,64,Id.darkball,Game.handler,1, 1));
                    
                    break;  
                    
                case KeyEvent.VK_R:
                    Game.lives++;
                    
                    break;
                case KeyEvent.VK_SPACE:
                    if (en.state != PlayerState.FIRE)
                        switch(en.facing){
                            case 0:
                                Game.handler.addEntity(new UltimateBeam(en.getX(), en.getY()-12, 64, 64, Id.ultibeam, Game.handler, en.facing));
                                break;
                            case 1:
                                Game.handler.addEntity(new UltimateBeam(en.getX(), en.getY()-12, 64, 64, Id.ultibeam, Game.handler, en.facing));
                                break;
                        }    
                    if (en.state == PlayerState.FIRE && !fire){
                        switch(en.facing){
                        case 0:
                            Game.handler.addEntity(new Fireball(en.getX() - 24, en.getY() + 12, 24, 24, Id.fireball, Game.handler, en.facing, 0));
                            fireball.play();
                            fire = true;
                            break;
                        case 1: 
                            Game.handler.addEntity(new Fireball(en.getX() + en.getWidth(), en.getY() + 12, 24, 24, Id.fireball, Game.handler, en.facing, 0));
                            fireball.play();
                            fire = true;
                            break;
                        }
                        
                    }
                    break;
                case KeyEvent.VK_T:
                    Game.switchLevel();
                    break;
                }
            }
            
        }
        
        
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en:Game.handler.entity){
            if (en.getId() == Id.player){
                switch(key) {
                case KeyEvent.VK_W:
                    en.setVelY(0);
                    if(en.jumping){
                        en.jumping = false;
                        en.falling = true;
                        en.gravity = 0.8;
                    }
                    break;
                case KeyEvent.VK_A:
                    aPressed = false;
                    if (dPressed){
                        en.setVelX(6);
                    }else{
                        en.setVelX(0);
                        stand = true;
                    }
                    break;
                case KeyEvent.VK_D:
                    dPressed = false;
                    if (aPressed){
                        en.setVelX(-6);
                    }else{
                        en.setVelX(0);
                        stand = true;
                    }
                case KeyEvent.VK_SPACE:
                    fire = false;
                    break;
            }
            }
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //not using
    }
    
}
