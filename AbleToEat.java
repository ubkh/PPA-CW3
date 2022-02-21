
public interface AbleToEat {

    Location findFood();

    boolean eatOrLeave(Prey animal);

    // will become common amongst all ANIMALS
    void incrementHunger();
    // will become common amongst all ANIMALS
    void incrementFoodLevel(int foodLevel);
}
