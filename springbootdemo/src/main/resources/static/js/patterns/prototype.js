function Base() {
	this.bar = 0;
}
Base.prototype = Object.assign(Object.create({}), {
	constructor: Base, 
	mem1: function() {
		
	}
})

function Drivered() {
}
Drivered.prototype = Object.assign(Object.create(Base.prototype), {
	constructor: Drivered,
	mem2: function() {
	}
})