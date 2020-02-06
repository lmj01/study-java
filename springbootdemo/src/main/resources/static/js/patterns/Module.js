const helloModule = (function(){
	function hello() {
		console.log('hello world!');
	}
	return {
		hello
	}
})();

helloModule.hello();