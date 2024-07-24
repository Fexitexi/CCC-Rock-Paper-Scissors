package fightingStyles;

import java.util.Comparator;

public class Paper extends FightingStyle{

    private static Paper instance;

    public static Paper getInstance() {
        if (instance == null) {
            instance = new Paper();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        if (other.beatsPaper()){
            return other;
        } else {
            return this;
        }
    }

    @Override
    public boolean beatsRock() {
        return true;
    }

    @Override
    public boolean beatsScissors() {
        return false;
    }

    @Override
    public boolean beatsPaper() {
        return true;
    }

    @Override
    public boolean beatsLizard() {
        return false;
    }

    @Override
    public boolean beatsSpock() {
        return true;
    }

    @Override
    public char getChar() {
        return 'P';
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return Lizard.getInstance();
    }

    @Override
    public FightingStyle getsDoubleBeatenBy(){
        return Scissors.getInstance();
    }

    @Override
    public FightingStyle getBeats(){
        return Spock.getInstance();
    }

    @Override
    public FightingStyle getDoubleBeats(){
        return Rock.getInstance();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Paper;
    }

    @Override
    public Comparator<FightingStyle> getComparator() {
        return new Comparator<FightingStyle>() {
            @Override
            public int compare(FightingStyle o1, FightingStyle o2) {
                return o1.fights(o2).equals(o1) ? 1 : -1;
            }
        };
    }
}
