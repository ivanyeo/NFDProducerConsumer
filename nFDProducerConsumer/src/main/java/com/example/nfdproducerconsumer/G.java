package com.example.nfdproducerconsumer;

import android.util.Log;

public class G {
	private static String DEFAULT_TAG = "[NFDProducerConsumer]";
	
	public static void Log(String tag, String message, Object ... args) {
		Log.d(tag, String.format(message, args));
	}
	
	public static void Log(String tag, String message) {
		Log(tag, message, "");
	}
	
	public static void Log(String message) {
		Log(DEFAULT_TAG, message);
	}
}
