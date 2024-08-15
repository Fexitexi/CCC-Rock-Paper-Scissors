package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TournamentBranch {
    private TournamentBranch upperBranch;
    private TournamentBranch lowerBranch;
    private int level;
    private FightingStyle participant;

    //Constructor
    private TournamentBranch(){
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

    public TournamentBranch(List<FightingStyle> participants, FightingStyle winningStyle, int level){
        //TODO: terribly inefficient, basically recalculating the tree twice for each branch
        this.level = level;

        if (participants.size() == 1){
            //base case, only two participants one of them is undefined
            if (participants.getFirst().equals(Undefined.getInstance())){
                this.participant = winningStyle;
            } else {
                this.participant = participants.getFirst();
            }
            return;
        }

        if (participants.stream().noneMatch(p -> p.equals(Undefined.getInstance()))){
            //base case, all participants are defined
            this.upperBranch = new TournamentBranch(participants.subList(0, participants.size() / 2),level + 1);
            this.lowerBranch = new TournamentBranch(participants.subList(participants.size() / 2, participants.size()),level + 1);
            return;
        }

        List<FightingStyle> upperWinners = possibleWinners(participants.subList(0, participants.size() / 2)).stream().filter(p -> p.fights(winningStyle).equals(winningStyle)).toList();
        List<FightingStyle> lowerWinners = possibleWinners(participants.subList(participants.size() / 2, participants.size())).stream().filter(p -> p.fights(winningStyle).equals(winningStyle)).toList();

        if (upperWinners.contains(winningStyle)){
            this.upperBranch = new TournamentBranch(participants.subList(0, participants.size() / 2), winningStyle, level + 1);
            this.lowerBranch = new TournamentBranch(participants.subList(participants.size() / 2, participants.size()), lowerWinners.getFirst(), level + 1);
        } else {
            this.upperBranch = new TournamentBranch(participants.subList(0, participants.size() / 2), upperWinners.getFirst(), level + 1);
            this.lowerBranch = new TournamentBranch(participants.subList(participants.size() / 2, participants.size()), winningStyle, level + 1);
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
            return this.calcParticipant().getChar() + "";
        }

        return this.upperBranch.getStandingsAtLevel(level) + this.lowerBranch.getStandingsAtLevel(level);
    }

    private static List<FightingStyle> possibleWinners(List<FightingStyle> participants){
        if (participants.size() == 1){
            if (participants.getFirst().equals(Undefined.getInstance())){
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
