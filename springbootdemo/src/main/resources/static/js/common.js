// 全局小工具

window.mjpost = function(url, args, type) {
	return new Promise((resolve, reject)=>{
		let xhr = new XMLHttpRequest();
		xhr.open('POST', url, true);
		if (type == 'json') {
			xhr.responseType = type;
			xhr.setRequestHeader('Content-Type','application/json');
		} else if (type == 'arraybuffer') {
			xhr.responseType = type;
			xhr.setRequestHeader('Content-Type','application/json');
		}				
		xhr.onload = function() {
			if (this.status == 200) {
				resolve(this.response);
			} else {
				reject(this.response);
			}
		}
		if (args instanceof Object) {
			if (type == 'formdata') {
				console.log(args);
				xhr.send(args);
			} else xhr.send(JSON.stringify(args));
		} else xhr.send();
	});
}
window.mjlog = console.log;
window.mjok = function() {
	let len = arguments.length;
	if (len == 1) {
		console.log('success---', arguments[0]);	
	} else if (len == 2) {
		console.log('success---', arguments[0], arguments[1]);
	} else if (len == 3) {
		console.log('success---', arguments[0], arguments[1], arguments[2]);
	} else if (len == 4) {
		console.log('success---', arguments[0], arguments[1], arguments[2], arguments[3]);
	}	
}
window.mjfail = function() {
	let len = arguments.length;
	if (len == 1) {
		console.log('failure---', arguments[0]);	
	} else if (len == 2) {
		console.log('failure---', arguments[0], arguments[1]);
	} else if (len == 3) {
		console.log('failure---', arguments[0], arguments[1], arguments[2]);
	} else if (len == 4) {
		console.log('failure---', arguments[0], arguments[1], arguments[2], arguments[3]);
	}	
}
