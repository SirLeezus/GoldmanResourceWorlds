package lee.code.resourceworlds.listeners;

import lee.code.resourceworlds.enums.ResourceWorld;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntitySpawnListener implements Listener {

  @EventHandler (priority = EventPriority.MONITOR)
  public void onSpawnEntity(CreatureSpawnEvent e) {
    if (e.isCancelled()) return;
    if (!ResourceWorld.getAllWorlds().contains(e.getLocation().getWorld().getName())) return;
    if (!e.getEntity().getType().equals(EntityType.PILLAGER)) return;
    if (!CoreUtil.canSpawnEntityRNG(10)) return;
    e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.ILLUSIONER);
  }

  @EventHandler (priority = EventPriority.MONITOR)
  public void onEntityDeath(EntityDeathEvent e) {
    if (e.isCancelled()) return;
    if (!e.getEntity().getType().equals(EntityType.ILLUSIONER)) return;
    e.getEntity().getLocation().getWorld().dropItemNaturally(e.getEntity().getLocation(), new ItemStack(Material.TOTEM_OF_UNDYING));
  }
}
