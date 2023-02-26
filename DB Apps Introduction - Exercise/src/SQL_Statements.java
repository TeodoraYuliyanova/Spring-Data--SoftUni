public class SQL_Statements {

    static final String E_02_GET_VILLAINS_NAMES = "SELECT v.`name`,COUNT(DISTINCT(mv.`minion_id`)) AS `count`\n" +
            "FROM `villains` v\n" +
            "JOIN `minions_villains` mv\n" +
            "ON v.`id` = mv.`villain_id`\n" +
            "JOIN `minions` m \n" +
            "ON m.`id` = mv.`minion_id`\n" +
            "GROUP BY mv.`villain_id`\n" +
            "HAVING `count` > 15\n" +
            "ORDER BY `count` DESC";

    static final String GET_MINIONS_NAMES_BY_VILLAIN_ID = "SELECT `name`,`age`\n" +
            "FROM `minions` m\n" +
            "JOIN `minions_villains` mv\n" +
            "ON m.`id` = mv.`minion_id`\n" +
            "WHERE mv.`villain_id` = ?";

    static final String GET_VILLAIN_NAME_BY_ID = "SELECT `name` FROM `villains` WHERE `id` = ?";


    static final String ADD_MINION = "INSERT INTO `minions`(`name`,`age`,`town_id`) VALUES (?,?,(SELECT `id` FROM `towns` WHERE `name` = ?))";
    static final String ADD_TOWN = "INSERT INTO `towns`(`name`) VALUES (?)";
    static final String ADD_VILLAIN = "INSERT INTO `villains`(`name`,`evilness_factor`) VALUES (?,'evil')";
    static final String CHECK_IF_TOWN_EXISTS = "SELECT t.`name` FROM  `towns` t WHERE t.`name` = ?";

    static final String ADD_MINION_TO_VILLAIN = "INSERT INTO `minions_villains`(`minion_id`,`villain_id`)\n" +
            "VALUES(?,?)";

    static final String CHECK_IF_VILLAIN_EXISTS = "SELECT `name` FROM `villains` WHERE `name` = ?";

    static final String GET_LAST_MINION = "SELECT m.`id` FROM `minions` m ORDER BY m.`id` DESC LIMIT 1";

    static final String GET_LAST_VILLAIN = "SELECT v.`id` FROM `villains` v ORDER BY v.`id` DESC LIMIT 1";


    static final String GET_TOWNS_IN_COUNTRY = "SELECT * FROM `towns` WHERE `country` = ?";

    static final String MAKE_TOWN_UPPERCASE = "UPDATE `towns` \n" +
            "SET `name` = UPPER(`name`)\n" +
            "WHERE `name` = ?";

    static final String ALL_MINIONS_NAMES = "SELECT `name` FROM `minions`";

    static final String UPDATE_MINION_AGE = "UPDATE `minions` SET `age` = `age` + 1  WHERE `id` = ?";

    static final String UPDATE_MINION_NAME = "UPDATE `minions` SET `name` = lower(name) WHERE `id` = ?";

    static final String ALL_MINIONS = "SELECT `name`,`age` FROM `minions`";


    static final String MINION_NAME_AGE_BY_ID = "SELECT `name`,`age` FROM `minions` WHERE `id` = ?";
}
