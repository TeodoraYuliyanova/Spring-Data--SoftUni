package softuni.exam.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static Path TOWNS_PATH_JSON = Path.of("src/main/resources/files/json/towns.json");
    public static Path AGENTS_PATH_JSON = Path.of("src/main/resources/files/json/agents.json");
    public static Path APARTMENTS_PATH_XML = Path.of("src/main/resources/files/xml/apartments.xml");
    public static Path OFFERS_PATH_XML = Path.of("src/main/resources/files/xml/offers.xml");
}
