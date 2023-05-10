package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user)
    {
        this.worldy = worldY;
        this.worldx = worldX;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;

    }
    public void update()
    {
        /*if(user == gp.player)
        {

        }

         */
        if(user!=gp.player)
        {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true)
            {
                damagePlayer(attack+3);
                alive = false;
            }
        }
        switch (direction)
        {
            case "up": worldy -= speed; break;
            case "down":worldy += speed; break;
            case "left":worldx -= speed; break;
            case "right":worldx += speed; break;
        }
        life--;
        if(life <= 0)
        {
            alive = false;
        }
        spriteCounter++;
        if(spriteCounter > 12)
        {
            if(spriteNum == 1)
            {
                spriteNum = 2;
            }
            else if(spriteNum == 2)
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
