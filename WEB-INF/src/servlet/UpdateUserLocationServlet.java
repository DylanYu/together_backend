package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import together.RequestHandler;

import net.sf.json.JSONObject;


public class UpdateUserLocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		try {
			/* 读取数据 */
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb =new StringBuffer("");
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			
			String uid;
			String longitude;
			String latitude;
			String fromClient = new String();
			JSONObject requestJson = new JSONObject();
			if(sb.toString().equals("")){
				fromClient = "{\"no\":\"no\"}";
				requestJson = JSONObject.fromObject(fromClient);
				uid = "0";
				longitude = "0";
				latitude = "0";
			}
			else {
				fromClient = sb.toString();
				requestJson = JSONObject.fromObject(fromClient);
				uid = requestJson.getString("uid");
				longitude = requestJson.getString("longitude");
				latitude = requestJson.getString("latitude");
			}
			
			RequestHandler handler = new RequestHandler();
			int success= handler.updateUserLocation(uid, longitude, latitude);
			StringBuffer sbResult = new StringBuffer();
			if(success > 0)
				sbResult.append("successl\n");
			else
				sbResult.append("fail\n");
			result = sbResult.toString();
		} catch (Exception e) {
			result = "{err:\"error\"}" + e.toString();
		} finally {
			/* 返回数据 */
			System.out.println("返回报文:" + result);
			PrintWriter pw = response.getWriter();
			response.setCharacterEncoding("utf-8");
			pw.write(result);
			pw.flush();
			pw.close();
		}
	}


	public UpdateUserLocationServlet() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void init() throws ServletException {
		super.init();
	}
}
