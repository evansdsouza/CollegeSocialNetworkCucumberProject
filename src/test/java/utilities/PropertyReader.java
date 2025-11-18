package utilities;

public class PropertyReader {
    public static String get(String key) {
        return ConfigReader.getProperty(key);
    }
}
