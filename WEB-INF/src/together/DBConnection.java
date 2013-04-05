package together;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

public class DBConnection {
	private static Connection conn = null;
	private static volatile DBConnection INSTANCE = null;
	
	private static String user = SaeUserInfo.getAccessKey();
	private static String pass = SaeUserInfo.getSecretKey();
	private static String port = "3307";
	private static String db = "app_itogether";
	private static String host = "w.rdc.sae.sina.com.cn";
	private static String host2 = "r.rdc.sae.sina.com.cn";

	private DBConnection() {
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		synchronized (DBConnection.class) {
			if (null == INSTANCE) {
				INSTANCE = new DBConnection();
			}
//			if(null == conn) {
//				Class.forName("com.mysql.jdbc.Driver").newInstance();
//				conn = DriverManager.getConnection(
//						String.format("jdbc:mysql://%s:%s/%s", host2, port, db), 
//						user, pass);
//			}
		}
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(
				String.format("jdbc:mysql://%s:%s/%s", host, port, db), 
				user, pass);
		return conn;
	}
}
