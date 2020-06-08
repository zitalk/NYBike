package cn.tedu.nybike.entity;

import java.io.Serializable;

/**
 * 封装每日骑行总数量的实体类
 * JavaBean类的规范：
 * 1.所有属性用private修饰
 * 2.所有的属性使用包裹类类型
 * 3.提供的无参构造器（必须有）、带参构造器（可选）
 * 4.提供get/set方法
 * 5.提供hashCode和equals方法
 * 6.实现toString方法
 * 7.实现Serializable接口
 * @author 81895
 *
 */
public class TripDayCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 67274145658998925L;
	
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer count;
	
	public TripDayCount() {
	}

	public TripDayCount(Integer year, Integer month, Integer day, Integer count) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.count = count;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
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
		TripDayCount other = (TripDayCount) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
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

	@Override
	public String toString() {
		return "TripDayCount [year=" + year + ", month=" + month + ", day=" + day + ", count=" + count + "]";
	}
	
	
	
	
	
}
