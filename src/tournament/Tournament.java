package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        this.root = createFirstRoundRec(this.participants, 0);
    }

    public Tournament(int rocks, int papers, int scissors, FightingStyle winningStyle, int safeRound){
        //attributing winning, neutral and prohibited Style and amount
        FightingStyle prohibitedStyle = winningStyle.getsBeatenBy();
        FightingStyle neutralStyle = prohibitedStyle.getsBeatenBy();
        HashMap<FightingStyle, Integer> amountMap = new HashMap<>();

        switch (winningStyle) {
            case Paper paper -> {
                amountMap.put(winningStyle, papers);
                amountMap.put(prohibitedStyle, scissors);
                amountMap.put(neutralStyle, rocks);
            }
            case Rock rock -> {
                amountMap.put(winningStyle, rocks);
                amountMap.put(prohibitedStyle, papers);
                amountMap.put(neutralStyle, scissors);
            }
            case Scissors scissor -> {
                amountMap.put(winningStyle, scissors);
                amountMap.put(prohibitedStyle, rocks);
                amountMap.put(neutralStyle, papers);
            }
            default -> throw new RuntimeException("Incorrect instance of winningStyle");
        }

        //creating participant list iteratively
        List<FightingStyle> participants = new ArrayList<>();
        //first, get all prohibited ones out of the way while neutralising them
        while (amountMap.get(prohibitedStyle) >= 3) {
            participants.add(prohibitedStyle);
            participants.add(prohibitedStyle);
            participants.add(prohibitedStyle);
            participants.add(neutralStyle);
            amountMap.replace(neutralStyle, amountMap.get(neutralStyle) - 1);
        }
        //clear edge case (2 prohibited, 1 neutral)
        if(amountMap.get(prohibitedStyle) == 2 && amountMap.get(neutralStyle) == 1){
            participants.add(prohibitedStyle);
            participants.add(neutralStyle);
            participants.add(prohibitedStyle);
            //if the edge case happens, in order to be a valid input, there must be at least 5 winning type remaining
        } else {
            //clean up the remaining ones
            for (int i = 0; i < amountMap.get(prohibitedStyle); i++) {
                participants.add(prohibitedStyle);
            }
            //neutralise remaining
            for (int i = 0; i < amountMap.get(neutralStyle); i++) {
                participants.add(neutralStyle);
            }
        }
        //secure winner
        for (int i = 0; i < amountMap.get(winningStyle); i++) {
            participants.add(winningStyle);
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        //building tournament tree
        this.root = createFirstRoundRec(participants, 0);
    }

    /*public Tournament(int rocks, int papers, int scissors, FightingStyle winningStyle) {
        String res;
        List<FightingStyle> participants = new ArrayList<>();

        switch (winningStyle) {
            case Paper paper -> {
                res = buildWinner(scissors, rocks, papers, paper);
            }
            case Rock rock -> {
                res = buildWinner(papers, scissors, rocks, rock);
            }
            case Scissors scissor -> {
                res = buildWinner(rocks, papers, scissors, scissor);
            }
            default -> {
                throw new RuntimeException("Incorrect FightingStyle as input");
            }
        }

        for (int i = 0; i < res.length(); i++) {
            participants.add(FightingStyle.parseCharToFightingStyle(res.charAt(i)));
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        this.root = createFirstRoundRec(this.participants, 0);
    }*/

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
                participants = buildWinner(participants, paper);
            }
            case Rock rock -> {
                participants = buildWinner(participants, rock);
            }
            case Scissors scissor -> {
                System.out.println("Initital: ");
                for (FightingStyle fightingStyle : participants) {
                    System.out.print(fightingStyle.getChar());
                }
                System.out.println();
                participants = buildWinner(participants, scissor);
            }
            case Spock spock -> {
                participants = buildWinner(participants, spock);
            }
            case Lizard lizard -> {
                participants = buildWinner(participants, lizard);
            }
            default -> {
                throw new RuntimeException("Incorrect FightingStyle as input");
            }
        }

        this.participants = participants;
        this.levels = (int) (Math.log(participants.size()) / Math.log(2));
        this.root = createFirstRoundRec(this.participants, 0);
    }

    public TournamentBranch getRoot(){
        return this.root;
    }

    public int getLevels(){
        return this.levels;
    }

    public TournamentBranch createFirstRoundRec(List<FightingStyle> participants, int level) {
        //base case
        if (participants.size() <= 1) {
            return TournamentBranch.TournamentBranchBuilder.aBranch()
                    .withParticipant(participants.getFirst())
                    .withLevel(level)
                    .build();
        }

        //splitting Array and making recursive calls
        return TournamentBranch.TournamentBranchBuilder.aBranch()
                .withUpperBranch(createFirstRoundRec(participants.subList(0, participants.size() / 2), level + 1))
                .withLowerBranch(createFirstRoundRec(participants.subList(participants.size() / 2, participants.size()), level + 1))
                .withLevel(level)
                .build();
    }

    public List<FightingStyle> buildWinner(List<FightingStyle> participants, FightingStyle winningStyle) {
        for (FightingStyle style : participants) {
            //System.out.print(style.getChar());
        }
        System.out.print("\n");
        int total = participants.size();
        List<FightingStyle> res = new ArrayList<>();

        if (Scissors.getInstance().getComparator().compare(winningStyle, new Tournament(FightingStyle.getCharsFromList(participants)).root.calcParticipant()) <= 0) {
            System.out.println("true base case 1");
            return participants;
        }

        //base case no prohibited
        if (participants.stream().noneMatch(f -> f.fights(winningStyle).equals(f) && !f.equals(winningStyle))) {
            System.out.println("base case 1 reached");
            return participants;
        }
        //base case only two left
        if (total == 2) {
            System.out.println("base case 2 reached");
            return participants;
        }

        //difficult case
        List<FightingStyle> lList = new ArrayList<>();

        //sort the List
        participants.sort(Scissors.getInstance().getComparator());


        FightingStyle toBeAdded = participants.getFirst();
        FightingStyle leftWinner;

        //split the List
        if (participants.stream().anyMatch(fightingStyle -> fightingStyle.equals(winningStyle.getDoubleBeats()))) {
            leftWinner = winningStyle.getDoubleBeats();
            lList.add(leftWinner);
            if(!participants.remove(leftWinner)) {
                throw new RuntimeException("remove didn't work");
            }
            while (participants.getFirst().equals(toBeAdded) && lList.size() < participants.size()){
                lList.add(participants.removeFirst());
            }
        } else {
            leftWinner = winningStyle.getBeats();
            lList.add(leftWinner);
            if(!participants.remove(leftWinner)) {
                throw new RuntimeException("remove didn't work");
            }
            if (participants.stream().anyMatch(f -> f.equals(leftWinner.getsDoubleBeatenBy()))) {
                //then there needs to be max this amount to neutralise else just fill with
                for (int i = 0; i < Math.log(participants.size() / 2.0) / Math.log(2); i++) {
                    if(participants.remove(leftWinner.getDoubleBeats())) {
                        lList.add(leftWinner.getDoubleBeats());
                    } else {
                        break;
                    }
                }
            }
        }

        while (lList.size() < participants.size()) {
            lList.add(participants.removeFirst());
        }

        System.out.println("Left Side: ");
        for (FightingStyle fightingStyle : lList) {
            System.out.print(fightingStyle.getChar());
        }
        System.out.println();
        System.out.println("Right Side: ");
        for (FightingStyle participant : participants) {
            System.out.print(participant.getChar());
        }

        res = buildWinner(lList, leftWinner);
        res.addAll(buildWinner(participants, winningStyle));

        return res;
    }
}
