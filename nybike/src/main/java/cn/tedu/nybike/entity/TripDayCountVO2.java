package cn.tedu.nybike.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 封装前端页面所需数据的实体类
 * xData echarts的x轴所需数据
 * yData1 echarts的y轴所需数据1
 * yData2 echarts的y轴所需数据2
 * @author 81895
 *
 */
public class TripDayCountVO2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5863969990335678519L;
	private Set<String> xData;
	private List<Integer> yData1;
	private List<Integer> yData2;
	public TripDayCountVO2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TripDayCountVO2(Set<String> xData, List<Integer> yData1, List<Integer> yData2) {
		super();
		this.xData = xData;
		this.yData1 = yData1;
		this.yData2 = yData2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xData == null) ? 0 : xData.hashCode());
		result = prime * result + ((yData1 == null) ? 0 : yData1.hashCode());
		result = prime * result + ((yData2 == null) ? 0 : yData2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripDayCountVO2 other = (TripDayCountVO2) obj;
		if (xData == null) {
			if (other.xData != null)
				return false;
		} else if (!xData.equals(other.xData))
			return false;
		if (yData1 == null) {
			if (other.yData1 != null)
				return false;
		} else if (!yData1.equals(other.yData1))
			return false;
		if (yData2 == null) {
			if (other.yData2 != null)
				return false;
		} else if (!yData2.equals(other.yData2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TripDayCountVO2 [xData=" + xData + ", yData1=" + yData1 + ", yData2=" + yData2 + "]";
	}
	public Set<String> getxData() {
		return xData;
	}
	public void setxData(Set<String> xData) {
		this.xData = xData;
	}
	public List<Integer> getyData1() {
		return yData1;
	}
	public void setyData1(List<Integer> yData1) {
		this.yData1 = yData1;
	}
	public List<Integer> getyData2() {
		return yData2;
	}
	public void setyData2(List<Integer> yData2) {
		this.yData2 = yData2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	

}
