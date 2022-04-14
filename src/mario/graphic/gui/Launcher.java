/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.graphic.gui;

import java.awt.Color;
import java.awt.Graphics;
import mario.Game;

/**
 *
 * @author tolet
 */
public class Launcher {
    
    public Button[] buttons;
    
    public Launcher(){
        buttons = new Button[4];
        
        buttons[0] = new Button(200,100,Game.getFrameWidth()/2+200,100,"Start Game");
        buttons[1] = new Button(200,250,Game.getFrameWidth()/2+200,100,"Leaderboard");
        buttons[2] = new Button(200,400,Game.getFrameWidth()/2+200,100,"Control");
        buttons[3] = new Button(200,550,Game.getFrameWidth()/2+200,100,"Exit Game");
    }
    
    public void render(Graphics g){
        g.drawImage(Game.launcherBG, 0, 0, 1200, 800, null);
        for (int i = 0; i < 4; i++){
            buttons[i].render(g);
        }
    }

}