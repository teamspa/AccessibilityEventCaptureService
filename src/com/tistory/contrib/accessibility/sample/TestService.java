package com.tistory.contrib.accessibility.sample;

import com.tistory.contrib.accessibility.AccessibilityEventCaptureService;

public class TestService extends AccessibilityEventCaptureService {

	public TestService() {
		super();
		this.setTag(TestService.class.getSimpleName());
	}

	@Override
	protected boolean isDebugMode() {
		return true;
	}
}
