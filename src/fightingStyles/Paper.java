package fightingStyles;

public class Paper extends FightingStyle{
    @Override
    public FightingStyle fights(FightingStyle other) {
        if (other.beatsPaper()){
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
        return false;
    }

    @Override
    public boolean beatsPaper() {
        return true;
    }

    @Override
    public char getChar() {
        return 'P';
    }

    @Override
    public FightingStyle getsBeatenBy(){
        return new Scissors();
    }
}
