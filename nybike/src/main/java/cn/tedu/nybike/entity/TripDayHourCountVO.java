package cn.tedu.nybike.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TripDayHourCountVO implements Serializable {

	private static final long serialVersionUID = -5381290411450809429L;

	private List<String> xData;
	private Map<String, List<Integer>> yDataMap;
	public TripDayHourCountVO() {
	}
	public TripDayHourCountVO(List<String> xData, Map<String, List<Integer>> yDataMap) {
		this.xData = xData;
		this.yDataMap = yDataMap;
	}
	@Override
	public String toString() {
		return "TripDayHourCountVO [xData=" + xData + ", yDataMap=" + yDataMap + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xData == null) ? 0 : xData.hashCode());
		result = prime * result + ((yDataMap == null) ? 0 : yDataMap.hashCode());
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
		TripDayHourCountVO other = (TripDayHourCountVO) obj;
		if (xData == null) {
			if (other.xData != null)
				return false;
		} else if (!xData.equals(other.xData))
			return false;
		if (yDataMap == null) {
			if (other.yDataMap != null)
				return false;
		} else if (!yDataMap.equals(other.yDataMap))
			return false;
		return true;
	}
	public List<String> getxData() {
		return xData;
	}
	public void setxData(List<String> xData) {
		this.xData = xData;
	}
	public Map<String, List<Integer>> getyDataMap() {
		return yDataMap;
	}
	public void setyDataMap(Map<String, List<Integer>> yDataMap) {
		this.yDataMap = yDataMap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
