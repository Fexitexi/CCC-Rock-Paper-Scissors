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
            result = solveLevel3(line);

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
        System.out.println("rocks: " + rocks);
        int papers = Integer.parseInt(line.substring(line.indexOf('R') + 2, line.indexOf('P')));
        System.out.println("papers: " + papers);
        int scissors = Integer.parseInt(line.substring(line.indexOf('P') + 2, line.indexOf('S')));
        System.out.println("scissors: " + scissors);
        System.out.println("total: " + (rocks + papers + scissors));

        Tournament t = new Tournament(rocks, papers, scissors, new Scissors());

        return t.getRoot().getStandingsAtLevel(t.getLevels());
    }
}
