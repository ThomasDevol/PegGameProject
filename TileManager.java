package Tile;
import javax.imageio.ImageIO;
import java.io.IOException;
import main.GamePanel; 
import java.awt.Graphics2D;

public class TileManager {
    GamePanel gp; 
    Tile[] tile; 
    public TileManager(GamePanel gp) 
    {
        this.gp = gp; //instance variable = input variable
        tile = new Tile[3]; // holds 3 types of tiles: grass, rock, water
        
        getTileImage(); 
    }
    
    public void getTileImage() {
        try{ 
            
            tile[0] = new Tile(); 
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); 
            
            tile[1] = new Tile(); 
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png")); 
            
            tile[2] = new Tile(); 
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")); 
            
        }catch(IOException e) { 
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) { 
            g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);  
    }
}