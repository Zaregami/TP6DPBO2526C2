import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Logic implements ActionListener, KeyListener {
    // ukuran frame
    int frameWidth = 360;
    int frameHeight = 640;

    // posisi & ukuran player
    int playerStartX = frameWidth / 2;
    int playerStartY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    // posisi & ukuran pipa
    int pipeStartX = frameWidth;
    int pipeStartY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    // kecepatan pipa ke kiri
    int pipeVelocityX = -2;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    boolean gameOver = false;
    double score = 0;

    public Logic() {
        // load gambar & inisialisasi player
        birdImage = getImageAsset("assets/bird.png");
        player = new Player(playerStartX, playerStartY, playerWidth, playerHeight, birdImage);

        // load gambar pipa
        lowerPipeImage = getImageAsset("assets/lowerPipe.png");
        upperPipeImage = getImageAsset("assets/upperPipe.png");
        pipes = new ArrayList<Pipe>();

        // spawn pipa setiap 1.5 detik
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Pipa");
                placePipes();
            }
        });

        // timer loop 60 fps
        gameLoop = new Timer(1000 / 60, this);
    }

    private Image getImageAsset(String path) {
        URL url = getClass().getResource(path);
        if (url == null) url = getClass().getResource("/" + path);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(path).getImage();
        }
        return new ImageIcon(path).getImage();
    }

    public void startGame() {
        gameOver = false;
        pipes.clear();
        score = 0;
        player.setPosX(playerStartX);
        player.setPosY(playerStartY);
        player.setVelocityY(0);
        gameLoop.start();
        pipesCooldown.start();
        Sound.playBGM("bgm.wav");
    }

    public void restartGame() {
        startGame();
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return (int) score;
    }

    // pasang pipa atas & bawah acak
    public void placePipes() {
        int randomPosY = (int) (pipeStartY - (pipeHeight / 4) - Math.random() * (pipeHeight / 4));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartX, randomPosY + openingSpace + pipeHeight, pipeWidth,
                pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // cek tabrakan burung dengan pipa
    public boolean collision(Player a, Pipe b) {
        return a.getPosX() < b.getPosX() + b.getWidth() &&
               a.getPosX() + a.getWidth() > b.getPosX() &&
               a.getPosY() < b.getPosY() + b.getHeight() &&
               a.getPosY() + a.getHeight() > b.getPosY();
    }

    public void move() {
        if (gameOver) return;

        // update posisi player dengan gravitasi
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // gerak pipa ke kiri
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // tambah skor kalau burung lewat pipa
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true);
                score += 0.5;
                Sound.play("point.wav");
            }

            // tabrakan dengan pipa
            if (collision(player, pipe)) {
                triggerGameOver();
            }
        }

        // jatuh ke bawah
        if (player.getPosY() > frameHeight - player.getHeight() - 40) {
            triggerGameOver();
        }
    }

    private void triggerGameOver() {
        if (!gameOver) {
            gameOver = true;
            gameLoop.stop();
            pipesCooldown.stop();
            Sound.stopBGM();
            Sound.play("hit.wav");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // spasi untuk lompat
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                player.setVelocityY(-9);
                Sound.play("jump.wav");
            }
        }

        // tombol R untuk restart
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
