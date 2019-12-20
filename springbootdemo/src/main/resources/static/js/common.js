// 全局小工具

window.mjpost = function(url, args, type) {
	return new Promise((resolve, reject)=>{
		let xhr = new XMLHttpRequest();
		xhr.open('POST', url, true);
		if (type == 'json') {
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

