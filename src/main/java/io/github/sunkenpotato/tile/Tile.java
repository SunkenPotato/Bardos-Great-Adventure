package io.github.sunkenpotato.tile;

import java.awt.image.BufferedImage;

/**
 * Tile class
 * Represents any tile (not {@code Object}) in-game.
 */
public class Tile {

    public final BufferedImage image;
    public final boolean collision;
    public final String name;

    public Tile(BufferedImage image, String name) {
        this.image = image;
        this.name = name;
        this.collision = false;
    }

    public Tile(BufferedImage image, String name, boolean collision) {
        this.image = image;
        this.name = name;
        this.collision = collision;
    }
}
