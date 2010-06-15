window.robot = {
		
	onload: function(userCallback) {
		if (this.ready) {
			userCallback();
		} else {
			this.userCallback = userCallback;
		}
	},
	
	init: function() {
		var jarUrl = "../JSRobot.jar";
		document.write('<applet archive="' + jarUrl + '" code="com.ephox.jsrobot.JSRobot" id="robotApplet" width="10" height="10" mayscript="true"><param name="mayscript" value="true" /></applet>');
		this.appletInstance = document.getElementById('robotApplet');
	},
	
	callback: function() {
		this.ready = true;
		if (this.userCallback) {
			this.userCallback();
		}
	},
	
	type: function(key, shiftKey, callback) {
		this.getApplet().typeKey(this.getKeycode(key), shiftKey);
		setTimeout(callback, 100);
	},
	
	forwardDelete: function(callback) {
		this.type(0x7F, false, callback);
	},
	
	getKeycode: function(key) {
		if (key.toUpperCase && key.charCodeAt) {
			if (/^[a-zA-Z\n\b\t]$/.test(key)) {
				return key.toUpperCase().charCodeAt(0);
			} else {
				throw { message: 'Invalid character to type. Must be a-z or A-Z, otherwise use the key code directly.' };
			}
		}
		return key;
	},
	
	getApplet: function() {
		return this.appletInstance;
	}
};

window.robot.init();
