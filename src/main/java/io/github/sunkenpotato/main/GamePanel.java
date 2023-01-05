package io.github.sunkenpotato.main;

import io.github.sunkenpotato.entity.Player;
import io.github.sunkenpotato.object.Object;
import io.github.sunkenpotato.tile.TileHandler;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


@SuppressWarnings("all")
public class GamePanel extends JPanel implements Runnable{
    Properties settings = new Properties();
    // Dev settings
    boolean devMode = true;

    // screen settings
    final int originalTileSize = 36; // 16x16 Tile
    public static final float scale = 1.5f;

    // tile settings and update ratex
    public final int tileSize = (int) (originalTileSize * scale); // 48 x 48 Tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    final int FPS = 60;

    // system logic
    TileHandler tileHandler = new TileHandler(this);
    KeyHandler keyHandler = new KeyHandler(this);
    Thread gameThread;

    // entity and object
    public SoundHandler soundH = new SoundHandler();
    public Player player = new Player(this, keyHandler, soundH);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetHandler assetHandler = new AssetHandler(this);
    public Object[] objects = new Object[10];


    // Set player's default position
    int playerX = screenWidth / 2;
    int playerY = screenHeight / 2;
    int playerSpeed = 3;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public GamePanel() {
        try {
            settings.load(new FileInputStream(new File("settings.properties")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    /**
     * Setup for Objects
     */
    public void setup() {
        assetHandler.setObject();

    }

    /**
     * Starts game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Game thread code
     * @see Runnable
     * @see Thread
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread.isAlive()) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // Runs 60x a second
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            // Runs once a second
            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }

    }

    /**
     * Update information
     */
    public void update() {
        player.update();
    }

    /**
     * Paint screen
     * @param graphics
     */
    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // TILES
        tileHandler.render(graphics2D);

        // Special Objects
        for(var obj:objects) {
            if(obj != null) {
                obj.render(graphics2D, this);
            }
        }

        // PLAYER
        player.render(graphics2D);


        graphics2D.dispose();
    }

    public void playMusic(int index) {
        soundH.openAudioF(index);
        soundH.play();
        soundH.loop();
    }

    public void stopMusic() {
        soundH.stop();
    }

    public void playSf(int index) {
        soundH.openAudioF(index);
        soundH.play();
    }
}
