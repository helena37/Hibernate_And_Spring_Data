import java.sql.SQLException;

import static com.imports.MyValues.*;

public class E06_removeVillain {
    public static void main(String[] args) throws SQLException {
        connectionProperties();

        System.out.println("Enter villain Id: ");
        int villainId = Integer.parseInt(reader.readLine());

        findOutput(villainId);
        releaseAllMinionsByGivenVillainId(villainId);
        removeVillainFromDatabase(villainId);
        
        connection.close();
    }
    
    private static boolean checkIfVillainExists(int villainId) throws SQLException {
        query = "SELECT id " +
                "FROM minions_db.villains " +
                "WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    private static void findOutput(int villainId) throws SQLException {
        query = "SELECT COUNT(m.name) released_minions_count " +
                "FROM minions_db.minions m " +
                "JOIN minions_db.minions_villains mv ON m.id = mv.minion_id " +
                "JOIN minions_db.villains v ON mv.villain_id = v.id " +
                "WHERE v.id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            released_minions = resultSet.getInt(
                    "released_minions_count");
        }
    }

    private static void releaseAllMinionsByGivenVillainId(int villainId) throws SQLException {
        query = "DELETE minions_db.minions_villains " +
                "FROM minions_db.minions_villains " +

                "WHERE villain_id = ?;";

        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        statement.execute();
    }

    private static void removeVillainFromDatabase(int villainId) throws SQLException {

        if(!checkIfVillainExists(villainId)) {
            System.out.println("No such villain was found");
            return;
        } else {
            printVillainNameInOutput(villainId);
        }

        query = "DELETE FROM minions_db.villains " +
                "WHERE id = ?";


        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        statement.execute();

    }

    private static void printVillainNameInOutput(int villainId) throws SQLException {
        query = "SELECT name " +
                "FROM minions_db.villains " +
                "WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.printf("%s was deleted\r\n", resultSet.getString("name"));
            System.out.println(String.format(
                    "%d minions released",
                    released_minions
            ));
        }
    }
}
