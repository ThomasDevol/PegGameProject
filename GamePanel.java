package main;
import javax.swing.JPanel;

import entity.Player;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable
{
    //Screen settings
    final int originalTileSize = 16; //16x16 tile size
    final int scale = 3; // to size up the characters 48x48 pixels
    
    public final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12; //4x3 ratio
    final int screenWidth = tileSize * maxScreenCol; //768 Pixels
    final int screenHeight = tileSize * maxScreenRow; //576 Pixels
    
    //FPS
    int FPS = 60;
    
    KeyHandler keyH = new KeyHandler(); // handles input
    Thread gameThread;
    Player player = new Player(this,keyH);
    
    // set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //helps with performance
        this.addKeyListener(keyH); //recognizes key input
        this.setFocusable(true);// this allows the Panel to receive input
    }
    
    
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override //this is our game Loop!
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime; 
        while(gameThread != null)
        {
             currentTime = System.nanoTime(); //Returns current value of JVM high-resolution time source in Nanoseconds
            
            delta += (currentTime-lastTime)/ drawInterval;
            
            lastTime = currentTime;
            
            if(delta >= 1)
            {
            // 1 UPDATE: update information such as character Position
           update();
           // 2 DRAW: Draw the screen with Updated Information 
           repaint(); // How Paint Component Is Called (IDK why it works like this)
           delta--; //Reseting Time
        }
        
        }
    }
    
    public void update()
    {
       player.update();
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        player.draw(g2);
        
        g2.dispose(); // releses the graphics memory
    }
}