package com.ephox.keytyper;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MagicKeyTyper extends Applet {

	public void init() {
		System.setSecurityManager(null);
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
