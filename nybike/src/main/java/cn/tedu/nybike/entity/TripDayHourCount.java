package cn.tedu.nybike.entity;

import java.io.Serializable;

public class TripDayHourCount implements Serializable{
	private static final long serialVersionUID = -5204835970765363924L;
	
	private Integer tripYear;
	private Integer tripMonth;
	private Integer tripDay;
	private Integer tripDayOfWeek;
	private Integer tripHour;
	private Integer gender;
	private Integer tripCount;
	
	public TripDayHourCount() {
	}
	
	public TripDayHourCount(Integer tripYear, Integer tripMonth, Integer tripDay, Integer tripDayOfWeek,
			Integer tripHour, Integer gender, Integer tripCount) {
		this.tripYear = tripYear;
		this.tripMonth = tripMonth;
		this.tripDay = tripDay;
		this.tripDayOfWeek = tripDayOfWeek;
		this.tripHour = tripHour;
		this.gender = gender;
		this.tripCount = tripCount;
	}
	@Override
	public String toString() {
		return "TripDayHourCount [tripYear=" + tripYear + ", tripMonth=" + tripMonth + ", tripDay=" + tripDay
				+ ", tripDayOfWeek=" + tripDayOfWeek + ", tripHour=" + tripHour + ", gender=" + gender + ", tripCount="
				+ tripCount + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((tripCount == null) ? 0 : tripCount.hashCode());
		result = prime * result + ((tripDay == null) ? 0 : tripDay.hashCode());
		result = prime * result + ((tripDayOfWeek == null) ? 0 : tripDayOfWeek.hashCode());
		result = prime * result + ((tripHour == null) ? 0 : tripHour.hashCode());
		result = prime * result + ((tripMonth == null) ? 0 : tripMonth.hashCode());
		result = prime * result + ((tripYear == null) ? 0 : tripYear.hashCode());
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
		TripDayHourCount other = (TripDayHourCount) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (tripCount == null) {
			if (other.tripCount != null)
				return false;
		} else if (!tripCount.equals(other.tripCount))
			return false;
		if (tripDay == null) {
			if (other.tripDay != null)
				return false;
		} else if (!tripDay.equals(other.tripDay))
			return false;
		if (tripDayOfWeek == null) {
			if (other.tripDayOfWeek != null)
				return false;
		} else if (!tripDayOfWeek.equals(other.tripDayOfWeek))
			return false;
		if (tripHour == null) {
			if (other.tripHour != null)
				return false;
		} else if (!tripHour.equals(other.tripHour))
			return false;
		if (tripMonth == null) {
			if (other.tripMonth != null)
				return false;
		} else if (!tripMonth.equals(other.tripMonth))
			return false;
		if (tripYear == null) {
			if (other.tripYear != null)
				return false;
		} else if (!tripYear.equals(other.tripYear))
			return false;
		return true;
	}
	public Integer getTripYear() {
		return tripYear;
	}
	public void setTripYear(Integer tripYear) {
		this.tripYear = tripYear;
	}
	public Integer getTripMonth() {
		return tripMonth;
	}
	public void setTripMonth(Integer tripMonth) {
		this.tripMonth = tripMonth;
	}
	public Integer getTripDay() {
		return tripDay;
	}
	public void setTripDay(Integer tripDay) {
		this.tripDay = tripDay;
	}
	public Integer getTripDayOfWeek() {
		return tripDayOfWeek;
	}
	public void setTripDayOfWeek(Integer tripDayOfWeek) {
		this.tripDayOfWeek = tripDayOfWeek;
	}
	public Integer getTripHour() {
		return tripHour;
	}
	public void setTripHour(Integer tripHour) {
		this.tripHour = tripHour;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getTripCount() {
		return tripCount;
	}
	public void setTripCount(Integer tripCount) {
		this.tripCount = tripCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
