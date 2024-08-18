package tournament;

import fightingStyles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemoBranch {
    private MemoBranch upperBranch;
    private MemoBranch lowerBranch;
    private int level;
    private List<FightingStyle> possibleParticipants;
    private boolean containsUndefined;
    private boolean containsRandomFighter;

    public MemoBranch(List<FightingStyle> participants, int level) {
        this.level = level;

        if (participants.size() == 1){
            if (participants.getFirst().equals(Undefined.getInstance())) {
                this.containsUndefined = true;
                this.possibleParticipants = Arrays.asList(Rock.getInstance(), Paper.getInstance(), Scissors.getInstance(), Lizard.getInstance(), Spock.getInstance());
            } else if (participants.getFirst().equals(RandomFighter.getInstance())) {
                this.containsRandomFighter = true;
                this.possibleParticipants = Arrays.asList(Rock.getInstance(), Paper.getInstance(), Scissors.getInstance(), Lizard.getInstance(), Spock.getInstance());
            } else {
                this.possibleParticipants = participants;
            }
            return;
        }

        if(participants.stream().anyMatch(p -> p.equals(Undefined.getInstance()))){
            this.containsUndefined = true;
        }

        if(participants.stream().anyMatch(p -> p.equals(RandomFighter.getInstance()))){
            this.containsRandomFighter = true;
        }

        this.upperBranch = new MemoBranch(participants.subList(0, participants.size() / 2), level + 1);
        this.lowerBranch = new MemoBranch(participants.subList(participants.size() / 2, participants.size()), level + 1);
        List<FightingStyle> temp = new ArrayList<>();

        for (FightingStyle upperWinner : this.upperBranch.getPossibleParticipants()) {
            for (FightingStyle lowerWinner : this.lowerBranch.getPossibleParticipants()) {
                temp.add(upperWinner.fights(lowerWinner));
            }
        }

        this.possibleParticipants = temp.stream().distinct().toList();
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
