package com.tistory.contrib.accessibility;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public abstract class AccessibilityEventCaptureService extends AccessibilityService {

	public static final String ACTION_CAPTURE_NOTIFICATION = "action_capture_notification";
	public static final String EXTRA_NOTIFICATION_TYPE = "extra_notification_type";
	public static final String EXTRA_PACKAGE_NAME = "extra_package_name";
	public static final String EXTRA_MESSAGE = "extra_message";

	public static final int EXTRA_TYPE_NOTIFICATION = 0x19;
	public static final int EXTRA_TYPE_OTHERS = EXTRA_TYPE_NOTIFICATION + 1;

	public static String TAG = AccessibilityEventCaptureService.class.getSimpleName();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (isDebugMode()) {
			Log.d(TAG, "onAccessibilityEvent");
		}

		if (hasMessage(event) == false) {
			return;
		}

		final int eventType = event.getEventType();
		final String sourcePackageName = (String) event.getPackageName();
		final List<CharSequence> messages = event.getText();
		final CharSequence message = messages.get(0);

		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {

			Intent sendingIntent = new Intent(ACTION_CAPTURE_NOTIFICATION);

			Parcelable parcelable = event.getParcelableData();
			if (parcelable instanceof Notification) {
				sendingIntent.putExtra(EXTRA_NOTIFICATION_TYPE, EXTRA_TYPE_NOTIFICATION);
			} else {
				sendingIntent.putExtra(EXTRA_NOTIFICATION_TYPE, EXTRA_TYPE_OTHERS);
			}
			sendingIntent.putExtra(EXTRA_PACKAGE_NAME, sourcePackageName);
			sendingIntent.putExtra(EXTRA_MESSAGE, message);
			getApplicationContext().sendBroadcast(sendingIntent);

			if (isDebugMode()) {
				Log.d(TAG, sourcePackageName + " : " + message);
			}
		}
	}

	protected abstract boolean isDebugMode();

	protected void setTag(String tag) {
		AccessibilityEventCaptureService.TAG = tag;
	}

	@Override
	protected void onServiceConnected() {
		if (isDebugMode()) {
			Log.d(TAG, "onServiceConnected");
			android.os.Debug.waitForDebugger();
		}
		super.onServiceConnected();
	}

	@Override
	public void onInterrupt() {
	}

	private boolean hasMessage(AccessibilityEvent event) {
		return event != null && (event.getText().size() > 0);
	}
}
