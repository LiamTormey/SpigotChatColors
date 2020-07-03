package llt.spigot.plugin;

import java.util.Random;

public class CommonMethods {

	/**
	 * 
	 * @param min Inclusive
	 * @param 
	 * @return Random Number
	 */
	public static int getRandomNumber(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
}
