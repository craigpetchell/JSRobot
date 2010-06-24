JSRobot
=======
A testing utility for web-apps that can generate real keystrokes rather than simply simulating the JavaScript event firing. This allows the keystroke to trigger the built-in browser behaviour which isn't otherwise possible.

Usage
-----
The two required files are robot.js and JSRobot.jar, they must be on the same server as the HTML page.

Include the robot.js file at the end of the body tag.  It will create then provide a window.robot object which JavaScript tests can interact with. Note that the robot loads asynchronously - you can use the onload method to be notified when it is ready for use.

API
---
* onload(function) - Calls the function parameter when the robot is ready to use or immediately if the robot is already set up.
* type(key, shiftKey, callback) - Types the specified key, optionally with the shift key down. Since JavaScript must run to completion, the keystroke must happen asynchronously.  The callback function is called after the keystroke has occurred.
* forwardDelete(callback) - a utility function to simplifying typing the forward delete key.
* captureScreenShot - Take a screen shot (of the whole screen) and save it to the local client machine (not the server!). Returns the full path to the screenshot file on disk.
* setScreenShotDirectory - Set the local directory on the client that screenshots are saved to. Each screen shot is automatically given a unique name.

Specifying Keys
---------------
JSRobot doesn't type characters, it actually simulates pressing specific keys on the keyboard. The [Java KeyEvent class](http://java.sun.com/j2se/1.5.0/docs/api/java/awt/event/KeyEvent.html) provides a good description of how this works.

JSRobot can also accept the characters a-z (or A-Z) for the standard alphabetic keys, plus \t for tab, \n for enter and \b for backspace. For other keys, it can accept the [Java numeric code for the key](http://java.sun.com/j2se/1.5.0/docs/api/constant-values.html#java.awt.event.KeyEvent.VK_5) directly.

API Changes
-----------
You should expect the API to change and improve somewhat before it stabilises which is likely to break backwards compatibility.