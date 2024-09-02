package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoBranch {
    private MemoBranch upperBranch;
    private MemoBranch lowerBranch;
    private List<FightingStyle> possibleParticipants;
    private boolean containsUndefined;
    private boolean containsRandomFighter;

    public MemoBranch(List<FightingStyle> participants) {

        if (participants.size() == 1) {
            if (participants.getFirst().equals(RandomFighter.getInstance())) {
                this.possibleParticipants = Arrays.asList(Rock.getInstance(), Paper.getInstance(), Scissors.getInstance(), Lizard.getInstance(), Spock.getInstance());
                this.containsRandomFighter = true;
            } else if (participants.getFirst().equals(Undefined.getInstance())){
                this.containsUndefined = true;
                this.possibleParticipants = new ArrayList<>();
            } else {
                this.possibleParticipants = participants;
            }
            return;
        }

        if(participants.stream().anyMatch(p -> p.equals(RandomFighter.getInstance()))){
            this.containsRandomFighter = true;
        }

        if(participants.stream().anyMatch(p -> p.equals(Undefined.getInstance()))){
            this.containsUndefined = true;
        }

        this.upperBranch = new MemoBranch(participants.subList(0, participants.size() / 2));
        this.lowerBranch = new MemoBranch(participants.subList(participants.size() / 2, participants.size()));
        List<FightingStyle> temp = new ArrayList<>();

        if (!this.upperBranch.containsUndefined) {
            for (FightingStyle upperWinner : this.upperBranch.getPossibleParticipants()) {
                if (!this.lowerBranch.containsUndefined) {
                    for (FightingStyle lowerWinner : this.lowerBranch.getPossibleParticipants()) {
                        temp.add(upperWinner.fights(lowerWinner));
                    }
                } else {
                    temp.addAll(this.upperBranch.getPossibleParticipants());
                    break;
                }
            }
        } else {
            if (!this.lowerBranch.containsUndefined) {
                temp.addAll(this.lowerBranch.getPossibleParticipants());
            }
        }

        this.possibleParticipants = temp.stream().distinct().toList();
    }

    public List<MemoBranch> findWinningPath(FightingStyle winningStyle) {
        List<MemoBranch> path = new ArrayList<>();

        if (this.upperBranch == null){
            //this is a leaf
            //will only be called if there is the winning style in the leaf or undefined
            return path;
        }

        if (this.upperBranch.containsBeatable(winningStyle) || this.lowerBranch.containsBeatable(winningStyle)) {
            //cant have a possible beater of the winning style in any neighboring branch
            return null;
        }

        if (this.upperBranch.containsStyle(winningStyle) || this.upperBranch.containsUndefined) {
            if (this.upperBranch.findWinningPath(winningStyle) != null) {
                path.add(this.upperBranch);
                path.addAll(this.upperBranch.findWinningPath(winningStyle));
                return path;
            }
        }

        if (this.lowerBranch.containsStyle(winningStyle) || this.lowerBranch.containsUndefined) {
            if (this.lowerBranch.findWinningPath(winningStyle) != null) {
                path.add(this.lowerBranch);
                path.addAll(this.lowerBranch.findWinningPath(winningStyle));
                return path;
            }
        }

        return null;
    }

    public boolean containsBeatable(FightingStyle winningStyle) {
        return this.possibleParticipants.contains(winningStyle.getsBeatenBy()) || this.possibleParticipants.contains(winningStyle.getsDoubleBeatenBy());
    }

    public boolean containsStyle(FightingStyle style) {
        return this.possibleParticipants.contains(style);
    }

    public List<FightingStyle> getPossibleParticipants() {
        return this.possibleParticipants;
    }

    public MemoBranch getUpperBranch() {
        return this.upperBranch;
    }

    public MemoBranch getLowerBranch() {
        return this.lowerBranch;
    }

    public boolean containsUndefined() {
        return this.containsUndefined;
    }

    public boolean containsRandomFighter() {
        return this.containsRandomFighter;
    }
}
