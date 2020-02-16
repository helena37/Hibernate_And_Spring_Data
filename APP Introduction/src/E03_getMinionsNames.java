import com.imports.MyValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The first part of the code is from Exercise (Chavdar Mitkov)
 * The second code print only the unique results and is written by myself -> see @author
 * @author Elena Hristoskova
 * @since 2020-02-15
 */

public class E03_getMinionsNames {
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        MyValues.connectionProperties();

        getMinionsNames();

        MyValues.connection.close();
    }

//    private static void getMinionsNames() throws IOException, SQLException {
//        reader =
//                new BufferedReader(
//                        new InputStreamReader(
//                                System.in
//                        )
//                );
//
//        System.out.println("Enter villain id: ");
//        int villain_id = Integer.parseInt(reader.readLine());
//
//        if (!checkIfEntityExists(villain_id)) {
//            System.out.println(String.format("No villain with ID %d exists in the database.",
//                    villain_id));
//            return;
//        }
//
//        System.out.println(String.format(
//                "Villain: %s", getVillainsNameById(villain_id)
//        ));
//
//        getMinionsNameAndAgeByVillainsId(villain_id);
//
//    }
//
//    private static void getMinionsNameAndAgeByVillainsId(int villain_id) throws SQLException {
//        com.imports.MyValues.query = "SELECT m.name,\n" +
//                "       m.age\n" +
//                "FROM minions_db.minions m\n" +
//                "JOIN minions_db.minions_villains mv on m.id = mv.minion_id\n" +
//                "WHERE mv.villain_id = ?;";
//
//        com.imports.MyValues.statement = com.imports.MyValues.connection.prepareStatement(com.imports.MyValues.query);
//        com.imports.MyValues.statement.setInt(1, villain_id);
//        ResultSet resultSet = com.imports.MyValues.statement.executeQuery();
//
//        int counter = 0;
//
//        while (resultSet.next()) {
//            System.out.println(String.format(
//                    "%d. %s %d",
//                    ++counter,
//                    resultSet.getString("name"),
//                    resultSet.getInt("age")
//            ));
//        }
//    }
//
//    private static String getVillainsNameById(int villain_id) throws SQLException {
//        com.imports.MyValues.query = "SELECT name FROM minions_db.villains WHERE id = ?";
//        com.imports.MyValues.statement = com.imports.MyValues.connection.prepareStatement(com.imports.MyValues.query);
//        com.imports.MyValues.statement.setInt(1, villain_id);
//        ResultSet resultSet = com.imports.MyValues.statement.executeQuery();
//
//        return resultSet.next() ? resultSet.getString("name") : null;
//    }
//
//    private static boolean checkIfEntityExists(int villain_id) throws SQLException {
//        com.imports.MyValues.query = "SELECT * FROM minions_db.villains WHERE id = ?";
//        com.imports.MyValues.statement = com.imports.MyValues.connection.prepareStatement(com.imports.MyValues.query);
//        com.imports.MyValues.statement.setInt(1, villain_id);
//        ResultSet resultSet = com.imports.MyValues.statement.executeQuery();
//
//        return resultSet.next();
//    }

    private static void getMinionsNames() throws IOException, SQLException {
        reader =
                new BufferedReader(
                        new InputStreamReader(
                                System.in
                        )
                );

        MyValues.query = "SELECT v.name, m.name, m.age " +
                "FROM minions_db.minions m " +
                "JOIN minions_db.minions_villains mv on m.id = mv.minion_id " +
                "JOIN minions_db.villains v on mv.villain_id = v.id " +
                "WHERE mv.villain_id = ?;";

        System.out.println("Enter villain id: ");

        int villainsId = Integer.parseInt(reader.readLine());

        MyValues.statement = MyValues.connection.prepareStatement(MyValues.query);
        MyValues.statement.setInt(1, villainsId);

        ResultSet resultSet = MyValues.statement.executeQuery();


        //Map to be filled with data from query string!!!
        Map<String, LinkedHashMap<String, Integer>> result = new LinkedHashMap<>();

        if (!resultSet.next()) {
            System.out.println(String.format("No villain with ID %d exists in the database.",
                    villainsId));
            return;
        }

        //Fill map with data that must be printed to the end
        while (resultSet.next()) {
            String vName = resultSet.getString("v.name");
            String mName = resultSet.getString("m.name");
            int mAge = resultSet.getInt("m.age");

            if (!result.containsKey(vName)) {
                result.put(vName, new LinkedHashMap<>());
            }
            result.get(vName).putIfAbsent(mName, mAge);
        }

        int counter = 0;

        //Print data
        for (Map.Entry<String, LinkedHashMap<String, Integer>> entry : result.entrySet()) {
            System.out.println("Villain: " + entry.getKey());
            LinkedHashMap<String, Integer> minions = entry.getValue();

            for (Map.Entry<String, Integer> minion : minions.entrySet()) {
                counter++;
                System.out.println(counter + "." + " " + minion.getKey() + " " + minion.getValue());
            }
        }
    }
}
