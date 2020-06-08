package cn.tedu.nybike.service;

import org.junit.Test;

import cn.tedu.nybike.entity.TripDayCountVO2;

public class TripServiceTest {
	TripService service = new TripService();
	
//	@Test
//	public void findDayCount() {
//	
//		TripDayCountVO vo = service.findDayCount();
//		System.out.println(vo);
//	}
	@Test
	public void findDayCount2() {
	
		TripDayCountVO2 vo = service.findDayCount2();
		System.out.println(vo);
	}

}
