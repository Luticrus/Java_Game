package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Sake extends Entity {
    public OBJ_Sake(GamePanel gp)
    {
        super(gp);
        name = "Sake";
        down1 = setup("/objects/Sake", gp.tileSize, gp.tileSize);

    }
}
