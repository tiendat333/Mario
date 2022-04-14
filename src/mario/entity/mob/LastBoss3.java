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
import mario.entity.Fireball;
import mario.tile.Tile;
import states.BossState3;

/**
 *
 * @author tolet
 */
public class LastBoss3 extends Entity{

    private int z = 0;
    private int c = 0;
    private int v = 0;
    private int blr = 0;
    private int k = 1;
    private int speed = 0;
    
    private int frame = 0;
    private int frameSecond = 0;
    private int frame2Second = 0;
    private int frameBeam = 0;
    private int frameDarkBall = 0;
    private int frameSpinning = 0;
    private int frameDelay = 0;
    private int frameDelaySecond = 0;
    private int frameDelay2Second = 0;
    private int frameDelayBeam = 0;
    private int frameDelayDarkBall = 0;
    private int frameDelaySpinning = 0;
    
    private boolean scoresCal = false;
    
    
    
    public Random random = new Random();
    
    public LastBoss3(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        Game.bossPhase = 3;
        
        int dir = random.nextInt(2);
        switch(dir){
            case 0:
                setVelX(-speed);
                break;
            case 1:
                setVelX(speed);
                break;             
        }
        
        bossState3 = BossState3.DARKBALL;
    }

    public void render(Graphics g) {
        if (bossState3 == BossState3.BEAMING)
            g.drawImage(Game.bossFinal[frameBeam+2].getBufferedImage(), x, y, width, height, null);
        else if (bossState3 == BossState3.DARKBALL)
            if (facing == 0)
                g.drawImage(Game.bossFinal[frameDarkBall+9].getBufferedImage(), x, y, width, height, null);
            else
                g.drawImage(Game.bossFinal[frameDarkBall+6].getBufferedImage(), x, y, width, height, null);
        else if (bossState3 == BossState3.SPINNING)
            if (facing == 0)
                g.drawImage(Game.bossFinal[frameSpinning+16].getBufferedImage(), x, y, width, height, null);
            else
                g.drawImage(Game.bossFinal[frameSpinning+12].getBufferedImage(), x, y, width, height, null);
        else
            g.drawImage(Game.bossFinal[1].getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        x += velX;
        y += velY;
        
        if (velX < 0)
            facing = 0;
        else if (velX > 0)
            facing = 1;
        
        //if (getY() == 64*10 && getX() == 64*3)
        
        //if (getY() == 64*6 && getX() == 64*3)
        
        //if (getY() == 64*6 && getX() == 64*19)
        
        if (Game.boss2hitted == 3)
            bossState3 = BossState3.BEAMING;
        else if(Game.boss2hitted == 6)
            bossState3 = BossState3.SPINNING;
        else if (Game.boss2hitted == 10){
            if (!scoresCal){
                Game.scores = Game.scores + 15000;
                scoresCal = true;
            }
            die();
        }
        
        if (bossState3 == BossState3.RAGE)
            k = 3;
        else 
            k = 2;
            frameDelaySecond++;
            if (frameDelaySecond >= 60){
                frameSecond++;
                if (frameSecond > 2){
                    frameSecond = 0;
                }
                frameDelaySecond = 0;
            }
        
            frameDelay2Second++;
            if (frameDelay2Second >= 120){
                frame2Second++;
                if (frame2Second > 1){
                    frame2Second = 0;
                }
                frameDelay2Second = 0;
            }   
        if (bossState3 == BossState3.BEAMING){
            frameDelayBeam++;
            if (frameDelayBeam >= 60){
                frameBeam++;
                if (frameBeam > 3){
                    frameBeam = 0;
                }
                frameDelayBeam = 0;
            }
        }else{
            frameDelayBeam = 0;
            frameBeam = 0;
        }
        
        if (bossState3 == BossState3.DARKBALL){
            frameDelayDarkBall++;
            if (frameDelayDarkBall >= 60){
                frameDarkBall++;
                if (frameDarkBall > 2){
                    frameDarkBall = 0;
                }
                frameDelayDarkBall = 0;
            }
        }else{
            frameDelayDarkBall = 0;
            frameDarkBall = 0;
        }
        
        if (bossState3 == BossState3.SPINNING){
            frameDelaySpinning++;
            speed = 3;
            if (frameDelaySpinning >= 4){
                frameSpinning++;
                if (frameSpinning > 3){
                    Game.handler.addEntity(new Fireball(getX() + getWidth()/2-10, getY()+getHeight()/2-10, 20, 20, Id.darkball, Game.handler, 1, 4));
                    frameSpinning = 0;
                }
                frameDelaySpinning = 0;
            }
        }else{
            frameDelaySpinning = 0;
            frameSpinning = 0;
        }
        
                
        if (bossState3 == BossState3.BEAMING){
            speed = 0;
            double randomDouble1 = Math.random();
            randomDouble1 = randomDouble1 * 6 + 2;
            int randomInt1 = (int) randomDouble1;
            int ud = random.nextInt(2);
            int bs = random.nextInt(2);
            int blrr = random.nextInt(2);
            if (frameBeam == 1 && frameDelayBeam == 0){
                z = randomInt1;
                c = ud;
                v = bs;
                blr = blrr;
                warning(z,c,v,blr);
            }
                
            if (frameBeam == 3 && frameDelayBeam == 0)
                beaming(z,c,v,blr);
        }
        
        if (bossState3 == BossState3.DARKBALL){
            speed = 1;
            if (frameDarkBall == 2 && frameDelayDarkBall == 0)
                for (int i = 0; i < k; i++){
                    Game.handler.addEntity(new Fireball(getX() + getWidth(), getY(), 64, 64, Id.darkball, Game.handler, 1, 1));
                    Game.handler.addEntity(new Fireball(getX() - 64, getY(), 64, 64, Id.darkball, Game.handler, 0, 1));
                }
        }
       
                
                
        for(Tile t:handler.tile){
            if(t.getId() == Id.wall) {
                
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                
                }
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(speed);
                    
                }
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(-speed);
                }
            }                       
        }
        
        for (int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if (e.getId() == Id.player){
                if (bossState3 != BossState3.IDLE){
                        if (getX() >= e.getX() -4 && getX() <= e.getX() +4)
                            setVelX(0);
                        else if (e.getX() < getX())
                            setVelX(-speed);
                        else if (e.getX() > getX())
                            setVelX(speed);
                        
                        if (getY() >= e.getY() -4 && getY() <= e.getY() +4)
                            setVelY(0);
                        else if (e.getY() < getY())
                            setVelY(-speed);
                        else if (e.getY() > getY())
                            setVelY(speed);
                }else if (bossState3 == BossState3.SPINNING){
                    if (e.getX() < getX())
                        setVelX(-3);
                    else if (e.getX() > getX())
                        setVelX(3);
                }
            }else if (e.getId() == Id.ultibeam)
                if (getBounds().intersects(e.getBounds())){
                    Game.boss2hitted++;
                    e.die();
                }
        }
        
        
        
        if(velX != 0){
            frameDelay++;
            if (frameDelay >= 7){
                frame++;
                if (frame >= 2){
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    
    }
    
    public void beaming(int z, int c, int v, int b){
        switch(v){
            case 0:
                for (int i = 0; i < z; i++)
                    Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0));
                for (int i = z+2; i < z+6; i++)
                    Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0));
                for (int i = z+8; i < z + 12; i++)
                    Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0)); 
                for (int i = z+14; i < 26; i++)
                    Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0)); 
                break;
            case 1:
                switch(blr){
                    case 0:
                        for (int i = 0; i < 11; i++)        
                            Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0)); 
                        break;
                    case 1:
                        for (int i = 11; i < 22; i++)        
                            Game.handler.addEntity(new Beam(64*i,0,64,64*15,Id.beam,Game.handler,0)); 
                        break;
                }
                break;             
        }
        
        switch(c){
            case 0:
                for (int i = 0; i < 7; i++)        
                Game.handler.addEntity(new Beam(0,64*i,64*25,64,Id.beam,Game.handler,1)); 
                break;
            case 1:
                for (int i = 8; i < 15; i++)        
                Game.handler.addEntity(new Beam(0,64*i,64*25,64,Id.beam,Game.handler,1)); 
                break;             
        }
        
               
    }
    
    public void warning(int z, int c, int v, int b){
        switch(v){
            case 0:
                for (int i = 0; i < z; i++)
                    Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                for (int i = z+2; i < z+6; i++)
                    Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                for (int i = z+8; i < z + 12; i++)
                    Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                for (int i = z+14; i < 26; i++)
                    Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                break;
            case 1:
                switch(blr){
                    case 0:
                        for (int i = 0; i < 11; i++)        
                            Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2)); 
                        break;
                    case 1:
                        for (int i = 11; i < 22; i++)        
                            Game.handler.addEntity(new Beam(64*i,Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                        break;
                }
                break;             
        }
        switch(c){
            case 0:
                for (int i = 0; i < 7; i++)        
                Game.handler.addEntity(new Beam(Game.getVisibleArea().x + 270,64*i,64,64,Id.beam,Game.handler,2)); 
                break;
            case 1:
                for (int i = 8; i < 15; i++)        
                Game.handler.addEntity(new Beam(Game.getVisibleArea().x + 270,64*i,64,64,Id.beam,Game.handler,2)); 
                break;             
        }    
    }
}
