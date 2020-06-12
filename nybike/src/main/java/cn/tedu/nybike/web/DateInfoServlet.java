package cn.tedu.nybike.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.tedu.nybike.entity.DateInfoVO;
import cn.tedu.nybike.entity.TripDayCountVO;
import cn.tedu.nybike.service.TripService;

public class DateInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 8804763002272618121L;

	private TripService service = new TripService();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DateInfoVO vo = service.findDateInfo();
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().write(JSON.toJSONString(vo));

		

	}

}
