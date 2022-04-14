/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;
import mario.Game;
import static mario.Game.coinsound;
import static mario.Game.oneup;
import static mario.Game.power_up;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.entity.Particle;
import mario.input.KeyInput;
import mario.tile.Tile;
import mario.tile.Trail;
import states.BossState;
import states.KoopaState;
import states.PlayerState;

/**
 *
 * @author tolet
 */
public class Player extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    private int particleDelay = 0;
    private int restoreTime;
    
    public int pixelsTravelled = 0;
    public int invincibilityTime = 0;
    
    private Random random;
    
    private boolean invincible = false;
    private boolean restoring;
    
    public Player(int x, int y, int width, int height,  Id id, Handler handler){
        super(x, y, width, height,  id, handler);
        
        state = PlayerState.SMALL;
        
        random = new Random();
    }
    
    public void render(Graphics g) {
        if (state == PlayerState.FIRE){
            if ( facing == 0 && !KeyInput.stand){
                g.drawImage(Game.firePlayer[frame].getBufferedImage(), x, y, width, height, null);
            }else if ( facing == 1 && !KeyInput.stand){
                g.drawImage(Game.firePlayer[frame+4].getBufferedImage(), x, y, width, height, null);
            }
            if ( (KeyInput.stand == true) && facing == 0){
                g.drawImage(Game.firePlayer[0].getBufferedImage(), x, y, width, height, null);
            }else if ( (KeyInput.stand) && facing == 1 ){
                g.drawImage(Game.firePlayer[4].getBufferedImage(), x, y, width, height, null);
            }
            
        }else{
            if ( facing == 0 && !KeyInput.stand){
                g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
            }else if ( facing == 1 && !KeyInput.stand){
                g.drawImage(Game.player[frame+4].getBufferedImage(), x, y, width, height, null);
            }
            if ( (KeyInput.stand == true) && facing == 0){
                g.drawImage(Game.player[0].getBufferedImage(), x, y, width, height, null);
            }else if ( (KeyInput.stand) && facing == 1 ){
                g.drawImage(Game.player[4].getBufferedImage(), x, y, width, height, null);
            }
        }
        
    }

    public void tick() {
        x+=velX;
        y+=velY;
        
        if (getY() > Game.deathY)
            die();
                
        if (invincible){
            if (state == PlayerState.FIRE){
                if (facing == 0)
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.firePlayer[frame].getBufferedImage()));
                else if (facing == 1)
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.firePlayer[frame+4].getBufferedImage()));
            
                if ( (KeyInput.stand == true) && facing == 0){
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.firePlayer[0].getBufferedImage()));
                }else if ( (KeyInput.stand) && facing == 1 ){
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.firePlayer[4].getBufferedImage()));
                }
            }else{
                if (facing == 0)
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[frame].getBufferedImage()));
                else if (facing == 1)
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[frame+4].getBufferedImage()));
            
                if ( (KeyInput.stand == true) && facing == 0){
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[0].getBufferedImage()));
                }else if ( (KeyInput.stand) && facing == 1 ){
                    handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, Game.player[4].getBufferedImage()));
            }
            }
            
            particleDelay++;
            if (particleDelay >= 3){
                handler.addEntity(new Particle(getX() + random.nextInt(getWidth()), getY() + random.nextInt(getHeight()), 10, 10, Id.particle, handler));
                
                particleDelay = 0;
            }
            
            invincibilityTime++;
            if (invincibilityTime >= 600){
                invincible = false;
                invincibilityTime = 0;
            }
            
            if (velX == 5)
                setVelX(8);
            else if (velX == -5)
                setVelX(-8);   
        }else{
            if (velX == 8)
                setVelX(5);
            else if (velX == -8)
                setVelX(-5); 
        }
        
        if (restoring){
            restoreTime++;
            
            if (restoreTime >=90){
                restoring = false; 
                restoreTime = 0;
            }
        }
        
        for(int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
                if (getBounds().intersects(t.getBounds())){
                    if (t.getId() == Id.flag){
                        Game.switchLevel();
                    }
                }
                if (t.isSolid() && !goingDownPipe){
                    if(getBoundsTop().intersects(t.getBounds())){
                    setVelY(0);
                    if(jumping && !goingDownPipe){
                        jumping = false;
                        falling = true;
                        gravity = 0.8;
                    }
                    if (t.getId() == Id.powerUp)
                        if(getBoundsTop().intersects(t.getBounds()))
                            t.activated = true;
                    
                }
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling){                        
                        falling = false;
                    }
                }else{
                    if(!falling && !jumping){
                        gravity = 0.8;
                        falling = true;
                    } 
                }
                if(getBoundsLeft().intersects(t.getBounds())){
                    if (state == PlayerState.SMALL){ 
                        x = t.getX()+ width*3/2 -8;
                    }
                    else {
                        x = t.getX()+ width;
                    } 
                }else
                    nextWall = false;
                if(getBoundsRight().intersects(t.getBounds())){
                    x = t.getX()- width;
                    nextWall = true;
                }else
                    nextWall = false;
                
            }   
        }
                
        for (int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            
            if (e.getId() == Id.mushroom){
                switch (e.getType()){
                    case 0:
                        if (getBounds().intersects(e.getBounds())){
                            
                            Game.scores = Game.scores + 1000;
                            if (state == PlayerState.SMALL){
                                int tpX = getX();
                                int tpY = getY();
                                width += (width/3);
                                height += (height/3);
                                setX( tpX - width);
                                setY( tpY - height);
                                state = PlayerState.BIG; 
                                
                            }    
                            power_up.play();
                            e.die();
                        }
                        
                        break;
                    case 1:
                        if (getBounds().intersects(e.getBounds())){
                            Game.scores = Game.scores + 1000;
                            Game.lives++;
                            oneup.play();
                            e.die();
                        }
                        break;
                }
                
            }else if (e.getId() == Id.goomba || e.getId() == Id.towerBoss || e.getId() == Id.plant){
                if (invincible && getBounds().intersects(e.getBounds())){
                    e.die();
                    Game.scores = Game.scores + 200;
                }else{
                    if (getBoundsBottom().intersects(e.getBoundsTop())){
                        if (e.getId() != Id.towerBoss){
                            e.die();                            
                            Game.damage.play();
                            Game.scores = Game.scores + 200;
                        }
                            
                        else if (e.attackable ){
                            if (e.hp > 2){
                                e.hp--;
                                e.falling = true;
                                e.gravity = 3.0;
                                e.attackable = false;
                                jumping = true;
                                falling = false;
                                gravity = 5.0;
                                e.bossState = BossState.RECOVERING;
                                e.phaseTime = 0;
                            }else{
                                e.falling = true;
                                e.gravity = 1.0;
                                e.attackable = false;
                                jumping = true;
                                falling = false;
                                gravity = 5.0;
                                Game.bossPhase++;
                            }
                        }
                    }else if (getBounds().intersects(e.getBounds()) && e.bossState != BossState.SECOND){                        
                        takeDamage();
                    }
                }
                
                
            }else if (getBounds().intersects(e.getBounds()) && e.getId() == Id.coin){
                    Game.coins++;
                    Game.scores = Game.scores + 200;
                    coinsound.play();
                    e.die();
            }else if (e.getId() == Id.koopa){
                if (invincible && getBounds().intersects(e.getBounds())){
                    e.die();
                    Game.scores = Game.scores + 200;
                }else{
                    if (e.koopaState == KoopaState.WALKING){
                        if (getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SHELL;
                        
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }else if (getBounds().intersects(e.getBounds())){
                            takeDamage();
                        }
                    
                    }else if (e.koopaState == KoopaState.SHELL){
                        if (getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SPINNING;
                        
                            int dir = random.nextInt(2);
        
                            switch(dir){
                                case 0:
                                    e.setVelX(-10);
                                    break;
                                case 1:
                                    e.setVelX(10);
                                    break;             
                            }
                        
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }
                    
                        if (getBoundsLeft().intersects(e.getBoundsRight())){
                            e.setVelX(-10);
                            e.koopaState = KoopaState.SPINNING;
                        }
                    
                        if (getBoundsRight().intersects(e.getBoundsLeft())){
                            e.setVelX(10);
                            e.koopaState = KoopaState.SPINNING;
                        }
                    
                    }else if (e.koopaState == KoopaState.SPINNING){
                        if (getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SHELL;
                        
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }else if (getBounds().intersects(e.getBounds())){
                            takeDamage();
                        }
                    }
                }
                
            }else if (e.getId() == Id.star && getBounds().intersects(e.getBounds())){
                invincible = true;
                Game.scores = Game.scores + 1000;
                e.die();
            }else if (e.getId() == Id.flower && getBounds().intersects(e.getBounds())){
                if (state == PlayerState.SMALL){
                    int tpX = getX();
                    int tpY = getY();
                    width += (width/3);
                    height += (height/3);
                    setX( tpX - width);
                    setY( tpY - height);
                }
                Game.scores = Game.scores + 1000;
                state = PlayerState.FIRE;
                power_up.play();
                e.die();
            }else if (e.getId() == Id.beam && getBounds().intersects(e.getBounds())){
                //die();
            }
        }
        
        if(jumping){
            gravity-= 0.15;
            setVelY((int)-gravity);
            if(gravity<= 0.6){
                jumping = false;
                jumpcount = 0;
                falling = true;
            }
        }
        
        if(falling){
            gravity += 0.15;
            setVelY((int)gravity);
        }
        if(velX != 0){
            frameDelay++;
            if (frameDelay >= 3){
                frame++;
                if (frame >= 4){
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        
        if (goingDownPipe){
            for (int i = 0; i < Game.handler.tile.size(); i++){
                Tile t = Game.handler.tile.get(i);
                if (t.getId() == Id.pipe){
                    if (getBounds().intersects(t.getBounds())){
                        switch(t.facing){
                        case 0: //going up pipe
                            setVelY(-5);
                            setVelX(0);
                            pixelsTravelled += -velY;
                            break;
                        case 2: //going down pipe
                            setVelY(5);
                            setVelX(0);
                            pixelsTravelled += velY;
                            break;
                        }
                        if (pixelsTravelled > t.height + height)
                            goingDownPipe = false; 
                            pixelsTravelled = 0;
                    }
                    
                }
            }
        }
        
    }
    
    public void takeDamage(){
        if (restoring)
            return;
        if (state == PlayerState.SMALL){
            die();
            return;
        }else if(state == PlayerState.BIG){
            width -= (width/4);
            height -= (height/4);
            x += width/4;
            y += height/4;
            
            state = PlayerState.SMALL;
            Game.damage.play();
            
            restoring = true;
            restoreTime = 0;
            return;
        }else if (state == PlayerState.FIRE){
            state = PlayerState.BIG;
            Game.damage.play();
            
            restoring = true;
            restoreTime = 0;
            return;
        }
    }
    
}
