package fightingStyles;

import java.util.Comparator;

public class RandomFighter extends FightingStyle {

    private static RandomFighter instance;

    public static RandomFighter getInstance() {
        if (instance == null) {
            instance = new RandomFighter();
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
        return true;
    }

    @Override
    boolean beatsScissors() {
        return true;
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
        return 'Z';
    }
}

