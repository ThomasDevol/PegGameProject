package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    
    public Player(GamePanel gp, KeyHandler keyH)
    {
    	this.gp = gp;
    	this.keyH = keyH;
    	
    	setDefaultValues();
    	getPlayerImage();
    }
    public void setDefaultValues()
    {
    	x = 100;
    	y = 100;
    	speed = 4;
    	direction = "down";
    }
    public void getPlayerImage()
    {
    	try {
    		up1 = ImageIO.read(getClass().getResourceAsStream("/player/back1.png"));
    		up2 = ImageIO.read(getClass().getResourceAsStream("/player/back2.png"));
    		down1 = ImageIO.read(getClass().getResourceAsStream("/player/foward1.png"));
    		down2 = ImageIO.read(getClass().getResourceAsStream("/player/foward2.png"));
    		left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
    		left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
    		right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
    		right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    public void update()
    {
    	 if(keyH.upPressed == true)
         {
    		 direction = "up";
             y -= speed; //moves up, (0,0) is the top left corner btw
             
         }
         else if(keyH.downPressed == true)
         {
        	 direction = "down";
             y += speed;
         }
         else if(keyH.leftPressed == true)
         {
        	 direction = "left";
             x -= speed;
         }
         else if(keyH.rightPressed == true)
         {
        	 direction + "right";
             x += speed;
         }
    }
    public void draw(Graphics2D g2)
    {
    	BufferedImage image = null;
    	switch(direction)
    	{
    	case "up":
    		image = up1;
    		break;
    	case "down":
    		image = down1;
    		break;
    	case "left":
    		image = left1;
    		break;
    	case "right":
    		image = right1;
    		break;
    	}
        g2.drawImage(image,x, y, gp.tileSize,gp.tileSize,null);
    }
}