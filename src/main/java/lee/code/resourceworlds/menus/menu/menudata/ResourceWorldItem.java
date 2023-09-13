package lee.code.resourceworlds.menus.menu.menudata;

import lee.code.resourceworlds.enums.ResourceWorld;
import lee.code.resourceworlds.lang.Lang;
import lee.code.resourceworlds.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@AllArgsConstructor
public enum ResourceWorldItem {
  WORLD(Material.GRASS_BLOCK, "&2&lResource World", "&6» &eClick to teleport to world!", 29, ResourceWorld.WORLD_RESOURCE),
  NETHER(Material.NETHERRACK, "&c&lResource Nether", "&6» &eClick to teleport to world!", 31, ResourceWorld.NETHER_RESOURCE),
  END(Material.END_STONE, "&5&lResource End", "&6» &eClick to teleport to world!", 33, ResourceWorld.END_RESOURCE),
  TIMER(Material.CLOCK, "&r", null, 13, null),

  ;

  private final Material material;
  private final String name;
  private final String lore;
  @Getter private final int slot;
  @Getter private final ResourceWorld resourceWorld;

  public ItemStack createItem() {
    return ItemUtil.createItem(material, name, lore, 0, null);
  }

  public ItemStack createTimerItem(String time) {
    final ItemStack item = new ItemStack(material);
    final ItemMeta itemMeta = item.getItemMeta();
    itemMeta.displayName(Lang.MENU_RESOURCE_WORLD_TIMER_NAME.getComponent(new String[]{time}));
    item.setItemMeta(itemMeta);
    return item;
  }
}
