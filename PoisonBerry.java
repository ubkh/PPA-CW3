import java.util.List;

public class PoisonBerry extends Plant {

    private static final double MAX_SIZE = 10.0;
    private static final int MAX_AGE = 20;
    private static final int BREEDING_AGE = 16;
    private static final double LOW_BREEDING_PROBABILITY = 0.1;
    private static final double HIGH_BREEDING_PROBABILITY = 0.2;
    private static final int MAX_LITTER_SIZE = 2;
    private static final double DEFAULT_SIZE = 1.00;
    private static final int DEFAULT_FOOD_VALUE = 5;
    private static final double DEFAULT_GROWTH_RATE = 1.2;

    public PoisonBerry(int foodValue, double size, boolean randomAge, Field field, Location location) {
        super(true, foodValue, size, randomAge, field, location);
        setGrowthRate(DEFAULT_GROWTH_RATE);
        setBreedingProbability(LOW_BREEDING_PROBABILITY);
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
        return new PoisonBerry(DEFAULT_FOOD_VALUE, DEFAULT_SIZE, true, field, location);
    }

    @Override
    public void act(List<Entity> newBerries, Weather weather, TimeOfDay time) {
        if (isAlive()) {
            setBreedingProbability(LOW_BREEDING_PROBABILITY);
//            // only grows in rain
//            if (weather.getType() == WeatherType.RAIN) {
//                grow();
//            }
            //If it has recently rained or is sunny, grow at a higher growth rate
            if (weather.getRecentWeather().contains(WeatherType.RAIN) ||
                    weather.getRecentWeather().contains(WeatherType.SUN)){
                //grow(HIGH_GROWTH_RATE);
                setBreedingProbability(HIGH_BREEDING_PROBABILITY);
            }
            grow();
            giveBirth(newBerries);
        }
    }

    @Override
    public double getMaxSize() {
        return MAX_SIZE;
    }

    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

}
