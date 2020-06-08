package cn.tedu.nybike.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.tedu.nybike.entity.TripDayCountVO2;
import cn.tedu.nybike.service.TripService;

@WebServlet("/tripDayCount2")
public class TripDayCountServlet2 extends HttpServlet{

	private TripService service = new TripService();
	
	private static final long serialVersionUID = -2714775926396105178L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		
		//调用TripDayCountVO方法，获取vo对象
		TripDayCountVO2 vo = service.findDayCount2();
		//判断vo对象是否为null
		if(vo!=null) {
			//不为null
			//将vo对象转换成JSON字符串,
			String jsonStr = JSON.toJSONString(vo);
			//通知浏览器，本次返回的数据格式为JSON格式
			resp.setContentType("application/json;cahrset=utf-8");
			//将JSON字符串添加到resp对象中
			resp.getWriter().write(jsonStr);
			
		}else {
			resp.setContentType("application/json;cahrset=utf-8");
			//将JSON字符串添加到resp对象中
			resp.getWriter().write("{}");
		}
		//为null
		    //返回空的JSON字符串
		    
	
	
	
	
	}
	

}
