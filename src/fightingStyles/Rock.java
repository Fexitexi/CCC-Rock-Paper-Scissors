package fightingStyles;

import java.util.Comparator;

public class Rock extends FightingStyle{

    private static Rock instance;

    public static Rock getInstance() {
        if (instance == null) {
            instance = new Rock();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        if (other.beatsRock()){
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
        return true;
    }

    @Override
    public boolean beatsPaper() {
        return false;
    }

    @Override
    public boolean beatsLizard() {
        return true;
    }

    @Override
    public boolean beatsSpock() {
        return false;
    }

    @Override
    public char getChar() {
        return 'R';
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return Spock.getInstance();
    }

    @Override
    public FightingStyle getsDoubleBeatenBy(){
        return Paper.getInstance();
    }

    @Override
    public FightingStyle getBeats(){
        return Scissors.getInstance();
    }

    @Override
    public FightingStyle getDoubleBeats(){
        return Lizard.getInstance();
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
