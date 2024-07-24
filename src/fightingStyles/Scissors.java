package fightingStyles;

import java.util.Comparator;

public class Scissors extends FightingStyle {

    private static Scissors instance;

    public static Scissors getInstance() {
        if (instance == null) {
            instance = new Scissors();
        }
        return instance;
    }

    @Override
    public FightingStyle fights(FightingStyle other) {
        if(other.beatsScissors()) {
            return other;
        } else {
            return this;
        }
    }

    @Override
    boolean beatsRock() {
        return false;
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
        return false;
    }

    @Override
    public char getChar() {
        return 'S';
    }

    @Override
    public FightingStyle getsBeatenBy() {
        return Rock.getInstance();
    }

    @Override
    public FightingStyle getsDoubleBeatenBy(){
        return Spock.getInstance();
    }

    @Override
    public FightingStyle getBeats(){
        return Lizard.getInstance();
    }

    @Override
    public FightingStyle getDoubleBeats(){
        return Paper.getInstance();
    }

    @Override
    public Comparator<FightingStyle> getComparator() {
        return (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            switch (o1) {
                case Rock rock -> {
                    return -1;
                }
                case Spock spock -> {
                    if (o2 instanceof Rock) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                case Paper paper -> {
                    if (o2 instanceof Lizard || o2 instanceof Scissors) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                case Lizard lizard -> {
                    if (o2 instanceof Scissors) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                default -> {
                    return 1;
                }
            }

        };
    }
}
