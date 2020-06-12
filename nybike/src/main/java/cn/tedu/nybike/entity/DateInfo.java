package cn.tedu.nybike.entity;

import java.io.Serializable;
/**
 * 封装所有日期数据对象的实体类
 * @author 81895
 *
 */
public class DateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer dayOfWeek;
	private String  dayOfWeekStr;
	private static final String[] WEEK_STR= {"周日","周一","周二","周三","周四","周五","周六"};
	
	
	private String createDayOfWeekStr(int dayOfWeek) {
		//规避数组下标越界异常
		if(dayOfWeek<1 || dayOfWeek>WEEK_STR.length) {
			return "未知位置异常";
		}
		return WEEK_STR[dayOfWeek-1];
	}
	
	public DateInfo() {
	}
	public DateInfo(Integer year, Integer month, Integer day, Integer dayOfWeek, String dayOfWeekStr) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayOfWeek = dayOfWeek;
		this.dayOfWeekStr = dayOfWeekStr;
	}
	@Override
	public String toString() {
		return "DateInfo [year=" + year + ", month=" + month + ", day=" + day + ", dayOfWeek=" + dayOfWeek
				+ ", dayOfWeekStr=" + dayOfWeekStr + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		result = prime * result + ((dayOfWeekStr == null) ? 0 : dayOfWeekStr.hashCode());
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		DateInfo other = (DateInfo) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (dayOfWeek == null) {
			if (other.dayOfWeek != null)
				return false;
		} else if (!dayOfWeek.equals(other.dayOfWeek))
			return false;
		if (dayOfWeekStr == null) {
			if (other.dayOfWeekStr != null)
				return false;
		} else if (!dayOfWeekStr.equals(other.dayOfWeekStr))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		//根据dayOfWeek生成dayOfWeekStr
		this.dayOfWeekStr = createDayOfWeekStr(dayOfWeek);
		
	}
	public String getDayOfWeekStr() {
		return dayOfWeekStr;
	}
	public void setDayOfWeekStr(String dayOfWeekStr) {
		this.dayOfWeekStr = dayOfWeekStr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
