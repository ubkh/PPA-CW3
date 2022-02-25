import java.util.List;

public class Grass extends Plant implements Consumable {

    private static final double GROWTH_RATE = 0.1;
    private static final double MAX_SIZE = 10.0;
    private static final int MAX_AGE = 20;
    private static final int BREEDING_AGE = 10;
    private static final double BREEDING_PROBABILITY = 0.1;
    private static final int MAX_LITTER_SIZE = 2;
    private static final double DEFAULT_SIZE = 1.00;

    private static final int FOOD_VALUE = 1;

    public Grass(double size, boolean randomAge, Field field, Location location) {
        super(size, randomAge, field, location);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected Organism createNewOrganism(Field field, Location location) {
        return new Grass(DEFAULT_SIZE, true, field, location);
    }

    @Override
    public void act(List<Entity> newGrass, Weather weather, TimeOfDay time) {
        grow();
    }

    @Override
    public double getGrowthRate() {
        return GROWTH_RATE;
    }

    @Override
    public double getMaxSize() {
        return MAX_SIZE;
    }

    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    public int getFoodValue() {
        return FOOD_VALUE;
    }
}
