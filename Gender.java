import java.util.Random;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getRandom() {
        Gender[] values = Gender.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }
}
