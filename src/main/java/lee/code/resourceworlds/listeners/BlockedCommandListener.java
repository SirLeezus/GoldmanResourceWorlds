package lee.code.resourceworlds.listeners;

import lee.code.resourceworlds.enums.ResourceWorld;
import lee.code.resourceworlds.lang.Lang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockedCommandListener implements Listener {

  @EventHandler
  public void onBlockCommands(PlayerCommandPreprocessEvent e) {
    final String worldName = e.getPlayer().getWorld().getName();
    if (!worldName.equals(ResourceWorld.WORLD_RESOURCE.getDisplayName()) && !worldName.equals(ResourceWorld.END_RESOURCE.getDisplayName()) && !worldName.equals(ResourceWorld.NETHER_RESOURCE.getDisplayName())) return;
    final String command = e.getMessage();
    if (!command.contains("/home add") && !command.contains("/t create") && !command.contains("/towns create") && !command.contains("/t claim") && !command.contains("/towns claim")) return;
    e.getPlayer().sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_BLOCKED.getComponent(null)));
    e.setCancelled(true);
  }
}
