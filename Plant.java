import java.util.List;

public abstract class Plant extends Organism implements Growable {

    private double size;

    public Plant(double size, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.size = size;
    }

    @Override
    abstract public void act(List<Entity> newPlants, Weather weather, TimeOfDay time);

    // Grow at given rate.
    @Override
    public void grow() {
        size = size*getGrowthRate() > getMaxSize() ? 1 : size*getGrowthRate();
    }

    abstract public double getGrowthRate();

    abstract public double getMaxSize();

    @Override
    abstract public double getBreedingProbability();

    @Override
    abstract public int getMaxLitterSize();

    @Override
    protected boolean canBreed() {
        return getAge() >= getBreedingAge();
    }

    public double getSize() {
        return this.size;
    }
}
