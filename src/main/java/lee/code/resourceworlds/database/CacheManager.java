package lee.code.resourceworlds.database;

import lee.code.resourceworlds.database.cache.CacheServer;
import lee.code.resourceworlds.database.cache.CacheWorld;
import lombok.Getter;

public class CacheManager {
  @Getter private final CacheServer cacheServer;
  @Getter private final CacheWorld cacheWorld;

  public CacheManager(DatabaseManager databaseManager) {
    this.cacheServer = new CacheServer(databaseManager);
    this.cacheWorld = new CacheWorld(databaseManager);
  }
}
