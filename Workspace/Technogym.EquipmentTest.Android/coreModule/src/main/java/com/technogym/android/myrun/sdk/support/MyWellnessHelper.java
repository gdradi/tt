package com.technogym.android.myrun.sdk.support;

import java.util.HashMap;
import java.util.Map;

public class MyWellnessHelper {

	public static final String METS_MIN_KEY = "mets_min";
	public static final String MOVES_KEY = "moves";
	public static final String KCAL_KEY = "k_calories";
	public static final String VO2_KEY = "vo_2";

	public static Map<String, Double> getDerivedProperties(final Double weight, final Double gradient,
			final double speed, final Double period) {

		Double vo2 = 0.0;
		Double nVelLow = 5.0;
		Double nVelHigh = 7.0;

		Double lowVO2 = nVelLow * 5.0 / 3.0 + nVelLow * gradient * 0.3 + 3.5;
		Double highVO2 = nVelHigh * 10.0 / 3.0 + nVelHigh * gradient * 0.15 + 3.5;
		Double MM = (highVO2 - lowVO2) / (nVelHigh - nVelLow);
		Double NN = lowVO2 - MM * nVelLow;

		if (speed <= nVelLow) {
			vo2 = speed * 5.0 / 3.0 + speed * gradient * 0.3 + 3.5;
		} else if (speed >= nVelHigh) {
			vo2 = speed * 10.0 / 3.0 + speed * gradient * 0.15 + 3.5;
		} else {
			vo2 = speed * MM + NN;
		}

		Double MetsMin = vo2 / 3.5;
		Double KCAL = MetsMin * weight * period / 3600.0;
		Double MOVE = 0.0;

		if (weight > 0.0) {
			MOVE = KCAL / (weight * 0.006);
		}

		final HashMap<String, Double> result = new HashMap<String, Double>();
		result.put(METS_MIN_KEY, MetsMin);
		result.put(KCAL_KEY, KCAL);
		result.put(MOVES_KEY, MOVE);
		result.put(VO2_KEY, vo2);

		return result;
	}

}
