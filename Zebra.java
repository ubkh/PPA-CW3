import java.util.List;

public class Zebra extends Prey {

    public Zebra(int foodValue, boolean randomAge, Field field, Location location) {
        super(foodValue, randomAge, field, location);
    }

    @Override
    public void act(List<Animal> newZebras) {
        incrementAge();
        if(isAlive()) {
            giveBirth(newZebras);
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    @Override
    void giveBirth(List<Animal> newZebras) {
        // New zebras are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Zebra young = new Zebra(super.getFoodValue(),true, field, loc);
            newZebras.add(young);
        }
    }
}
