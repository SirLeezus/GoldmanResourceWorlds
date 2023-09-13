package lee.code.resourceworlds.commands.cmds;

import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.commands.SubCommand;
import lee.code.resourceworlds.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCMD extends SubCommand {
  private final ResourceWorlds resourceWorlds;

  public SetSpawnCMD(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
  }

  @Override
  public String getName() {
    return "setspawn";
  }

  @Override
  public String getDescription() {
    return "Set a resource world spawn.";
  }

  @Override
  public String getSyntax() {
    return "/resourceworld setspawn";
  }

  @Override
  public String getPermission() {
    return "resourceworld.command.setspawn";
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
    final String worldName = player.getWorld().getName();
    if (!resourceWorlds.getWorldManager().getCustomWorlds().contains(worldName)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_SET_SPAWN_INVALID_WORLD.getComponent(null)));
      return;
    }
    resourceWorlds.getCacheManager().getCacheWorld().setSpawn(worldName, player.getLocation());
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SET_SPAWN_SUCCESSFUL.getComponent(new String[]{worldName})));
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
    return new ArrayList<>();
  }
}
