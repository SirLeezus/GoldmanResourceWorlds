package lee.code.resourceworlds.commands.cmds;

import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.commands.SubCommand;
import lee.code.resourceworlds.lang.Lang;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HelpCMD extends SubCommand {
  private final ResourceWorlds resourceWorlds;

  public HelpCMD(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
  }

  @Override
  public String getName() {
    return "help";
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
    performSender(player, args);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    performSender(console, args);
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_HELP_LINE_1.getComponent(null));
    lines.add(Lang.COMMAND_HELP_LINE_2.getComponent(null));
    lines.add(Lang.COMMAND_HELP_LINE_3.getComponent(null));
    lines.add(Lang.COMMAND_HELP_LINE_4.getComponent(null));
    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_HELP_FOOTER.getComponent(null));
    for (Component line : lines) sender.sendMessage(line);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
