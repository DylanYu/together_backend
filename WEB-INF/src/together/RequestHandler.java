package together;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.sf.json.JSONObject;

public class RequestHandler {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	
	public RequestHandler() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		conn = DBConnection.getConnection();
		String queryUserAll = "select * from user";
		pstmt = conn.prepareStatement(queryUserAll);
	}
	
	public ArrayList<JSONObject> listUser(String uid) throws SQLException {
		ArrayList<JSONObject> array = new ArrayList<JSONObject>();
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			JSONObject obj = new JSONObject();
			obj.put("uid", rs.getString(1));
			obj.put("uname", rs.getString(2));
			obj.put("longitude", rs.getString(3));
			obj.put("latitude", rs.getString(4));
			array.add(obj);
		}
		return array;
	}
	
	public ArrayList<JSONObject> listEvent(String uid, String radius){return null;}
	
	public boolean newEvent(String ename, String uid, String type, 
			String longitude, String latitude, String startDate, 
			String startTime, String endDate, String endTime){return true;}
	
	public boolean updateUserLocation(String uid, String logitude, String latitude){return true;}
}
