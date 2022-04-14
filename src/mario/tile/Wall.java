/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.tile;

import java.awt.Color;
import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;

/**
 *
 * @author tolet
 */
public class Wall extends Tile{

    private int type;
    
    
    public Wall(int i, int i1, int i2, int i3, boolean bln, Id id, Handler hndlr, int type) {
        super(i, i1, i2, i3, bln, id, hndlr);
        
        this.type = type;
    }

    public void render(Graphics g) {
        if (type == 0)
            g.drawImage(Game.wall[0].getBufferedImage(), x, y, width, height, null);
        else if (type == 1)
            g.drawImage(Game.wall[1].getBufferedImage(), x, y, width, height, null);
        else if (type == 2)
            g.drawImage(Game.wall[2].getBufferedImage(), x, y, width, height, null);
        else
            g.drawImage(Game.wall[3].getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
    }
    
}
