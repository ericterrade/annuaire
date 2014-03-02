package fr.treeptik.annuaire.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static Date parse(String date) {

		Date result = null;

		try {

			result = dateFormat.parse(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String format(Date date) {

		return dateFormat.format(date);
	}

	public static java.sql.Date toSqlDate(Date date) {

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;

	}

	public static Date toUtilDate(java.sql.Date date) {

		Date utilDate = new Date(date.getTime());
		return utilDate;

	}

}
