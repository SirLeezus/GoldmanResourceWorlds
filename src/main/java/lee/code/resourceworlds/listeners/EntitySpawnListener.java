package lee.code.resourceworlds.listeners;

import lee.code.resourceworlds.enums.ResourceWorld;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawnListener implements Listener {

  @EventHandler (priority = EventPriority.MONITOR)
  public void onSpawnEntity(CreatureSpawnEvent e) {
    if (e.isCancelled()) return;
    if (!ResourceWorld.getAllWorlds().contains(e.getLocation().getWorld().getName())) return;
    if (e.getEntity().getType().equals(EntityType.PILLAGER)) {
      if (!CoreUtil.canSpawnEntityRNG(10)) return;
      e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.ILLUSIONER);
    }
  }
}
