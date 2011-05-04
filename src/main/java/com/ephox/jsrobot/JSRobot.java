/* Copyright 2010 Ephox Corporation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.ephox.jsrobot;

import netscape.javascript.JSObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Permission;

public class JSRobot extends Applet {

	private static final long serialVersionUID = 1L;

	private boolean started = false;

	private Robot robot;

	private File _screenshotDir;


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
		System.err.println("Start");
		// Started can be called multiple times if the applet is hidden and shown again so we guard against that.
		if (!started) {
			started = true;
			final Timer t = new Timer(100, new ActionListener() {
				private int attempts = 0;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isShowing() && attempts < 50) {
						attempts++;
						checkNotMinimized();
						return;
					}
					((Timer) e.getSource()).stop();
//					clickToFocusBrowser();
					new Thread(new Runnable() {
						public void run() {
							waitForIdle();
							performCallback();
						}
					}).start();
				}
			});
			t.start();
		}
	}

	protected void checkNotMinimized() {
		Container parent = getParent();
		while (!(parent instanceof Frame) && parent != null) {
			parent = parent.getParent();
		}
		if (parent != null) {
			Frame window = (Frame) parent;
			if (window.getExtendedState() == JFrame.ICONIFIED) {
				window.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		} else {
			System.err.println("Failed to get a containing frame.");
		}
	}

	public String setClipboard(String content) {
		System.err.println("Set clipboard");
		try {
			StringSelection transferable = new StringSelection(content);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private void waitForIdle() {
		System.err.println("Wait for idle");
		try {
			getRobot().waitForIdle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clickToFocusBrowser() {
		System.err.println("Click to focus browser.");
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
		System.err.println("Got window");
		System.err.println(js.eval("window.robot.callback()"));
		System.err.println("Finished performing callback");
	}

	public String typeKey(final int keycode, final boolean shiftKey) {
		return doTypeKey(keycode, shiftKey ? KeyEvent.VK_SHIFT : -1);
	}

	public String typeAsShortcut(int keycode) {
		return doTypeKey(keycode, getShortcutKey());
	}

	public String typeSymbolsAboveNumberKeys() {
		for (int i = 0; i < 10; i++) {
			doTypeKey(KeyEvent.VK_0 + i,  KeyEvent.VK_SHIFT);
		}
		return null;
	}

	private String doTypeKey(final int keycode, final int modifierKey) {
		try {
			System.err.println("Typing key:" +  keycode +" with modifier " + modifierKey);
			Robot robot = getRobot();
			if (modifierKey >= 0) {
				robot.keyPress(modifierKey);
			}
			robot.keyPress(keycode);
			robot.keyRelease(keycode);
			if (modifierKey >= 0) {
				robot.keyRelease(modifierKey);
			}
			System.err.println("Typed key");
			waitForIdle();
			System.err.println("Idle");
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return t.getMessage();
		}
	}

	private int getShortcutKey() {
		switch (getToolkit().getMenuShortcutKeyMask()) {
			case KeyEvent.CTRL_MASK:
				return KeyEvent.VK_CONTROL;
			case KeyEvent.META_MASK:
				return KeyEvent.VK_META;
			case KeyEvent.ALT_MASK:
				return KeyEvent.VK_ALT;
			case KeyEvent.ALT_GRAPH_MASK:
				return KeyEvent.VK_ALT_GRAPH;
			default:
				throw new IllegalStateException("Menu shortcut key is unrecognised: " + getToolkit().getMenuShortcutKeyMask());
		}
	}

	public void setScreenShotDirectory(String dir) {
		_screenshotDir = new File(dir);
	}

	public String captureScreenShot() {
		try {
			Robot robot = getRobot();
			BufferedImage screenshot = robot.createScreenCapture(getGraphicsConfiguration().getBounds());
			File outputFile = File.createTempFile("TestScreenshot", ".jpg", _screenshotDir);
			ImageIO.write(screenshot, "jpg", outputFile);
			return outputFile.getAbsolutePath();
		} catch (Throwable t) {
			t.printStackTrace();
			return t.getMessage();
		}
	}
}
