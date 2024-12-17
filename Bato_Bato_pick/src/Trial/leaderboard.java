package Trial;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class leaderboard {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Save player score with timestamp
    public void saveScore(int score) {
        String playerName = JOptionPane.showInputDialog("Enter your name for the leaderboard:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            String currentTime = LocalDateTime.now().format(TIME_FORMAT);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE, true))) {
                writer.write(playerName + " - " + score + " - " + currentTime);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Show leaderboard data sorted by score and game time
    public void showLeaderboard() {
        StringBuilder leaderboard = new StringBuilder("Leaderboard:\n");
        List<LeaderboardEntry> scores = new ArrayList<>();

        // Load leaderboard data
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 3) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    String time = parts[2];
                    scores.add(new LeaderboardEntry(name, score, time));
                }
            }
        } catch (IOException e) {
            leaderboard.append("No leaderboard data available.\n");
        }

        // Sort by score descending, then by time ascending
        scores.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed()
                              .thenComparing(LeaderboardEntry::getGameTime));

        // Append sorted data
        int rank = 1;
        for (LeaderboardEntry entry : scores) {
            leaderboard.append(rank++).append(". ")
                       .append(entry.getName())
                       .append(" - Score: ").append(entry.getScore())
                       .append(" - Time: ").append(entry.getFormattedTime())
                       .append("\n");
        }

        // Display leaderboard
        JOptionPane.showMessageDialog(null, leaderboard.toString());
    }

    // Reset leaderboard data
    public void resetLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            // Overwrite file with empty content
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Leaderboard has been reset.");
    }

    // Inner class to represent a leaderboard entry
    private static class LeaderboardEntry {
        private final String name;
        private final int score;
        private final LocalDateTime gameTime;

        public LeaderboardEntry(String name, int score, String time) {
            this.name = name;
            this.score = score;
            this.gameTime = LocalDateTime.parse(time, TIME_FORMAT);
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public LocalDateTime getGameTime() {
            return gameTime;
        }

        public String getFormattedTime() {
            return gameTime.format(TIME_FORMAT);
        }
    }
}
