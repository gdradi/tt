package com.technogym.mycycling.support;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalsFormatHelper {

	public static String format(final Double value) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');

		DecimalFormat frmt = new DecimalFormat("0.#", otherSymbols);

		return frmt.format(value);
	}

	public static String format(final Integer value) {
		return format(Double.valueOf(value));
	}

}
