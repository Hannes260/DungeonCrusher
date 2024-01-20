package de.hannezhd.dungeoncrusher.sql;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MYSQLManager {
    private FileConfiguration mysqlConfig;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private static MYSQLManager instance;
    private static final Logger logger = Logger.getLogger(MYSQLManager.class.getName());
    private HikariDataSource dataSource;

    public MYSQLManager(File dataFolder) {
        File configFile = new File(dataFolder, "mysql.config");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                mysqlConfig = YamlConfiguration.loadConfiguration(configFile);

                // Setze Standardwerte
                mysqlConfig.set("mysql.host", "localhost");
                mysqlConfig.set("mysql.port", 3306);
                mysqlConfig.set("mysql.database", "meinedatenbank");
                mysqlConfig.set("mysql.username", "root");
                mysqlConfig.set("mysql.password", "passwort");
                mysqlConfig.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mysqlConfig = YamlConfiguration.loadConfiguration(configFile);
        }

        loadConfig();
        initializeDataSource();
    }

    public static MYSQLManager getInstance(File dataFolder) {
        if (instance == null) {
            instance = new MYSQLManager(dataFolder);
        }
        return instance;
    }

    private void loadConfig() {
        host = mysqlConfig.getString("mysql.host");
        port = mysqlConfig.getInt("mysql.port");
        database = mysqlConfig.getString("mysql.database");
        username = mysqlConfig.getString("mysql.username");
        password = mysqlConfig.getString("mysql.password");
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("autoReconnect", true);
        config.setMaximumPoolSize(10); // Anpassen nach Bedarf
        config.setMinimumIdle(2);
        config.setIdleTimeout(0);
        config.addDataSourceProperty("autoclose", false);
        config.setPoolName("DungeonCrusher");
        dataSource = new HikariDataSource(config);
    }

    public void disconnect() {
        try (Connection connection = dataSource.getConnection()) {
            if (dataSource != null) {
                dataSource.close();
                logger.info("Verbindung zur MySQL getrennt");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Fehler beim Schließen der Datenquelle", e);
        }
    }

    public void createTables() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS player_accounts ("
                    + "uuid VARCHAR(255) PRIMARY KEY,"
                    + "balance VARCHAR(255) NOT NULL"
                    + ")";
            statement.execute(createTableQuery);

            String createStatsTableQuery = "CREATE TABLE IF NOT EXISTS player_stats ("
                    + "uuid VARCHAR(255) PRIMARY KEY,"
                    + "deaths INT DEFAULT 0 NOT NULL,"
                    + "kills INT DEFAULT 0 NOT NULL,"
                    + "dungeon INT DEFAULT 0 NOT NULL,"
                    + "sword_lvl INT DEFAULT 0 NOT NULL,"
                    + "helm_lvl INT DEFAULT 0 NOT NULL,"
                    + "chestplate_lvl INT DEFAULT 0 NOT NULL,"
                    + "leggings_lvl INT DEFAULT 0 NOT NULL,"
                    + "boots_lvl INT DEFAULT 0 NOT NULL"
                    + ")";
            statement.execute(createStatsTableQuery);
            String createItemsTableQuery = "CREATE TABLE IF NOT EXISTS player_items ("
                    + "uuid VARCHAR(255) PRIMARY KEY,"
                    + "cobblestone INT DEFAULT 0 NOT NULL,"
                    + "stone INT DEFAULT 0 NOT NULL,"
                    + "raw_copper INT DEFAULT 0 NOT NULL,"
                    + "copper_ingot INT DEFAULT 0 NOT NULL,"
                    + "raw_iron INT DEFAULT 0 NOT NULL,"
                    + "iron_ingot INT DEFAULT 0 NOT NULL,"
                    + "raw_gold INT DEFAULT 0 NOT NULL,"
                    + "gold_ingot INT DEFAULT 0 NOT NULL,"
                    + "diamond INT DEFAULT 0 NOT NULL,"
                    + "diamond_ore INT DEFAULT 0 NOT NULL,"
                    + "netherite_scrap INT DEFAULT 0 NOT NULL,"
                    + "netherite_ingot INT DEFAULT 0 NOT NULL,"
                    + "coal INT DEFAULT 0 NOT NULL"
                    + ")";
            statement.execute(createItemsTableQuery);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBalance(String uuid, String balance) {
        try (Connection connection = dataSource.getConnection()) {
            String checkQuery = "SELECT uuid FROM player_accounts WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String updateQuery = "UPDATE player_accounts SET balance = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, balance);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            checkStatement.close();
                        }
                    } else {
                        String insertQuery = "INSERT INTO player_accounts (uuid, balance) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setString(2, balance);
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Aktualisieren des Kontostands in der Datenbank", e);
        }
    }
    public String getBalance(String uuid) {
        String balance = "0.00";

        try (Connection connection = dataSource.getConnection()) {
            // Query vorbereiten
            String query = "SELECT balance FROM player_accounts WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                // Query ausführen und Ergebnis abrufen
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        balance = resultSet.getString("balance");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
    public int getItemAmount(String uuid, String itemName) {
        int itemAmount = 0;

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT " + itemName + " FROM player_items WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        itemAmount = resultSet.getInt(itemName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemAmount;

    }

    public void updateItemAmount(String uuid, String itemName, int newItemAmount) {
        try (Connection connection = dataSource.getConnection()) {
            // Aktualisiere die Item-Menge
            String updateQuery = "UPDATE player_items SET " + itemName + " = ? WHERE uuid = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, newItemAmount);
                updateStatement.setString(2, uuid);

                int rowsUpdated = updateStatement.executeUpdate();

                // Wenn keine Zeile aktualisiert wurde, füge einen neuen Datensatz ein
                if (rowsUpdated == 0) {
                    String insertQuery = "INSERT INTO player_items (uuid, " + itemName + ") VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, uuid);
                        insertStatement.setInt(2, newItemAmount);
                        insertStatement.executeUpdate();
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getDeaths(String uuid) {
        int totalDeaths = 0;

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT deaths FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        totalDeaths += resultSet.getInt("deaths");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDeaths;
    }
    public void updateDeaths(String uuid, int newDeaths) {
        try (Connection connection = dataSource.getConnection()) {
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere die Todesanzahl
                        String updateQuery = "UPDATE player_stats SET deaths = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newDeaths);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            checkStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, deaths) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newDeaths);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getKills(String uuid) {
        String kills = "0";

        try (Connection connection = dataSource.getConnection()) {
            // Query vorbereiten
            String query = "SELECT kills FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                // Query ausführen und Ergebnis abrufen
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        kills = String.valueOf(resultSet.getInt("kills"));
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kills;
    }
    public void updateKills(String uuid, String newKills) {
        String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
        String updateQuery = "UPDATE player_stats SET kills = ? WHERE uuid = ?";
        String insertQuery = "INSERT INTO player_stats (uuid, kills) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, uuid);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Spieler existiert, also aktualisiere die Kill-Anzahl
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, newKills);
                        updateStatement.setString(2, uuid);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Spieler existiert nicht, füge neuen Datensatz ein
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, uuid);
                        insertStatement.setString(2, newKills);
                        insertStatement.executeUpdate();
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Fehler beim Aktualisieren der Kills in der Datenbank", e);
        }
    }
    public int getSwordLevel(String uuid) {
        int swordLevel = 0;

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT sword_lvl FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        swordLevel = resultSet.getInt("sword_lvl");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return swordLevel;
    }
    public void updateSwordLevel(String uuid, int newSwordLevel) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Schwertlevel
                        String updateQuery = "UPDATE player_stats SET sword_lvl = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newSwordLevel);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, sword_lvl) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newSwordLevel);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getHelmetLevel(String uuid) {
        int helmetLevel = 0;

        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT helm_lvl FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        helmetLevel = resultSet.getInt("helm_lvl");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return helmetLevel;
    }
    public void updateHelmLevel(String uuid, int newHelmLevel) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Helmlevel
                        String updateQuery = "UPDATE player_stats SET helm_lvl = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newHelmLevel);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, helm_lvl) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newHelmLevel);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getChestplateLevel(String uuid) {
        int chestplateLevel = 0;

        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT chestplate_lvl FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        chestplateLevel = resultSet.getInt("chestplate_lvl");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chestplateLevel;
    }
    public void updateChestplateLevel(String uuid, int newChestplateLevel) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Chestplatelevel
                        String updateQuery = "UPDATE player_stats SET chestplate_lvl = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newChestplateLevel);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, chestplate_lvl) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newChestplateLevel);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getLeggingsLevel(String uuid) {
        int leggingsLevel = 0;

        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT leggings_lvl FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        leggingsLevel = resultSet.getInt("leggings_lvl");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leggingsLevel;
    }
    public void updateLeggingsLevel(String uuid, int newLeggingsLevel) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Leggingslevel
                        String updateQuery = "UPDATE player_stats SET leggings_lvl = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newLeggingsLevel);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, leggings_lvl) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newLeggingsLevel);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getBootsLevel(String uuid) {
        int bootsLevel = 0;

        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT boots_lvl FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        bootsLevel = resultSet.getInt("boots_lvl");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bootsLevel;
    }
    public void updateBootsLevel(String uuid, int newBootsLevel) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Bootslevel
                        String updateQuery = "UPDATE player_stats SET boots_lvl = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newBootsLevel);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, boots_lvl) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newBootsLevel);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getDungeonCountForPlayer(String uuid) {
        int dungeonCount = 0;

        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT dungeon FROM player_stats WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, uuid);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        dungeonCount = resultSet.getInt("dungeon");
                        statement.close();
                        resultSet.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dungeonCount;
    }
    public void updateDungeonCount(String uuid, int newDungeonCount) {
        try (Connection connection = dataSource.getConnection()){
            // Überprüfen, ob der Spieler bereits in der Tabelle vorhanden ist
            String checkQuery = "SELECT uuid FROM player_stats WHERE uuid = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, uuid);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Spieler existiert, also aktualisiere den Dungeon-Zähler
                        String updateQuery = "UPDATE player_stats SET dungeon = ? WHERE uuid = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, newDungeonCount);
                            updateStatement.setString(2, uuid);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                            resultSet.close();
                        }
                    } else {
                        // Spieler existiert nicht, füge neuen Datensatz ein
                        String insertQuery = "INSERT INTO player_stats (uuid, dungeon) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setString(1, uuid);
                            insertStatement.setInt(2, newDungeonCount);
                            insertStatement.executeUpdate();
                            insertStatement.close();
                            resultSet.close();
                        }
                    }
                }
            }

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}