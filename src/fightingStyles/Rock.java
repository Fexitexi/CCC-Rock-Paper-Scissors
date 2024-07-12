package fightingStyles;

public class Rock implements FightingStyle{
    @Override
    public char fights(FightingStyle other) {
        return other.fightsWithRock();
    }

    @Override
    public char fightsWithRock() {
        return 'R';
    }

    @Override
    public char fightsWithScissors() {
        return 'R';
    }

    @Override
    public char fightsWithPaper() {
        return 'P';
    }
}
