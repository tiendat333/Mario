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
import states.BossState2;

/**
 *
 * @author tolet
 */
public class LastBoss2 extends Entity{
    
    private int frame = 0;
    private int frameSecond = 0;
    private int frame2Second = 0;
    private int frameDelay = 0;
    private int frameDelayIntro = 0;
    private int frameDelaySecond = 0;
    private int frameDelay2Second = 0;
    private int frameDelayLastBeam = 0;
    
    private int frameDelayBeam = 0;
    private int phaseTime = 0;
    
    private int k = 1;
    private int[] xi = new int[10], yi = new int [10];
    private int warningcount = 0;
    
    public Random random = new Random();
    
    private boolean beamed = false;
    private boolean warning = false;
    private boolean darkball = false;
    private boolean landing = false;
    private boolean finalbeamready = false;
    private boolean finalattackbeam = false;
    private boolean finalattackbeam2 = false;
    private boolean lastbeam = false;
    private boolean scoresCal = false;
    
    public LastBoss2(int x, int y, int width, int height, Id id, Handler handler, int type) {
        super(x, y, width, height, id, handler);
        
        int dir = random.nextInt(2);
        
        if (Game.bossPhase == 0 && !Game.showDeathScreen){
            Game.bossPhase = 1;
        }else if (Game.bossPhase == 1 && !Game.showDeathScreen)
            Game.bossPhase = 2;
        bossState2 = BossState2.IDLE;
        
        this.type = type;
    }
    
    public void render(Graphics g) {
        if (frameDelayIntro<500){
            if (0 < frameDelayIntro && frameDelayIntro < 150){
                switch(getType()){
                    case 0:
                        g.drawImage(Game.rightBoss2[0].getBufferedImage(), x, y, width, height, null);
                        break;
                    case 1:
                        g.drawImage(Game.leftBoss2[0].getBufferedImage(), x, y, width, height, null);
                        break;
                }
            }else if (150 < frameDelayIntro && frameDelayIntro < 300){
                switch(getType()){
                    case 0:
                        g.drawImage(Game.rightBoss2[1].getBufferedImage(), x, y, width, height, null);
                        break;
                    case 1:
                        g.drawImage(Game.leftBoss2[1].getBufferedImage(), x, y, width, height, null);
                        break;
                }
            }else if (300 < frameDelayIntro && frameDelayIntro < 400){
                switch(getType()){
                    case 0:
                        g.drawImage(Game.rightBoss2[2].getBufferedImage(), x, y, width, height, null);
                        break;
                    case 1:
                        g.drawImage(Game.leftBoss2[2].getBufferedImage(), x, y, width, height, null);
                        break;
                }
            }else if (400 < frameDelayIntro && frameDelayIntro < 500){
                switch(getType()){
                    case 0:
                        g.drawImage(Game.rightBoss2[9].getBufferedImage(), x, y, width, height, null);
                        break;
                    case 1:
                        g.drawImage(Game.leftBoss2[9].getBufferedImage(), x, y, width, height, null);
                        break;
                }
            }
        }else if (bossState2 == BossState2.FINALATTACK){
            switch(getType()){
            case 0:
                if (0 < frameDelayBeam && frameDelayBeam < 30)
                    g.drawImage(Game.rightBoss2[10].getBufferedImage(), x, y, width, height, null);
                else if (30 < frameDelayBeam && frameDelayBeam < 60) 
                    g.drawImage(Game.rightBoss2[11].getBufferedImage(), x, y, width, height, null);
                else if (60 < frameDelayBeam && frameDelayBeam < 90) 
                    g.drawImage(Game.rightBoss2[12].getBufferedImage(), x, y, width, height, null);
                else if (90 < frameDelayBeam && frameDelayBeam < 120) 
                    g.drawImage(Game.rightBoss2[13].getBufferedImage(), x, y, width, height, null);
                else if (120 < frameDelayBeam && frameDelayBeam < 150) 
                    g.drawImage(Game.rightBoss2[14].getBufferedImage(), x, y, width, height, null);
                else if (150 < frameDelayBeam && frameDelayBeam < 180) 
                    g.drawImage(Game.rightBoss2[15].getBufferedImage(), x, y, width, height, null);
                else 
                    g.drawImage(Game.rightBoss2[16].getBufferedImage(), x, y, width, height, null);
                
                break;
            case 1:
                if (0 < frameDelayBeam && frameDelayBeam < 30)
                    g.drawImage(Game.leftBoss2[10].getBufferedImage(), x, y, width, height, null);
                else if (30 < frameDelayBeam && frameDelayBeam < 60) 
                    g.drawImage(Game.leftBoss2[11].getBufferedImage(), x, y, width, height, null);
                else if (60 < frameDelayBeam && frameDelayBeam < 90) 
                    g.drawImage(Game.leftBoss2[12].getBufferedImage(), x, y, width, height, null);
                else if (90 < frameDelayBeam && frameDelayBeam < 120) 
                    g.drawImage(Game.leftBoss2[13].getBufferedImage(), x, y, width, height, null);
                else if (120 < frameDelayBeam && frameDelayBeam < 150) 
                    g.drawImage(Game.leftBoss2[14].getBufferedImage(), x, y, width, height, null);
                else if (150 < frameDelayBeam && frameDelayBeam < 180) 
                    g.drawImage(Game.leftBoss2[15].getBufferedImage(), x, y, width, height, null);
                else 
                    g.drawImage(Game.leftBoss2[16].getBufferedImage(), x, y, width, height, null);
                break;
            }
            
            
        }else
            switch(getType()){
            case 0:
                g.drawImage(Game.rightBoss2[frame+3].getBufferedImage(), x, y, width, height, null);
                break;
            case 1:
                g.drawImage(Game.leftBoss2[frame+3].getBufferedImage(), x, y, width, height, null);
                break;
            }
    }
    
    public void tick() {
        x += velX;
        y += velY;
        
        if (bossState2 == BossState2.FINALATTACK && !finalbeamready){
            frameDelayBeam++;
            if (frameDelayBeam > 181)
                finalbeamready = true;
        }
        
        if (bossState2 == BossState2.FINALATTACK && !landing){
            setVelY(18);
            landing = true;
        }
            
        if (velY == 0 && landing && !finalattackbeam && 150 < frameDelayBeam && frameDelayBeam < 180){
            switch(getType()){
            case 0:
                Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,5));
                
                break;
            case 1:
                Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,8));
                break;
            }
            
            finalattackbeam = true;
        }
            
        phaseTime++;
        
        if (finalattackbeam && !finalattackbeam2 && finalbeamready){
            
                switch(getType()){
                    case 0:
                        Game.handler.addEntity(new Beam(getX()-64, getY(), 64, 64,Id.beam,Game.handler,3));
                        Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,4));
                        Game.handler.addEntity(new Beam(getX()-64, getY()-64*11, 64, 64*11,Id.beam,Game.handler,5));
                        break;
                    case 1:
                        Game.handler.addEntity(new Beam(getX()+64, getY(), 64, 64,Id.beam,Game.handler,6));
                        Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,7));
                        Game.handler.addEntity(new Beam(getX()+64, getY()-64*11, 64, 64*11,Id.beam,Game.handler,8));
                        break;
                }
                finalattackbeam2 = true;
            
           
        }
        
        if (finalattackbeam2 && !lastbeam){
            frameDelayLastBeam++;
            if (frameDelayLastBeam > 20){
                switch(getType()){
                    case 0:
                        Game.handler.addEntity(new Beam(getX()-128, getY(), 64, 64,Id.beam,Game.handler,3));
                        Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,4));
                        Game.handler.addEntity(new Beam(getX()-64, getY()-64*10, 64, 64*11,Id.beam,Game.handler,4));
                        Game.handler.addEntity(new Beam(getX()-128, getY()-64*11, 64, 64*11,Id.beam,Game.handler,5));
                        break;
                    case 1:
                        Game.handler.addEntity(new Beam(getX()+128, getY(), 64, 64,Id.beam,Game.handler,6));
                        Game.handler.addEntity(new Beam(getX(), getY()-64*11, 64, 64*11,Id.beam,Game.handler,7));
                        Game.handler.addEntity(new Beam(getX()+64, getY()-64*10, 64, 64*11,Id.beam,Game.handler,7));
                        Game.handler.addEntity(new Beam(getX()+128, getY()-64*11, 64, 64*11,Id.beam,Game.handler,8));
                        break;
                }
                lastbeam = true;
                frameDelayLastBeam = 0;
            }
            
        }
        
        if (lastbeam){
            frameDelayLastBeam++;
            if (frameDelayLastBeam > 20 && !Game.showDeathScreen){
                Game.bossPhase = 3;
                Game.switchLevel();
                Game.boss2hitted = 0;
            }
        }
            
        
        if (bossState2 == BossState2.IDLE)
            frameDelayIntro++;
        if (frameDelayIntro > 500 && bossState2 == BossState2.IDLE)
            bossState2 = BossState2.DARKBALL;
        
            frameDelay++;
            if (frameDelay >= 60){
                frame++;
                if (frame > 6){
                    frame = 0;
                }
                frameDelay = 0;
            }
            if (bossState2 == BossState2.DARKBALL || bossState2 == BossState2.RAGE){
                frameDelay2Second++;
                if (frameDelay2Second >= 60){
                    frame2Second++;
                    if (frame2Second > 1){
                        frame2Second = 0;
                    }
                    frameDelay2Second = 0;
                    darkball = false;
                }
            }
            
            
            if (bossState2 == BossState2.BEAMING || bossState2 == BossState2.RAGE){
                frameDelaySecond++;
                if (frameDelaySecond >= 40){
                    frameSecond++;
                    if (frameSecond > 1){
                        frameSecond = 0;
                        warningcount++;
                    }
                    frameDelaySecond = 0;
                }
            }
            
          
        
        
            
         
        if (Game.boss2hitted == 3)
            bossState2 = BossState2.BEAMING;
        else if(Game.boss2hitted == 6)
            bossState2 = BossState2.RAGE;
        else if (Game.boss2hitted == 10){
            bossState2 = BossState2.FINALATTACK;
            if (!scoresCal){
                Game.scores = Game.scores + 10000;
                scoresCal = true;
            }
        }
            
        if (bossState2 == BossState2.RAGE)
            k = 2;
        else 
            k = 1;
        if (bossState2 == BossState2.BEAMING || bossState2 == BossState2.RAGE){
            
                if(frameSecond > 0){
                    
                    if (warningcount ==2 && !warning ){
                        for (int i = 0; i < k; i++){
                            double randomDouble1 = Math.random();
                            randomDouble1 = randomDouble1 * 18 + 1;
                            int randomInt1 = (int) randomDouble1;
                            double randomDouble2 = Math.random();
                            randomDouble2 = randomDouble2 * 11 + 1;
                            int randomInt2 = (int) randomDouble2;
                            xi[i] = randomInt1;
                            yi[i] = randomInt2;
                            Game.handler.addEntity(new Beam(Game.getVisibleArea().x + 270,64*(yi[i]-2),64,64,Id.beam,Game.handler,2));
                            Game.handler.addEntity(new Beam(64*(xi[i]+4),Game.getVisibleArea().y + 1155,64,64,Id.beam,Game.handler,2));
                        }
                        Game.beamsound.stop();
                        Game.darkfireballsound.stop();
                        warning = true;
                    }else if (warningcount == 4){
                        Game.beamsound.play();
                        for (int i = 0; i < k; i++){
                            Game.handler.addEntity(new Beam(100,64*(yi[i]-2),64*23,64,Id.beam,Game.handler,1));
                            Game.handler.addEntity(new Beam(64*(xi[i]+4),0,64,64*23,Id.beam,Game.handler,0));
                        }
                        warningcount = 0;
                        warning = false;
                    }
            }
        }
        
        if (bossState2 == BossState2.DARKBALL || bossState2 == BossState2.RAGE){
            
            if (frame2Second >0 && !darkball){
                Game.darkfireballsound.play();
                for (int i = 0; i < k; i++){
                    if (type == 0)
                        Game.handler.addEntity(new Fireball(getX() + getWidth(), getY(), 64, 64, Id.darkball, Game.handler, 1, 1));
                    else
                        Game.handler.addEntity(new Fireball(getX() - 64, getY(), 64, 64, Id.darkball, Game.handler, 0, 1));
                }
                
            }
            darkball = true;
        }
        
        for (int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if (e.getId() == Id.player && bossState2 != BossState2.IDLE ){
                if (getBounds().intersects(e.getBounds()))
                    e.die();
            }else if (e.getId() == Id.ultibeam)
                if (getBounds().intersects(e.getBounds())){
                    Game.boss2hitted++;
                    e.die();
                }
        }
        
        for(Tile t:handler.tile){
            if(t.solid){
                if(getBoundsBottom().intersects(t.getBounds()))
                    setVelY(0);
            }
        }
    }
    
    public void chooseState(){
        int nextPhase = random.nextInt(2);
        if (nextPhase == 0)
            bossState2 = bossState2.BEAMING;
        else if (nextPhase == 1)
            bossState2 = bossState2.DARKBALL;
        
        phaseTime = 0;
    }
}
