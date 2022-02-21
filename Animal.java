import java.util.List;

public abstract class Animal extends Organism {

    private Gender gender;

    public Animal(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        // randomly assign male or female
        this.gender = Gender.getRandom();
    }

    @Override
    abstract public void act(List<Entity> newAnimals);

    @Override
    abstract protected boolean canBreed();

    private Gender getGender() {
        return this.gender;
    }

    protected boolean isMale() {
        return getGender() == Gender.MALE;
    }
}