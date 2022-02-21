import java.util.List;

public abstract class Plant extends Organism {

    public Plant(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

    }

    @Override
    public void act(List<Entity> newOrganisms) {

    }

    @Override
    public double getBreedingProbability() {
        return 0;
    }

    @Override
    public int getMaxLitterSize() {
        return 0;
    }

    @Override
    protected boolean canBreed() {
        return false;
    }
}
