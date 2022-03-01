public enum TimeOfDay {
    EARLY_MORNING,                  // possibly change these to 00:00  to 02:00
    SUNRISE,    //this default reset
    LATE_MORNING,   //goat is 10% less active
    MIDDAY,
    EARLY_AFTERNOON, //cheetah sleeps
    LATE_AFTERNOON,
    SUNSET,  //vulture sleeps, elephant is 15% less active
    EVENING,
    NIGHT,  //lion sleeps
    AROUND_MIDNIGHT; // Zebra is 10% less active

    //if zebra/goat/elephant is "tired" i.e its a cerain time of day, then there is only a chance of
    //them moving - not restricted from dying of disease or breeding

    //add more times of day to prevent species dying out.

    private static final TimeOfDay[] values = values();

    public TimeOfDay next() {
        return values[(this.ordinal()+1) % values.length];
    }
}
