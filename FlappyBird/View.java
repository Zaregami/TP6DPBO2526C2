import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class View extends JPanel {
    private Logic logic;
    private Image backgroundImage;
    private JLabel scoreLabel;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(logic.frameWidth, logic.frameHeight));
        setFocusable(true);
        addKeyListener(logic);

        // load background
        File bgFile = new File("assets/background.png");
        if (bgFile.exists()) {
            backgroundImage = new ImageIcon("assets/background.png").getImage();
        } else {
            var url = getClass().getResource("/assets/background.png");
            if (url == null) url = getClass().getResource("assets/background.png");
            if (url != null) {
                backgroundImage = new ImageIcon(url).getImage();
            }
        }

        // label skor
        setLayout(new BorderLayout());
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(scoreLabel, BorderLayout.NORTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // gambar background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, logic.frameWidth, logic.frameHeight, null);
        } else {
            g.setColor(new Color(112, 197, 206));
            g.fillRect(0, 0, logic.frameWidth, logic.frameHeight);
        }

        // gambar player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // gambar pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // update skor
        scoreLabel.setText("Score: " + logic.getScore());

        // tampilan game over
        if (logic.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, logic.frameWidth, logic.frameHeight);

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm1 = g.getFontMetrics();
            String goText = "GAME OVER";
            int x1 = (logic.frameWidth - fm1.stringWidth(goText)) / 2;
            g.drawString(goText, x1, logic.frameHeight / 2 - 30);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            FontMetrics fm2 = g.getFontMetrics();
            String scoreText = "Final Score: " + logic.getScore();
            int x2 = (logic.frameWidth - fm2.stringWidth(scoreText)) / 2;
            g.drawString(scoreText, x2, logic.frameHeight / 2 + 10);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.PLAIN, 15));
            FontMetrics fm3 = g.getFontMetrics();
            String rstText = "Press 'R' to Restart";
            int x3 = (logic.frameWidth - fm3.stringWidth(rstText)) / 2;
            g.drawString(rstText, x3, logic.frameHeight / 2 + 45);
        }
    }
}
