package fightingStyles;

public class Paper implements FightingStyle{
    @Override
    public char fights(FightingStyle other) {
        return other.fightsWithPaper();
    }

    @Override
    public char fightsWithRock() {
        return 'P';
    }

    @Override
    public char fightsWithScissors() {
        return 'S';
    }

    @Override
    public char fightsWithPaper() {
        return 'P';
    }
}
