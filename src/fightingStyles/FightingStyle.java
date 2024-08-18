package fightingStyles;

import java.util.Comparator;
import java.util.List;

public abstract class FightingStyle {
    public abstract FightingStyle fights(FightingStyle other);
    public abstract FightingStyle getsBeatenBy();
    public abstract FightingStyle getsDoubleBeatenBy();
    public abstract FightingStyle getBeats();
    public abstract FightingStyle getDoubleBeats();
    public abstract Comparator<FightingStyle> getComparator();
    abstract boolean beatsRock();
    abstract boolean beatsScissors();
    abstract boolean beatsPaper();
    abstract boolean beatsLizard();
    abstract boolean beatsSpock();

    public static FightingStyle parseCharToFightingStyle(char c) {
        if (c == 'R') {
            return Rock.getInstance();
        } else if (c == 'S') {
            return Scissors.getInstance();
        } else if (c == 'P') {
            return Paper.getInstance();
        } else if (c == 'L') {
            return Lizard.getInstance();
        } else if (c == 'Y') {
            return Spock.getInstance();
        } else if (c == 'X') {
            return Undefined.getInstance();
        } else if (c == 'Z'){
            return RandomFighter.getInstance();
        }else {
            throw new RuntimeException("Wrong Char Input!");
        }
    }

    public abstract char getChar();

    public static String getCharsFromList(List<FightingStyle> list) {
        String res = "";

        for (FightingStyle f : list) {
            res = res + f.getChar();
        }

        return res;
    }

}
