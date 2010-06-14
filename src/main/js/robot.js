window.robot = {
		
	onload: function(userCallback) {
		if (this.ready) {
			userCallback();
		} else {
			this.userCallback = userCallback;
		}
	},
	
	init: function() {
		document.write('<applet cache_archive="../JSRobot.jar" cache_archive_ex="../JSRobot.jar" cache_option="Plugin" archive="../JSRobot.jar" code="com.ephox.jsrobot.JSRobot" id="robotApplet" width="10" height="10" mayscript="true"><param name="mayscript" value="true" /></applet>');
		this.appletInstance = document.getElementById('robotApplet');
	},
	
	callback: function() {
		this.ready = true;
		if (this.userCallback) {
			this.userCallback();
		}
	},
	
	type: function(key, shiftKey, callback) {
		shiftKey = !!shiftKey;
		this.getApplet().typeKey(key, shiftKey);
		setTimeout(callback, 10);
	},
	
	getApplet: function() {
		return document.getElementById('robotApplet');
	}
};

window.robot.init();
