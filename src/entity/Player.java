package entity;


import java.awt.Graphics2D;
import java.io.IOException;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.awt.Rectangle;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    
    public Player(GamePanel gp, KeyHandler keyH)
    {
    	this.gp = gp;
    	this.keyH = keyH;
    	
    	screenX = gp.screenWidth/2 - (gp.tileSize/2);
    	screenY = gp.screenHeight/2 - (gp.tileSize/2); 
    	
		solidArea = new Rectangle(); //Adjust based on character model
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;

    	setDefaultValues();
    	getPlayerImage();
    }
    public void setDefaultValues()
    {
    	worldX = gp.tileSize * 23;
    	worldY = gp.tileSize * 21;
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

		up1 = setup("back1");
		up2 = setup("back2");
		down1 = setup("foward1");
		down2 = setup("foward2");
		left1 = setup("left1");
		left2 = setup("left2");
		right1 = setup("right1");
		right2 = setup("right2");
    }

	public BufferedImage setup(String imageName)
	{
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try{
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
	}
    public void update()
    {
    	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true)
      {
		//CHECK AND SET DIRECTION
    	 if(keyH.upPressed == true)
         {
    		 direction = "up";
            
             
         }
         else if(keyH.downPressed == true)
         {
        	 direction = "down";
             
         }
         else if(keyH.leftPressed == true)
         {
        	 direction = "left";
             
         }
         else if(keyH.rightPressed == true)
         {
        	 direction = "right";
            
         }
		 // CHECK TILE COLLISION
		 collisionOn = false;
		 gp.cChecker.checkTile(this);
		 
		 //CHECK OBJECT COLLISION
		 int objIndex = gp.cChecker.checkObject(this,true);
		 pickUpObject(objIndex);
		 
		 
		 
		 // IF COLLISION IS FALSE,PLAYER CAN MOVE
		 if(collisionOn == false)
		 {
			switch(direction)
			{
				case "up":
				worldY -= speed;
					break;
				case "down":
				worldY += speed;
					break;
				case "left":
				worldX -= speed;
					break;
				case "right":
				worldX += speed;
					break;
			}
		 }


    	 spriteCounter++;
    	 if(spriteCounter > 12)
    	 {
    		 if(spriteNum == 1)
    		 {
    			spriteNum = 2;
    		 }
    		 else if(spriteNum == 2)
    		 {
    			spriteNum = 1; 
    		 }
    		 spriteCounter = 0;
    	 }
      }
    }
    
    
    public void pickUpObject(int i)
    {
    	if(i != 999) //999 means no object is touched
    	{
    		String objectName = gp.obj[i].name;
    		
    		switch(objectName)
    		{
    		case "Key":
    			gp.playSE(1);
    			hasKey++;
    			gp.obj[i] = null;
    			gp.ui.showMessage("You got a key!");
    			break;
    		case "Door":
    			if(hasKey > 0)
    			{
    				gp.playSE(3);
    				gp.obj[i] = null;
    				hasKey--;
    				gp.ui.showMessage("You opened the door!");
    				
    			}
    			else
    			{
    				gp.ui.showMessage("you need a key!");
    			}
    			
    			break;
    		case "Boots":
    			gp.playSE(2);
    			speed += 1;
    			gp.obj[i] = null;
    			gp.ui.showMessage("Speed up!");
    			break;
    		case "Chest":
    			gp.ui.gameFinished = true;
    			gp.stopMusic();
    			gp.playSE(4);
    			break;
    		}
    	}
    }
    
    
    
    public void draw(Graphics2D g2)
    {
    	BufferedImage image = null;
    	switch(direction)
    	{
    	case "up":
    		if (spriteNum == 1)
    		{
    		image = up1;
    		}
    		if (spriteNum == 2)
    		{
    		image = up2;
    		}
    		break;
    	case "down":
    		if (spriteNum == 1) {
    		image = down1;
    		}
    		if (spriteNum == 2)
    		{
    		image = down2;
    		}
    		break;
    	case "left":
    		if (spriteNum == 1)
    		{
    		image = left1;
    		}
    		if (spriteNum == 2)
    		{
    		image = left2;
    		}
    		break;
    	case "right":
    		if (spriteNum == 1)
    		{
    		image = right1;
    		}
    		if (spriteNum == 2)
    		{
    		image = right2;
    		}
    		break;
    	}
        g2.drawImage(image,screenX, screenY,null);
    }
}