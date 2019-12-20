let mj = window.mj || {};

// decrement date
mj.decDate = (a,b)=>{
	let x = new Date(a), y = new Date(b);
	if (x <= y) return 1;
	return -1;
}
// increase date
mj.incDate = (a,b)=>{
	let x = new Date(a), y = new Date(b);
	if (x <= y) return -1;
	return 1;
}