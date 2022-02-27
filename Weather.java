import java.util.*;

public class Weather {

    private static final double SUN_PROBABILITY = 0.2;
    private static final double RAIN_PROBABILITY = 0.1;
    private static final double FOG_PROBABILITY = 0.3;
    private static final double SNOW_PROBABILITY = 0.05;
    private static final double STORM_PROBABILITY = 0.02;

    private static final int MAX_HOURS = 3;

    private ArrayList<WeatherType> recentWeather;
    private WeatherType type;
    private int hours;
    private int count;

    private static final Random rand = Randomizer.getRandom();

    public Weather(WeatherType initialType) {
        this.type = initialType;
        recentWeather = new ArrayList<>();
    }

    public void generate() {
        if (count != 0) {
            count++;
            if (count > hours) {
                count = 0; // reset
            }
            return;
        }

        this.hours = rand.nextInt(MAX_HOURS);

        // Switch statements can't be used here due to double accuracy rules.
        if (rand.nextDouble() <= SUN_PROBABILITY) {
            type = WeatherType.SUN;
        } else if (rand.nextDouble() <= RAIN_PROBABILITY) {
            type = WeatherType.RAIN;
        } else if (rand.nextDouble() <= FOG_PROBABILITY) {
            type = WeatherType.FOG;
        } else if (rand.nextDouble() <= SNOW_PROBABILITY) {
            type = WeatherType.SNOW;
        } else if (rand.nextDouble() <= STORM_PROBABILITY) {
            type = WeatherType.STORM;
        }

        //If in the last 4 weathers we had, there was rain or sun, grow at a higher rate.

        //Adds to the end of the list,
        recentWeather.add(type);

        if (recentWeather.size() == 4){
            //remove from the start of the list
            recentWeather.remove(0);
        }
        count++;
        //return type;
    }

    public ArrayList<WeatherType> getRecentWeather() {
        return recentWeather;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

    public WeatherType getType() {
        return this.type;
    }
}
