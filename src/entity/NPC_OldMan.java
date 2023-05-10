package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();

    }
    public void getImage()
    {
        up1 = setup("/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/oldman_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
        up3 = setup("/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up4 = setup("/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down3 = setup("/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down4 = setup("/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        right3 = setup("/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right4 = setup("/NPC/oldman_right_2", gp.tileSize, gp.tileSize);
        left3 = setup("/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left4 = setup("/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue()
    {
        dialogues[0] = "Hello, child. So you've finally awaken.";
        dialogues[1] = "Who am I? I am just an old man.";
        dialogues[2] = "I am no enemy of yours.";
        dialogues[3] = "Oh so u don't remember?";
        dialogues[4] = "If u wish to find more about yourself you must get out \nof here and follow the path outside.";
        dialogues[5] = "GO now! Follow the path and face your destiny!";
        dialogues[6] = "Here, take a key! You can earn more by killing monsters!";
    }
    public void setAction()
    {
        actionLockCounter ++;

        if(actionLockCounter == 120)
        {
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25)
            {
                direction = "up";
            }
            if(i > 25 && i <= 50)
            {
                direction = "down";
            }
            if(i > 50 && i<= 75)
            {
                direction = "left";
            }
            if(i > 75 && i<= 100)
            {
                direction = "right";
            }
            actionLockCounter = 0;

        }


    }
    public void speak()
    {
        super.speak();
    }

}
