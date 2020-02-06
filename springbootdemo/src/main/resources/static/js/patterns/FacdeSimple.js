// https://wanago.io/2019/12/09/javascript-design-patterns-facade-react-hooks/
class Bed {
	makeTheBed() {
		console.log('the bed is read');
	}
}

class AirFreshener {
	spray() {
		console.log('a nice small spread through the air');
	}
}

class TrashCan {
	takeOutTrash() {
		console.log('the trash is taken out');
	}
}

class Dishwasher {
	fill() {
		console.log('the dishwasher is filled');
	}
	wash() {
		console.log('the dishwasher is working');
		return new Promise((resolve)=>{
			resolve();
		});
	}
	empyt() {
		console.log('the dishwasher is empty');
	}
}

class HoseCleaningFacade {
	constructor(bed, trashCan, airFreshener, dishwasher) {
		this.bed = bed;
		this.trashCan = trashCan;
		this.airFreshener = airFreshener;
		this.dishwasher = dishwasher;
	}
	
	cleanTheHouse() {
		this.bed().makeTheBed();
		this.trashCan.takeOutTrash();
		this.airFreshener.spray();
		
		this.dishwasher.fill();
		this.dishwasher.wash().then(this.dishwasher.empty);
	}
}

const houseCleaning = new HoseCleaningFacade(
	new Bed(),
	new TrashCan(),
	new AirFreshener(),
	new Dishwasher()
);
houseCleaning.cleanTheHouse();

