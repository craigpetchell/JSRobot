window.robot = {
		
	init: function(callback) {
		this.callback = callback;
		setTimeout(function() {
			this.appletInstance = document.createElement('applet');
			this.appletInstance.width = 1;
			this.appletInstance.height = 1;
			this.appletInstance.archive = '../JSRobot.jar';
			this.appletInstance.code = 'com.ephox.jsrobot.JSRobot';
			this.appletInstance.id = 'robotApplet';
			
			document.body.appendChild(this.appletInstance);
		}, 1);
	},
	
	type: function(key, shiftKey) {
		this.getApplet().typeKey(key, shiftKey);
	},
	
	getApplet: function() {
		return this.appletInstance;
	},
};