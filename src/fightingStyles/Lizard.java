package fightingStyles;

import java.util.Comparator;

public class Lizard extends FightingStyle{

    private static Lizard instance;

    public static Lizard getInstance() {
        if (instance == null) {
            instance = new Lizard();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        if(other.beatsLizard()){
            return other;
        } else {
            return this;
        }
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return Scissors.getInstance();
    }

    @Override
    public FightingStyle getsDoubleBeatenBy(){
        return Rock.getInstance();
    }

    @Override
    public FightingStyle getBeats(){
        return Paper.getInstance();
    }

    @Override
    public FightingStyle getDoubleBeats(){
        return Spock.getInstance();
    }

    @Override
    boolean beatsRock() {
        return false;
    }

    @Override
    boolean beatsScissors() {
        return false;
    }

    @Override
    boolean beatsPaper() {
        return true;
    }

    @Override
    boolean beatsLizard() {
        return true;
    }

    @Override
    boolean beatsSpock() {
        return true;
    }

    @Override
    public char getChar() {
        return 'L';
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
