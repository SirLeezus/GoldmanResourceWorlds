package lee.code.resourceworlds.database.handler;

import lee.code.resourceworlds.database.DatabaseManager;
import lee.code.resourceworlds.database.tables.ServerTable;
import lee.code.resourceworlds.database.tables.WorldTable;

public class DatabaseHandler {
  private final DatabaseManager databaseManager;

  public DatabaseHandler(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  public void updateServerDatabase(ServerTable serverTable) {
    databaseManager.updateServerTable(serverTable);
  }

  public void updateWorldDatabase(WorldTable worldTable) {
    databaseManager.updateWorldTable(worldTable);
  }
}
