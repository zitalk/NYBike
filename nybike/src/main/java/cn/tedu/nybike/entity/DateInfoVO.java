package cn.tedu.nybike.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DateInfoVO implements Serializable{

	private static final long serialVersionUID = -7451469518023582185L;

	//key:2019年  value:0401(周一),0402(周二)...
	private Map<String, List<String>> dateInfoMap;

	public DateInfoVO() {
	}

	public DateInfoVO(Map<String, List<String>> dateInfoMap) {
		this.dateInfoMap = dateInfoMap;
	}

	@Override
	public String toString() {
		return "DateInfoVO [dateInfoMap=" + dateInfoMap + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateInfoMap == null) ? 0 : dateInfoMap.hashCode());
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
		DateInfoVO other = (DateInfoVO) obj;
		if (dateInfoMap == null) {
			if (other.dateInfoMap != null)
				return false;
		} else if (!dateInfoMap.equals(other.dateInfoMap))
			return false;
		return true;
	}

	public Map<String, List<String>> getDateInfoMap() {
		return dateInfoMap;
	}

	public void setDateInfoMap(Map<String, List<String>> dateInfoMap) {
		this.dateInfoMap = dateInfoMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
	
	
}
