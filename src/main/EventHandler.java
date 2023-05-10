package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gp)
    {

        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row <gp.maxWorldRow)
        {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 15;
            eventRect[col][row].y = 15;
            eventRect[col][row].width = 30;
            eventRect[col][row].height = 30;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }

        }

    }
    public void checkEvent()
    {
        ///CHECK IF PLAYER IS MORE THAN 1 TILE AWAY FROM THE LAST EVENT
        int xDistance = Math.abs(gp.player.worldx - previousEventX);
        int yDistance = Math.abs(gp.player.worldy - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize)
        {
            canTouchEvent = true;
        }

        if(canTouchEvent)
        {
            if(hit(70,47, "right") == true)
            {
                damagePit(70,47, gp.dialogueState);
            }
            if(hit(70,46, "any") == true)
            {
                healingPool(70, 46, gp.dialogueState);
            }
            if(hit(20,19, "any") == true)
            {
                tpEvent(20, 19, gp.dialogueState);
            }
        }

    }
    public boolean hit(int col, int row, String regDirection){

        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldx +gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldy +gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false)
        {
            if(gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any"))
            {
                hit = true;

                previousEventX = gp.player.worldx;
                previousEventY = gp.player.worldy;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePit(int col, int row, int gameState)
    {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You stepped on a broken knife!";
        gp.player.life -=1;
        eventRect[col][row].eventDone = true;
        canTouchEvent = false;

        ///Respawn monster
        gp.aSetter.setMonster();
    }
    public void healingPool(int col, int row, int gameState)
    {

            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the water! You feel recovered!";
            gp.player.life = gp.player.maxLife;
            eventRect[col][row].eventDone = true;
            canTouchEvent = false;

    }
    public void tpEvent(int col, int row, int gameState)
    {

            gp.gameState = gameState;
            gp.ui.currentDialogue = "You were teleported!";
            gp.player.worldx = gp.tileSize*52;
            gp.player.worldy = gp.tileSize*47;

    }

}
