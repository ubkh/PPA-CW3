import java.util.List;

public abstract class Prey extends Animal implements Consumable {

    private int foodValue;

    public Prey(int foodValue, boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

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

    protected void incrementFoodValue(int foodValue) {
        this.foodValue += foodValue;
    }

    @Override
    public boolean eat(Consumable consumable) {
        if (consumable.isPoisonous()) {
            infect(this);
            System.out.println("INFECTED PREY");
        }
        incrementFoodValue(consumable.getFoodValue());
        consumable.setEaten();
        return true;
    }

    // Prey can never be poisonous.
    @Override
    public boolean isPoisonous() {
        return false;
    }
}
