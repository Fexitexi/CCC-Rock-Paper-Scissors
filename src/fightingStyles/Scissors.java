package fightingStyles;

public class Scissors implements FightingStyle {
    @Override
    public char fights(FightingStyle other) {
        return other.fightsWithScissors();
    }

    @Override
    public char fightsWithRock() {
        return 'R';
    }

    @Override
    public char fightsWithScissors() {
        return 'S';
    }

    @Override
    public char fightsWithPaper() {
        return 'S';
    }
}
