import com.imports.MyValues.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.imports.MyValues.*;

public class E05_changeTownNamesCasing {
    public static void main(String[] args) throws SQLException, IOException {
        /*
        Write a program that changes all town names to uppercase for a given country.
        Print the number of towns that were changed in the format provided in examples.
        On the next line print the names that were changed, separated by coma and a space.
         */
        connectionProperties();

        changeTownsByGivenCountryName();

        connection.close();
    }

    private static void changeTownsByGivenCountryName() throws SQLException, IOException {
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );
        String countryName = reader.readLine();

        query = "UPDATE minions_db.towns t\n" +
                "SET t.name = UPPER(t.name)\n" +
                "WHERE country = ?;";
        statement = connection.prepareStatement(query);
        statement.setString(1, countryName);
        statement.executeUpdate();

        printNumberOfAffectedTowns(countryName);
        printAllTownsInGivenCountry(countryName);
    }

    private static void printAllTownsInGivenCountry(String countryName) throws SQLException {
        query = "SELECT name " +
                "FROM minions_db.towns " +
                "WHERE country = ?";

        statement = connection.prepareStatement(query);
        statement.setString(1, countryName);

        ResultSet resultSet = statement.executeQuery();

        List<String> towns = new ArrayList<>();
        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        if (towns.size() > 0) {
            System.out.print("[" + String.join(", ", towns) + "]");
        } else {
            System.out.println("No town names were affected.");
        }
    }

    private static void printNumberOfAffectedTowns(String countryName) throws SQLException {
        query = "SELECT COUNT(name) town_count " +
                "FROM minions_db.towns WHERE country = ?" +
                "GROUP BY country";
        statement = connection.prepareStatement(query);
        statement.setString(1, countryName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println(String.format("%d town names were affected. ",
                    resultSet.getInt("town_count")));
        }
    }
}
