package exam.constants;

import java.nio.file.Path;

public enum Paths {
    ;

    public static Path TOWNS_IMPORT_XML = Path.of("src/main/resources/files/xml/towns.xml");
    public static Path SHOPS_IMPORT_XML = Path.of("src/main/resources/files/xml/shops.xml");
    public static Path CUSTOMERS_IMPORT_JSON = Path.of("src/main/resources/files/json/customers.json");
    public static Path LAPTOPS_IMPORT_JSON = Path.of("src/main/resources/files/json/laptops.json");
}
