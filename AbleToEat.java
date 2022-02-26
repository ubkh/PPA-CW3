
public interface AbleToEat {

    Location findFood();

    // NOTE: eat() only returns true if the consumable was actually eaten
    boolean eat(Consumable consumable);

    // will become common amongst all ANIMALS
    void incrementHunger();
    // will become common amongst all ANIMALS
    void incrementFoodLevel(int foodLevel);
}
