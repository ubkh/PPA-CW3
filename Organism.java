import java.util.List;
import java.util.Random;

public abstract class Organism implements Entity {

    private boolean alive;
    private Field field;
    private Location location;

    private int age;

    private static final Random rand = Randomizer.getRandom();

    public Organism(boolean randomAge, Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);

        if (randomAge) {
            age = rand.nextInt(getMaxAge());
        } else {
            age = 0;
        }
    }

    @Override
    abstract public void act(List<Entity> newOrganisms, Weather weather, TimeOfDay time);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Removes the current organism from the field.
     */
    protected void remove() {
        setDead();
        if(location != null) {
            field.clear(location);
            location = null;
            //setField(null);
        }
    }

    /**
     * Indicate that the animal is no longer alive.
     */
    protected void setDead()
    {
        alive = false;
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return this.location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    // TEMP
    protected void setLocationToNull() {
        location = null;
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return this.field;
    }

    abstract public double getBreedingProbability();

    abstract public int getMaxLitterSize();

    abstract public int getMaxAge();

    abstract public int getBreedingAge();

    protected int breed() {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    abstract protected Organism createNewOrganism(Field field, Location location);

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newOrganisms A list to return newly born organisms.
     */
    protected void giveBirth(List<Entity> newOrganisms) {
        // TODO: Account for fact adjacent organism may have free adjacent location, but current one may not.

        // New organisms are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newOrganisms.add(createNewOrganism(field, loc));
        }
    }

    // MAY CHANGE - canBreed() may not need specific calls for each species
    abstract protected boolean canBreed();

    /**
     * Increase the age.
     * This could result in the organisms death.
     */
    protected void incrementAge()
    {
        if (isAlive()) {
            age++;

            if (age > getMaxAge()) {
                //setDead();
                remove();
            }
        }

    }

    protected int getAge() {
        return this.age;
    }

    protected void setField(Field field) {
        this.field = field;
    }

}
