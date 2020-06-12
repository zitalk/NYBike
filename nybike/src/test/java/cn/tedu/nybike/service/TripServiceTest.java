package cn.tedu.nybike.service;

import org.junit.Test;

import cn.tedu.nybike.entity.DateInfoVO;
import cn.tedu.nybike.entity.TripDayCountVO;
import cn.tedu.nybike.entity.TripDayCountVO2;
import cn.tedu.nybike.entity.TripDayHourCount;
import cn.tedu.nybike.entity.TripDayHourCountVO;

public class TripServiceTest {
	TripService service = new TripService();
	String []dateArray = {"2020-04-01","2019-02-05"};
	@Test
	public void findDayHourCount() {
		TripDayHourCountVO vo = service.findDayHourCount(dateArray);
		System.out.println(vo);
	}
	@Test
	public void findDateInfo() {
		DateInfoVO vo = service.findDateInfo();
		System.out.println(vo);
	}
//	@Test
//	public void findDayCount() {
//	
//		TripDayCountVO vo = service.findDayCount();
//		System.out.println(vo);
//	}
//	@Test
//	public void findDayCount2() {
//	
//		TripDayCountVO2 vo = service.findDayCount2();
//		System.out.println(vo);
//	}

}
