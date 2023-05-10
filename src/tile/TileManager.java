
package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager
{
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp)
    {
        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map3.txt");
    }
    public void getTileImage()
    {
        setup(0, "terain_ss_00", false);
        setup(1, "terain_ss_01", false);
        setup(2, "terain_ss_02", false);
        setup(3, "terain_ss_03", false);
        setup(4, "terain_ss_04", false);
        setup(5, "terain_ss_05", false);
        setup(6, "terain_ss_06", false);
        setup(7, "terain_ss_07", false);
        setup(8, "terain_ss_08", false);
        setup(9, "terain_ss_09", false);

        setup(10, "terain_ss_10", false);
        setup(11, "terain_ss_11", false);
        setup(12, "terain_ss_12", false);
        setup(13, "terain_ss_13", false);
        setup(14, "terain_ss_14", false);
        setup(15, "terain_ss_15", false);
        setup(16, "terain_ss_16", false);
        setup(17, "terain_ss_17", false);
        setup(18, "terain_ss_18", false);
        setup(19, "terain_ss_19", false);
        setup(20, "terain_ss_20", false);
        setup(21, "terain_ss_21", false);
        setup(22, "terain_ss_22", false);
        setup(23, "terain_ss_23", false);
        setup(24, "terain_ss_24", false);
        setup(25, "terain_ss_25", false);
        setup(26, "terain_ss_26", false);
        setup(27, "terain_ss_27", false);
        setup(28, "terain_ss_28", false);
        setup(29, "terain_ss_29", false);
        setup(30, "terain_ss_30", false);
        setup(31, "terain_ss_31", false);
        setup(32, "terain_ss_32", false);
        setup(33, "terain_ss_33", false);
        setup(34, "terain_ss_34", false);
        setup(35, "terain_ss_35", false);
        setup(36, "terain_ss_36", false);
        setup(37, "terain_ss_37", false);
        setup(38, "terain_ss_38", false);
        setup(39, "terain_ss_39", false);
        setup(40, "terain_ss_40", false);
        setup(41, "terain_ss_41", false);
        setup(42, "terain_ss_42", false);
        setup(43, "terain_ss_43", false);
        setup(44, "terain_ss_44", false);
        setup(45, "terain_ss_45", false);
        setup(46, "terain_ss_46", false);
        setup(47, "terain_ss_47", false);
        setup(48, "terain_ss_48", false);
        setup(49, "terain_ss_49", false);
        setup(50, "terain_ss_50", true);
        setup(51, "terain_ss_51", true);
        setup(52, "terain_ss_52", true);
        setup(53, "terain_ss_53", true);
        setup(54, "terain_ss_54", true);
        setup(55, "terain_ss_55", true);
        setup(56, "terain_ss_56", true);
        setup(57, "terain_ss_57", true);
        setup(58, "terain_ss_58", false);
        setup(59, "terain_ss_59", false);
        setup(60, "terain_ss_60", false);
        setup(61, "terain_ss_61", false);
        setup(62, "terain_ss_62", false);
        setup(63, "terain_ss_63", false);
        setup(64, "terain_ss_64", false);
        setup(65, "terain_ss_65", false);
        setup(66, "terain_ss_66", false);
        setup(67, "terain_ss_67", false);
        setup(68, "terain_ss_68", false);
        setup(69, "terain_ss_69", false);
        setup(70, "terain_ss_70", false);
        setup(71, "terain_ss_71", false);
        setup(72, "terain_ss_72", false);
        setup(73, "terain_ss_73", true);
        setup(74, "terain_ss_74", true);
        setup(75, "terain_ss_75", false);
        setup(76, "terain_ss_76", false);
        setup(77, "terain_ss_77", false);
        setup(78, "terain_ss_78", false);
        setup(79, "terain_ss_79", true);
        setup(80, "terain_ss_80", true);
        setup(81, "terain_ss_81", false);
        setup(82, "terain_ss_82", false);
        setup(83, "terain_ss_83", false);
        setup(84, "terain_ss_84", false);
        setup(85, "terain_ss_85", false);
        setup(86, "terain_ss_86", false);
        setup(87, "terain_ss_87", false);
        setup(88, "terain_ss_88", false);
        setup(89, "terain_ss_89", false);
        setup(90, "terain_ss_90", false);

    }
    public void setup(int index, String imageName, boolean collision)
    {
        UtilityTool uTool = new UtilityTool();
        try
        {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tile/"+ imageName + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath)
    {
        try
        {
            InputStream is = getClass().getResourceAsStream("/maps/map3.txt");
            BufferedReader br = new BufferedReader( new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = br.readLine();

                while(col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (IOException e)
        {

        }

    }

    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldx + gp.player.screenx;
            int screenY = worldY - gp.player.worldy + gp.player.screeny;
           /* ///Stop moving camera at the edge


            if(gp.player.screenx > gp.player.worldx)
            {
                screenX = worldX;
            }
            if(gp.player.screeny > gp.player.worldy)
            {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenx;
            if(rightOffset > gp.worldWidth - gp.player.worldx)
            {
                screenX = gp.screenWidth -(gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screeny;
            if(bottomOffset > gp.worldHeight - gp.player.worldy)
            {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            */
            if( worldX + gp.tileSize > gp.player.worldx - gp.player.screenx &&
                worldX - gp.tileSize < gp.player.worldx + gp.player.screenx &&
                worldY + gp.tileSize > gp.player.worldy - gp.player.screeny &&
                worldY - gp.tileSize < gp.player.worldy + gp.player.screeny)
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
           /* else if(gp.player.screenx > gp.player.worldx ||
                    gp.player.screeny > gp.player.worldy ||
                    rightOffset > gp.worldWidth - gp.player.worldx ||
                    bottomOffset > gp.worldHeight - gp.player.worldy)
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            */
            worldCol++;


            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }



    }

}
