package com.ephox.jsrobot;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import netscape.javascript.*;

public class JSRobot extends Applet {

	private static final long serialVersionUID = 1L;

	public void init() {
		System.setSecurityManager(null);
		performCallback();
	}
	
	private void performCallback() {
		JSObject js = JSObject.getWindow(this);
		js.call("robot.callback", new Object[0]);
	}
	
	public boolean typeKey(int keycode, boolean shiftKey) {
		try {
			Robot robot = new Robot();
			robot.setAutoWaitForIdle(true);
			if (shiftKey) {
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			robot.keyPress(keycode);
			robot.keyRelease(keycode);
			if (shiftKey) {
				robot.keyRelease(KeyEvent.VK_SHIFT);
			}
			return true;
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}
}
