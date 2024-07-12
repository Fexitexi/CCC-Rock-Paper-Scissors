package fightingStyles;

public interface FightingStyle {
    char fights(FightingStyle other);
    char fightsWithRock();
    char fightsWithScissors();
    char fightsWithPaper();
}
