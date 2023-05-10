package entity;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.PublicKey;


public class Player extends Entity
{

    KeyHandler keyH;

    public final int screenx;
    public final int screeny;
    int standCounter = 0;
    public int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH)
    {
        super(gp);
        this.keyH = keyH;

        screeny = gp.screenHeight/2 - (gp.tileSize/2);
        screenx = gp.screenWidth/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 22;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 53;

        attackArea.width = 64;
        attackArea.height = 64;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();

    }
    public void setDefaultValues()
    {
        //spawn point
        worldx = gp.tileSize * 69;
        worldy = gp.tileSize * 46;
        speed = 4;
        direction = "down";

        ///PLAYER STATUS
        maxLife = 6;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        life = maxLife;
        attack = getAttack();
        defense = getDefense();
        hasKey = 0;
    }
    ///tutorial 25
    public int getAttack()
    {
        return attack = strength + dexterity;
    }
    public int getDefense()
    {
        return defense = strength + dexterity;
    }
    public void getPlayerImage()
    {
        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/up3", gp.tileSize, gp.tileSize);
        up4 = setup("/player/up4", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        down3 = setup("/player/down3", gp.tileSize, gp.tileSize);
        down4 = setup("/player/down4", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/right3", gp.tileSize, gp.tileSize);
        right4 = setup("/player/right4", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/left3", gp.tileSize, gp.tileSize);
        left4 = setup("/player/left4", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage()
    {
        attackUp1 = setup("/player/Attack_up_0", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/Attack_up_1", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/Attack_down_0", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/Attack_down_1", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/Attack_left_0", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/player/Attack_left_1", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/player/Attack_right_0", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/player/Attack_right_1", gp.tileSize*2, gp.tileSize);
    }

    public void update()
    {
        if(attacking == true)
        {

            attacking();

        }

        ///For no character movement while idle check tutorial3-min22:00
        ///For diagonal movement change if else to if
        if(     keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed==true || keyH.rightPressed == true
                || keyH.enterPressed == true) {
            if (keyH.upPressed)
            {
                direction = "up";
            }
            if (keyH.downPressed)
            {
                direction = "down";
            }
            if (keyH.leftPressed)
            {
                direction = "left";
            }
            if (keyH.rightPressed)
            {
                direction = "right";
            }
            ///CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            ///CHECK OBJECT COLLISION

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            ///CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            ///CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            ///CHECK EVENT COLLISION
            gp.eHandler.checkEvent();



            ///IF COLLISION FALSE , PLAYER CANT MOVE
            if (collisionOn == false && keyH.enterPressed == false) {
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
        }
        else
        {
            standCounter++;
            if(standCounter == 20)
            {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        ///THIS NEED TO BE OUTSIDE OF KEY IF STATEMENT!
        if(invincible == true)
        {
            invincibleCounter++;
            if(invincibleCounter > 60)
            {
                invincible = false ;
                invincibleCounter = 0;
            }
        }
        if(life <= 0)
        {
            gp.gameState = gp.gameOverState;
            gp.playSE(11);
        }

    }

    public void attacking()
    {
        spriteCounter++;
        if(spriteCounter <= 5)
        {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 15)
        {
            spriteNum = 2;

            //SAVE THE CURRENT WORLDX WORLDY AND SOLIDAREA
            int currentWorldX = worldx;
            int currentWorldY = worldy;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            ///adjust player's worldx/y for the attackArea

            switch (direction)
            {
                case"up": worldy -= attackArea.height;break;
                case "down":worldy += attackArea.height;break;
                case"left":worldx -= attackArea.width;break;
                case"rigt":worldx += attackArea.width;break;

            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //CHECK MONSTER COLLISION WITH THE UPDATED WORLDX WORLDY AND SOLID AREA
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            ///AFTER CHECKING COLLISION RESTORE THE ORIGINAL DATA
            worldx = currentWorldX;
            worldy = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 15)
        {
            spriteCounter = 0;
            spriteNum = 1;
            attacking = false;
        }
    }

    public void pickUpObject (int i)
    {
        if(i != 999)
        {
            String objectName = gp.obj[i].name;

            if(objectName == "Key") {
                hasKey++;
                gp.playSE(1);
                gp.ui.addMessage("Key +1");
                gp.obj[i] = null;
            }
            if(objectName == "Chest") {
                if (hasKey > 0) {
                    //gp.playSE(2);
                    gp.ui.addMessage("Key -1");
                    gp.obj[i] = null;
                    hasKey--;
                    gp.gameState = gp.YouWonState;
                }
            }


            if(objectName == "Door") {
                if (hasKey > 0) {
                    ///MAKE ANOTHER DOOR SOUND PLS UWU KYS
                    //gp.playSE(2);
                    gp.ui.addMessage("Key -1");
                    gp.obj[i] = null;
                    hasKey--;
                }
            }
            if(objectName == "Bronze_Coin") {
                gp.playSE(1);
                gp.ui.addMessage("Coin +" + gp.obj[i].value);
                gp.player.coin += gp.obj[i].value;
                gp.obj[i] = null;
            }
            if(objectName == "Heart") {
                gp.playSE(1);
                gp.ui.addMessage("Life +" + gp.obj[i].value);
                gp.player.life += gp.obj[i].value;
                if(gp.player.life >= gp.player.maxLife)
                {
                    gp.player.life = gp.player.maxLife;
                }
                gp.obj[i] = null;
            }
        }


    }
    public void interactNPC(int i)
    {
        int ok =0;
        if(gp.keyH.enterPressed == true && keyH.rightPressed==false &&
                keyH.leftPressed==false &&
                keyH.downPressed==false &&
                keyH.upPressed==false && spriteCounter2 == 3)
        {

            if(i != 999)
            {
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();

            }
            else
            {
                attacking = true;
                keyH.rightPressed=false;
                keyH.leftPressed=false;
                keyH.downPressed=false;
                keyH.upPressed=false;
                spriteCounter2 = 0;
            }



        }
        if(spriteCounter2 < 3)
        {
            spriteCounter2++;
        }
        gp.keyH.enterPressed = false;

    }

    public void contactMonster(int i)
    {
        if(i != 999)
        {
            if(invincible == false && gp.monster[i].dying == false)
            {
                gp.playSE(7);
                int damage =gp.monster[i].attack - defense;
                if(damage < 0)
                {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i)
    {
        if(i != 999) {
            if(gp.monster[i].invincible == false)
            {
                gp.playSE(8);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0)
                {
                    damage = 0;
                }

                gp.monster[i].life -=damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
            }
            if(gp.monster[i].life <= 0 && gp.monster[i].dying==false)
            {
                gp.monster[i].dying = true;
                gp.ui.addMessage("Killed the "+ gp.monster[i].name + "!");
                gp.ui.addMessage("Exp + "+ gp.monster[i].exp);
                exp += gp.monster[i].exp;
                checkLevelUp();
            }
        }
    }
    public void checkLevelUp()
    {
        if(exp >= nextLevelExp)
        {
            exp = 0;
            level++;
            nextLevelExp *=2;
            maxLife += 2;
            if(level%2 == 1)
            {
                strength++;
            }else{

                dexterity++;
            }
            attack = getAttack();
            defense = getDefense();
            life = maxLife;

            gp.playSE(9);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!";
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int tempScreenX = screenx;
        int tempScreenY = screeny;

        switch (direction) {
            case "up" -> {
                if(attacking == false)
                {
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
                if(attacking == true)
                {
                    tempScreenY = screeny - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;

                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                    if (spriteNum == 3) {
                        image = attackUp2;
                    }
                    if (spriteNum == 4) {
                        image = attackUp2;
                    }
                }

            }
            case "down" -> {
                if(attacking == false)
                {
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
                if(attacking == true)
                {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                    if (spriteNum == 3) {
                        image = attackDown2;
                    }
                    if (spriteNum == 4) {
                        image = attackDown2;
                    }
                }

            }
            case "right" -> {
                if(attacking == false)
                {
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
                if(attacking == true)
                {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                    if (spriteNum == 3) {
                        image = attackRight2;
                    }
                    if (spriteNum == 4) {
                        image = attackRight2;
                    }
                }

            }
            case "left" -> {
                if(attacking == false)
                {
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
                if(attacking == true)
                {
                    tempScreenX = screenx - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        image = attackLeft2;
                    }
                    if (spriteNum == 4) {
                        image = attackLeft2;
                    }
                }

            }
        }
        /*int x = screenx, y = screeny;
        if(screenx > worldx)
        {
            x = worldx;
        }
        if(screeny > worldy)
        {
            y = worldy;
        }
        int rightOffset = gp.screenWidth - screenx;
        if(rightOffset > gp.worldWidth - worldx)
        {
            x = gp.screenWidth -(gp.worldWidth - worldx);
        }
        int bottomOffset = gp.screenHeight - screeny;
        if(bottomOffset > gp.worldHeight - worldy)
        {
            y = gp.screenHeight - (gp.worldHeight - worldy);
        }

         */

        if(invincible == true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        ///RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        ///DEBUG

        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:"+invincibleCounter, 10, 400);*/
    }

}
