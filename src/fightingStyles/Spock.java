package fightingStyles;

import java.util.Comparator;

public class Spock extends FightingStyle{

    private static Spock instance;

    public static Spock getInstance() {
        if (instance == null) {
            instance = new Spock();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        if(other.beatsSpock()){
            return other;
        } else {
            return this;
        }
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return Paper.getInstance();
    }

    @Override
    public FightingStyle getsDoubleBeatenBy(){
        return Lizard.getInstance();
    }

    @Override
    public FightingStyle getBeats(){
        return Rock.getInstance();
    }

    @Override
    public FightingStyle getDoubleBeats(){
        return Scissors.getInstance();
    }

    @Override
    boolean beatsRock() {
        return true;
    }

    @Override
    boolean beatsScissors() {
        return true;
    }

    @Override
    boolean beatsPaper() {
        return false;
    }

    @Override
    boolean beatsLizard() {
        return false;
    }

    @Override
    boolean beatsSpock() {
        return true;
    }

    @Override
    public char getChar() {
        return 'Y';
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
