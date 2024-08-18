package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tournament {
    private TournamentBranch root;
    private List<FightingStyle> participants;
    private int levels;

    public Tournament(String participants) {
        //calculate the levels of the tournament tree
        this.levels = (int) (Math.log(participants.length()) / Math.log(2));

        //parse the participants
        this.participants = new ArrayList<>();
        for (int i = 0; i < participants.length(); i++) {
            this.participants.add(FightingStyle.parseCharToFightingStyle(participants.charAt(i)));
        }

        //create tournament branches recursively
        this.root = new TournamentBranch(this.participants, 0);
    }

    public Tournament(String participants, FightingStyle winningStyle) {
        //calculate the levels of the tournament tree
        this.levels = (int) (Math.log(participants.length()) / Math.log(2));

        //parse the participants
        this.participants = new ArrayList<>();
        for (int i = 0; i < participants.length(); i++) {
            this.participants.add(FightingStyle.parseCharToFightingStyle(participants.charAt(i)));
        }

        //create tournament branches recursively
        this.root = new TournamentBranch(this.participants, winningStyle,0, null);
    }

    public Tournament(int rocks, int papers, int scissors, FightingStyle winningStyle, int safeRound){
        //attributing winning, neutral and prohibited Style and amount
        FightingStyle prohibitedStyle;
        FightingStyle neutralStyle;
        HashMap<FightingStyle, Integer> amountMap = new HashMap<>();

        switch (winningStyle) {
            case Paper paper -> {
                prohibitedStyle = Scissors.getInstance();
                neutralStyle = Rock.getInstance();
                amountMap.put(winningStyle, papers);
                amountMap.put(prohibitedStyle, scissors);
                amountMap.put(neutralStyle, rocks);
            }
            case Rock rock -> {
                prohibitedStyle = Paper.getInstance();
                neutralStyle = Scissors.getInstance();
                amountMap.put(winningStyle, rocks);
                amountMap.put(prohibitedStyle, papers);
                amountMap.put(neutralStyle, scissors);
            }
            case Scissors scissor -> {
                prohibitedStyle = Rock.getInstance();
                neutralStyle = Paper.getInstance();
                amountMap.put(winningStyle, scissors);
                amountMap.put(prohibitedStyle, rocks);
                amountMap.put(neutralStyle, papers);
            }
            default -> throw new RuntimeException("Incorrect instance of winningStyle");
        }

        //creating participant list iteratively
        List<FightingStyle> participants = new ArrayList<>();
        //first, get all prohibited ones out of the way while neutralising them
        while (amountMap.get(prohibitedStyle) >= Math.pow(2, safeRound + 1) - 1) {
            for (int i = 0; i < Math.pow(2, safeRound + 1) - 1; i++) {
                participants.add(prohibitedStyle);
            }
            amountMap.replace(prohibitedStyle, amountMap.get(prohibitedStyle) - (int) Math.pow(2, safeRound + 1) - 1);
            participants.add(neutralStyle);
            amountMap.replace(neutralStyle, amountMap.get(neutralStyle) - 1);
        }
        //clear edge case (2 prohibited, 1 neutral)
        if(amountMap.get(neutralStyle) >= 1){
            participants.add(neutralStyle);
            amountMap.replace(neutralStyle, amountMap.get(neutralStyle) - 1);
            //if the edge case happens, in order to be a valid input, there must be at least 5 winning type remaining
        }
        //clean up the remaining ones
        for (int i = 0; i < amountMap.get(prohibitedStyle); i++) {
            participants.add(prohibitedStyle);
        }
        for (int i = 0; i < amountMap.get(neutralStyle); i++) {
            participants.add(neutralStyle);
        }
        for (int i = 0; i < amountMap.get(winningStyle); i++) {
            participants.add(winningStyle);
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        //building tournament tree
        this.root = new TournamentBranch(participants, 0);
    }

    public Tournament(int rocks, int papers, int scissors, FightingStyle winningStyle) {
        List<FightingStyle> participants = new ArrayList<>();

        for (int i = 0; i < rocks; i++) {
            participants.add(Rock.getInstance());
        }
        for (int i = 0; i < papers; i++) {
            participants.add(Paper.getInstance());
        }
        for (int i = 0; i < scissors; i++) {
            participants.add(Scissors.getInstance());
        }

        switch (winningStyle) {
            case Paper paper -> {
                participants = buildWinner(participants, Paper.getInstance());
            }
            case Rock rock -> {
                participants = buildWinner(participants, Rock.getInstance());
            }
            case Scissors scissor -> {
                System.out.println("Initital: ");
                for (FightingStyle fightingStyle : participants) {
                    System.out.print(fightingStyle.getChar());
                }
                System.out.println();
                participants = buildWinner(participants, Scissors.getInstance());
            }
            default -> {
                throw new RuntimeException("Incorrect FightingStyle as input");
            }
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        this.root = new TournamentBranch(this.participants, 0);
    }

    public Tournament(int rocks, int papers, int scissors, int spocks, int lizards, FightingStyle winningStyle) {
        List<FightingStyle> participants = new ArrayList<>();

        for (int i = 0; i < rocks; i++) {
            participants.add(Rock.getInstance());
        }
        for (int i = 0; i < papers; i++) {
            participants.add(Paper.getInstance());
        }
        for (int i = 0; i < scissors; i++) {
            participants.add(Scissors.getInstance());
        }
        for (int i = 0; i < spocks; i++) {
            participants.add(Spock.getInstance());
        }
        for (int i = 0; i < lizards; i++) {
            participants.add(Lizard.getInstance());
        }

        switch (winningStyle) {
            case Paper paper -> {
                participants = buildWinner(participants, Paper.getInstance());
            }
            case Rock rock -> {
                participants = buildWinner(participants, Rock.getInstance());
            }
            case Scissors scissor -> {
                System.out.println("Initital: ");
                for (FightingStyle fightingStyle : participants) {
                    System.out.print(fightingStyle.getChar());
                }
                System.out.println();
                participants = buildWinner(participants, Scissors.getInstance());
            }
            case Spock spock -> {
                participants = buildWinner(participants, Spock.getInstance());
            }
            case Lizard lizard -> {
                participants = buildWinner(participants, Lizard.getInstance());
            }
            default -> {
                throw new RuntimeException("Incorrect FightingStyle as input");
            }
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        this.root = new TournamentBranch(this.participants, 0);
    }

    public TournamentBranch getRoot(){
        return this.root;
    }

    public int getLevels(){
        return this.levels;
    }

    public List<FightingStyle> buildWinner(List<FightingStyle> participants, FightingStyle winningStyle) {
        //base case, if the input already produces the winningStyle (or better as defined by the finalWinner comparator), then it will just be returned
        if (Scissors.getInstance().getComparator().compare(winningStyle, new Tournament(FightingStyle.getCharsFromList(participants)).root.calcParticipant()) <= 0) {
            return participants;
        }

        //difficult case
        List<FightingStyle> lList = new ArrayList<>();

        //sort the List by the comparator
        participants.sort(Scissors.getInstance().getComparator());

        FightingStyle leftWinner;

        //split the List
        if (participants.stream().anyMatch(fightingStyle -> fightingStyle.equals(winningStyle.getDoubleBeats()))) {
            //adding one neutralises the whole half
            leftWinner = winningStyle.getDoubleBeats();
            lList.add(leftWinner);
            participants.remove(leftWinner);

        } else {
            leftWinner = winningStyle.getBeats();
            lList.add(leftWinner);
            participants.remove(leftWinner);

            if (participants.stream().anyMatch(f -> f.equals(leftWinner.getsDoubleBeatenBy()))) {
                //if there is at least one which can beat winning style, then ensure validity by adding enough intermediate neutralizers
                for (int i = 0; i < Math.log(participants.size() / 2.0) / Math.log(2); i++) {
                    if(participants.remove(leftWinner.getDoubleBeats())) {
                        lList.add(leftWinner.getDoubleBeats());
                    } else {
                        //if there are less intermediate neutralizers, there also have to be less winningStyle beaters
                        break;
                    }
                }
            }
        }

        while (lList.size() < participants.size()) {
            lList.add(participants.removeFirst());
        }

        List<FightingStyle> res = buildWinner(lList, leftWinner);
        res.addAll(buildWinner(participants, winningStyle));
        return res;
    }
}