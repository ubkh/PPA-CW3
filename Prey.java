import java.util.List;

public abstract class Prey extends Animal {

    private final int foodValue;

    public Prey(int foodValue, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        this.foodValue = foodValue;
    }

    @Override
    abstract public void act(List<Entity> newPrey, Weather weather, TimeOfDay time);

    @Override
    abstract public boolean canBreed();

    protected void setEaten() {
        if(getLocation() != null) {
            getField().clear(getLocation());
            setLocationToNull();
            setField(null);
        }
    }

    protected int getFoodValue() {
        return this.foodValue;
    }
}
