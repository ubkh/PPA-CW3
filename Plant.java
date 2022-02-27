import java.util.List;

public abstract class Plant extends Organism implements Growable, Consumable {

    private double size;
    private final int foodValue;
    private final boolean poisonous;
    private double growthRate;
    private double breedingProbability;

    public Plant(boolean poisonous, int foodValue, double size, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.size = size;
        this.foodValue = foodValue;
        this.poisonous = poisonous;
    }

    @Override
    abstract public void act(List<Entity> newPlants, Weather weather, TimeOfDay time);

    //Grow at given rate.
    @Override
    public void grow() {
        size = size*getGrowthRate() > getMaxSize() ? 1 : size*getGrowthRate();
        if (size == 0) {
            remove();
        }
    }
//    @Override
//    public void grow(double growthRate) {
//        size = size * growthRate > getMaxSize() ? 1 : size * growthRate;
//    }

    @Override
    public double getGrowthRate() {
        return this.growthRate;
    }

    @Override
    public void setGrowthRate(double rate) {
        this.growthRate = rate;
    }

    @Override
    abstract public double getMaxSize();

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
        if (getLocation() != null) {
            getField().clear(getLocation());
            setLocationToNull();
            setField(null);
        }
    }

    @Override
    public boolean isPoisonous() {
        return this.poisonous;
    }

    protected void setBreedingProbability(double probability) {
        this.breedingProbability = probability;
    }

    @Override
    public double getBreedingProbability() {
        return this.breedingProbability;
    }
}
