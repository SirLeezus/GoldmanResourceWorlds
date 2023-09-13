package lee.code.resourceworlds.managers;

import lee.code.resourceworlds.database.CacheManager;
import lee.code.resourceworlds.enums.ResourceWorld;
import lee.code.resourceworlds.lang.Lang;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldManager {
  @Getter final Set<String> customWorlds = ConcurrentHashMap.newKeySet();
  final boolean shouldReset;

  public WorldManager(CacheManager cacheManager) {
    this.shouldReset = cacheManager.getCacheServer().isResetReady();
    if (shouldReset) cacheManager.getCacheServer().setNextReset();
    for (ResourceWorld resourceWorld : ResourceWorld.values()) loadWorld(resourceWorld.getName(), resourceWorld.getEnvironment());
  }

  private void loadWorld(String name, World.Environment environment) {
    if (shouldReset) copyGoldenWorld(name, environment);
    else createWorld(name, environment);
  }

  private void createWorld(String name, World.Environment environment) {
    final long seed = 1542201397407325465L;
    WorldCreator wcWorld = new WorldCreator(name);
    wcWorld.environment(environment);
    wcWorld.seed(seed);
    wcWorld.createWorld();
    customWorlds.add(name);
    Bukkit.getLogger().info(Lang.LOG_WORLD_LOADED.getString(new String[]{name}));
  }

  private void copyGoldenWorld(String name, World.Environment environment) {
    final File worldDirectory = new File("./ResourceWorlds/" + name);
    if (!worldDirectory.exists()) {
      Bukkit.getLogger().info(Lang.LOG_ERROR_GOLDEN_DIRECTORY_NOT_FOUND.getString(new String[]{worldDirectory.getPath(), name}));
      createWorld(name, environment);
      return;
    }
    copyWorldFolder(name, worldDirectory, new File(Bukkit.getWorldContainer(), name));
    createWorld(name, environment);
  }

  private void copyWorldFolder(String name, File source, File target) {
    try {
      FileUtils.deleteDirectory(target);
      FileUtils.copyDirectory(source, target);
      Bukkit.getLogger().info(Lang.LOG_GOLDEN_COPY_SUCCESSFUL.getString(new String[]{name}));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
