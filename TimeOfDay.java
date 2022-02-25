public enum TimeOfDay {
    SUNRISE,
    MORNING,
    AFTERNOON,
    SUNSET,
    EVENING,
    NIGHT;

    private static final TimeOfDay[] values = values();

    public TimeOfDay next() {
        return values[(this.ordinal()+1) % values.length];
    }
}
