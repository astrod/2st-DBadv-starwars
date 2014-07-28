import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestGalaxy {
	final static int threadCount = 5;
	
	public static void main(String[] args) throws SQLException {
		Connection cn = null;
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		ConnectionDataSet dd = new ConnectionDataSet();
		DBConnectionManager db = dd.map.get("10.73.45.68");
		
		try{
			//연결 요청
			cn = db.getConnection();
			
			///쿼리 전송 및 실행
			
			cs = cn.prepareCall("{CALL selectUser(?, ?, ?)}");
			cs.setString(1, "@tip");
			cs.setString(2, "@tuid");
			cs.setString(3, "@tgid");
			
			cs.registerOutParameter(1, Types.CHAR);
			cs.registerOutParameter(2, Types.INTEGER);
			cs.registerOutParameter(3, Types.INTEGER);
			
			cs.execute();
			
			String tIP = cs.getString(1);
			int tUID = cs.getInt(2);
			int tGID =cs.getInt(3);
			
			System.out.println(tIP + " " +tUID + " "+tGID);
			
			DBConnectionManager db2 = dd.map.get(tIP);
			cn = db2.getConnection();
			
			ps = cn.prepareStatement("select s.* from ship s inner join user u on s.UID = u.UID where s.UID = ?;");
			ps.setInt(1, tUID);
			

			
	}catch (Exception e){
		e.printStackTrace();
		
	} finally {
		//연결 해제
		db.freeConnection(cn, ps, rs);
		if(cs != null) cs.close();

	}
}
	
	/* 회원가입 / 배 10척 생성하는 자바 코드 + 프로시저 + 기타등등 */
//	public static void main(String[] args) throws SQLException {
//		Connection cn = null;
//		
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		CallableStatement cs = null;
//
//		ConnectionDataSet dd = new ConnectionDataSet();
//		DBConnectionManager db = dd.map.get("10.73.45.68");
//		
//		
//		try{
//			//연결 요청
//			cn = db.getConnection();
//			
//			///쿼리 전송 및 실행
//			
//			cs = cn.prepareCall("{CALL adduser(?, ?, ?)}");
//			cs.setString(1, "@juid");
//			cs.setString(2, "@jdbid");
//			cs.setString(3, "@jgid");
//			
//			cs.registerOutParameter(1, Types.INTEGER);
//			cs.registerOutParameter(3, Types.INTEGER);
//			
//			cs.execute();
//			
//			int juid = cs.getInt(1);
//			int jgid = cs.getInt(3);
//			
//			System.out.println(juid + " " + jgid);
//			
//			cs = cn.prepareCall("{CALL getIP(?, ?, ?)}");
//			cs.setInt(1, juid);
//			cs.setInt(2, jgid);
//			cs.setString(3, "@jIP");
//			
//			cs.registerOutParameter(3, Types.CHAR);
//
//			cs.execute();
//			
//			String IP = cs.getString(3);
//			
//			System.out.println(IP);
//						
//			DBConnectionManager db2 = dd.map.get(IP);
//			
//			cn = db2.getConnection();
//			
//			ps = cn.prepareStatement("CALL createShip(?,?)");
//			ps.setInt(1, juid);
//			ps.setInt(2, jgid);
//			ps.executeQuery();
//			
//		} catch (Exception e){
//			e.printStackTrace();
//			
//		} finally {
//			//연결 해제
//			db.freeConnection(cn, ps, rs);
//			if(cs != null) cs.close();
//
//		}
//
//	}
	
//	public static void main(String[] args) throws InterruptedException{
//		GameMaster g1 = new GameMaster(100000);
//		g1.addUser();
//		
//		ExecutorService exec = Executors.newFixedThreadPool(5);
//		
//		for(int i=0; i<5; i++) {
//			exec.execute(g1);
//		}
//		
//		try {
//			exec.shutdown();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
}
