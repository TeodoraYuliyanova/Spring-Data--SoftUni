import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class PrintAllMinionNames {
    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getConnection();

        PreparedStatement allMinionsNames = connection.prepareStatement(SQL_Statements.ALL_MINIONS_NAMES);
        ResultSet minionsSet = allMinionsNames.executeQuery();

        ArrayDeque<String> minionsQue = new ArrayDeque<>();

        while (minionsSet.next()) {
            String minionName = minionsSet.getString("name");
            minionsQue.add(minionName);

        }

        while (!minionsQue.isEmpty()) {
            System.out.println(minionsQue.pollFirst());
            System.out.println(minionsQue.pollLast());

        }

        connection.close();
    }
}
