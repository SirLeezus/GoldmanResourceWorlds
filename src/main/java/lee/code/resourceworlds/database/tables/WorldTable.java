package lee.code.resourceworlds.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "world")
public class WorldTable {
  @DatabaseField(id = true, canBeNull = false)
  private String name;

  @DatabaseField(columnName = "spawn")
  private String spawn;

  public WorldTable(String name) {
    this.name = name;
  }
}
