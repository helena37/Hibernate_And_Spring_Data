import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static com.imports.MyValues.*;

/**
 * @author Elena Hristoskova
 * @since 2020-02-17
 */
public class E08_increaseMinionsAge {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );
        connectionProperties();
        /*
        Read from the console minion IDs, separated by space.
        Increment the age of those minions by 1 and make their names title
        to lower case. Finally, print the names and the ages of all minions
        that are in the database. See the examples below.
         */

        System.out.println("Enter minions id's, separated by single spaces: ");

        int[] minionId = Arrays
                .stream(reader.readLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        modifyMinionNamesAndAges(minionId);

        connection.close();
    }

    private static void modifyMinionNamesAndAges(int[] minionId) throws SQLException {
        for (int i : minionId) {
            query = "UPDATE minions_db.minions m " +
                    "SET m.name = LOWER(m.name)," +
                    " m.age = m.age + 1 " +
                    "WHERE m.id = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1, i);
            statement.executeUpdate();
        }

        printMinionsAndTheirAges();
    }

    private static void printMinionsAndTheirAges() throws SQLException {
        query = "SELECT name, age " +
                "FROM minions_db.minions";

        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println(String.format(
                    "%s %d",
                    resultSet.getString("name"),
                    resultSet.getInt("age")
            ));
        }
    }
}
