public abstract class Prey extends Animal {

    private int foodValue;

    /**
     * Create a new animal at location in field.
     *
     * @param field    The field currently occupied.
     * @param location The location within the field.
     */
    public Prey(int foodValue, boolean randomAge, Field field, Location location, int maxAge) {
        super(field, location);
        super.maxAge = maxAge;
        this.foodValue = foodValue;
        age = 0;
        if(randomAge) {
            age = rand.nextInt(maxAge);
        }
    }

    public int getFoodValue() {
        return this.foodValue;
    }
}
