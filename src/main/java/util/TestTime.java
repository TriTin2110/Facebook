package util;

import java.util.Calendar;
import java.util.Date;

public class TestTime {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.set(2024, 4, 2);
		Date date = cal.getTime();
		long time = System.currentTimeMillis() - date.getTime();
		time /= 1000;
		System.out.println(time);
		int day = 0, hour = 0, min = 0, sec = 0;
		while (time > 0) {
			time /= 24 * 60 * 60;
			day += 1;
		}
		while (time >= (60 * 60)) {
			time /= 60 * 60;
			hour += 1;
		}
		while (time >= 60) {
			time /= 60;
			min += 1;
		}
		while (time > 0) {
			sec += 1;
			time--;
		}
		System.out.println(
				(day > 0) ? day + " ngày" : (hour > 0) ? hour + " giờ" : (min > 0) ? min + " phút" : sec + " giây");
	}
}
