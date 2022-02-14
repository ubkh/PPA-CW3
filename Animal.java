import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    protected int age;
    private int breedingProbability;
    private int maxLitterSize;
    private int breedingAge;
    protected int maxAge;

    private boolean isMale;

    protected Random rand = Randomizer.getRandom();

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);

        // randomly assign male or female
        this.isMale = rand.nextBoolean();
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
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
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     * @param animal Animal which we check to be adjacent or not.
     */
    private boolean canBreed(Animal animal)
    {
        for (Location loc : field.adjacentLocations(location)) {
            if (!animal.getLocation().equals(loc)) {
                return false;
            }
        }

        if (((animal.isMale() && isMale())) || ((!animal.isMale() && !isMale()))) {
            return false;
        }

        return age >= breedingAge;
    }

    /**
     * Increase the age.
     * This could result in the animals death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born rabbits.
     */
    abstract void giveBirth(List<Animal> newAnimals);

    public boolean isMale() {
        return this.isMale;
    }
}
