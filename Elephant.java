import java.util.Iterator;
import java.util.List;

public class Elephant extends Prey {

    private static final double BREEDING_PROBABILITY = 0.2;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int BREEDING_AGE = 10;
    private static final int MAX_AGE = 100;

    private static final int DEFAULT_FOOD_VALUE = 5;

    private static final double SPREAD_DISEASE_PROBABILITY = 0.1;
    private static final double DEATH_BY_DISEASE_PROBABILITY = 0.001;

    public Elephant(int foodValue, boolean randomAge, Field field, Location location) {
        super(foodValue, randomAge, field, location);
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
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    protected double getDiseaseSpreadProbability() {
        return SPREAD_DISEASE_PROBABILITY;
    }

    @Override
    protected double getDeathByDiseaseProbability() {
        return DEATH_BY_DISEASE_PROBABILITY;
    }

    @Override
    protected Organism createNewOrganism(Field field, Location location) {
        return new Elephant(DEFAULT_FOOD_VALUE, true, field, location);
    }

    @Override
    public void act(List<Entity> newZebras, Weather weather, TimeOfDay time) {
        incrementAge();
        setActiveness(1);

        if(isAlive()) {
            giveBirth(newZebras);


            if (getRandom().nextDouble() <= getDeathByDiseaseProbability() ) {
                remove();
                return;
            }

            //Could move below 2 if statements above the disease statement
            //50% Chance of acting when it is around midnight
            if (time == TimeOfDay.SUNSET){
                this.setActiveness(0.85);
            }

            //rand.nextDouble() <= getBreedingProbability()
            if (rand.nextDouble() <= getActiveness()){
                // Try to move into a free location.
                Location newLocation;

                if (getRandom().nextDouble() <= getDiseaseSpreadProbability() ) {
                    newLocation = findAnimalToInfect();
                } else {
                    newLocation = findFood();
                }

                // Random chance to do either?

                if ((newLocation == null) || (getFoodValue() > 10)) {
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }

                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    //setDead();
                    remove();
                }
            }
        } else {
            decayifDead();
        }
    }

    @Override
    public boolean canBreed() {
        if (getAge() < getBreedingAge()) {
            return false;
        }

        for (Location loc : getField().adjacentLocations(getLocation())) {
            Object animal = getField().getObjectAt(loc);
            if (animal instanceof Elephant) {
                Elephant zebra = (Elephant) animal;
                if (!(((zebra.isMale() && isMale())) || ((!zebra.isMale() && !isMale())))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();

            Object organism = field.getObjectAt(where);
            if(organism instanceof Plant) {
                Plant plant = (Plant) organism;
                // kills animal
                //prey.setDead();
                //eatOrLeave(prey);
                if (plant.isAlive()) {
                    //System.out.println("ALIVE");
                    plant.setDead();
                    // NOTE: ONLY RETURN WHERE IF EATEN
                    boolean eaten = eat(plant);

                    //return where;
                    return eaten ? where : null;
                }
            }
        }
        return null;
    }
}
