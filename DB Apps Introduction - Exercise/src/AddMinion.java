import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddMinion {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        List<String> minionInput = Arrays.stream(scanner.nextLine().split(": ")).toList();
        List<String> minionInfo = Arrays.stream(minionInput.get(1).split(" ")).toList();

        String minionName = minionInfo.get(0);
        int minionAge = Integer.parseInt(minionInfo.get(1));
        String minionTown = minionInfo.get(2);

        List<String> villainInput = Arrays.stream(scanner.nextLine().split(": ")).toList();
        List<String> villainInfo = Arrays.stream(villainInput.get(1).split(" ")).toList();
        String villainName = villainInfo.get(0);

        Connection connection = Utils.getConnection();

        PreparedStatement checkTownStatement = connection.prepareStatement(SQL_Statements.CHECK_IF_TOWN_EXISTS);
        checkTownStatement.setString(1, minionTown);
        ResultSet checkTownResult = checkTownStatement.executeQuery();

        if (!checkTownResult.next()) {
            PreparedStatement addTownStatement = connection.prepareStatement(SQL_Statements.ADD_TOWN);
            addTownStatement.setString(1, minionTown);
            addTownStatement.executeUpdate();
            System.out.printf(Outputs.TOWN_SUCCESSFULLY_ADDED, minionTown);
        }

        PreparedStatement minionStatement = connection.prepareStatement(SQL_Statements.ADD_MINION);
        minionStatement.setString(1, minionName);
        minionStatement.setInt(2, minionAge);
        minionStatement.setString(3, minionTown);
        minionStatement.executeUpdate();

        PreparedStatement checkIfVillainExists = connection.prepareStatement(SQL_Statements.CHECK_IF_VILLAIN_EXISTS);
        checkIfVillainExists.setString(1, villainName);
        ResultSet VillainResultSet = checkIfVillainExists.executeQuery();

        if (!VillainResultSet.next()) {
            PreparedStatement addVillainStm = connection.prepareStatement(SQL_Statements.ADD_VILLAIN);
            addVillainStm.setString(1, villainName);
            addVillainStm.executeUpdate();
            System.out.printf(Outputs.VILLAIN_SUCCESSFULLY_ADDED, villainName);
        }

        PreparedStatement lastMinionStm = connection.prepareStatement(SQL_Statements.GET_LAST_MINION);
        ResultSet lastMinionSet = lastMinionStm.executeQuery();
        lastMinionSet.next();
        int minionId = lastMinionSet.getInt("id");

        PreparedStatement lastVillainStm = connection.prepareStatement(SQL_Statements.GET_LAST_VILLAIN);
        ResultSet lastVillainSet = lastVillainStm.executeQuery();
        lastVillainSet.next();
        int villainId = lastVillainSet.getInt("id");

        PreparedStatement addMinionToVillainStm = connection.prepareStatement(SQL_Statements.ADD_MINION_TO_VILLAIN);
        addMinionToVillainStm.setInt(1, minionId);
        addMinionToVillainStm.setInt(2, villainId);
        addMinionToVillainStm.executeUpdate();
        System.out.printf(Outputs.MINION_SUCCESSFULLY_ADDED_TO_VILLAIN, minionName, villainName);

        connection.close();
    }
}
