package lee.code.resourceworlds.menus.menu;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.resourceworlds.ResourceWorlds;
import lee.code.resourceworlds.lang.Lang;
import lee.code.resourceworlds.menus.menu.menudata.ResourceWorldItem;
import lee.code.resourceworlds.menus.system.MenuButton;
import lee.code.resourceworlds.menus.system.MenuGUI;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class ResourceWorldMenu extends MenuGUI {
  private final ResourceWorlds resourceWorlds;
  private ScheduledTask scheduledTask = null;

  public ResourceWorldMenu(ResourceWorlds resourceWorlds) {
    this.resourceWorlds = resourceWorlds;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 45, Lang.MENU_RESOURCE_WORLD_TITLE.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    for (ResourceWorldItem resourceWorldItem : ResourceWorldItem.values()) {
      addButton(resourceWorldItem.getSlot(), createButton(player, resourceWorldItem));
    }
    createTimerItem();
    super.decorate(player);
  }

  @Override
  public void onClose(InventoryCloseEvent e) {
    if (scheduledTask != null) scheduledTask.cancel();
  }

  private MenuButton createButton(Player player, ResourceWorldItem resourceWorldItem) {
    return new MenuButton()
      .creator(p -> resourceWorldItem.createItem())
      .consumer(e -> {
        if (!resourceWorlds.getCacheManager().getCacheWorld().hasSpawn(resourceWorldItem.getResourceWorld().getDisplayName())) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MENU_NO_SPAWN_SET.getComponent(new String[]{resourceWorldItem.getResourceWorld().getDisplayName()})));
          return;
        }
        getMenuSoundManager().playClickSound(player);
        player.teleportAsync(resourceWorlds.getCacheManager().getCacheWorld().getSpawn(resourceWorldItem.getResourceWorld().getDisplayName())).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.WORLD_TELEPORT_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(resourceWorldItem.getResourceWorld().getDisplayName())})));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.WORLD_TELEPORT_FAILED.getComponent(new String[]{CoreUtil.capitalize(resourceWorldItem.getResourceWorld().getDisplayName())})));
        });
      });
  }

  private void createTimerItem() {
    scheduledTask = Bukkit.getAsyncScheduler().runAtFixedRate(resourceWorlds, scheduledTask -> {
      final ItemStack timerItem = ResourceWorldItem.TIMER.createTimerItem(resourceWorlds.getCacheManager().getCacheServer().getNextReset());
      addButton(ResourceWorldItem.TIMER.getSlot(), new MenuButton().creator(p->timerItem).consumer(e-> {}));
      getInventory().setItem(ResourceWorldItem.TIMER.getSlot(), timerItem);
    },0, 1, TimeUnit.SECONDS);
  }
}
