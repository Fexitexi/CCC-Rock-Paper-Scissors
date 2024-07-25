package tournament;

import fightingStyles.FightingStyle;

public class TournamentBranch {
    private TournamentBranch upperBranch;
    private TournamentBranch lowerBranch;
    private int level;
    private FightingStyle participant;

    //Constructor
    private TournamentBranch(){
    }

    //Setter
    public void setUpperBranch(TournamentBranch upperBranch) {
        this.upperBranch = upperBranch;
    }

    public void setLowerBranch(TournamentBranch lowerBranch) {
        this.lowerBranch = lowerBranch;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setParticipant(FightingStyle participant) {
        this.participant = participant;
    }

    //Getter
    public TournamentBranch getUpperBranch() {
        return upperBranch;
    }

    public TournamentBranch getLowerBranch() {
        return lowerBranch;
    }

    public int getLevel() {
        return level;
    }

    public FightingStyle getParticipant() {
        return participant;
    }


    public FightingStyle calcParticipant() {
        if (this.participant != null){
            return this.participant;
        }

        return this.upperBranch.calcParticipant().fights(this.lowerBranch.calcParticipant());
    }

    public String getStandingsAtLevel(int level) {
        if (this.level == level) {
            return this.calcParticipant().getChar() + "";
        }

        return this.upperBranch.getStandingsAtLevel(level) + this.lowerBranch.getStandingsAtLevel(level);
    }

    public void checkSolutionLevel4(int rocks, int papers, int scissors, String line){
        int total = rocks + papers + scissors;
        if (total != line.length()) {
            throw new RuntimeException("Incorrect line length");
        }

        //counting the occurences of the fighting styles
        int[] counts = new int[3];
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case 'R' -> counts[0]++;
                case 'P' -> counts[1]++;
                case 'S' -> counts[2]++;
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

        //checking if the winner is scissors
        Tournament t = new Tournament(line);
        if (t.getRoot().calcParticipant().getChar() != 'S') {
            throw new RuntimeException("Scissors should win");
        }
    }

    public void checkSolutionLevel5(int rocks, int papers, int scissors, int spocks, int lizard, String line){
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

        //checking if the winner is scissors
        Tournament t = new Tournament(line);
        if (t.getRoot().calcParticipant().getChar() != 'S') {
            throw new RuntimeException("Scissors should win");
        }
    }


    public static class TournamentBranchBuilder{
        private TournamentBranch upperBranch;
        private TournamentBranch lowerBranch;
        private int level;
        private FightingStyle participant;

        public static TournamentBranchBuilder aBranch() {
            return new TournamentBranchBuilder();
        }

        public TournamentBranchBuilder withUpperBranch(TournamentBranch upperBranch){
            this.upperBranch = upperBranch;
            return this;
        }

        public TournamentBranchBuilder withLowerBranch(TournamentBranch lowerBranch){
            this.lowerBranch = lowerBranch;
            return this;
        }

        public TournamentBranchBuilder withLevel(int level){
            this.level = level;
            return this;
        }

        public TournamentBranchBuilder withParticipant(FightingStyle participant){
            this.participant = participant;
            return this;
        }

        public TournamentBranch build(){
            TournamentBranch branch =  new TournamentBranch();
            branch.setUpperBranch(this.upperBranch);
            branch.setLowerBranch(this.lowerBranch);
            branch.setLevel(this.level);
            branch.setParticipant(this.participant);
            return branch;
        }
    }
}
