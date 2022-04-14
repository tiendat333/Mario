/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario;

/**
 *
 * @author PHAM TIEN DAT
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mario.entity.Entity;
import mario.entity.mob.LastBoss;
import mario.entity.mob.Player;
import mario.graphic.Sprite;
import mario.graphic.SpriteSheet;
import mario.graphic.gui.LeaderBoard;
import mario.graphic.gui.Launcher;
import mario.input.KeyInput;
import mario.input.MouseInput;
import mario.tile.Tile;
import mario.tile.Wall;

public class Game extends Canvas implements Runnable {

    public LinkedList<Tile> tile = new LinkedList<Tile>();    
    
    public static final int WIDTH = 270;
    public static final int HEIGHT = 770;
    public static final int SCALE = 4;
    private static final String TITLE = "Mario";
	
    private Thread thread;
    private boolean running = false;
	
    private static BufferedImage[] levels;
    
    public static BufferedImage launcherBG;
    private static BufferedImage background;
    private static BufferedImage background2;
    private static BufferedImage background3;
    private static BufferedImage background4;
    private static BufferedImage background5;
    
    private static int playerX, playerY;
    private static int level = 0;

    public static int coins = 0;
    public static int lastCoins = 0;
    public static int scores = 0;
    public static int lastScores = 0;
    public static int lives = 3;
    public static int deathScreenTime = 0;
    public static int deathY = 0;
    public static int bossPhase = 0;
    public static int boss2hitted = 0;
    
    public static boolean showDeathScreen = true;
    public static boolean gameOver = false;
    public static boolean playing = false;
    public static boolean songplayed = false, bosssongplayed = false, truebossongplayed = false;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;
    
    
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
    public static Sprite pipe;
    
     
    public static Sprite mushroom;     
    public static Sprite lifeMushroom;    
    public static Sprite star;
    public static Sprite fireBall;
    public static Sprite flower;
    public static Sprite darkBall;
    
    
    public static Sprite[] player;
    public static Sprite[] firePlayer;
    public static Sprite[] goomba;
    public static Sprite[] coin;
    public static Sprite[] plant;
    public static Sprite[] koopa;
    public static Sprite[] flag;
    public static Sprite[] particle;
    public static Sprite[] boss;
    public static Sprite[] leftBoss2;
    public static Sprite[] rightBoss2;
    public static Sprite[] beam;
    public static Sprite[] ultimatebeam;
    public static Sprite[] bossFinal;
    public static Sprite[] wall;     
    
    public static Sound damage, die, jump, power, theme, win, oneup, coinsound, power_up, fireball, 
            level_clear, transtheme, bosstheme, lose, beamsound, darkfireballsound, truebosstheme;
    
    public Game() {
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }
    
    private void init(){
        handler = new Handler();
        sheet = new SpriteSheet("/graphic/spritesheet.png");
        cam = new Camera();
        launcher = new Launcher();
        mouse = new MouseInput();
        
        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        powerUp = new Sprite(sheet,5,1);
        usedPowerUp = new Sprite(sheet,6,1);
        
        mushroom = new Sprite(sheet,2,1);
        lifeMushroom = new Sprite(sheet,8,1);        
        star = new Sprite(sheet,12,1);
        fireBall = new Sprite(sheet,9,2);
        flower = new Sprite(sheet,10,2);
        darkBall = new Sprite(sheet,11,2);
        
        wall = new Sprite[4];
        player = new Sprite[8];
        firePlayer = new Sprite[8];
        goomba = new Sprite[2];
        flag = new Sprite[3];
        particle = new Sprite[5];
        coin = new Sprite[2];
        plant = new Sprite[2];
        koopa = new Sprite[5];
        boss = new Sprite[51];
        leftBoss2 = new Sprite[17];
        rightBoss2 = new Sprite[17];
        beam = new Sprite[9];
        ultimatebeam = new Sprite[8];
        bossFinal = new Sprite[22];
        
        levels = new BufferedImage[5];
        
        pipe = new Sprite(sheet,7,1);
        
        wall[0] = new Sprite(sheet,1,1);
        
        for(int i = 0; i < wall.length - 1; i++){
            wall[i+1] = new Sprite(sheet,i+10,11);
        }
        
        wall[3] = new Sprite(sheet,12,11);
        
        for(int i = 0; i < player.length; i++){
            player[i] = new Sprite(sheet,i+1,2);
            firePlayer[i] = new Sprite(sheet,i+1,3);
        }
        
        for(int i = 0; i < goomba.length; i++){
            goomba[i] = new Sprite(sheet,i+3,1);
        }
        
        for (int i = 0; i < flag.length; i++){
            flag[i] = new Sprite(sheet,i+9,1);
        }
        
        for (int i = 0; i < particle.length; i++){
            particle[i] = new Sprite(sheet,i+1,4);
        }
        
        for (int i = 0; i < coin.length; i++){
            coin[i] = new Sprite(sheet,i+13,1);
        }        
            
        for (int i = 0; i < plant.length; i++){
            plant[i] = new Sprite(sheet,i+15,1);
        }      
        
        for (int i = 0; i < koopa.length; i++){
            koopa[i] = new Sprite(sheet,i+12,2);
        }      
        
        for (int i = 0; i < 8; i++){
            boss[i] = new Sprite(sheet,i+9,3);
        }      
        
        for (int i = 0; i < 11; i++){
            boss[i+8] = new Sprite(sheet,i+6,4);
        }      
        
        for (int i = 0; i < 15; i++){
            boss[i+19] = new Sprite(sheet,i+1,5);
        }      
        
        for (int i = 0; i < 6; i++){
            boss[i+34] = new Sprite(sheet,i+1,6);
        }
        
        for (int i = 0; i < 10; i++){
            boss[i+40] = new Sprite(sheet,i+7,6);
        }
        
        boss[50] = new Sprite(sheet,16,5);
        
        for(int i = 0; i < 10; i++){
            rightBoss2[i] = new Sprite(sheet,i+1,7);
            leftBoss2[i] = new Sprite(sheet,i+1,8);
            
        }
        
        for (int i = 0; i < 7; i++){
            rightBoss2[i+10] = new Sprite(sheet,i+1,9);
            leftBoss2[i+10] = new Sprite(sheet,i+1,10);
        }
        
        for (int i = 0; i < 3; i++){
            beam[i] = new Sprite(sheet,i+11,7);
        }
        
        for (int i = 0; i < 3; i++){
            beam[i+3] = new Sprite(sheet,i+8,9);
            beam[i+6] = new Sprite(sheet,i+8,10);
        }
        
        
        for (int i = 0; i < 3; i++){
            ultimatebeam[i] = new Sprite(sheet,i+14,7);
        }
        
        for (int i = 0; i < 5; i++){
            ultimatebeam[i+3] = new Sprite(sheet,i+11,8);
        }
        
        bossFinal[0] = new Sprite(sheet,16,8);
        
        for (int i = 0; i < 6; i++){
            bossFinal[i+1] = new Sprite(sheet,i+11,9);
            bossFinal[i+7] = new Sprite(sheet,i+11,10);
        }
        
        for (int i = 0; i < 9; i++){
            bossFinal[i+13] = new Sprite(sheet,i+1,11);
        }
        
        try {
            
            levels[0] = ImageIO.read(getClass().getResource("/map/level1.png"));
            levels[1] = ImageIO.read(getClass().getResource("/map/level2.png"));
            levels[2] = ImageIO.read(getClass().getResource("/map/boss1.png"));
            levels[3] = ImageIO.read(getClass().getResource("/map/boss2.png"));
            levels[4] = ImageIO.read(getClass().getResource("/map/boss3.png"));
            launcherBG = ImageIO.read(getClass().getResource("/graphic/background.png"));
            background = ImageIO.read(getClass().getResource("/graphic/background.png"));
            background2 = ImageIO.read(getClass().getResource("/graphic/black.png"));
            background3 = ImageIO.read(getClass().getResource("/graphic/blackhole.png"));
            background4 = ImageIO.read(getClass().getResource("/graphic/beach.png"));
            background5 = ImageIO.read(getClass().getResource("/graphic/blackhole2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jump = new Sound("/audio/jump.wav");
        die = new Sound("/audio/die.wav");
        damage = new Sound("/audio/damage.wav");
        power = new Sound("/audio/power.wav");
        win = new Sound("/audio/win.wav");
        oneup = new Sound("/audio/1up.wav");
        power_up = new Sound("/audio/power_up.wav");
        coinsound = new Sound("/audio/coin.wav");
        fireball = new Sound("/audio/fireball.wav");
        level_clear = new Sound("/audio/level_clear.wav");
        lose = new Sound("/audio/lose.wav");
        beamsound = new Sound("/audio/beam.wav");
        darkfireballsound = new Sound("/audio/darkfire.wav");
        
        theme = new Sound("/music/Theme.wav");
        transtheme = new Sound ("/music/Ultra_Instinct.wav");
        truebosstheme = new Sound ("/music/Hopes_and_Dreams.wav");
        bosstheme = new Sound("/music/Solaris_Phase_2.wav");
    }
	private synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
                init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		while(running) {
                    try {
                        long now = System.nanoTime();
                        delta+=(now-lastTime)/ns;
                        lastTime = now;
                        while(delta>=1) {
                            tick();
                            ticks++;
                            delta--;
                        }
                        render();
                        frames++;
                        if(System.currentTimeMillis()-timer>1000) {
                            timer+=1000;
                            System.out.println(frames + " Frames Per Second " + ticks + " Ticks Per Second");
                            frames = 0;
                            ticks = 0;
                            
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
		}
		stop();
	}
	
	public void render() throws IOException {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
                
                
                
                if (!showDeathScreen){
                    if (bossPhase == 0 && level == 0)
                        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                    else if (bossPhase == 0 && level == 2)
                        g.drawImage(background4, 0, 0, getWidth(), getHeight(), null);
                    else if (bossPhase == 1 ||  level == 1)
                        g.drawImage(background2, 0, 0, getWidth(), getHeight(), null);
                    else if (bossPhase == 2)
                        g.drawImage(background3, 0, 0, getWidth(), getHeight(), null);
                    else if (bossPhase == 3)
                        g.drawImage(background5, 0, 0, getWidth(), getHeight(), null);
                }else{
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    
                    if (!gameOver){
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Courier", Font.BOLD, 50));	
                        g.drawImage(player[4].getBufferedImage(), 450 ,300 ,100 ,100, null);
                        g.drawString("x" + lives, 550, 400);
                        g.drawImage(coin[0].getBufferedImage(), 10,10, 50, 50, null);       
                        g.drawString("x" + Game.coins,54,54);
        
                        g.drawImage(player[4].getBufferedImage(), 930, 10, 50, 50, null);       
                        g.drawString("x" + Game.lives,984,54);
                        g.drawString("Score x" + Game.scores,384,54);
        
                    }else {
                        g.setColor(Color.RED);
                        g.setFont(new Font("Courier", Font.BOLD, 100));	
                        g.drawString("YOU DIED", 300, 400);
                    }
                }
                
                if (bossPhase == 1){
                    if (!songplayed){
                        theme.close();
                        transtheme.play();
                        songplayed = true;
                    }
                    
                }
                
                if (bossPhase == 2){
                    if (!bosssongplayed){
                        transtheme.close();
                        bosstheme.play();
                        bosssongplayed = true;
                    }
                }
                
                if (bossPhase == 3){
                    if (!truebossongplayed){
                        bosstheme.close();
                        truebosstheme.play();
                        truebossongplayed = true;
                    }
                    
                }
                
                
                if (playing)
                    g.translate(cam.getX(), cam.getY());
		if (!showDeathScreen && playing)
                    handler.render(g); 
                else if (!playing)
                    launcher.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void tick() {
            if (playing)
                handler.tick();
            
            for( Entity e:handler.entity){
                if ( e.getId() == Id.player){
                    //if (!e.goingDownPipe)
                        cam.tick(e);
                }
            }
            
            if (showDeathScreen && !gameOver && playing) 
                deathScreenTime++;
            if (deathScreenTime >= 180){
                if (!gameOver){
                    showDeathScreen = false;
                    deathScreenTime = 0;
                    handler.clearLevel();
                    handler.createLevel(levels[level]);
                    if (bossPhase == 0)
                        theme.play();
                    else if (bossPhase == 2)
                        bosstheme.play();
                    else if (bossPhase == 3)
                        truebosstheme.play();
                }else if (gameOver){
                    showDeathScreen = false;
                    deathScreenTime = 0;
                    playing = false;
                    gameOver = false;
                }
                
                
            }
	}
	
	public static int getFrameWidth(){
            return WIDTH*SCALE;
        }
        
        public static int getFrameHeight(){
            return HEIGHT*SCALE;
        }
        
        public static void switchLevel(){
            Game.level++;
            
            handler.clearLevel();
            handler.createLevel(levels[level]);
            lastCoins = coins;
            lastScores = scores;
            win.play();
        }
        
	
        public static Rectangle getVisibleArea(){
            for (int i = 0; i < handler.entity.size(); i++){
                Entity e = handler.entity.get(i);
                if (e.getId() == Id.player){
                    if (!e.goingDownPipe){
                        playerX = e.getX();
                        playerY = e.getY();
                    
                        return new Rectangle(playerX-(getFrameWidth()/2-5),playerY -(getFrameHeight()/2-5), getFrameWidth() + 300, getFrameHeight() + 10);
                
                    }
                }
                    
            }
            
            return new Rectangle(playerX-(getFrameWidth()/2-5),playerY -(getFrameHeight()/2-5), getFrameWidth() + 300, getFrameHeight() + 10);
        }
	
        public static int getDeathY(){
            LinkedList<Tile> tempList = handler.tile;
            
            Comparator<Tile> tileSorter = new Comparator<Tile>(){
                public int compare(Tile t1, Tile t2){
                    if (t1.getY() > t2.getY())
                        return -1;
                    if (t1.getY() < t2.getY())
                        return 1;
                    return 0;
                }
            };
            
            Collections.sort(tempList, tileSorter);
            return tempList.getFirst().getY() + tempList.getFirst().getHeight();
        }
        
        public static void openControl(){
            
        }
        
        public static void showTop() {
        }
        
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
                
                game.start();
                    
	}

}
