package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TournamentBranch {
    private TournamentBranch upperBranch;
    private TournamentBranch lowerBranch;
    private int level;
    private FightingStyle participant;
    private List<FightingStyle> possibleParticipants;

    //Constructor
    private TournamentBranch() {
    }

    public TournamentBranch(List<FightingStyle> participants, int level) {
        //base case
        if (participants.size() <= 1) {
            this.participant = participants.getFirst();
            this.level = level;
            this.lowerBranch = null;
            this.upperBranch = null;
        } else {
            //splitting Array and making recursive calls
            this.upperBranch = new TournamentBranch(participants.subList(0, participants.size() / 2), level + 1);
            this.lowerBranch = new TournamentBranch(participants.subList(participants.size() / 2, participants.size()), level + 1);
            this.level = level;
        }
    }

    public TournamentBranch(List<FightingStyle> participants, FightingStyle winningStyle, int level, MemoBranch memo) {
        //TODO: terribly inefficient, basically recalculating the tree twice for each branch
        this.level = level;

        if (participants.size() == 1) {
            //base case, only one participant left
            if (participants.getFirst().equals(Undefined.getInstance())) {
                this.participant = winningStyle;
            } else {
                this.participant = participants.getFirst();
            }
            return;
        }
        List<FightingStyle> upperParticipants = participants.subList(0, participants.size() / 2);
        List<FightingStyle> lowerParticipants = participants.subList(participants.size() / 2, participants.size());


        if (!upperParticipants.contains(Undefined.getInstance())) {
            //base case, all participants are defined
            this.upperBranch = new TournamentBranch(upperParticipants, level + 1);
            if (this.upperBranch.calcPossibleParticipants().stream().allMatch(p -> p.fights(winningStyle).equals(winningStyle))) {
                //winningstyle does not get beaten, therefore possible winning branch
                this.lowerBranch = new TournamentBranch(lowerParticipants, winningStyle, level + 1, null);
            } else {
                this.lowerBranch = new TournamentBranch(lowerParticipants, Paper.getInstance(), level + 1, null);
            }
        } else if (!lowerParticipants.contains(Undefined.getInstance())) {
            this.lowerBranch = new TournamentBranch(lowerParticipants, level + 1);
            if (this.lowerBranch.calcPossibleParticipants().stream().allMatch(p -> p.fights(winningStyle).equals(winningStyle))) {
                //winningstyle does not get beaten, therefore possible winning branch
                this.upperBranch = new TournamentBranch(upperParticipants, winningStyle, level + 1, null);
            } else {
                this.upperBranch = new TournamentBranch(upperParticipants, Paper.getInstance(), level + 1, null);
            }
        } else {
            //neither branch without undefined, propagate further
            this.upperBranch = new TournamentBranch(upperParticipants, winningStyle, level + 1, null);
            this.lowerBranch = new TournamentBranch(lowerParticipants, winningStyle, level + 1, null);
        }
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
            return this.participant.getChar() + "";
        }

        return this.upperBranch.getStandingsAtLevel(level) + this.lowerBranch.getStandingsAtLevel(level);
    }

    private static List<FightingStyle> possibleWinners(List<FightingStyle> participants) {
        if (participants.size() == 1) {
            if (participants.getFirst().equals(Undefined.getInstance())) {
                return Arrays.asList(Rock.getInstance(), Paper.getInstance(), Scissors.getInstance(), Lizard.getInstance(), Spock.getInstance());
            }
            return participants;
        }

        List<FightingStyle> upperWinners = possibleWinners(participants.subList(0, participants.size() / 2));
        List<FightingStyle> lowerWinners = possibleWinners(participants.subList(participants.size() / 2, participants.size()));
        List<FightingStyle> winners = new ArrayList<>();

        for (FightingStyle upperWinner : upperWinners) {
            for (FightingStyle lowerWinner : lowerWinners) {
                winners.add(upperWinner.fights(lowerWinner));
            }
        }

        return winners.stream().distinct().toList();
    }

    private List<FightingStyle> calcPossibleParticipants(){
        if (this.upperBranch == null) {
            //leaf
            if (this.participant.equals(RandomFighter.getInstance())) {
                return Arrays.asList(Rock.getInstance(), Paper.getInstance(), Scissors.getInstance(), Lizard.getInstance(), Spock.getInstance());
            } else if (this.participant.equals(Undefined.getInstance())) {
                return new ArrayList<>();
            } else {
                return List.of(this.participant);
            }
        }
        ArrayList<FightingStyle> winners = new ArrayList<>();

        for (FightingStyle f : this.upperBranch.calcPossibleParticipants()) {
            for (FightingStyle g : this.lowerBranch.calcPossibleParticipants()) {
                winners.add(f.fights(g));
            }
        }

        return winners.stream().distinct().toList();
    }

    public static class TournamentBranchBuilder {
        private TournamentBranch upperBranch;
        private TournamentBranch lowerBranch;
        private int level;
        private FightingStyle participant;

        public static TournamentBranchBuilder aBranch() {
            return new TournamentBranchBuilder();
        }

        public TournamentBranchBuilder withUpperBranch(TournamentBranch upperBranch) {
            this.upperBranch = upperBranch;
            return this;
        }

        public TournamentBranchBuilder withLowerBranch(TournamentBranch lowerBranch) {
            this.lowerBranch = lowerBranch;
            return this;
        }

        public TournamentBranchBuilder withLevel(int level) {
            this.level = level;
            return this;
        }

        public TournamentBranchBuilder withParticipant(FightingStyle participant) {
            this.participant = participant;
            return this;
        }

        public TournamentBranch build() {
            TournamentBranch branch = new TournamentBranch();
            branch.setUpperBranch(this.upperBranch);
            branch.setLowerBranch(this.lowerBranch);
            branch.setLevel(this.level);
            branch.setParticipant(this.participant);
            return branch;
        }
    }
}
