package nn.iamj.borne.modules.database;

import org.bukkit.configuration.file.YamlConfiguration;
import nn.iamj.borne.Borne;
import nn.iamj.borne.modules.util.logger.LoggerProvider;

import java.sql.*;

public final class DataBase {

    private Connection connection;

    private String url;
    private String user;
    private String pass;

    public DataBase() {
        final YamlConfiguration configuration = Borne.getBorne().getConfigManager().getFile("config.yml");

        if (configuration == null) return;

        final String host = configuration.getString("DATABASE.CONNECTION.HOST", "localhost");
        final String port = configuration.getString("DATABASE.CONNECTION.PORT", "3306");
        final String base = configuration.getString("DATABASE.CONNECTION.BASE", "borne");

        this.user = configuration.getString("DATABASE.USER.NAME", "root");
        this.pass = configuration.getString("DATABASE.USER.PASS", "");

        final String params = configuration.getString("DATABASE.PARAMS", "autoReconnect=true");

        this.url = "jdbc:mysql://{host}:{port}/{base}?{params}"
                .replace("{host}", host)
                .replace("{port}", port)
                .replace("{base}", base)
                .replace("{params}", params);
    }

    public void executeUpdate(final String sql, final Object... args) {
        try {
            this.connect();

            final PreparedStatement statement = connection.prepareStatement(sql);
            int i = 1;

            for (Object arg : args) {
                statement.setObject(i, arg);
                i++;
            }

            statement.executeUpdate();
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

    public void executeUpdate(final String sql) {
        try {
            this.connect();

            final PreparedStatement statement = connection.prepareStatement(sql);

            statement.executeUpdate();
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

    public ResultSet fetchData(final String sql, final Object... args) {
        try {
            this.connect();

            final PreparedStatement statement = connection.prepareStatement(sql);
            int i = 1;

            for (final Object arg : args) {
                statement.setObject(i, arg);
                i++;
            }

            return statement.executeQuery();
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
            return null;
        }
    }

    public ResultSet fetchData(final String sql) {
        try {
            this.connect();

            final PreparedStatement statement = connection.prepareStatement(sql);

            return statement.executeQuery();
        } catch (final Exception exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
            return null;
        }
    }

    public void create() {
        this.executeUpdate("CREATE TABLE IF NOT EXISTS `profiles` (" +
                "`UNIQUEID` VARCHAR(36)," +
                "`NICKNAME` VARCHAR(64)," +
                "`LEVEL` INTEGER," +
                "`EXPERIENCE` DOUBLE," +
                "`MONEY` DOUBLE," +
                "`DONATED` DOUBLE," +
                "`DEVOLVE` DOUBLE," +
                "`STORAGE` MEDIUMTEXT," +
                "`SETTINGS` VARCHAR(1028)," +
                "`STATISTIC` VARCHAR(2056)" +
                ")");
    }

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) return;

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (final ClassNotFoundException | SQLException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (final SQLException exception) {
            LoggerProvider.getInstance().error("Ops!", exception);
        }
    }

}
