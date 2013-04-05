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


public class ListEventServlet extends HttpServlet {
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
			String radius = null;
			JSONObject requestJson = new JSONObject();
			String fromClient = new String();
			if(sb.toString().equals("")){
				fromClient = "{\"no\":\"no\"}";
				uid = "0";
				requestJson = JSONObject.fromObject(fromClient);
			}
			else {
				fromClient = sb.toString();
				requestJson = JSONObject.fromObject(fromClient);
				uid = requestJson.getString("uid");
				radius = requestJson.getString("radius");
			}
			
			RequestHandler handler = new RequestHandler();
			ArrayList<JSONObject> array = handler.listEvent(uid, radius);
			StringBuffer sbResult = new StringBuffer();
			if(array == null)
				sbResult.append("no result\n");
			else
				for(JSONObject obj: array) {
					sbResult.append(obj.toString()+ "\n");
				}
			sbResult.append("from client:\n" + requestJson.toString());
			result = sbResult.toString();
		} catch (Exception e) {
			result = "{err:\"error\"}" + e.toString();
		} finally {
			/* 返回数据 */
			System.out.println("返回报文:" + result);
			PrintWriter pw = response.getWriter();
			pw.write("from server:\n" + result);
			pw.flush();
			pw.close();
		}
	}


	public ListEventServlet() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
