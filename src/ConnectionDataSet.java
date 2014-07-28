import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionDataSet {
	HashMap<String, DBConnectionManager> map;
	
	public ConnectionDataSet() {
		map = new HashMap<String, DBConnectionManager>();
		DBConnectionManager m1 = DBConnection.getInstance();
		DBConnectionManager m2 = DBConnection_Second.getInstance();
		DBConnectionManager m3 = DBConnection_Third.getInstance();
		
		
		map.put("10.73.45.68", m1);
		map.put("10.211.55.3", m2);
		map.put("10.73.45.73", m3);
	}
	
	
}
