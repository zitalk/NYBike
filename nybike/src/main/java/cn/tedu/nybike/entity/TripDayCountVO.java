package cn.tedu.nybike.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 封装前端页面所需数据的实体类
 * xData echarts的x轴所需数据
 * yData echarts的y轴所需数据
 * @author 81895
 *
 */
public class TripDayCountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5863969990335678519L;
	private List<String> xData;
	private List<Integer> yData;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xData == null) ? 0 : xData.hashCode());
		result = prime * result + ((yData == null) ? 0 : yData.hashCode());
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
		TripDayCountVO other = (TripDayCountVO) obj;
		if (xData == null) {
			if (other.xData != null)
				return false;
		} else if (!xData.equals(other.xData))
			return false;
		if (yData == null) {
			if (other.yData != null)
				return false;
		} else if (!yData.equals(other.yData))
			return false;
		return true;
	}

	public List<String> getxData() {
		return xData;
	}

	public void setxData(List<String> xData) {
		this.xData = xData;
	}

	public List<Integer> getyData() {
		return yData;
	}

	public void setyData(List<Integer> yData) {
		this.yData = yData;
	}

	@Override
	public String toString() {
		return "TripDAYCountVO [xData=" + xData + ", yData=" + yData + "]";
	}

	public TripDayCountVO(List<String> xData, List<Integer> yData) {
		super();
		this.xData = xData;
		this.yData = yData;
	}

	public TripDayCountVO() {
	}

}
