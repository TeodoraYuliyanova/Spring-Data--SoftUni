import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String country = scanner.nextLine();
        List<String> townsUpdated = new ArrayList<>();

        Connection connection = Utils.getConnection();

        PreparedStatement getTownsInCountryStm = connection.prepareStatement(SQL_Statements.GET_TOWNS_IN_COUNTRY);
        getTownsInCountryStm.setString(1,country);
        ResultSet townsInCountrySet = getTownsInCountryStm.executeQuery();

        while (townsInCountrySet.next()){

            String townName = townsInCountrySet.getString("name");
            PreparedStatement makeTownUpperCase = connection.prepareStatement(SQL_Statements.MAKE_TOWN_UPPERCASE);
            makeTownUpperCase.setString(1,townName);
            makeTownUpperCase.executeUpdate();
            townsUpdated.add(townName);

        }

        if (townsUpdated.isEmpty()){
            System.out.println(Outputs.NO_TOWNS_AFFECTED);
        }else {
            int countTownsUpdated = townsUpdated.size();
            System.out.printf(Outputs.COUNT_TOWNS_UPDATED,countTownsUpdated);
            System.out.println(townsUpdated);
        }

        connection.close();
    }
}
