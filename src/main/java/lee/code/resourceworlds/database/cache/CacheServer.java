package lee.code.resourceworlds.database.cache;

import lee.code.resourceworlds.database.DatabaseManager;
import lee.code.resourceworlds.database.handler.DatabaseHandler;
import lee.code.resourceworlds.database.tables.ServerTable;
import lee.code.resourceworlds.lang.Lang;
import lee.code.resourceworlds.utils.CoreUtil;
import lombok.Getter;

public class CacheServer extends DatabaseHandler {
  @Getter private ServerTable serverTable = null;

  public CacheServer(DatabaseManager databaseManager) {
    super(databaseManager);
  }

  public void setServerTable(ServerTable serverTable) {
    this.serverTable = serverTable;
  }

  public boolean isResetReady() {
    return serverTable.getResourceWorldResetTime() < System.currentTimeMillis();
  }

  public void setNextReset(long value) {
    serverTable.setResourceWorldResetTime(value);
    updateServerDatabase(serverTable);
  }

  public void setNextReset() {
    serverTable.setResourceWorldResetTime(System.currentTimeMillis() + CoreUtil.millisecondsToMidnightPST());
    updateServerDatabase(serverTable);
  }

  public String getNextReset() {
    if (serverTable.getResourceWorldResetTime() < System.currentTimeMillis()) return Lang.NEXT_RESTART.getString();
    return CoreUtil.parseTime(serverTable.getResourceWorldResetTime() - System.currentTimeMillis());
  }
}
