function GridMesh(unit, size, divisions, color1, color2) {
	let _step = size / divisions,
		_half = size / 2;
	color1 = new THREE.Color(color1 !== undefined ? color1 : 0x111111);
	color2 = new THREE.Color(color2 !== undefined ? color2 : 0x888888);
	let _vertices = [], _colors = [];
	for (let i=0,j=0,k=-_half;i<=divisions;i++,k+=_step) {
		if (unit == 100) {
			if (k % 10 !== 0) continue;				
		} else if (unit == 10) {
			if (k % 2 !== 0) continue;
		}
		
		_vertices.push(-_half, 0, k, _half, 0, k);
		_vertices.push(k, 0, -_half, k, 0, _half);
		
		let color = unit == 1 ? (i%10==0?color1:color2) : 
			(k%unit==0?color1:color2);
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
	}
	let geo = new THREE.BufferGeometry();
	geo.addAttribute('position', new THREE.Float32BufferAttribute(_vertices, 3));
	geo.addAttribute('color', new THREE.Float32BufferAttribute(_colors, 3));
	let mat = new THREE.LineBasicMaterial({
		vertexColors: THREE.VertexColors,
		depthTest: false,
		transparent: true,
		opacity: 0.4,
		linewidth: 0.5
	});
	THREE.LineSegments.call(this, geo, mat);
}
//GridMesh.prototype = Object.assign(Object.create(THREE.LineSegments.prototype), {
//	constructor: GridMesh,
//});
GridMesh.prototype = Object.create(THREE.LineSegments.prototype);
GridMesh.prototype.constructor = GridMesh;


function GridGeometry(unit, size, divisions, color1, color2) {
	let _step = size / divisions, _half = size / 2;
	color1 = new THREE.Color(color1 !== undefined ? color1 : 0x111111);
	color2 = new THREE.Color(color2 !== undefined ? color2 : 0x888888);
	let _vertices = [], _colors = [];
	for (let i=0,j=0,k=-_half;i<=divisions;i++,k+=_step) {
		if (unit == 100) {
			if (k % 10 !== 0) continue;				
		} else if (unit == 10) {
			if (k % 2 !== 0) continue;
		}
		
		_vertices.push(-_half, 0, k, _half, 0, k);
		_vertices.push(k, 0, -_half, k, 0, _half);
		
		let color = unit == 1 ? (i%10==0?color1:color2) : 
			(k%unit==0?color1:color2);
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
		color.toArray(_colors, j);j+=3;
	}
	let geo = new THREE.BufferGeometry();
	geo.addAttribute('position', new THREE.Float32BufferAttribute(_vertices, 3));
	geo.addAttribute('color', new THREE.Float32BufferAttribute(_colors, 3));
	return geo;
}

function GridMaterial() {
	return new THREE.LineBasicMaterial({
		vertexColors: THREE.VertexColors,
		depthTest: false,
		transparent: true,
		opacity: 0.4,
		linewidth: 0.5
	});
}