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
// uuid replace
mj.uuid = (uuidv1)=>{return uuidv1.replace(new RegExp('-','gi'), '');}
// to file
mj.save2File = (blobData, nameFile, type)=>{
	//type = type || 'text/plain';
	type = type || 'application/octet-stream';
	let blob = new Blob([blobData], {
		type: type
	});
	var link = document.createElement('a');
	link.href = URL.createObjectURL(blob);
	link.download = nameFile;
	link.click();
}
