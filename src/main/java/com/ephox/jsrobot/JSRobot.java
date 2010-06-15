/* Copyright 2010 Ephox Corporation.  All rights reserved. */

package com.ephox.jsrobot;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.security.*;
import java.util.*;

import netscape.javascript.*;

public class JSRobot extends Applet {

	private static final long serialVersionUID = 1L;
	
	private boolean started = false;

	private Robot robot;
	
	public void init() {
		System.err.println("Init");
		System.setSecurityManager(new SecurityManager() {
			@Override
			public void checkPermission(Permission perm) {
			}
		});
		this.setFocusable(false);
	}
	
	public void start() {
		// Started can be called multiple times if the applet is hidden and shown again so we guard against that.
		if (!started) {
			started = true;
			final Timer t = new Timer();
			t.schedule(new TimerTask() {
				private int attempts = 0;
				@Override
				public void run() {
					if (!isShowing() && attempts < 50) {
						attempts++;
						t.schedule(this, 100);
						return;
					}
					clickToFocusBrowser();
					waitForIdle();
					performCallback();
				}
			}, 100);
		}
	}
	
	private void waitForIdle() {
		try {
			getRobot().waitForIdle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clickToFocusBrowser() {
		try {
			Robot robot = getRobot();
			Point p = getLocationOnScreen();
			System.err.println(p);
			robot.mouseMove(p.x, p.y - 5);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized Robot getRobot() throws AWTException {
		if (robot == null) {
			robot = new Robot();
		}
		return robot;
	}
	
	private void performCallback() {
		System.err.println("Perform callback");
		JSObject js = JSObject.getWindow(this);
		js.eval("window.robot.callback()");
	}
	
	public String typeKey(final int keycode, final boolean shiftKey) {
		try {
			doTypeKey(keycode, shiftKey);
			waitForIdle();
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return t.getMessage();
		}
	}

	private void doTypeKey(int keycode, boolean shiftKey) throws AWTException {
		Robot robot = getRobot();
		if (shiftKey) {
			robot.keyPress(KeyEvent.VK_SHIFT);
		}
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
		if (shiftKey) {
			robot.keyRelease(KeyEvent.VK_SHIFT);
		}
	}
}
