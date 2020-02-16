
import com.imports.MyValues.*;
import java.sql.*;

import static com.imports.MyValues.*;

public class E02_getVillainsNames {

    public static void main(String[] args) throws SQLException {

        connectionProperties();


        getVillainsNamesAndCountOfMinions();

        connection.close();
    }

    private static void getVillainsNamesAndCountOfMinions() throws SQLException {
        query = "SELECT v.name,COUNT(mv.minion_id) minions_count " +
                "FROM minions_db.villains v " +
                "JOIN minions_db.minions_villains mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING minions_count > 15 " +
                "ORDER BY minions_count DESC;";

        statement =
                connection.prepareStatement(query);

        ResultSet resultSet =
                statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(
                    String.format(
                            "%s %d",
                            resultSet.getString("name"),
                            resultSet.getInt("minions_count")));
        }
    }
}
