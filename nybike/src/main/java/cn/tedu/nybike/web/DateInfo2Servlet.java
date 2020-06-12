package cn.tedu.nybike.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.tedu.nybike.entity.DateInfoVO;
import cn.tedu.nybike.entity.TripDayCountVO;
import cn.tedu.nybike.service.TripService;

public class DateInfo2Servlet extends HttpServlet {

	private static final long serialVersionUID = 8804763002272618121L;

	private TripService service = new TripService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DateInfoVO vo = service.findDateInfo();
		//将数据保存到request作用域中
		req.setAttribute("dateInfoVO",vo );
		
		//将请求转发给tripDayHourCount.jsp  /是从webapp目录开始算的
//		RequestDispatcher rd = req.getRequestDispatcher("/tripDayHourCount.jsp");
//        rd.forward(req, resp);
		req.getRequestDispatcher("/tripDayHourCount.jsp").forward(req, resp);
		

	}

}
