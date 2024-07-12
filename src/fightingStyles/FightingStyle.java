package fightingStyles;

import java.util.List;

public abstract class FightingStyle {
    public abstract FightingStyle fights(FightingStyle other);
    public abstract FightingStyle getsBeatenBy();
    abstract boolean beatsRock();
    abstract boolean beatsScissors();
    abstract boolean beatsPaper();

    public static FightingStyle parseCharToFightingStyle(char c){
        if (c == 'R') {
            return new fightingStyles.Rock();
        } else if (c == 'S') {
            return new fightingStyles.Scissors();
        } else if (c == 'P') {
            return new fightingStyles.Paper();
        }else {
            throw new RuntimeException("Wrong Char Input!");
        }
    };

    public abstract char getChar();

    public static String getCharsFromList(List<FightingStyle> list) {
        String res = "";

        for (FightingStyle f : list) {
            res = res + f.getChar();
        }

        return res;
    }
}
