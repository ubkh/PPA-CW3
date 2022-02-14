import java.util.List;


// Note: FlyingAnimal can be used as an interface

public class Vulture extends Animal implements AbleToEat {

    private int foodLevel;

    @Override
    public void act(List<Animal> newVultures) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newVultures);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
    public void eatOrLeave(Animal animal) {
        animal.setEaten();
    }

    @Override
    void giveBirth(List<Animal> newAnimals) {

    }

    public void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
}
