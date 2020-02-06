class Singleton {
	static instance;
	constructor() {
		// logic here
	}
	static getInstance() {
		if (Singleton.instance) {
			return Singleton.instance;
		}
		Singleton.instance = new Singleton();
		return Singleton.instance;
	}
}
let singleton = Singleton.getInstance();
