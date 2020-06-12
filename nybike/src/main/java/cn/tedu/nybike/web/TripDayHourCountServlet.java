package cn.tedu.nybike.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.tedu.nybike.entity.TripDayHourCountVO;
import cn.tedu.nybike.service.TripService;


public class TripDayHourCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TripService service = new TripService();
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//解决请求参数的乱码问题  必须写在req.getParameter方法之前
		request.setCharacterEncoding("UTF-8");
		String dateStr = request.getParameter("dateArray");
		if(dateStr==null) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("请至少选择一个日期!");
			return;
		}
		//前端交过来的其实是一个长字符串，这么写的数组其实就是第一个元素
		//String []dateArray = request.getParameterValues("dateArray");
		//正确的应该用分割，把长字符串分割成一个个小的字符串
		String []dateArray = dateStr.split(",");
		//调用service方法，获取vo对象
		TripDayHourCountVO vo =service.findDayHourCount(dateArray);
		String jsonStr = JSON.toJSONString(vo);
		//通知浏览器，本次返回的数据格式为JSON格式
		response.setContentType("application/json;charset=utf-8");
		//将JSON字符串添加到resp对象中
		response.getWriter().write(jsonStr);
	
	
	}

}
