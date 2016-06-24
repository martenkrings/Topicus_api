package controller;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Deze klasse maakt een verbinding met de database.
 * Deze verbinding hoeft maar een keer gemaakt te worden.
 */
public class DatabaseHandler {
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/Komodo";
    private final String DATABASE_USERNAME = "sa";
    private final String DATABASE_PASSWORD = "Saxion01";

    private static DatabaseHandler ourInstance = new DatabaseHandler();
    private ConnectionSource connectionSource;

    private DatabaseHandler() {
        init();
    }

    /**
     * Ddeze methode maakt de verbinding aan.
     */
    public void init() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHandler getInstance() {
        return ourInstance;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }
}
