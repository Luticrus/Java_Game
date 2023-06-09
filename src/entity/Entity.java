package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity
{
    GamePanel gp;
    public BufferedImage up1, up2, up3,  up4, down1, down2, down3, down4, right1, right2, right3, right4, left1, left2, left3, left4;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image,image2,image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];



    ///STATE
    public int worldx, worldy;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    ///COUNTER
    public int invincibleCounter = 0;
    public int spriteCounter = 0;
    public int spriteCounter2 = 0;
    public int actionLockCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter;
    public int shotAvailableCounter = 0;


    ///CHARACTER STATUS
    public int speed;
    public int maxLife;
    public int life;
    public String name;
    public int type; ///0 = PLAYER, 1=NPC; 2=MONSTER
    public final int type_pickUpOnly = 1;
    public int value;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Projectile projectile;
    public int score = 0;

    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setAction()
    {
    }
    public void damageReaction()
    {}
    public void checkDrop()
    {

    }
    public void  dropItem(Entity droppedItem)
    {
        for(int i = 0; i<gp.obj.length; i++)
        {
            if(gp.obj[i] == null)
            {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldx = worldx;
                gp.obj[i].worldy = worldy;
                break;
            }
        }
    }

    public void speak(){
        int ok = 0;
        if(dialogues[dialogueIndex] == null)
        {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if(dialogueIndex == 6 && ok ==0)
        {
            gp.player.hasKey++;
            ok = 1;
        }

        switch (gp.player.direction)
        {
            case "up":
                direction = "down";
                break;
            case"down":
                direction = "up";
                break;
            case"left":
                direction = "right";
                break;
            case"right":
                direction = "left";
                break;
        }
    }
    public void update()
    {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        if(this.type == 2 && contactPlayer == true)
        {
            damagePlayer(attack);
        }



        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    worldy -= speed;
                    break;
                case "down":
                    worldy += speed;
                    break;
                case "left":
                    worldx -= speed;
                    break;
                case "right":
                    worldx += speed;
                    break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 15)
        {
            if(spriteNum <4)
            {
                spriteNum++;
            }
            else
                spriteNum = 1;
            spriteCounter=0;
        }
        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter > 30)
            {
                invincible = false ;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter<30)
        {
            shotAvailableCounter++;
        }




    }
    public void damagePlayer(int attack)
    {
        if(gp.player.invincible == false)
        {
            //WE CAN GIVE DMG
            gp.playSE(7);
            int damage =attack - gp.player.defense;
            if(damage < 0)
            {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldx - gp.player.worldx + gp.player.screenx;
        int screenY = worldy - gp.player.worldy + gp.player.screeny;

        if( worldx + gp.tileSize > gp.player.worldx - gp.player.screenx &&
                worldx - gp.tileSize < gp.player.worldx + gp.player.screenx &&
                worldy + gp.tileSize > gp.player.worldy - gp.player.screeny &&
                worldy - gp.tileSize < gp.player.worldy + gp.player.screeny)
        {
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                    if (spriteNum == 4) {
                        image = up4;
                    }
                }
                case "down" -> {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if (spriteNum == 4) {
                        image = down4;
                    }
                }
                case "right" -> {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                    if (spriteNum == 4) {
                        image = right4;
                    }
                }
                case "left" -> {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                    if (spriteNum == 4) {
                        image = left4;
                    }
                }
            }
            ///MONSTER HP BAR
            if(type == 2 && hpBarOn == true)
            {
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1,screenY-16, gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                if(hpBarValue<=0)
                {
                    hpBarValue = 0;
                }
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600)
                {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }



            if(invincible == true) {
                hpBarOn=true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4F);
            }

            if(dying == true)
            {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY,null);
            changeAlpha(g2, 1f);
        }
    }
    public void dyingAnimation(Graphics2D g2)
    {
        int i = 5;
        dyingCounter++;
        if(dyingCounter <= i)
        {
             changeAlpha(g2,0f);
        }
        if(dyingCounter > i && dyingCounter <= i*2)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*8)
        {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaledImage(image, width, height);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }

}
