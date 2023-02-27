import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        List<Integer> minionsIds = Arrays.stream(scanner.nextLine().split(" ")).map(Integer::parseInt).toList();

        Connection connection = Utils.getConnection();

        for (int i = 0; i < minionsIds.size(); i++) {
            int minionId = minionsIds.get(i);

            PreparedStatement updateMinionAge = connection.prepareStatement(SQL_Statements.UPDATE_MINION_AGE);
            updateMinionAge.setInt(1, minionId);
            updateMinionAge.executeUpdate();

            PreparedStatement updateMinionName = connection.prepareStatement(SQL_Statements.UPDATE_MINION_NAME);
            updateMinionName.setInt(1,minionId);
            updateMinionName.executeUpdate();
        }

        PreparedStatement allMinionStm = connection.prepareStatement(SQL_Statements.ALL_MINIONS);
        ResultSet minionsSet = allMinionStm.executeQuery();

        while (minionsSet.next()) {
            String minionName = minionsSet.getString("name");
            int minionAge = minionsSet.getInt("age");

            System.out.printf(Outputs.PRINT_ALL_MINIONS, minionName, minionAge);

        }
        connection.close();
    }
}
