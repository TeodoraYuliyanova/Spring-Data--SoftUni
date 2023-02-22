import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final String COLUMN_LABEL_NAME = "name";
    private static final String COLUMN_LABEL_AGE = "age";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getConnection();
        final int villainId = Integer.parseInt(scanner.nextLine());

        final PreparedStatement villainStatement = connection.prepareStatement(SQL_Statements.GET_VILLAIN_NAME_BY_ID);
        villainStatement.setInt(1, villainId);
        ResultSet villainSet = villainStatement.executeQuery();

        if (!villainSet.next()) {
            System.out.printf(Outputs.NO_SUCH_VILLAIN_FORMAT, villainId);
            return;
        }

        String villainName = villainSet.getString(COLUMN_LABEL_NAME);
        System.out.println("Villain: " + villainName);

        final PreparedStatement minionsStatement = connection.prepareStatement(SQL_Statements.GET_MINIONS_NAMES_BY_VILLAIN_ID);
        minionsStatement.setInt(1, villainId);

        ResultSet minionsSet = minionsStatement.executeQuery();

        for (int i = 1; minionsSet.next(); i++) {
            String minionName = minionsSet.getString(COLUMN_LABEL_NAME);
            int minionAge = minionsSet.getInt(COLUMN_LABEL_AGE);

            System.out.printf(Outputs.MINIONS_NAME_AGE_FORMAT, i, minionName, minionAge);
        }
        connection.close();

    }
}
