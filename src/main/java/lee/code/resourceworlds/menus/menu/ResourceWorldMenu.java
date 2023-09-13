package lee.code.resourceworlds.menus.menu;

import lee.code.resourceworlds.database.cache.CacheWorld;
import lee.code.resourceworlds.lang.Lang;
import lee.code.resourceworlds.menus.menu.menudata.ResourceWorldItem;
import lee.code.resourceworlds.menus.system.MenuButton;
import lee.code.resourceworlds.menus.system.MenuGUI;
import lee.code.resourceworlds.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ResourceWorldMenu extends MenuGUI {
  private final CacheWorld cacheWorld;

  public ResourceWorldMenu(CacheWorld cacheWorld) {
    this.cacheWorld = cacheWorld;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_RESOURCE_WORLD_TITLE.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    for (ResourceWorldItem resourceWorldItem : ResourceWorldItem.values()) {
      addButton(resourceWorldItem.getSlot(), createButton(player, resourceWorldItem));
    }
    super.decorate(player);
  }

  private MenuButton createButton(Player player, ResourceWorldItem resourceWorldItem) {
    return new MenuButton()
      .creator(p -> resourceWorldItem.createItem())
      .consumer(e -> {
        if (!cacheWorld.hasSpawn(resourceWorldItem.getResourceWorld().getDisplayName())) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_MENU_NO_SPAWN_SET.getComponent(new String[]{resourceWorldItem.getResourceWorld().getDisplayName()})));
          return;
        }
        player.teleportAsync(cacheWorld.getSpawn(resourceWorldItem.getResourceWorld().getDisplayName())).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_WORLD_TELEPORT_SUCCESSFUL.getComponent(new String[]{CoreUtil.capitalize(resourceWorldItem.getResourceWorld().getDisplayName())})));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_WORLD_TELEPORT_FAILED.getComponent(new String[]{CoreUtil.capitalize(resourceWorldItem.getResourceWorld().getDisplayName())})));
        });
      });
  }
}
