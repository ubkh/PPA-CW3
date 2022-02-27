import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.03;
    // The probability that a rabbit will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.1;

    private static final double VULTURE_CREATION_PROBABILITY = 0.025;

    private static final double ELEPHANT_CREATION_PROBABILITY = 0.025;
    private static final double CHEETAH_CREATION_PROBABILITY = 0.03;
    private static final double GOAT_CREATION_PROBABILITY = 0.025;


    private static final double GRASS_CREATION_PROBABILITY = 0.02;

    // List of organisms in the field.
    private List<Entity> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The current hour of the simulation.
    private int hour;
    // Indicates current state of time in the simulation.
    private TimeOfDay currentTime;

    private Weather currentWeather;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        organisms = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Zebra.class, Color.BLUE);
        view.setColor(Lion.class, Color.RED);
        view.setColor(Vulture.class, Color.ORANGE);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Goat.class, Color.PINK);
        view.setColor(Elephant.class, Color.GRAY);
        view.setColor(Cheetah.class, Color.MAGENTA);

        // Setup a valid starting point
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(180);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn organisms.
        List<Entity> neworganisms = new ArrayList<>();
        // Let all rabbits act.
        for(Iterator<Entity> it = organisms.iterator(); it.hasNext(); ) {
            Entity entity = it.next();
            Organism organism = (Organism) entity;

            organism.act(neworganisms, currentWeather, currentTime);
            if(! organism.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born foxes and rabbits to the main lists.
        organisms.addAll(neworganisms);

        int day = step/120 +1;
        hour = (step/5) % 24 + 1;


        view.showStatus(step, field);
        view.updateTimeLabel(day,hour);

        // every hour, generate new weather if we are done with current weather
        if (step % 5 == 0) {
            currentWeather.generate();
            System.out.println("HOUR " + hour);
            System.out.println(currentWeather.getType());
        }

        // update time
        if ((hour % 4 == 0) && (step % 5 == 0)) {
            //System.out.println("HOUR " + hour);
            currentTime = currentTime.next();
            //System.out.println(currentTime);
        }
    }

    public int getHour() {
        return hour;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;

        currentTime = TimeOfDay.SUNRISE;
        currentWeather = new Weather(WeatherType.SUN); // make this random at start

        organisms.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(20, true, field, location);
                    organisms.add(lion);
                }
                else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(5, 15, true, field, location);
                    organisms.add(zebra);
                }
                else if(rand.nextDouble() <= VULTURE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Vulture vulture = new Vulture(15, true, field, location);
                    organisms.add(vulture);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(1, 1, true, field, location);
                    organisms.add(grass);
                }
                else if(rand.nextDouble() <= GOAT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Goat goat = new Goat(5, 20, true, field, location);
                    organisms.add(goat);
                }
                else if(rand.nextDouble() <= ELEPHANT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Elephant elephant = new Elephant(5, 20, true, field, location);
                    organisms.add(elephant);
                }
                else if(rand.nextDouble() <= CHEETAH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Cheetah cheetah = new Cheetah(15, true, field, location);
                    organisms.add(cheetah);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
