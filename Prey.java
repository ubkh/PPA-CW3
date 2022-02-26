import java.util.List;

public abstract class Prey extends Animal implements Consumable {

    private final int foodValue;

    public Prey(int foodValue, int foodLevel, boolean randomAge, Field field, Location location) {
        super(foodLevel, randomAge, field, location);

        this.foodValue = foodValue;
    }

    @Override
    abstract public void act(List<Entity> newPrey, Weather weather, TimeOfDay time);

    @Override
    abstract public boolean canBreed();

    @Override
    public void setEaten() {
        if(getLocation() != null) {
            getField().clear(getLocation());
            setLocationToNull();
            setField(null);
        }
    }

    @Override
    public int getFoodValue() {
        return this.foodValue;
    }

}
