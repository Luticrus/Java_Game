package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class KeyHandler implements KeyListener
{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public KeyHandler(GamePanel gp)
    {
        this.gp=gp;
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        ///FOR CLASS SELECTION/CONTROLS GUIDE/SHORT STORY TELLING CHECK #17 19:00
        int code = e.getKeyCode();
        if(gp.gameState == gp.titleState)
        {
            try {
                titleScreen(code);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(gp.gameState == gp.playState)
        {
            playState(code);
        }
        else if(gp.gameState == gp.pauseState)
        {
            pauseState(code);
        }
        else if(gp.gameState == gp.dialogueState)
        {
            dialogueState(code);
        }
        else if(gp.gameState == gp.characterStatus)
        {
            characterStatus(code);
        }
        else if(gp.gameState == gp.optionsState)
        {
            optionState(code);
        }
        else if(gp.gameState == gp.gameOverState)
        {
            gameOverState(code);
        }
        else if(gp.gameState == gp.YouWonState)
        {
            YouWonState(code);
        }

    }
    public void gameOverState(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = 1;
            }
            gp.playSE(10);
        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1)
            {
                gp.ui.commandNum = 0;
            }
            gp.playSE(10);
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(gp.ui.commandNum == 0)
            {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1)
            {
                gp.stopMusic();
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    public void YouWonState(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = 1;
            }
            gp.playSE(10);
        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1)
            {
                gp.ui.commandNum = 0;
            }
            gp.playSE(10);
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(gp.ui.commandNum == 0)
            {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1)
            {
                gp.stopMusic();
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    public void titleScreen(int code) throws SQLException {
        if(gp.gameState == gp.titleState)
        {

            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                gp.playSE(10);
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum=2;
                }
            }

            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                gp.playSE(10);
                if(gp.ui.commandNum > 2)
                {
                    gp.ui.commandNum=0;
                }
            }
            if(code == KeyEvent.VK_ENTER)
            {
                if(gp.ui.commandNum == 0)
                {
                    gp.playSE(10);
                    gp.gameState = gp.playState;
                    gp.playMusic(6);
                }
                if(gp.ui.commandNum == 1)
                {
                    ///ADD LATER
                }
                if(gp.ui.commandNum == 2)
                {
                    gp.playSE(10);
                    DataBaseConfiguration.CloseDataBaseConnection(gp.c);
                    System.exit(0);
                }
            }
        }
    }
    public void playState(int code)
    {

        if(gp.gameState == gp.playState)
        {
            if(code == KeyEvent.VK_W)
            {
                upPressed = true;
            }

            if(code == KeyEvent.VK_A)
            {
                leftPressed = true;
            }

            if(code == KeyEvent.VK_S)
            {
                downPressed = true;
            }

            if(code == KeyEvent.VK_D)
            {
                rightPressed = true;
            }

            if(code == KeyEvent.VK_P)
            {
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER)
            {
                enterPressed = true;
            }
            if(code == KeyEvent.VK_C)
            {
                gp.gameState = gp.characterStatus;
            }
            if(code == KeyEvent.VK_ESCAPE)
            {
                gp.gameState = gp.optionsState;
            }
        }
    }
    public void pauseState(int code)
    {
        if(gp.gameState == gp.pauseState)
        {
            if(code == KeyEvent.VK_P)
            {
                gp.gameState = gp.playState;
            }
        }
    }
    public void dialogueState(int code)
    {
        if(gp.gameState == gp.dialogueState)
        {
            if(code == KeyEvent.VK_ENTER)
            {
                gp.gameState = gp.playState;
            }
        }

    }
    public void characterStatus(int code)
    {
        if(gp.gameState == gp.characterStatus)
        {
            if(code == KeyEvent.VK_C)
            {
                gp.gameState = gp.playState;
            }
        }
    }


    public void optionState(int code)
    {
        if(code == KeyEvent.VK_ESCAPE)
        {
            gp.Config.Update(gp.c,gp.stmt);
            gp.gameState = gp.playState;

        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState)
        {
            case 0: maxCommandNum = 5;break;
            case 3: maxCommandNum = 1;break;
        }
        if(code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            gp.playSE(10);
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = maxCommandNum;
            }

        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            gp.playSE(10);
            if(gp.ui.commandNum > maxCommandNum)
            {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A)
        {
            if(gp.ui.subState == 0)
            {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0)
                {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(10);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0)
                {
                    gp.se.volumeScale--;

                    gp.playSE(10);
                }
            }

        }
        if(code == KeyEvent.VK_D)
        {
            if(gp.ui.subState == 0)
            {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5)
                {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(10);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5)
                {
                    gp.se.volumeScale++;
                    gp.playSE(10);
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code =e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }

        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }

        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }
    }
}
