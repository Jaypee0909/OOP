package Trial;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class leaderboard {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    public void saveScore(int score) {
        String playerName = JOptionPane.showInputDialog("Enter your name for the leaderboard:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE, true))) {
                writer.write(playerName + " - " + score);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showLeaderboard() {
        StringBuilder leaderboard = new StringBuilder("Leaderboard:\n");
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            leaderboard.append("No leaderboard data available.");
        }

        if (!scores.isEmpty()) {
            scores.sort((a, b) -> Integer.compare(
                Integer.parseInt(b.split(" - ")[1]),
                Integer.parseInt(a.split(" - ")[1])
            ));
            for (String score : scores) {
                leaderboard.append(score).append("\n");
            }
            
        }

        JOptionPane.showMessageDialog(null, leaderboard.toString());
    }
    
}
