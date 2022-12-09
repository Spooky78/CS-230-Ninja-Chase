package com.example.cs230;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//TODO: Change score into score[]
//TODO: Change high score table so it needs an input of level index
//TODO: Link with the game.

public class AllProfile {
    //score in list of levels
    private static final String PROFILE_FILE_PATH = "CS-230/src/main/resources/Levels/Profile.txt";
    private static final String SCORE_FILE_PATH = "CS-230/src/main/resources/Levels/Score.txt";
    private static final boolean[] DEFAULT_LEVEL_LIST = {true, false, false, false};
    private static ArrayList<PlayerProfile> allProfile = new ArrayList<>();
    private static ArrayList<PlayerScore> allScores = new ArrayList<>();
    private static ArrayList<String> nameList = new ArrayList<>();
    private static ArrayList<Integer> scoreList = new ArrayList<>();

    private final int MAX_HIGH_SCORE_SIZE = 10;
    private ArrayList<Integer> sortedScoreList = new ArrayList<>();
    private ArrayList<Integer> allProfileIndex = new ArrayList<>();
    private int[] tenHighScore = new int[10];
    private String[] tenHighScoreName = new String[10];

    //When the game started, it loads all the profile in the file path.
    public static void loadProfile() {
        boolean[] isLevelUnlocked;
        nameList.clear();
        try {
            File playerProfile = new File(PROFILE_FILE_PATH);
            Scanner playerProfileReader = new Scanner(playerProfile);
            while (playerProfileReader.hasNextLine()) {
                String readLine = playerProfileReader.nextLine();
                String[] params = readLine.split(", ");
                String name = params[0];
                String booleanInString = params[1];
                isLevelUnlocked = stringToBoolean(booleanInString);
                allProfile.add(new PlayerProfile(name, isLevelUnlocked));
                nameList.add(name);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadScore() {
        String playerName;
        int playerScore = 0;
        try {
            File playerScoreFile = new File(SCORE_FILE_PATH);
            Scanner playerScoreReader = new Scanner(playerScoreFile);
            while (playerScoreReader.hasNextLine()) {
                playerName = playerScoreReader.nextLine();
                allScores.add(new PlayerScore(playerName, playerScore));
                scoreList.add(playerScore);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //if not existed, add a name as well as the profile index
    public static void addName(String name) {
        //check if the name is existed
        if (!nameList.contains(name)) {
            //if not exists, add a new name.
            allProfile.add(new PlayerProfile(name, DEFAULT_LEVEL_LIST));
            nameList.add(name);
        } else if (nameList.contains(name)) {
            System.out.println("Sorry, the name has been created by someone else.");
        } else {
            System.out.println("Sorry, there seems to be some Errors.");
        }
        writeProfileInTxt();
    }

    public void addScore(String playerName, int playerScore) {
        allScores.add(new PlayerScore(playerName, playerScore));
        scoreList.add(playerScore);
    }

    //return string array list of names to the game panel
    public static ArrayList<String> getAllNamesInProfiles() {
        return nameList;
    }

    //TODO: create a deleting profile button and push.
    public static void deleteProfile(String name) {
        for (int i = 0; i < allProfile.size(); i++) {
            if (name.equals(allProfile.get(i).getName())) {
                allProfile.remove(i);
                nameList.remove(name);
            }
        }
        for (int i = 0; i < allScores.size(); i++) {
            if (name.equals(allScores.get(i).getPlayerName())) {
                allScores.remove(i);
                scoreList.remove(i);
            }
        }
        writeProfileInTxt();
        writeScoreInTxt();
    }

    // a method to rewrite every profile
    // when adding/deleting the method, writeTxt has to be run so the txt of the profile have all the profile names.
    public static void writeProfileInTxt() {
        try {
            FileWriter playerProfile = new FileWriter(PROFILE_FILE_PATH);
            for (int i = 0; i < allProfile.size(); i++) {
                playerProfile.write(allProfile.get(i).getName() + ", ");
                for (int j = 0; j < allProfile.get(i).getIsLevelUnlocked().length; j++) {
                    if (allProfile.get(i).getIsLevelUnlocked()[j] == true) {
                        playerProfile.write("true ");
                    } else {
                        playerProfile.write("false ");
                    }
                }
                playerProfile.write("\n");
            }
            playerProfile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeScoreInTxt() {
        try {
            FileWriter playerScores = new FileWriter(SCORE_FILE_PATH);
            for (int i = 0; i < allScores.size(); i++) {
                playerScores.write(allScores.get(i).getPlayerName()+ " ");
                playerScores.write(allScores.get(i).getPlayerScore() + "\n");
            }
            playerScores.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //the level is updated once the level is completed.
    public void updateLevel(String profileName, int levelIndex) {
        for (int i = 0; i < allProfile.size(); i++) {
            if (profileName.equals(allProfile.get(i).getName())) {
                allProfile.get(i).changeLevelIndex(levelIndex);
            }
        }
        writeProfileInTxt();
    }

    public void createAndSortScoreList() {
        sortedScoreList = scoreList;
        Collections.sort(sortedScoreList, Collections.reverseOrder());
    }

    public int[] getTenHighScore() {
        for (int i = 0; i < sortedScoreList.size() && i < MAX_HIGH_SCORE_SIZE; i++) {
            tenHighScore[i] = sortedScoreList.get(i);
        }
        return tenHighScore;
    }

    public String[] getTenHighScoreName() {
        for (int i = 0; i < sortedScoreList.size() && i < MAX_HIGH_SCORE_SIZE; i++) {
            for(int j = 0; j < allScores.size(); j++) {
                if (sortedScoreList.get(i) == allScores.get(j).getPlayerScore() && allProfileIndex.contains(j)) {
                    tenHighScoreName[i] = allScores.get(j).getPlayerName();
                    allProfileIndex.add(j);
                }
            }
        }
        return tenHighScoreName;
    }

    public static ArrayList<String> getNameList() {
        return nameList;
    }

    public static boolean[] stringToBoolean(String string) {
        boolean[] booleanList = new boolean[4];
        switch(string) {
            case "true false false false":
                booleanList[0] = true;
                booleanList[1] = false;
                booleanList[2] = false;
                booleanList[3] = false;
                break;
            case "true true false false":
                booleanList[0] = true;
                booleanList[1] = true;
                booleanList[2] = false;
                booleanList[3] = false;
                break;
            case "true true true false":
                booleanList[0] = true;
                booleanList[1] = true;
                booleanList[2] = true;
                booleanList[3] = false;
                break;
            case "true true true true":
                booleanList[0] = true;
                booleanList[1] = true;
                booleanList[2] = true;
                booleanList[3] = true;
                break;
            default:
                booleanList = DEFAULT_LEVEL_LIST;
        }
        return booleanList;
    }

}
