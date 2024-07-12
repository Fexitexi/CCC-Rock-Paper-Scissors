import fightingStyles.FightingStyle;

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
            result = solveLevel1(line);

            //write result to file
            writer.write(result + "\n");
        }
    }

    public static String solveLevel1(String line) {
        //parse input into object array
        FightingStyle[] fightingStyles = new FightingStyle[2];
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == 'R') {
                fightingStyles[i] = new fightingStyles.Rock();
            } else if (c == 'S') {
                fightingStyles[i] = new fightingStyles.Scissors();
            } else if (c == 'P') {
                fightingStyles[i] = new fightingStyles.Paper();
            }
        }

        //solving
        return fightingStyles[0].fights(fightingStyles[1]) + "";
    }
}
