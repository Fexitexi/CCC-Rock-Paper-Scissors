package tournament;

import fightingStyles.FightingStyle;
import fightingStyles.Paper;
import fightingStyles.Rock;
import fightingStyles.Scissors;

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

    public Tournament(int rocks, int papers, int scissors, FightingStyle winningStyle) {
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

    public String buildWinner(int prohibited, int neutral, int winner, FightingStyle winningStyle) {
        int total = prohibited + neutral + winner;
        String res = "";
        //base case no winners
        if (winner <= 0) {
            for (int i = 0; i < prohibited; i++) {
                res = res + winningStyle.getsBeatenBy().getChar();
            }
            for (int i = 0; i < neutral; i++) {
                res = res + winningStyle.getsBeatenBy().getsBeatenBy().getChar();
            }
            return res;
        }
        //base case no prohibited
        if (prohibited <= 0) {
            for (int i = 0; i < neutral; i++) {
                res = res + winningStyle.getsBeatenBy().getsBeatenBy().getChar();
            }
            for (int i = 0; i < winner; i++) {
                res = res + winningStyle.getChar();
            }
            return res;
        }
        //base case only two left
        if (total == 2) {
            for (int i = 0; i < prohibited; i++) {
                res = res + winningStyle.getsBeatenBy().getChar();
            }
            for (int i = 0; i < neutral; i++) {
                res = res + winningStyle.getsBeatenBy().getsBeatenBy().getChar();
            }
            for (int i = 0; i < winner; i++) {
                res = res + winningStyle.getChar();
            }
            return res;
        }

        //difficult case
        int prohibitedL;
        int neutralL;
        int winnerL;

        if (prohibited >= (total / 2 - 1)){
            //prohibited and neutral fill the left side
            prohibitedL = total / 2 - 1;
            neutralL = 1;
            winnerL = 0;
        } else {
            prohibitedL = prohibited;
            neutralL = Math.min(neutral, total / 2 - prohibitedL);
            winnerL = (total / 2) - prohibitedL - neutralL;
        }

        return buildWinner(prohibitedL, neutralL, winnerL, winningStyle) +
                buildWinner(prohibited - prohibitedL, neutral - neutralL, winner - winnerL, winningStyle);
    }

}
