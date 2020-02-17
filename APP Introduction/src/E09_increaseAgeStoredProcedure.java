import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.imports.MyValues.*;

/**
 * @author Elena Hristoskova
 * @since 2020-02-17
 */
public class E09_increaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException, IOException {
        connectionProperties();
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );

        System.out.println("Enter minion id to be updated it's age: ");

        int id = Integer.parseInt(reader.readLine());

        updateMinionAge(id);

        printNameAndAgeOfUpdatedMinion(id);

        connection.close();
    }

    private static void printNameAndAgeOfUpdatedMinion(int id) throws SQLException {
        query = "SELECT name, age " +
                "FROM minions_db.minions " +
                "WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println(String.format(
                    "%s %d",
                    resultSet.getString("name"),
                    resultSet.getInt("age")
            ));
        }
    }

    private static void updateMinionAge(int id) throws SQLException {
        // Query for stored procedure is as follows:
        /*
        CREATE PROCEDURE usp_get_older(minion_id INT)
BEGIN
    UPDATE minions m
    SET m.age = m.age + 1
    WHERE m.id = minion_id;
end;
         */
        query = "CALL minions_db.usp_get_older(?)";

        statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        statement.executeUpdate();
    }
}
