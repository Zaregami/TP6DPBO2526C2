import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int frameWidth = 360;
        int frameHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Logic logic = new Logic();
        View view = new View(logic);
        logic.setView(view);

        // panel menu awal
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(112, 197, 206));

        menuPanel.add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("FLAPPY BIRD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 215, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel subtitleLabel = new JLabel("DPBO 2025/2026");
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(subtitleLabel);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // tombol mulai game
        JButton startBtn = new JButton("Start Game");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setPreferredSize(new Dimension(160, 42));
        startBtn.setMaximumSize(new Dimension(160, 42));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.setFocusPainted(false);
        startBtn.setBackground(new Color(115, 191, 46));
        startBtn.setForeground(Color.WHITE);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(menuPanel);
                frame.add(view);
                frame.revalidate();
                frame.repaint();
                view.requestFocus();
                logic.startGame();
            }
        });
        menuPanel.add(startBtn);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // tombol keluar
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.setPreferredSize(new Dimension(160, 42));
        exitBtn.setMaximumSize(new Dimension(160, 42));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setFocusPainted(false);
        exitBtn.setBackground(new Color(220, 53, 69));
        exitBtn.setForeground(Color.WHITE);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuPanel.add(exitBtn);

        menuPanel.add(Box.createVerticalGlue());

        frame.add(menuPanel);
        frame.setVisible(true);
    }
}
