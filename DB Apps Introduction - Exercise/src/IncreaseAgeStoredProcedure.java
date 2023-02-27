import java.sql.*;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connection = Utils.getConnection();

        int minionId = Integer.parseInt(scanner.nextLine());


        CallableStatement minionProcedureStm = connection.prepareCall("CALL usp_get_older (?)");
        minionProcedureStm.setInt(1,minionId);
        minionProcedureStm.executeUpdate();

        PreparedStatement minion = connection.prepareStatement(SQL_Statements.MINION_NAME_AGE_BY_ID);
        minion.setInt(1,minionId);
        ResultSet minionSet = minion.executeQuery();
        minionSet.next();
        String minionName = minionSet.getString("name");
        int minionAge = minionSet.getInt("age");

        System.out.printf(Outputs.PRINT_MINION, minionName, minionAge);

        connection.close();

    }
}
