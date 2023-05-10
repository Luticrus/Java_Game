package main;
import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_key;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI
{
    GamePanel gp;
    Font maruMonica, arial_80B;
    BufferedImage heart_full, heart_half, heart_empty;
    Graphics2D g2;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean messageOn = false;
    public boolean gameFinished = false;
    BufferedImage keyImage;
    public String currentDialogue = "";
    public int commandNum = 0;
    int subState = 0;
    public UI(GamePanel gp)
    {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        arial_80B = new Font("Arial", Font.BOLD, 80);

        ///CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;

    }
    public void addMessage(String text)
    {
       message.add(text);
       messageCounter.add(0);
    }
    public void draw (Graphics2D g2)
    {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        ///TITLE STATE
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
        ///PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        ///PAUSE STATE
        if(gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
        }
        ///DIALOGUE STATE
        if(gp.gameState == gp.dialogueState)
        {
            drawPlayerLife();
            drawDialogueScreen();
        }
        ///CHARACTER STATS
        if(gp.gameState == gp.characterStatus)
        {
            drawCharacterStatus();
        }
        ///OPTION STATE
        if(gp.gameState == gp.optionsState)
        {
            drawOptionScreen();
        }
        ///GAME OVER
        if(gp.gameState == gp.gameOverState)
        {
            drawGameOverScreen();
        }
        ///YOU WON!
        if(gp.gameState == gp.YouWonState)
        {
            drawYouWonState();
        }
    }
    public void  drawGameOverScreen()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "GAME OVER";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x , y);
        g2.setColor(Color.white);
        g2.drawString(text , x-4, y-4);

        ///RETRY
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text , x, y);
        if(commandNum == 0)
        {
            g2.drawString(">", x-40, y);
        }


        ///QUIT TO MENU
        text = "Quit";
        x=getXforCenteredText(text);
        y += 55;
        g2.drawString(text , x, y);
        if(commandNum == 1)
        {
            g2.drawString(">", x-40, y);
        }

    }
    public void  drawYouWonState()
    {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "YOU WON!";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x , y);
        g2.setColor(Color.white);
        g2.drawString(text , x-4, y-4);

        ///SCORE
        g2.setFont(g2.getFont().deriveFont(50F));
        gp.player.score = gp.player.coin*27 + gp.player.hasKey*50 + gp.player.life*20 + gp.player.level*101;
        text = "Score: "+ gp.player.score;
        x = getXforCenteredText(text);
        y+= gp.tileSize*1;
        g2.drawString(text , x, y);


        ///RETRY
        text = "Retry";
        x = getXforCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text , x, y);
        if(commandNum == 0)
        {
            g2.drawString(">", x-40, y);
        }


        ///QUIT TO MENU
        text = "Quit";
        x=getXforCenteredText(text);
        y += 55;
        g2.drawString(text , x, y);
        if(commandNum == 1)
        {
            g2.drawString(">", x-40, y);
        }

    }

    public void drawOptionScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        ///SUB WINDOW
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState)
        {
            case 0:options_top(frameX,frameY);break;
            case 1:options_fullScreenNotification(frameX,frameY);break;
            case 2:options_control(frameX,frameY);break;
            case 3:options_endGameConfirmation(frameX,frameY);break;
        }
        gp.keyH.enterPressed=false;
    }

    public void options_top(int frameX, int frameY)
    {
        int textX;
        int textY;

        ///TITLE
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        ///FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Full screen", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.playSE(10);
                if(gp.FullScreenOn == false)
                {
                    gp.FullScreenOn = true;
                }else if(gp.FullScreenOn == true)
                {
                    gp.FullScreenOn = false;
                }
                subState = 1;
            }
        }

        ///MUSIC
        textY +=gp.tileSize;
        g2.drawString("Music", textX,textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX-25, textY);
        }

        //SE
        textY +=gp.tileSize;
        g2.drawString("SFX", textX,textY);
        if(commandNum == 2)
        {
            g2.drawString(">", textX-25, textY);
        }


        //CONTROL
        textY +=gp.tileSize;
        g2.drawString("Controls", textX,textY);
        if(commandNum == 3)
        {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 2;
                commandNum = 0;
            }
        }

        ///QUIT
        textY +=gp.tileSize;
        g2.drawString("Quit", textX,textY);
        if(commandNum == 4)
        {
            g2.drawString(">", textX-25, textY);

            if(gp.keyH.enterPressed == true)
            {
                gp.Config.Update(gp.c,gp.stmt);
                gp.playSE(10);
                subState = 3;
                commandNum = 0;
            }

        }

        ///BACK
        textY +=gp.tileSize*2;
        g2.drawString("Back", textX,textY);
        if(commandNum == 5)
        {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.gameState = gp.playState;
                commandNum = 0;
                gp.Config.Update(gp.c,gp.stmt);
            }
        }

        ///FULL SCREEN CHECK BOX
        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2 +42;
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(textX,textY,24,24);
        if(gp.FullScreenOn == true)
        {
            g2.fillRect(textX,textY,24,24);
        }

        ///MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24);/// 120/5 = 24;
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        ///SE
        textY += gp.tileSize;
        g2.drawRect(textX,textY,120,24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);


    }

    public void drawCharacterStatus()
    {
        final int framex = gp.tileSize * 2;
        final int framey = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize *10;
        drawSubWindow(framex, framey, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textx = framex + 30;
        int texty = framey + gp.tileSize;
        final int lineHeight = 45;

        //names
        g2.drawString("Level",textx , texty);
        texty+=lineHeight;
        g2.drawString("Life",textx , texty);
        texty+=lineHeight;
        g2.drawString("Strength",textx , texty);
        texty+=lineHeight;
        g2.drawString("Dexterity",textx , texty);
        texty+=lineHeight;
        g2.drawString("Defense",textx , texty);
        texty+=lineHeight;
        g2.drawString("Attack",textx , texty);
        texty+=lineHeight;
        g2.drawString("Exp",textx , texty);
        texty+=lineHeight;
        g2.drawString("Next Level",textx , texty);
        texty+=lineHeight;
        g2.drawString("Coin",textx , texty);
        texty+=lineHeight;
        g2.drawString("Keys",textx , texty);
        texty+=lineHeight;

        ///Values
        int tailX = framex + frameWidth - 30;
        texty = framey + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.life +"/"+gp.player.maxLife);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.strength);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.defense);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.attack);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.exp);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.coin);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
        value = String.valueOf(gp.player.hasKey);
        textx = getXforAlignToRight(value ,tailX);
        g2.drawString(value,textx,texty);
        texty+=lineHeight;
    }
    public  int getXforAlignToRight(String text, int tailX)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    public void drawMessage()
    {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for(int i = 0; i< message.size(); i++)
        {
            if(message.get(i) != null)
            {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180)
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife()
    {
        int x = 16;
        int y = 16;
        int i = 0;
        ///DRAW BLANK HEART
        while(i < gp.player.maxLife/2)
        {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        ///RESET
        x = 16;
        y = 16;
        i = 0;
        ///DRAW CURRENT LIFE
        while (i < gp.player.life)
        {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life)
            {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public void drawTitleScreen()
    {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Hotaka, The Last Path";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+6, y+6);


        ///MainColor
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        ///HOTAKA IMAGE
        x=gp.screenWidth/2 - (gp.tileSize*2)/2;
        y+=gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
        ///MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-gp.tileSize/2,y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-gp.tileSize/2,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2)
        {
            g2.drawString(">",x-gp.tileSize/2,y);
        }

    }


    public void drawDialogueScreen()
    {
        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void options_control(int frameX, int frameY)
    {
        int textX;
        int textY;
        ///TITLE
        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY +gp.tileSize;
        g2.drawString(text, textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack/Interact", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Status", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;

        textX = frameX +gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD",textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER",textX, textY);
        textY += gp.tileSize;
        g2.drawString("C",textX, textY);
        textY += gp.tileSize;
        g2.drawString("P",textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC",textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0)
        {
            g2.drawString(">",textX-25, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.playSE(10);
                subState = 0;
                commandNum = 3;
            }
        }

    }

    public void options_fullScreenNotification(int frameX, int frameY)
    {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take \neffect after restarting the\ngame.";

        for(String line :currentDialogue.split("\n"))
        {
            g2.drawString(line,textX,textY);
            textY += 40;
        }
        ///BACK
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back" , textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">",textX-25, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.playSE(10);
                subState = 0;
            }
        }
    }
    public void options_endGameConfirmation(int frameX, int frameY)
    {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;
        currentDialogue = "Quit the game and \nreturn to the title screen ?";

        for(String line:currentDialogue.split("\n"))
        {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        ///YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text,textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                gp.stopMusic();
                gp.gameState = gp.titleState;
            }
        }

        //NO

        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text,textX,textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0,215);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5,width-10, height-10, 25, 25);
    }


    public void drawPauseScreen()
    {
        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

}
