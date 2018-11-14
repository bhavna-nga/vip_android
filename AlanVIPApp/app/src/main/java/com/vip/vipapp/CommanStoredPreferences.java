package com.vip.vipapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This is a utility class used throughout the app to access/ store pilot's preferences in the app 
 */
public class CommanStoredPreferences {

	/**
	 * This method is used to get the shared preferences instance to get and
	 * set data in Shared Preferences
	 * @param context - context for getting shared preferences instance
	 * @return
	 */
	private static SharedPreferences getPrefs(Context context) {
		return context.getSharedPreferences("private_file",Context.MODE_PRIVATE);
	}
	
	/**
	 * This method is used to check the key is exist in shared preferences or not
	 * @param context 
	 * @param key - key to check
	 * @return - boolean
	 */
	public static boolean checkForPreferences(Context context, String key)
	{
		return getPrefs(context).contains(key);
	}

	/**
	 * This method is used to get string preferences
	 * @param context - context for getting string preferences
	 * @param key - key for string preferences
	 * @return
	 */
	public static String getSentenceStringPref(Context context, String key) {
		return getPrefs(context).getString(key, null);
	}

	/**
	 * This method is used to get int preferences
	 * @param context - context for getting int preferences
	 * @param key - key for int preferences
	 * @return
	 */
	public static int getSentenceIntPref(Context context, String key) {
		return getPrefs(context).getInt(key, 0);
	}

	/**
	 * This method is used to get Long preferences
	 * @param context - context for getting Long preferences
	 * @param key - key for Long preferences
	 * @return
	 */
	public static Long getSentenceLongPref(Context context, String key) {
		return getPrefs(context).getLong(key, 0);
	}

	/**
	 * This method is used to get Boolean preferences
	 * @param context - context for getting Boolean preferences
	 * @param key - key for Boolean preferences
	 * @return
	 */
	public static boolean getSentenceBooleanPref(Context context, String key) {
		return getPrefs(context).getBoolean(key, false);
	}

	

	/**
	 * This method is used to set string preferences
	 * @param context - context for setting string preferences
	 * @param key - key for string preferences
	 * @param value - Value of String preferences
	 * @return
	 */
	public static void setSentenceStringPref(Context context, String key,
			String value) {
		// perform validation etc..
		getPrefs(context).edit().putString(key, value).commit();
	}

	/**
	 * This method is used to set Int preferences
	 * @param context - context for setting Int preferences
	 * @param key - key for Int preferences
	 * @param value - Value of Int preferences
	 * @return
	 */
	public static void setSentenceIntPref(Context context, String key, int value) {
		getPrefs(context).edit().putInt(key, value).commit();
	}

	/**
	 * This method is used to set Long preferences
	 * @param context - context for setting Long preferences
	 * @param key - key for Long preferences
	 * @param value - Value of Long preferences
	 * @return
	 */
	public static void setSentenceLongPref(Context context, String key,
			Long value) {
		getPrefs(context).edit().putLong(key, value).commit();
	}

	/**
	 * This method is used to set Boolean preferences
	 * @param context - context for setting Boolean preferences
	 * @param key - key of Boolean preferences
	 * @param value - Value of Boolean preferences
	 * @return
	 */
	public static void setSentenceBooleanPref(Context context, String key,
			boolean value) {
		// perform validation etc..
		getPrefs(context).edit().putBoolean(key, value).commit();
	}
	

	/**
	 * This method is used for clear specific preferences
	 * @param context - context for clearing preferences
	 * @param key - key of clearing preferences
	 */
	public static void clearSentencePrefs(Context context, String key) {
		getPrefs(context).edit().remove(key).commit();
	}

	/**
	 * This method is used for clear all preferences of App
	 * @param context - context for clearing preferences
	 */
	public static void clearAllPrefsData(Context context) {
		getPrefs(context).edit().clear().commit();
	}

}
