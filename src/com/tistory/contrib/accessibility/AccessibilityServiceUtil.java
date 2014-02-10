package com.tistory.contrib.accessibility;

import java.util.ArrayList;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

public class AccessibilityServiceUtil {

	public static ArrayList<String> getAllAccessibilityServices(Context context) {
		TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
		ArrayList<String> allAccessibilityServices = new ArrayList<String>();

		String settingValue = Settings.Secure.getString(
				context.getApplicationContext().getContentResolver(),
				Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

		if (settingValue != null) {
			colonSplitter.setString(settingValue);
			while (colonSplitter.hasNext()) {
				String accessabilityService = colonSplitter.next();
				allAccessibilityServices.add(accessabilityService);
			}
		}
		return allAccessibilityServices;
	}

	public static boolean isAccessibilityServiceOn(Context context, Class<?> clazz) {
		ArrayList<String> allAccessibilityServices = getAllAccessibilityServices(context);
		StringBuffer concat = new StringBuffer();
		concat.append(clazz.getPackage().getName());
		concat.append('/');
		concat.append(clazz.getName());

		return allAccessibilityServices.contains(concat.toString());
	}
}