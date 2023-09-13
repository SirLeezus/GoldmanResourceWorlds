package lee.code.resourceworlds.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@DatabaseTable(tableName = "server")
public class ServerTable {
  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(columnName = "resource_world_reset_time")
  private long resourceWorldResetTime;

  public ServerTable() {

  }
}
