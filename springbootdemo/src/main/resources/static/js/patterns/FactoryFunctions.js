const TYPE = {
	CODING:'coding',
	MUSIC:'music'
};
class Teacher {
	constructor(properties) {
		this.name = properties.name;
	}
}
class CodingTeacher extends Teacher {
	constructor(properties) {
		super(properties);
		this.type = TYPE.CODING;
	}
}
class MusicTeacher extends Teacher {
	constructor(properites) {
		super(properties);
		this.type = TYPE.MUSIC;
	}
}


const createPerson = ({
	firstName,
	lastName
}) => ({
	firstName,
	lastName,
	speak:()=>console.log(`My name is ${firstName} ${lastName}`);
});

const person = createPerson({
	firstName: 'Jone',
	lastName: 'Smith'
});
console.log(person.speak());