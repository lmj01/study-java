// https://wanago.io/2020/01/20/javascript-design-patterns-observer-typescript/

class Observer {
	update(message) {
		console.log(message);
	}
}

class Subject {
	//observers = []; // for the sample type
	observers = new Set(); // for any type object
	
	subscribe(observer) {
		this.observers.add(observer);
	}
	
	unsubscribe(observer) {
		this.observers.delete(observer);
	}
	
	notify(message) {
		this.observers.forEach((observer)=>{
			if (typeof observer == 'function') {
				
			} else {
				observer.update(message);	
			}			
		})
	}
}

const subject = new Subject();
// for observer is object, and has method update
subject.subscribe(new Observer());
subject.subscribe(new Observer());

subject.notify('Hello World!');

// for observer is function
const subject2 = new Subject();
subject2.subscribe((message)=>{
	console.log(message);
})

subject2.notify('Hello World!22');

// any other example
class PhoneInputSubject {
	observers = new Set();
	
	subscribe(observer) {
		this.observers.add(observer);
	}
	
	unsubscribe(observer) {
		this.observers.delete(observer);
	}
	
	shouldObserverBeNotified(message) {
		return /[+]*[0-9]+[+. -]*$/.test(message);
	}
	
	notify(message) {
		if (this.shouldObserverBeNotified(message)) {
			this.observers.forEach((observer)=>{
				observer(message);
			})
		}
	}
}

const subject3 = new PhoneInputSubject();

subject3.subscribe((phoneNumber)=>{
	console.log(`A new proper phone number: ${phoneNumber}`);
});

window.addEventListener('load', ()=>{
	const phoneInput = document.querySelector('#phoneInput');
	phoneInput.addEventListener('change', (event)=>{
		subject3.notify(event.target.value);
	})
});

