package lee.code.resourceworlds.lang;

import lee.code.resourceworlds.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  PREFIX("&2&lJourney &6➔ "),
  USAGE("&6&lUsage: &e{0}"),
  LOG_WORLD_LOADED("The world {0} was successfully loaded!"),
  LOG_GOLDEN_COPY_SUCCESSFUL("The golden world for {0} was successfully copied!"),
  LOG_ERROR_GOLDEN_DIRECTORY_NOT_FOUND("Could not find directory {0} so world {1} was not loaded."),
  ;
  @Getter private final String string;

  public String getString(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return value;
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return value;
  }

  public Component getComponent(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return CoreUtil.parseColorComponent(value);
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return CoreUtil.parseColorComponent(value);
  }
}
