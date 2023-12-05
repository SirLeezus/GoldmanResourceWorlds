package lee.code.resourceworlds.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;

import java.util.List;

@AllArgsConstructor
public enum ResourceWorld {
  WORLD_RESOURCE("world_resource_normal", World.Environment.NORMAL),
  NETHER_RESOURCE("world_resource_nether", World.Environment.NETHER),
  END_RESOURCE("world_resource_end", World.Environment.THE_END)
  ;
  @Getter private final String displayName;
  @Getter private final World.Environment environment;

  public static List<String> getAllWorlds() {
    return List.of(WORLD_RESOURCE.getDisplayName(), NETHER_RESOURCE.getDisplayName(), END_RESOURCE.getDisplayName());
  }
}
