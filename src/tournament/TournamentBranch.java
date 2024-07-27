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
