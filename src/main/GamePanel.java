package main;

 
import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;
import object.SuperObject;

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
   public final int maxScreenCol = 16;
   public final int maxScreenRow = 12; //4x3 ratio
   public final int screenWidth = tileSize * maxScreenCol; //768 Pixels
   public final int screenHeight = tileSize * maxScreenRow; //576 Pixels
    
   
   // WORLD SETTINGS
   // can change for our worlds
   public final int maxWorldCol = 50;
   public final int maxWorldRow = 50;
   public final int worldWith = tileSize * maxWorldCol; //DO NOT DELETE MAY HAVE TO USE IN RANDOM CASES
   public final int worldHeight = tileSize * maxWorldRow;
   
   
    //FPS
    int FPS = 60;
    
    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); // handles input
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    // ENTITY AND OBJECT
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10]; // the amount of objects allowed on a screen
    
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //helps with performance
        this.addKeyListener(keyH); //recognizes key input
        this.setFocusable(true);// this allows the Panel to receive input
    }
    
    public void setupGame()
    {
    	aSetter.setObject();
    	
    	playMusic(0); //Title Music
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


        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true)
        {
        drawStart = System.nanoTime();
        }

        // TILE
        tileM.draw(g2); // first layer makes it in the background. The reverse hides the player
        
        //OBJECT
        for(int i = 0; i < obj.length; i++)
        {
        	if(obj[i] != null)
        	{
        		obj[i].draw(g2,this);
        	}
        }
        
        //PLAYER
        player.draw(g2);
        
        //UI
        ui.draw(g2);
        
        
        if(keyH.checkDrawTime == true)
        {
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time: " + passed, 10, 400);
        System.out.println("Draw Time:"+ passed);
        }




        g2.dispose(); // Releases the graphics memory
    }
    public void playMusic(int i)
    {
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    public void stopMusic()
    {
    	music.stop();
    }
    public void playSE(int i)
    {
    	se.setFile(i);
    	se.play();
    }
}