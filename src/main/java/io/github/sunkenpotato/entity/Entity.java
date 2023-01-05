package io.github.sunkenpotato.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public List<BufferedImage> up, down, left, right;
    public Direction direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;


}
