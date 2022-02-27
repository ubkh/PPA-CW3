import java.util.Iterator;
import java.util.List;

public abstract class Animal extends Organism implements AbleToEat {

    private final Gender gender;
    private boolean asleep;
    private boolean infected;

    public Animal(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        // randomly assign male or female
        this.gender = Gender.getRandom();
        this.asleep = false;
        this.infected = false;
    }

    @Override
    abstract public void act(List<Entity> newAnimals, Weather weather, TimeOfDay time);

    @Override
    abstract public boolean eat(Consumable consumable);

    @Override
    abstract protected boolean canBreed();


    private Gender getGender() {
        return this.gender;
    }

    protected boolean isMale() {
        return getGender() == Gender.MALE;
    }

    protected void setAsleep(boolean asleep) {
        this.asleep = asleep;
    }

    protected boolean isAsleep() {
         return this.asleep;
    }

    protected boolean isInfected() {
        return this.infected;
    }

    private void setInfected(boolean infected) {
        this.infected = infected;
    }

    protected void infect(Animal animal) {
        animal.setInfected(true);
    }

    protected Location findAnimalToInfect() {
        if (!infected) {
            return null;
        }

        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();

            Object organism = field.getObjectAt(where);
            if (organism instanceof Animal) {
                Animal animal = (Animal) organism;
                if ((animal.isAlive() && (!animal.isInfected()))) {
                    infect(animal);
                    return where;
                }
            }
        }
        return null;
    }

    abstract protected double getDiseaseSpreadProbability();
}