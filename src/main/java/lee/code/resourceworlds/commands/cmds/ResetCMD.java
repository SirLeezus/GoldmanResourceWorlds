package lee.code.resourceworlds.commands.cmds;

import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.commands.SubCommand;
import lee.code.resourceworlds.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ResetCMD extends SubCommand {
  private final ResourceWorlds resourceWorlds;

  public ResetCMD(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
  }

  @Override
  public String getName() {
    return "reset";
  }

  @Override
  public String getDescription() {
    return "Set the reset timer to next restart.";
  }

  @Override
  public String getSyntax() {
    return "/resourceworld reset";
  }

  @Override
  public String getPermission() {
    return "resourceworld.command.reset";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return true;
  }

  @Override
  public void perform(Player player, String[] args) {
    performSender(player, args);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    performSender(console, args);
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
    resourceWorlds.getCacheManager().getCacheServer().setNextReset(0);
    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_RESET_SUCCESSFUL.getComponent(null)));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
