package fightingStyles;

import java.util.Comparator;

public class Undefined extends FightingStyle {

    private static Undefined instance;

    public static Undefined getInstance() {
        if (instance == null) {
            instance = new Undefined();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        return null;
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return null;
    }

    @Override
    public FightingStyle getsDoubleBeatenBy() {
        return null;
    }

    @Override
    public FightingStyle getBeats() {
        return null;
    }

    @Override
    public FightingStyle getDoubleBeats() {
        return null;
    }

    @Override
    public Comparator<FightingStyle> getComparator() {
        return null;
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
        return false;
    }

    @Override
    boolean beatsLizard() {
        return false;
    }

    @Override
    boolean beatsSpock() {
        return false;
    }

    @Override
    public char getChar() {
        return 'X';
    }
}
