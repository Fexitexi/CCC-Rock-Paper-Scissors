package fightingStyles;

public class Rock extends FightingStyle{
    @Override
    public FightingStyle fights(FightingStyle other) {
        if (other.beatsRock()){
            return other;
        } else {
            return this;
        }
    }

    @Override
    public boolean beatsRock() {
        return true;
    }

    @Override
    public boolean beatsScissors() {
        return true;
    }

    @Override
    public boolean beatsPaper() {
        return false;
    }

    @Override
    public char getChar() {
        return 'R';
    }

    @Override
    public FightingStyle getsBeatenBy(){
        return new Paper();
    }
}
