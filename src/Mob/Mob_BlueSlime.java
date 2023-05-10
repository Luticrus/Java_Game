package Mob;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Rock;
import object.OBJ_key;

import java.util.Random;

public class Mob_BlueSlime extends Entity {
    GamePanel gp;

    public Mob_BlueSlime(GamePanel gp) {
        super(gp);

        this.gp=gp;
        type = 2;
        name = "Blue Slime";
        speed = 2;
        maxLife = 8;
        life = maxLife;
        attack = 8;
        defense = 1;
        exp = 8;
        projectile = new OBJ_Rock(gp);


        solidArea.x = 3;
        solidArea.y = 20;
        solidArea.width = 58;
        solidArea.height = 42;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

    }
    public void getImage()
    {
        up1 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        up3 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        up4 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        down3 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        down4 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        left3 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        left4 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);
        right3 = setup("/Mobs/blueslime_down_1", gp.tileSize, gp.tileSize);
        right4 = setup("/Mobs/blueslime_down_2", gp.tileSize, gp.tileSize);

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
        int i = new Random().nextInt(100)+1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30)
        {
            projectile.set(worldx, worldy, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop()
    {
        ///CAST A DIE
        int i = new Random().nextInt(100)+1;

        ///SET THE MONSTER DROP RATE
        if(i<20)
        {
            dropItem(new OBJ_Heart(gp));
        }else if(i < 30 && i >= 20)
        {
            dropItem(new OBJ_key(gp));
        }else if(i < 80 && i>= 30)
        {
            dropItem(new OBJ_Coin_Bronze(gp));
        }

    }
}
