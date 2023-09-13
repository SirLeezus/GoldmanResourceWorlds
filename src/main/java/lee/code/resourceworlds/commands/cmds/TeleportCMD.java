package lee.code.resourceworlds.commands.cmds;

import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.commands.SubCommand;
import lee.code.resourceworlds.database.CacheManager;
import lee.code.resourceworlds.lang.Lang;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TeleportCMD extends SubCommand {
  private final ResourceWorlds resourceWorlds;

  public TeleportCMD(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
  }

  @Override
  public String getName() {
    return "teleport";
  }

  @Override
  public String getDescription() {
    return "Teleport to resource world.";
  }

  @Override
  public String getSyntax() {
    return "/resourceworld teleport &f<world>";
  }

  @Override
  public String getPermission() {
    return "resourceworld.command.teleport";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return false;
  }

  @Override
  public void perform(Player player, String[] args) {
    if (args.length < 2) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{getSyntax()}));
      return;
    }
    final String worldName = args[1];
    if (!resourceWorlds.getWorldManager().getCustomWorlds().contains(worldName)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_NOT_RESOURCE_WORLD.getComponent(new String[]{worldName})));
      return;
    }
    final CacheManager cacheManager = resourceWorlds.getCacheManager();
    if (!cacheManager.getCacheWorld().hasSpawn(worldName)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_TELEPORT_NO_SPAWN_SET.getComponent(new String[]{worldName})));
      return;
    }
    player.teleportAsync(cacheManager.getCacheWorld().getSpawn(worldName)).thenAccept(result -> {
      if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.WORLD_TELEPORT_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(worldName)})));
      else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.WORLD_TELEPORT_FAILED.getComponent(new String[]{CoreUtil.capitalize(worldName)})));
    });
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 2 && args[0].equalsIgnoreCase("teleport")) return StringUtil.copyPartialMatches(args[1], resourceWorlds.getWorldManager().getCustomWorlds(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
