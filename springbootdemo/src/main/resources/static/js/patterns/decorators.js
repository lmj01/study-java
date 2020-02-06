class Yogurt {
	constructor(flavor) {
		this.flavor = flavor;
	}
}

function frozen(object) {
	return Object.freeze(object);
}

const yogurt = frozen(new Yogurt('strawberry'));

function eatable(object) {
	object.eatable = true;
	return object;
}

const yogurt2 = eatable(
	frozen(
		new Yogurt('strawberry')
	)
);

const yogurt3 = frozen(
	eatable(
		new Yogurt('strawberry')
	)
)
