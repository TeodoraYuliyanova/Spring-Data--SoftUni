import java.sql.*;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_Statements.E_02_GET_VILLAINS_NAMES);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){

            String name = resultSet.getString("name");
            int count = resultSet.getInt("count");
            System.out.println(name + " " + count);
        }
        connection.close();
    }
}