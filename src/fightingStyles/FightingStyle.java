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

    public static FightingStyle parseCharToFightingStyle(char c){
        if (c == 'R') {
            return new fightingStyles.Rock();
        } else if (c == 'S') {
            return new fightingStyles.Scissors();
        } else if (c == 'P') {
            return new fightingStyles.Paper();
        }else if (c == 'L') {
            return new fightingStyles.Lizard();
        } else if (c == 'Y') {
            return new fightingStyles.Spock();
        } else {
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
