/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import static mario.Game.coin;
import static mario.Game.coins;
import static mario.Game.player;
import static mario.Game.theme;
import mario.entity.Entity;
import mario.entity.mob.Goomba;
import mario.entity.mob.Player;
import mario.entity.powerup.Mushroom;
import mario.entity.Coin;
import mario.entity.mob.Koopa;
import mario.entity.mob.LastBoss;
import mario.entity.mob.LastBoss2;
import mario.entity.mob.LastBoss3;
import mario.entity.powerup.PowerStar;
import mario.tile.Flag;
import mario.tile.Pipe;
import mario.tile.PowerUpBlock;
import mario.tile.Tile;
import mario.tile.Wall;

/**
 *
 * @author tolet
 */
public class Handler {
    
    public CopyOnWriteArrayList<Entity> entity = new CopyOnWriteArrayList<Entity>();
    public LinkedList<Tile> tile = new LinkedList<Tile>();
     
    public void render(Graphics g){
        
        for(int i = 0; i < entity.size(); i++){
            Entity e = entity.get(i);
            if (Game.getVisibleArea() != null && e.getBounds().intersects(Game.getVisibleArea()) && e.getId() != Id.particle)
                e.render(g);
        }
        for(int i = 0; i < tile.size(); i++){
            Tile t = tile.get(i);
            if (Game.getVisibleArea() != null && t.getBounds().intersects(Game.getVisibleArea()))
                t.render(g);
        }
        for(int i = 0; i < entity.size(); i++){
            Entity e = entity.get(i);
            if (Game.getVisibleArea() != null && e.getBounds().intersects(Game.getVisibleArea())&& e.getId() == Id.particle)
                e.render(g);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD, 40));	
        
        g.drawImage(coin[0].getBufferedImage(), Game.getVisibleArea().x + 280,10, 50, 50, null);       
        g.drawString("x" + Game.coins,Game.getVisibleArea().x + 330,54);
        
        g.drawImage(player[4].getBufferedImage(), Game.getVisibleArea().x + 1200, 10, 50, 50, null);       
        g.drawString("x" + Game.lives,Game.getVisibleArea().x + 1260,54);
        g.drawString("Score x" + Game.scores,Game.getVisibleArea().x + 630,54);
        
        if (Game.bossPhase > 1){
            g.drawImage(Game.boss[50].getBufferedImage(), Game.getVisibleArea().x + 1200, 74, 50, 50, null);       
            g.drawString("x" + (10 - Game.boss2hitted),Game.getVisibleArea().x + 1260,118);
        }
        
    }
    
    public void tick(){
        for(int i = 0; i < entity.size(); i++){
            Entity e = entity.get(i);
            e.tick();
        }
        for(int i = 0; i < tile.size(); i++){
            Tile t = tile.get(i);
            if (Game.getVisibleArea() != null && t.getBounds().intersects(Game.getVisibleArea()))
                t.tick();
        }
    }
    
    public void addEntity(Entity en){
        entity.add(en);        
    }
    
    public void removeEntity(Entity en){
        entity.remove(en);
    }
    public void addTile(Tile ti){
        tile.add(ti);
        
    }
    
    public void removeTile(Tile ti){
        tile.remove(ti);
    }
    
    public void createLevel(BufferedImage level){
        int width = level.getWidth();
        int height = level.getHeight();

        for (int y = 0; y < height; y++){
            for( int x = 0; x < width; x++){
                int pixel = level.getRGB(x,y);
                
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 0 && green == 0 && blue == 0)
                    addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this,0));
                if (red == 0 && green == 162 && blue == 232)
                    addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this,1));
                if (red == 163 && green == 73 && blue == 164)
                    addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this,2));
                if (red == 255 && green == 174 && blue == 201)
                    addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this,3));
                if (red == 0 && green == 0 && blue == 255)
                    addEntity(new Player(x*64,y*64,48,48,Id.player,this));
                if (red == 255 && green == 0 && blue == 0)
                    addEntity(new Goomba(x*64,y*64,64,64,Id.goomba,this));
                if (red == 255 && green == 255 && blue == 0)
                    addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.flower,1));
                if (red == 255 && green == 255 && blue == 100)
                    addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.mushroom,0));
                if (red == 255 && green == 255 && blue == 200)
                    addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.lifeMushroom,1));
                if (red == 255 && green == 119 && blue == 0)
                    addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.star,1));
                if (red == 0 && (green >= 123 && green <= 128 )&& blue == 0)
                    addTile(new Pipe(x*64,y*64,64,64,true,Id.pipe,this,128 - green,true));
                if (red == 255 && green == 250 && blue == 0)
                    addEntity(new Coin(x*64,y*68,40,40,Id.coin,this));
                if (red == 255 &&green == 0 && blue == 255)
                    addEntity(new LastBoss(x*64,y*64,64,64,Id.towerBoss,this,4));                
                if (red == 0 && green == 255 && blue == 0)
                    addTile(new Flag(x*64,y*64,64,64*5,true,Id.flag,this));
                if (red == 150 && green == 150 && blue == 0)
                    addEntity(new Koopa(x*64,y*64,64,64,Id.koopa,this));
                if (red == 189 && green == 51 && blue == 208)
                    addEntity(new LastBoss2(x*64,y*64,64,64,Id.lastboss2,this,0));
                if (red == 150 && green == 38 && blue == 166)
                    addEntity(new LastBoss2(x*64,y*64,64,64,Id.lastboss2,this,1));
                if (red == 150 && green == 150 && blue == 150)
                    addEntity(new LastBoss3(x*64,y*64,64,64,Id.lastboss3,this));
            }
        }
        
        Game.deathY = Game.getDeathY();
    }
    
    public void clearLevel(){
        entity.clear();
        tile.clear();
    }
    
}
