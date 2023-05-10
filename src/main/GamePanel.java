package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable
{
    ///SCREEN SETTINGS
    final int originalTileSize = 64; //64*64
    final int scale=1;//the scale of the tiles

    public final int tileSize = originalTileSize * scale;///64*64 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;///1024
    public final int screenHeight = tileSize * maxScreenRow;///768

    ///World Settings
    public final int maxWorldCol = 101;
    public final int maxWorldRow = 71;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    ///FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean FullScreenOn = false;



    //FPS

    int FPS = 60;
    //SYSTEM
    Sound music = new Sound();
    Sound se = new Sound();
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);

    ///ENTITY AND OBJECT

    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public Entity monster[] = new Entity[40];

    ///GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterStatus = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int YouWonState = 7;

    DataBaseConfiguration Config = new DataBaseConfiguration(this);
    Connection c;
    Statement stmt = null;


    {
        try {
            c = Config.GetDataBaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        //playMusic(6);
        //stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(FullScreenOn == true)
        {
            FullScreen();
        }


    }

    public void FullScreen()
    {
        ///GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        ///GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;//0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1)
            {
                update();
                //repaint();
                drawTempScreen();
                drawScreen();
                delta--;
            }

        }
    }

    public void update() {
        if (gameState == playState) {
            ///PLAYER
            player.update();
            //NPC
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length; i++)
            {
                if(monster[i] != null)
                {
                    if(monster[i].alive == true && monster[i].dying == false)
                    {
                        monster[i].update();
                    }
                    if(monster[i].alive == false)
                    {
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); i++)
            {
                if(projectileList.get(i) != null)
                {
                    if(projectileList.get(i).alive == true)
                    {
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false)
                    {
                        projectileList.remove(i);
                    }
                }
            }


        }
        if(gameState == pauseState)
        {
            ///nothing
        }
    }
    public void retry()
    {
        player.setDefaultValues();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setObject();
    }
    public void restart()
    {
        player.setDefaultValues();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setObject();
    }

    public void drawTempScreen()
    {
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        //OTHERS
        else
        {
            //Tile
            tileM.draw(g2);

            ///ADD ENTITIES TO THE LIST
            entityList.add(player);
            for(int i = 0; i < npc.length; i++)
            {
                if(npc[i] != null)
                {
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++)
            {
                if(obj[i] != null)
                {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++)
            {
                if(monster[i] != null)
                {
                    entityList.add(monster[i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++)
            {
                if(projectileList.get(i) != null)
                {
                    entityList.add(projectileList.get(i));
                }
            }

            ///SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldy, e2.worldy);
                    return result;

                }
            });

            //DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++)
            {
                entityList.get(i).draw(g2);
            }
            ///EMPTY ENTITY LIST
            entityList.clear();


            //UI
            ui.draw(g2);

        }

    }

    public void drawScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i)
    {
        se.setFile(i);
        se.play();
    }

}
