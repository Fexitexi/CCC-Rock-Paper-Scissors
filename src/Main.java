import fightingStyles.FightingStyle;
import fightingStyles.Paper;
import fightingStyles.Rock;
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
            //result = solveLevel3(line);
            result = solveLevel4(line);

            //write result to file
            writer.write(result + "\n");
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

    public static String solveLevel3(String line){
        //parse String into amounts
        int rocks = Integer.parseInt(line.substring(0, line.indexOf('R')));
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));

        Tournament t = new Tournament(rocks, papers, scissors, new Scissors(), 2);

        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }

    public static String solveLevel4(String line) {
        int rocks = Integer.parseInt(line.substring(0, line.indexOf('R')));
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));

        Tournament t = new Tournament(rocks, papers, scissors, new Scissors());

        //System.out.println("Rocks: " + rocks + ", Papers: " + papers + ", scissors: " + scissors);
        System.out.println(t.getRoot().calcParticipant().getChar());
        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }
}
