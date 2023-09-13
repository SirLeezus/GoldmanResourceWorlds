package lee.code.resourceworlds.database.cache;

import lee.code.resourceworlds.database.DatabaseManager;
import lee.code.resourceworlds.database.handler.DatabaseHandler;
import lee.code.resourceworlds.database.tables.WorldTable;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.Location;

import java.util.concurrent.ConcurrentHashMap;

public class CacheWorld extends DatabaseHandler {
  private final ConcurrentHashMap<String, WorldTable> worldCache = new ConcurrentHashMap<>();

  public CacheWorld(DatabaseManager databaseManager) {
    super(databaseManager);
  }

  public void setWorldTable(WorldTable worldTable) {
    worldCache.put(worldTable.getName(), worldTable);
  }

  public WorldTable getWorldTable(String world) {
    return worldCache.get(world);
  }

  public boolean hasSpawn(String world) {
    if (!worldCache.containsKey(world)) return false;
    return getWorldTable(world).getSpawn() != null;
  }

  public Location getSpawn(String world) {
    return CoreUtil.parseLocation(getWorldTable(world).getSpawn());
  }

  public void setSpawn(String world, Location location) {
    final WorldTable worldTable = getWorldTable(world);
    worldTable.setSpawn(CoreUtil.serializeLocation(location));
    updateWorldDatabase(worldTable);
  }

  public boolean hasWorlds() {
    return !worldCache.isEmpty();
  }
}
