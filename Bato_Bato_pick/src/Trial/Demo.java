package Trial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.border.EmptyBorder;

public class Demo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int playerScore = 0;
    private int cpuScore = 0;
    private JLabel playerIcon;
    private JLabel cpuIcon;
    private final leaderboard leaderboard;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Demo frame = new Demo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Demo() {
        setTitle("BATO-BATO PICK INTERACTIVE GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        leaderboard = new leaderboard();

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(173, 216, 230));
        JLabel titleLabel = new JLabel("BATO-BATO PICK INTERACTIVE GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Combined Score and Game Panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Score panel
        JPanel scorePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JLabel playerLabel = new JLabel("You: 0", JLabel.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel cpuLabel = new JLabel("CPU: 0", JLabel.CENTER);
        cpuLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scorePanel.add(playerLabel);
        scorePanel.add(cpuLabel);
        centerPanel.add(scorePanel, BorderLayout.NORTH);

        // Gameplay panel
        JPanel gamePanel = new JPanel(new GridLayout(1, 3, 20, 0));
        gamePanel.setBackground(new Color(240, 248, 255));
        playerIcon = new JLabel(new ImageIcon(getClass().getResource("PlayerDefault.png")), JLabel.CENTER);
        JLabel vsLabel = new JLabel("VS", JLabel.CENTER);
        vsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cpuIcon = new JLabel(new ImageIcon(getClass().getResource("ComDefault.png")), JLabel.CENTER);
        gamePanel.add(playerIcon);
        gamePanel.add(vsLabel);
        gamePanel.add(cpuIcon);
        centerPanel.add(gamePanel, BorderLayout.CENTER);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 20));
        JButton rockButton = new JButton("ROCK");
        rockButton.setBackground(new Color(255, 105, 97));
        JButton paperButton = new JButton("PAPER");
        paperButton.setBackground(new Color(255, 255, 102));
        JButton scissorsButton = new JButton("SCISSORS");
        scissorsButton.setBackground(new Color(135, 206, 250));
        JButton leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setBackground(new Color(144, 238, 144));
        JButton resetButton = new JButton("RESET LEADERBOARD");
        resetButton.setBackground(new Color(220, 220, 220));
        JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(new Color(220, 220, 220));

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners for buttons
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerChoice = e.getActionCommand();
                String cpuChoice = getRandomChoice();

                updateIcons(playerChoice, cpuChoice);
                String result = determineWinner(playerChoice, cpuChoice);
                JOptionPane.showMessageDialog(contentPane, "You chose " + playerChoice + ", CPU chose " + cpuChoice + ".\n" + result);
                playerLabel.setText("You: " + playerScore);
                cpuLabel.setText("CPU: " + cpuScore);
            }
        };

        rockButton.setActionCommand("Rock");
        rockButton.addActionListener(buttonListener);

        paperButton.setActionCommand("Paper");
        paperButton.addActionListener(buttonListener);

        scissorsButton.setActionCommand("Scissors");
        scissorsButton.addActionListener(buttonListener);

        leaderboardButton.addActionListener(e -> leaderboard.showLeaderboard());
        resetButton.addActionListener(e -> leaderboard.resetLeaderboard());

        exitButton.addActionListener(e -> {
            leaderboard.saveScore(playerScore);
            System.exit(0);
        });
    }

    private String getRandomChoice() {
        String[] choices = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        return choices[random.nextInt(3)];
    }

    private String determineWinner(String playerChoice, String cpuChoice) {
        if (playerChoice.equals(cpuChoice)) {
            return "It's a tie!";
        } else if ((playerChoice.equals("Rock") && cpuChoice.equals("Scissors")) ||
                   (playerChoice.equals("Paper") && cpuChoice.equals("Rock")) ||
                   (playerChoice.equals("Scissors") && cpuChoice.equals("Paper"))) {
            playerScore++;
            return "You win this round!";
        } else {
            cpuScore++;
            return "CPU wins this round!";
        }
    }

    private void updateIcons(String playerChoice, String cpuChoice) {
        playerIcon.setIcon(loadIcon(playerChoice.toLowerCase() + ".png"));
        cpuIcon.setIcon(loadIcon("Com" + cpuChoice.toLowerCase() + ".png"));
    }

    private ImageIcon loadIcon(String fileName) {
        java.net.URL imgURL = getClass().getResource(fileName);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + fileName);
            return new ImageIcon();
        }
    }
}
