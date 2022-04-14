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
import states.BossState;

/**
 *
 * @author tolet
 */
public class LastBoss extends Entity{
    
    public int jumpTime = 0;
     
    public boolean addJumpTime = false;
    private boolean scoresCal = false;
    
    private Random random;
    
    private int frameNormal = 0;
    private int frameRunning = 0;
    private int frameRecorering = 0;
    private int frameSpinning = 0;
    
    private int frameDelayFirst = 0;
    private int frameDelayRunning = 0;
    private int frameDelayRecorering = 0;
    private int frameDelaySpinning = 0;
    
    private int frameShining = 0;
    private int frameDelayShining = 0;
    private int frameDelayPowerUp = 0;

    public LastBoss(int x, int y, int width, int height, Id id, Handler handler, int hp) {
        super(x, y, width, height, id, handler);
        this.hp = hp;
        
        bossState = BossState.IDLE;
        
        random = new Random();
    }

    public void render(Graphics g) {
        if (bossState == bossState.SECOND){
            if (0 < frameDelayPowerUp && frameDelayPowerUp < 300)
                g.drawImage(Game.boss[34].getBufferedImage(), x, y, width, height, null);
            else if (300 < frameDelayPowerUp && frameDelayPowerUp < 600)
                g.drawImage(Game.boss[35].getBufferedImage(), x, y, width, height, null);
            else if (600 < frameDelayPowerUp && frameDelayPowerUp < 900){
                g.drawImage(Game.boss[frameShining+35].getBufferedImage(), x, y, width, height, null);
            }else if (900 < frameDelayPowerUp && frameDelayPowerUp < 1200){
                g.drawImage(Game.boss[36].getBufferedImage(), x, y, width, height, null);
            }else if (1200 < frameDelayPowerUp && frameDelayPowerUp < 1500){
                g.drawImage(Game.boss[frameShining+36].getBufferedImage(), x, y, width, height, null);
            }else if (1500 < frameDelayPowerUp && frameDelayPowerUp < 1800)
                g.drawImage(Game.boss[37].getBufferedImage(), x, y, width, height, null);
            else if (1800 < frameDelayPowerUp &&  frameDelayPowerUp < 2100)
                g.drawImage(Game.boss[38].getBufferedImage(), x, y, width, height, null);
            else if (2100 < frameDelayPowerUp && frameDelayPowerUp < 2400)
                g.drawImage(Game.boss[39].getBufferedImage(), x, y, width, height, null);
            else if (2400 < frameDelayPowerUp && frameDelayPowerUp < 3000){
                g.drawImage(Game.boss[40].getBufferedImage(), x, y, width, height, null);
            }else if (3000 < frameDelayPowerUp && frameDelayPowerUp < 3300){
                g.drawImage(Game.boss[41].getBufferedImage(), x, y, width, height, null);
            }else if (3300 < frameDelayPowerUp && frameDelayPowerUp < 3600){
                g.drawImage(Game.boss[42].getBufferedImage(), x, y, width, height, null);
            }else if (3600 < frameDelayPowerUp && frameDelayPowerUp < 3900){
                g.drawImage(Game.boss[43].getBufferedImage(), x, y, width, height, null);
            }else if (3900 < frameDelayPowerUp && frameDelayPowerUp < 4200){
                g.drawImage(Game.boss[44].getBufferedImage(), x, y, width, height, null);
            }else if (4200 < frameDelayPowerUp && frameDelayPowerUp < 4500){
                g.drawImage(Game.boss[45].getBufferedImage(), x, y, width, height, null);
            }else if (4500 < frameDelayPowerUp && frameDelayPowerUp < 4800){
                g.drawImage(Game.boss[46].getBufferedImage(), x, y, width, height, null);
            }else if (4800 < frameDelayPowerUp && frameDelayPowerUp < 5100){
                g.drawImage(Game.boss[frameShining+47].getBufferedImage(), x, y, width, height, null);
            }else if (5100 < frameDelayPowerUp && frameDelayPowerUp < 5400){
                g.drawImage(Game.boss[frameShining+48].getBufferedImage(), x, y, width, height, null);
            }else if (5400 < frameDelayPowerUp && !Game.showDeathScreen){
                Game.bossPhase = 2;
                Game.switchLevel();
            }
                
        
        }else if (facing == 0){
            if (bossState == BossState.IDLE)
                g.drawImage(Game.boss[frameNormal].getBufferedImage(), x, y, width, height, null);
            else if (bossState == bossState.RECOVERING)
                g.drawImage(Game.boss[frameRecorering+8].getBufferedImage(), x, y, width, height, null);
            else if (bossState == bossState.SPINNING)
                g.drawImage(Game.boss[frameSpinning+11].getBufferedImage(), x, y, width, height, null);
            else
                g.drawImage(Game.boss[frameRunning+19].getBufferedImage(), x, y, width, height, null);
        }
        else {
            if (bossState == BossState.IDLE)
                g.drawImage(Game.boss[frameNormal].getBufferedImage(), x, y, width, height, null);
            else if (bossState == bossState.RECOVERING)
                g.drawImage(Game.boss[frameRecorering+31].getBufferedImage(), x, y, width, height, null);
            else if (bossState == bossState.SPINNING)
                g.drawImage(Game.boss[frameSpinning+15].getBufferedImage(), x, y, width, height, null);
            else
                g.drawImage(Game.boss[frameRunning+25].getBufferedImage(), x, y, width, height, null);
        }
    }

    public void tick() {
        x += velX;
        y += velY;
        
        if (bossState == BossState.SECOND)
            frameDelayPowerUp++;
        
        if (2400 <frameDelayPowerUp && frameDelayPowerUp < 2700){
            y = -frameDelayPowerUp+2950;
        }
            
        
        if (velX < 0)
            facing = 0;
        else if (velX > 0)
            facing = 1;
        
        if (bossState != BossState.SECOND){
            frameDelayFirst++;
            if (frameDelayFirst >= 40){
                frameNormal++;
                if (frameNormal > 7){
                    frameNormal = 0;
                }
                frameDelayFirst = 0;
            }
        
            frameDelayRecorering++;
            if (frameDelayRecorering >= 30){
                frameRecorering++;
                if (frameRecorering > 2){
                    frameRecorering = 0;
                }
                frameDelayRecorering = 0;
            }    
        
            frameDelayRunning++;
            if (frameDelayRunning >= 5){
                frameRunning++;
                if (frameRunning > 4){
                    frameRunning = 0;
                }
                frameDelayRunning = 0;
            }
        
            frameDelaySpinning++;
            if (frameDelaySpinning >= 5){
                frameSpinning++;
                if (frameSpinning > 3){
                    frameSpinning = 0;
                }
                frameDelaySpinning = 0;
            }
        }
        
        
        frameDelayShining++;
        if (frameDelayShining >= 2){
            frameShining++;
            if (frameShining > 1){
                frameShining = 0;
            }
            frameDelayShining = 0;
        }    
        
        
        if (bossState != BossState.SECOND)
            phaseTime++;
        
        if ((phaseTime >= 300 && bossState == BossState.IDLE ) || (bossState != BossState.SECOND && phaseTime >= 600 && bossState != BossState.SPINNING)){
            chooseState();                       
        }
        
        if (bossState == BossState.RECOVERING && phaseTime >= 180){
            bossState = bossState.SPINNING;
            phaseTime = 0;
        }
        
        if (phaseTime >= 360 && bossState == BossState.SPINNING){
            phaseTime = 0;
            bossState = BossState.IDLE;
        }
        
        if (bossState == BossState.IDLE || bossState == BossState.RECOVERING){
            setVelX(0);
            setVelY(0);
        }
        
        if (bossState == BossState.JUMPING || bossState == BossState.RUNNING)
                attackable = true;
        else attackable = false;
        
        if (bossState != BossState.JUMPING){
            addJumpTime = false;
            jumpTime = 0;
        }
        
        if (addJumpTime){
            jumpTime++;
            if (jumpTime >= 30){
                addJumpTime = false;
                jumpTime = 0;
            }
            
            if (!jumping && !falling && frameDelayPowerUp < 2400){
                jumping = true;
                gravity = 8.0;
            }
        }
        
        for(int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
            if (t.isSolid()){
                if(getBoundsTop().intersects(t.getBounds())){
                    setVelY(0);
                    if(jumping){
                        jumping = false;
                        falling = true;
                        gravity = 0.8;
                    }                    
                }            
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling) {
                        falling = false;  
                        addJumpTime = true;
                    }                                       
                }
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(0);
                    if (bossState == BossState.RUNNING)
                        setVelX(4);
                    x = t.getX()+ t.width;
                }
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(0);
                    if (bossState == BossState.RUNNING)
                        setVelX(-4);
                    x = t.getX()- t.width;                            
                }
            }
        }
        
        for (int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if (e.getId() == Id.player){
                if (bossState == BossState.JUMPING){
                    if (jumping || falling){
                        if (getX() >= e.getX() -4 && getX() <= e.getX() +4)
                            setVelX(0);
                        else if (e.getX() < getX())
                            setVelX(-3);
                        else if (e.getX() > getX())
                            setVelX(3);
                    }else setVelX(0);   
                }else if (bossState == BossState.SPINNING){
                    if (e.getX() < getX())
                        setVelX(-3);
                    else if (e.getX() > getX())
                        setVelX(3);
                }
            }
        }
        
        if(jumping && frameDelayPowerUp < 2400){
            gravity-= 0.15;
            setVelY((int)-gravity);
            if(gravity<= 0.6){
                jumping = false;
                falling = true;
            }
        }
        
        if(falling && frameDelayPowerUp < 2400){
            gravity += 0.15;
            setVelY((int)gravity);
        }      
        
        if (Game.bossPhase == 1)
            secondPhaseTime();
    }
    
    public void chooseState(){
        int nextPhase = random.nextInt(2);
        if (nextPhase == 0){
            bossState = BossState.RUNNING;
            int dir = random.nextInt(2);
            if (dir == 0)
                setVelX(-4);
            else
                setVelX(4);
        }else if (nextPhase == 1){
            bossState = BossState.JUMPING;
            
            jumping = true;            
            gravity = 8.0;
        }
            
        phaseTime = 0;
    }
    
    public void secondPhaseTime(){
        bossState = BossState.SECOND;
        setVelX(0);
        if (!scoresCal){
            Game.scores = Game.scores + 5000;
            scoresCal = true;
        }
    }
    
    
}
