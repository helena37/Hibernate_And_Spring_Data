import com.imports.MyValues.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.imports.MyValues.*;

/**
 * @author Elena Hristoskova
 * @since 2020-02-16
 */

public class E04_addMinion {
    public static String minionName;
    public static int minionAge;
    public static String minionTown;
    public static String villainName;
    public static int townId;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {
        reader = new BufferedReader(
                new InputStreamReader(
                        System.in
                )
        );

        /*
        com.imports.MyValues is a class that contains all constants, variables and
        Methods that are usable in all exercises!!!
         */

        //Method with properties to create connection with database
        connectionProperties();

        System.out.println("Enter minions data: ");
        //Enter data in format: Minion: Robert 14 Berlin
        String[] minionsInfo = reader.readLine().split("\\s+");

        System.out.println("Enter villains data: ");
        //Enter data in format: Villain: Gru
        String[] villainsName = reader.readLine().split("\\s+");

        minionName = minionsInfo[1];
        minionAge = Integer.parseInt(minionsInfo[2]);
        minionTown = minionsInfo[3];

        villainName = villainsName[1];

        checkIfTownExists(minionTown);

        checkIfVillainExists(villainName);

        insertAllDataInToDatabase();

        connection.close();
    }

    private static void checkIfVillainExists(String villainName) throws SQLException {
        query = "SELECT name FROM minions_db.villains";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<String> villains = new ArrayList<>();

        while (resultSet.next()) {
            villains.add(resultSet.getString("name"));
        }

        if (!villains.contains(villainName)) {
            insertVillainInToTable(villainName);
        }
    }

    private static void insertAllDataInToDatabase() throws SQLException {
        getTownId(minionTown);

        query = "INSERT INTO minions_db.minions(name, age, town_id) " +
        "VALUES (?, ?, ?)";

        statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        statement.setInt(2, minionAge);
        statement.setInt(3, townId);

        statement.execute();

        System.out.println(String.format("Successfully added %s to be minion of %s.",
                minionName, villainName));
    }

    private static void insertVillainInToTable(String villainName) throws SQLException {
        query = "INSERT INTO minions_db.villains(name, evilness_factor) " +
                "VALUE (?, ?);";

        statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.setString(2, "evil");

        statement.execute();

        System.out.println(String.format("Villain %s was added to the database.",
                villainName));
    }

    private static void checkIfTownExists(String minionTown) throws SQLException {
        query = "SELECT name FROM minions_db.towns;";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<String> minionsNames = new ArrayList<>();

       while (resultSet.next()) {
           minionsNames.add(resultSet.getString("name"));
       }

       if(!minionsNames.contains(minionTown)) {
           insertTownInToTable(minionTown);
       }
    }

    private static void insertTownInToTable(String minionTown) throws SQLException {
        query = "INSERT INTO minions_db.towns(name) " +
                "VALUE (?);";

        statement = connection.prepareStatement(query);
        statement.setString(1, minionTown);

        statement.execute();
        System.out.println(String.format("Town %s was added to the database.",
                minionTown));
    }

    private static void getTownId(String minionTown) throws SQLException {
        query = "SELECT id FROM minions_db.towns WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, minionTown);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            townId = resultSet.getInt("id");
        }
    }
}
