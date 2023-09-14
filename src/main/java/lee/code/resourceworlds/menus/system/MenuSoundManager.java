package lee.code.resourceworlds.menus.system;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MenuSoundManager {

  public void playClickSound(Player player) {
    player.playSound(player, Sound.UI_BUTTON_CLICK, (float) 0.5, (float) 1);
  }
}
