package fightingStyles;

public class Scissors extends FightingStyle {
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
    public char getChar() {
        return 'S';
    }

    @Override
    public FightingStyle getsBeatenBy(){
        return new Rock();
    }
}
