/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import mario.Game;
import static mario.Game.theme;
import mario.Handler;
import mario.Id;
import mario.graphic.gui.LeaderBoard;
import states.BossState;
import states.BossState2;
import states.BossState3;
import states.KoopaState;
import states.PlayerState;

/**
 *
 * @author PHAM TIEN DAT
 */
public abstract class Entity {
    public int x, y;
    public int width, height;
    public int facing = 0; // 0 = left; 1 = right
    public int hp; // health point
    public int jumpcount = 0;
    
    public boolean jumping = false, falling = true;
    public boolean goingDownPipe = false;
    public boolean attackable = false;
    public  boolean nextWall = false;
    
    public int phaseTime;    
    public int type;
    
    public int velX, velY;
    
    public Id id;
    public BossState bossState;
    public BossState2 bossState2;
    public BossState3 bossState3;
    public KoopaState koopaState;
    public PlayerState state;
    public double gravity = 0.0;
    
    public Handler handler;
    
    public Entity(int x, int y, int width, int height, Id id, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }
    
    public abstract void render(Graphics g);    
    
    public abstract void tick();
    
    public void die(){
        handler.removeEntity(this);
        
        
        if (getId() == Id.player){
            Game.boss2hitted = 0;
            if (Game.bossPhase == 0)
                theme.stop();
            else if (Game.bossPhase == 1){
                theme.stop();
                Game.transtheme.stop();
            }
                
            else if (Game.bossPhase == 2){
                theme.stop();
                Game.bosstheme.stop();
            }
                
            else if (Game.bossPhase == 3)
                Game.truebosstheme.stop();
            Game.lives--;
            Game.showDeathScreen = true;
            Game.coins = Game.lastCoins;
            Game.scores = Game.lastScores;
            Game.boss2hitted = 0;
            if (Game.lives == 0){
                Game.gameOver = true;
                Game.lose.play();
                LeaderBoard top = new LeaderBoard();
                top.setScores();
                top.setVisible(true);
            }else{
                Game.die.play();
            }
            
            
        }
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Id getId() {
        return id;
    }
    


    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
    
    public int getType(){
        return type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
    
    public Rectangle getBounds(){
        return new Rectangle(getX(),getY(),width,height);
    }
    
    public Rectangle getBoundsTop(){
        return new Rectangle(getX()+ 10,getY(),width- 20,5);
    }
    
    public Rectangle getBoundsBottom(){
        return new Rectangle(getX()+ 10,getY()+ getHeight()-5, getWidth()- 20,7);
    }
    
    public Rectangle getBoundsLeft(){
        return new Rectangle(getX(),getY()+ 10,5,getHeight() - 20);
    }
    
    public Rectangle getBoundsRight(){
        return new Rectangle(getX()+ getWidth()- 5,getY()+ 10,5,getHeight() - 20);
    }
}
