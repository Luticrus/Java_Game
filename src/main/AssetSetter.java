package main;

import Mob.Mob_BlueSlime;
import Mob.Mob_GreenSlime;
import Mob.Mob_RedSlime;
import entity.NPC_OldMan;
import object.*;

public class AssetSetter
{
    GamePanel gp;
    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setObject()
    {
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldx = gp.tileSize*84;
        gp.obj[0].worldy = gp.tileSize*39;

        gp.obj[1] = new OBJ_Heart(gp);
        gp.obj[1].worldx = gp.tileSize*84;
        gp.obj[1].worldy = gp.tileSize*41;

        gp.obj[2] = new OBJ_Heart(gp);
        gp.obj[2].worldx = gp.tileSize*13;
        gp.obj[2].worldy = gp.tileSize*32;

        gp.obj[3] = new OBJ_Heart(gp);
        gp.obj[3].worldx = gp.tileSize*13;
        gp.obj[3].worldy = gp.tileSize*30;

        gp.obj[4] = new OBJ_Chest(gp);
        gp.obj[4].worldx = gp.tileSize*27;
        gp.obj[4].worldy = gp.tileSize*54;

        gp.obj[5] = new OBJ_Chest(gp);
        gp.obj[5].worldx = gp.tileSize*85;
        gp.obj[5].worldy = gp.tileSize*17;

    }

    public void setNPC()
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize*79;
        gp.npc[0].worldy = gp.tileSize*41;
    }

    public  void setMonster()
    {
        int i = 0;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*47;
        i++;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*48;
        i++;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*49;
        i++;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*50;
        i++;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*51;
        i++;
        gp.monster[i] = new Mob_GreenSlime(gp);
        gp.monster[i].worldx = gp.tileSize*79;
        gp.monster[i].worldy = gp.tileSize*52;
        i++;


        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*17;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*18;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*19;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*20;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*21;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*76;
        gp.monster[i].worldy = gp.tileSize*22;
        i++;


        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*16;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*17;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*18;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*19;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*20;
        i++;
        gp.monster[i] = new Mob_BlueSlime(gp);
        gp.monster[i].worldx = gp.tileSize*26;
        gp.monster[i].worldy = gp.tileSize*21;
        i++;

        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*41;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;
        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*42;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;
        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*43;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;
        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*44;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;
        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*45;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;
        gp.monster[i] = new Mob_RedSlime(gp);
        gp.monster[i].worldx = gp.tileSize*46;
        gp.monster[i].worldy = gp.tileSize*54;
        i++;



    }

}
