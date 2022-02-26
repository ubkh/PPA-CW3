import java.util.List;

public abstract class Plant extends Organism implements Growable, Consumable {

    private double size;
    private final int foodValue;

    public Plant(int foodValue, double size, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.size = size;
        this.foodValue = foodValue;
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

    @Override
    public int getFoodValue() {
        return this.foodValue;
    }

    @Override
    public void setEaten() {
        if(getLocation() != null) {
            getField().clear(getLocation());
            setLocationToNull();
            setField(null);
        }
    }
}
