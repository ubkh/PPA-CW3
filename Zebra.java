import java.util.List;

public class Zebra extends Prey {

    public Zebra(int foodValue, boolean randomAge, Field field, Location location) {
        super(foodValue, randomAge, field, location, 40);
        super.breedingAge = 15;
        super.breedingProbability= 0.4;
        super.maxLitterSize = 4;
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
            System.out.println("ZEBRA BREED");
            Location loc = free.remove(0);
            Zebra young = new Zebra(super.getFoodValue(),true, field, loc);
            newZebras.add(young);
        }
    }

    protected boolean canBreed()
    {
        if (age < breedingAge) {
            return false;
        }

        for (Location loc : getField().adjacentLocations(getLocation())) {

            Object animal = getField().getObjectAt(loc);

            if (animal instanceof Zebra) {
                //System.out.println("LION IN ADJACENT LOCATION");
                Zebra zebra = (Zebra) animal;

//                if (!lion.getLocation().equals(loc)) {
//                    continue;
//                }

                if (!(((zebra.isMale() && isMale())) || ((!zebra.isMale() && !isMale())))) {
                    System.out.println("ZEBRA CAN BREED");
                    return true;
                }
            }
        }
        return false;

        //return age >= breedingAge;
    }
}
