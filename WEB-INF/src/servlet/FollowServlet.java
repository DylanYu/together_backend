package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import together.RequestHandler;

public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		try {
			/* 读取数据 */
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb =new StringBuffer();
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			
			String eid = null;
			String startUid = null;
			String followUid = null;
			JSONObject requestJson = new JSONObject();
			String fromClient = new String();
			if(sb.toString().equals("")){
				eid = "0";
				startUid = "0";
				followUid = "0";
				requestJson = null;
			}
			else {
				fromClient = sb.toString();
				requestJson = JSONObject.fromObject(fromClient);
				eid = requestJson.getString("eid");
				startUid = requestJson.getString("startUid");
				followUid = requestJson.getString("followUid");
			}
			if(requestJson == null)
				result = "fail";
			else {
				RequestHandler handler = new RequestHandler();
				int success = handler.newFollow(eid, startUid, followUid);
				StringBuffer sbResult = new StringBuffer();
				if(success > 0)
					sbResult.append("success");
				else
					sbResult.append("fail");
				result = sbResult.toString();
			}
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


	public FollowServlet() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
