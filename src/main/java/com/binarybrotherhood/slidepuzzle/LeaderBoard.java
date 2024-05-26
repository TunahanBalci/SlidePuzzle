package com.binarybrotherhood.slidepuzzle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LeaderBoard {

    File leaderboardFile = new File("src/main/resources/leaderboard.properties");
    private Properties leaderboard = new Properties();
    private int[] time = new int[5]; // time in ms
    private String[] name = new String[5]; // record holder name

    public int[] getTime() {
        return time;
    }

    public String[] getName() {
        return name;
    }

    public void registerRecord(String name, int time, int index) {
        this.name[index] = name;
        this.time[index] = time;

        try {
            try {
                leaderboard.setProperty("PLAYER" + (index + 1) + "_NAME", name);
            } catch (Exception ee) {
                System.out.println("ERROR: Couldn't register name: " + ee.getMessage());
            }

            try {
                leaderboard.setProperty("PLAYER" + (index + 1) + "_TIME", Integer.toString(time));
            } catch (Exception ee) {
                System.out.println("ERROR: Couldn't register time: " + ee.getMessage());
            }

            saveChanges();

        } catch (Exception e) {
            System.out.println("ERROR: Couldn't register record: " + e.getMessage());
        }
    }

    public void initialize() {
        if (!leaderboardFile.exists()) {
            createLeaderboard();
        }

        try {
            FileInputStream configFileReader = new FileInputStream(leaderboardFile);
            leaderboard.load(configFileReader);

        } catch (Exception e) {
            createLeaderboard();
            System.out.println("ERROR: Couldn't read leaderboard file: " + e.getMessage());
        }

        for (int i = 0; i < 5; i++) {
            time[i] = 0;
            name[i] = "Player" + i;
        }

        for (int i = 0; i < 5; i++) {
            try {
                time[i] = Integer.parseInt(leaderboard.getProperty(("PLAYER" + (i + 1)) + "_TIME"));
                name[i] = leaderboard.getProperty(("PLAYER" + (i + 1)) + "_NAME");
            } catch (Exception e) {
                createLeaderboard();
                System.out.println("ERROR: Couldn't read leaderboard at index " + i + ": " + e.getMessage());
            }
        }
    }

    public void createLeaderboard() {
        try {
            leaderboardFile.getParentFile().mkdir();
            leaderboardFile.createNewFile();

            leaderboard.setProperty("PLAYER1_NAME", "Player1");
            leaderboard.setProperty("PLAYER1_TIME", "0");
            saveChanges();
            leaderboard.setProperty("PLAYER2_NAME", "Player2");
            leaderboard.setProperty("PLAYER2_TIME", "0");
            saveChanges();
            leaderboard.setProperty("PLAYER3_NAME", "Player3");
            leaderboard.setProperty("PLAYER3_TIME", "0");
            saveChanges();
            leaderboard.setProperty("PLAYER4_NAME", "Player4");
            leaderboard.setProperty("PLAYER4_TIME", "0");
            saveChanges();
            leaderboard.setProperty("PLAYER5_NAME", "Player5");
            leaderboard.setProperty("PLAYER5_TIME", "0");
            saveChanges();

        } catch (Exception e) {
            System.out.println("ERROR: Couldn't create leaderboard file");
        }
    }

    public void saveChanges() {
        try (FileOutputStream out = new FileOutputStream("src/main/resources/leaderboard.properties")) {
            leaderboard.store(out, "Saved changes in leaderboard file");
        } catch (IOException exception) {
            System.out.println("ERROR IN 'LEADERBOARD.PROPERTIES': \n" + exception.getMessage());
            createLeaderboard();
        }
    }

    public LeaderBoard getLeaderBoard() {
        initialize();
        return this;
    }
}
