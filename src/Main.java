import fightingStyles.FightingStyle;
import fightingStyles.Scissors;
import tournament.Tournament;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    /**
     * Do not change this method. Method Loads the Config and initializes the IOManager.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Config config = ConfigLoader.loadConfig("config.xml");

        IOManager ioManager = new IOManager(config.getInputPath(), config.getOutputPath(), config.getAllowedExtensions());

        ioManager.setDebug(config.isDebug());
        ioManager.setTargetSpecificLevel(config.getTargetSpecificLevel());
        ioManager.setCleanupOutput(config.getCleanupOutput());

        ioManager.initialize();
        ioManager.execute();
    }


    /**
     * method for reading input from input file and writing solution to output file
     * gets applied to all given input files
     * example of a program to output line length of each line
     * DO NOT CHANGE PARAMETERS OR RETURN TYPE
     *
     * @param reader Scanner object to read from input file
     * @param writer FileWriter object to write to output file
     * @throws IOException if an I/O error occurs
     */
    public static void solve(Scanner reader, FileWriter writer) throws IOException {
        //read lines from input
        //and write to file using: writer.write(result + "\n");
        int n = reader.nextInt();
        reader.nextLine(); //skip linebreak

        String result;
        for(int i = 0;i < n;i++) {
            String line = reader.nextLine();
            //result = solveLevel1(line);
            //result = solveLevel2(line);
            //result = solveLevel3(line, 2);
            //result = solveLevel4(line);
            //result = solveLevel5(line);
            result = solveLevel6(line);

            //write result to file
            writer.write(result + "\n");
        }
    }

    private static void checkOccurences(int rocks, int papers, int scissors, int spocks, int lizard, String line) {
        int total = rocks + papers + scissors + spocks + lizard;
        if (total != line.length()) {
            throw new RuntimeException("Incorrect line length");
        }

        //counting the occurences of the fighting styles
        int[] counts = new int[5];
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'R' -> counts[0]++;
                case 'P' -> counts[1]++;
                case 'S' -> counts[2]++;
                case 'Y' -> counts[3]++;
                case 'L' -> counts[4]++;
                default -> throw new RuntimeException("Incorrect character in line");
            }
        }
        if (counts[0] != rocks) {
            throw new RuntimeException("Incorrect amount of rocks");
        }
        if (counts[1] != papers) {
            throw new RuntimeException("Incorrect amount of papers");
        }
        if (counts[2] != scissors) {
            throw new RuntimeException("Incorrect amount of scissors");
        }
        if (counts[3] != spocks) {
            throw new RuntimeException("Incorrect amount of spocks");
        }
        if (counts[4] != lizard) {
            throw new RuntimeException("Incorrect amount of lizard");
        }
    }

    public static String solveLevel1(String line) {
        //parse input into object array
        FightingStyle[] fightingStyles = new FightingStyle[2];
        for (int i = 0; i < line.length(); i++) {
            fightingStyles[i] = FightingStyle.parseCharToFightingStyle(line.charAt(i));
        }

        //solving
        return fightingStyles[0].fights(fightingStyles[1]).getChar() + "";
    }

    public static String solveLevel2(String line){
        //parse String into Tournament
        Tournament t = new Tournament(line);

        return t.getRoot().getStandingsAtLevel(t.getLevels() - 2);
    }

    public static String solveLevel3(String line, int safeRound){
        //parse String into amounts
        int rocks = Integer.parseInt(line.substring(0, line.indexOf('R')));
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));

        Tournament t = new Tournament(rocks, papers, scissors, Scissors.getInstance(), safeRound);
        checkSolutionLevel3(rocks, papers, scissors, t.getRoot().getStandingsAtLevel(safeRound), Scissors.getInstance());

        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }

    private static void checkSolutionLevel3(int rocks, int papers, int scissors, String line, FightingStyle WinningStyle){
        checkOccurences(rocks, papers, scissors, 0, 0, line);
        //checking if the winner is scissors
        if (!line.contains("S") || line.contains("R")) {
            throw new RuntimeException("Scissors should win");
        }
    }

    public static String solveLevel4(String line) {
        int rocks = Integer.parseInt(line.substring(0, line.indexOf('R')));
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));

        Tournament t = new Tournament(rocks, papers, scissors, Scissors.getInstance());
        checkSolutionLevel4(rocks, papers, scissors, t.getRoot().getStandingsAtLevel(t.getLevels()));
        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }

    private static void checkSolutionLevel4(int rocks, int papers, int scissors, String line){
        checkOccurences(rocks, papers, scissors, 0, 0, line);
        //checking if the winner is scissors
        Tournament t = new Tournament(line);
        if (t.getRoot().calcParticipant().getChar() != 'S') {
            throw new RuntimeException("Scissors should win");
        }
    }

    public static String solveLevel5(String line) {
        int rocks = Integer.parseInt(line.substring(0, line.indexOf('R')));
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));
        int spock = Integer.parseInt(line.substring(line.indexOf('S') + 2, line.indexOf('Y')));
        int lizard = Integer.parseInt(line.substring(line.indexOf('Y') + 2, line.indexOf('L')));

        Tournament t = new Tournament(rocks, papers, scissors, spock, lizard, Scissors.getInstance());
        checkSolutionLevel5(rocks, papers, scissors, spock, lizard, t.getRoot().getStandingsAtLevel(t.getLevels()));
        System.out.println("successful generation");
        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }

    private static void checkSolutionLevel5(int rocks, int papers, int scissors, int spocks, int lizard, String line){
        checkOccurences(rocks, papers, scissors, spocks, lizard, line);
        Tournament t = new Tournament(line);
        if (t.getRoot().calcParticipant().getChar() != 'S') {
            throw new RuntimeException("Scissors should win");
        }
    }

    private static String solveLevel6(String line) {
        return "";
    }
}
