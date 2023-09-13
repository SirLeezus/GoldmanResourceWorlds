package lee.code.resourceworlds;

import lee.code.resourceworlds.database.CacheManager;
import lee.code.resourceworlds.database.DatabaseManager;
import lee.code.resourceworlds.managers.WorldManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class ResourceWorlds  extends JavaPlugin {
  @Getter private WorldManager worldManager;
  @Getter private CacheManager cacheManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(databaseManager);
    databaseManager.initialize(false);
    this.worldManager = new WorldManager(cacheManager);
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }
}
