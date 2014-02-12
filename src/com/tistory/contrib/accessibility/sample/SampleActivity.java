package com.tistory.contrib.accessibility.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tistory.contrib.accessibility.AccessibilityServiceUtil;
import com.tistory.contrib.accessibility.R;

public class SampleActivity extends Activity
		implements OnClickListener {
	TextView test_service_status;
	Button setting;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		test_service_status = (TextView) findViewById(R.id.test_service_status);
		setting = (Button) findViewById(R.id.setting);
		setting.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		boolean isSet = AccessibilityServiceUtil.isAccessibilityServiceOn(
				getApplicationContext(),
				"com.tistory.contrib.accessibility",
				"com.tistory.contrib.accessibility.sample.TestService");

		if (isSet) {
			test_service_status.setText(R.string.test_service_on);
		} else {
			test_service_status.setText(R.string.test_service_off);
		}

		super.onResume();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting:
			Intent accessibilityServiceIntent 
				= new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
			startActivity(accessibilityServiceIntent);
			break;
		}
	}
}