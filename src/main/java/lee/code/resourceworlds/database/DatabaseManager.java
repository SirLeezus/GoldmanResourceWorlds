package lee.code.resourceworlds.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.database.tables.ServerTable;
import lee.code.resourceworlds.database.tables.WorldTable;
import lee.code.resourceworlds.enums.ResourceWorld;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {
  private final ResourceWorlds resourceWorlds;
  private Dao<ServerTable, Integer> serverDao;
  private Dao<WorldTable, String> worldDao;
  private ConnectionSource connectionSource;

  public DatabaseManager(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
  }

  private String getDatabaseURL() {
    if (!resourceWorlds.getDataFolder().exists()) resourceWorlds.getDataFolder().mkdir();
    return "jdbc:sqlite:" + new File(resourceWorlds.getDataFolder(), "database.db");
  }

  public void initialize(boolean debug) {
    if (!debug) LoggerFactory.setLogBackendFactory(LogBackendType.NULL);
    try {
      final String databaseURL = getDatabaseURL();
      connectionSource = new JdbcConnectionSource(
        databaseURL,
        "test",
        "test",
        DatabaseTypeUtils.createDatabaseType(databaseURL));
      createOrCacheTables();
      createDefaultServerData();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void closeConnection() {
    try {
      connectionSource.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void createOrCacheTables() throws SQLException {
    final CacheManager cacheManager = resourceWorlds.getCacheManager();

    //World data
    TableUtils.createTableIfNotExists(connectionSource, WorldTable.class);
    worldDao = DaoManager.createDao(connectionSource, WorldTable.class);
    for (WorldTable worldTable : worldDao.queryForAll()) {
      cacheManager.getCacheWorld().setWorldTable(worldTable);
    }

    //Server data
    TableUtils.createTableIfNotExists(connectionSource, ServerTable.class);
    serverDao = DaoManager.createDao(connectionSource, ServerTable.class);
    for (ServerTable serverTable : serverDao.queryForAll()) {
      cacheManager.getCacheServer().setServerTable(serverTable);
    }
  }

  private void createDefaultServerData() {
    final CacheManager cacheManager = resourceWorlds.getCacheManager();
    if (cacheManager.getCacheServer().getServerTable() == null) {
      final ServerTable serverTable = new ServerTable();
      cacheManager.getCacheServer().setServerTable(serverTable);
      createServerTable(serverTable);
    }
    if (!cacheManager.getCacheWorld().hasWorlds()) {
      for (ResourceWorld resourceWorld : ResourceWorld.values()) {
        final WorldTable worldTable = new WorldTable(resourceWorld.getDisplayName());
        cacheManager.getCacheWorld().setWorldTable(worldTable);
        createWorldTable(worldTable);
      }
    }
  }

  private synchronized void createServerTable(ServerTable serverTable) {
    Bukkit.getAsyncScheduler().runNow(resourceWorlds, scheduledTask -> {
      try {
        serverDao.createIfNotExists(serverTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  public synchronized void updateServerTable(ServerTable serverTable) {
    Bukkit.getAsyncScheduler().runNow(resourceWorlds, scheduledTask -> {
      try {
        serverDao.update(serverTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  private synchronized void createWorldTable(WorldTable worldTable) {
    Bukkit.getAsyncScheduler().runNow(resourceWorlds, scheduledTask -> {
      try {
        worldDao.createIfNotExists(worldTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  public synchronized void updateWorldTable(WorldTable worldTable) {
    Bukkit.getAsyncScheduler().runNow(resourceWorlds, scheduledTask -> {
      try {
        worldDao.update(worldTable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }
}
