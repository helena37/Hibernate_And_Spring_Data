import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

import static com.imports.MyValues.*;

public class E07_printAllMinionNames {
    public static void main(String[] args) throws SQLException {
        connectionProperties();

        executeMinionNames();

        connection.close();
    }

    private static void executeMinionNames() throws SQLException {
        query = "SELECT name FROM minions_db.minions";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        Deque<String> minionNames = new ArrayDeque<>();

        while (resultSet.next()) {
            minionNames.add(resultSet.getString(1));
        }

        printMinions(minionNames);
    }

    private static void printMinions(Deque<String> minionNames) {
        int counter = 0;
        while (!minionNames.isEmpty()) {
            counter++;
            if (counter % 2 == 0) {
               String minion = minionNames.removeLast();
                System.out.println(minion);
            } else {
               String minion = minionNames.removeFirst();
                System.out.println(minion);
            }
        }
    }
}
