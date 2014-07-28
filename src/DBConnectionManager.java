import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBConnectionManager {
	public Connection getConnection() throws Exception;

	public void freeConnection(Connection cn, PreparedStatement ps, ResultSet rs);
}
