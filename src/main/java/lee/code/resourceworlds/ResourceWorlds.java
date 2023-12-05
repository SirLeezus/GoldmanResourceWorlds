package lee.code.resourceworlds;

import com.mojang.brigadier.tree.LiteralCommandNode;
import lee.code.resourceworlds.commands.CommandManager;
import lee.code.resourceworlds.commands.TabCompletion;
import lee.code.resourceworlds.database.CacheManager;
import lee.code.resourceworlds.database.DatabaseManager;
import lee.code.resourceworlds.listeners.BlockedCommandListener;
import lee.code.resourceworlds.listeners.EntitySpawnListener;
import lee.code.resourceworlds.managers.WorldManager;
import lee.code.resourceworlds.menus.system.MenuListener;
import lee.code.resourceworlds.menus.system.MenuManager;
import lombok.Getter;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class ResourceWorlds  extends JavaPlugin {
  @Getter private WorldManager worldManager;
  @Getter private CacheManager cacheManager;
  @Getter private CommandManager commandManager;
  @Getter private MenuManager menuManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(databaseManager);
    databaseManager.initialize(false);
    this.worldManager = new WorldManager(cacheManager);
    this.commandManager = new CommandManager(this);
    this.menuManager = new MenuManager();

    registerCommands();
    registerListeners();
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);
    getServer().getPluginManager().registerEvents(new BlockedCommandListener(), this);
    getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);
  }

  private void registerCommands() {
    getCommand("resourceworld").setExecutor(commandManager);
    getCommand("resourceworld").setTabCompleter(new TabCompletion(commandManager));
    loadCommodoreData();
  }

  private void loadCommodoreData() {
    try {
      final LiteralCommandNode<?> towns = CommodoreFileReader.INSTANCE.parse(getResource("resourceworld.commodore"));
      CommodoreProvider.getCommodore(this).register(getCommand("resourceworld"), towns);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
