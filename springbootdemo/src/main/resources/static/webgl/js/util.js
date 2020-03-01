let mj = window.mj || {};

/*
 * https://stackoverflow.com/questions/2866350/move-camera-to-fit-3d-scene
 * http://www.realtimerendering.com/udacity/transforms.html
 */
mj.fitCamera = function(app, mesh) {	
	mesh.geometry.computeBoundingBox();
	let bbox = mesh.geometry.boundingBox;
	let target = new THREE.Vector3(), size = new THREE.Vector3();
	bbox.getCenter(target);	
	console.log('target position---', target);
	
	bbox.getSize(size);
	let maxSide = Math.max(size.x, Math.max(size.y, size.x)),
		targetWidth, targetHeight;
	console.log('target size---', size, maxSide);
	
	let camera = app.camera, renderer = app.renderer;
	
	var viewport = new THREE.Vector4();
	renderer.getCurrentViewport(viewport);
	console.log('viewport---', viewport);
	
	targetWidth = maxSide;
	targetHeight = targetWidth / camera.aspect;
	console.log('target width&height---', targetWidth, targetHeight);
	
	
	let distance = target.clone().distanceTo(camera.position);
	console.log('camera to target distnace---', distance, camera.position);
	
	var vFOV = THREE.Math.degToRad(camera.fov);
	var height = 2 * Math.tan(vFOV / 2) * distance;
	var width = height * camera.aspect;
	console.log('fit camera-------',width, height);
	
	let maxLength = camera.near / Math.abs(distance - maxSide)  * (viewport.w / 2);
	console.log('current info---', camera.near, maxLength)
	let scale1 = (maxLength * 0.5)  / maxSide, scale2 = width/viewport.z;
	console.log('new maxlength for mesh---', scale1, scale2);
	
	mesh.scale.setScalar(scale1);	
}
mj.tanHalf = (deg) => { return Math.tan(THREE.Math.DEG2RAD * deg / 2) }
// here better is sqrt(2) to sqrt(3)
mj.disFactor = Math.sqrt(3); 
/*
 * blob to file
 */
mj.blob2file = function(blobData) {
	let blob = new Blob([blobData])
}
/*
 * export stl file
 * depend threejs stl export library
 */
mj.mesh2file = function(mesh, opt) {
	opt = opt || { binary: true };
	var exporter = new THREE.STLExporter();
	function stlExport(m) {
        var blob = new Blob([exporter.parse(m, opt)], { type: 'application/octet-stream' });
        var link = document.createElement("a")
        link.href = URL.createObjectURL(blob);
        link.download = m.name;
        link.click();
	}
	stlExport(mesh);
}
/*
 * get the rotate matrix
 * by three axes, at least two axes given
 */
mj.uvn = (function() {
	let unit1 = new THREE.Vector3(), unit2 = new THREE.Vector3(), tmp = new THREE.Vector3();
	let rot1 = new THREE.Vector3(), rot2 = new THREE.Vector3();
	let mat1 = new THREE.Matrix4(), mat2 = new THREE.Matrix4();
	return function(axesX, axesY, axesZ, mat) {
		mat = mat ? mat.identity() : new THREE.Matrix4();
		let axes1, axes2;
		if (!axesX) {
			unit1.set(0,1,0);
			unit2.set(0,0,1);
			axes1 = axesY;
			axes2 = axesZ;
		} else if (!axesY) {
			unit1.set(1,0,0);
			unit2.set(0,0,1);
			axes1 = axesX;
			axes2 = axesZ;
		} else if (!axesZ) {
			unit1.set(1,0,0);
			unit2.set(0,1,0);
			axes1 = axesX;
			axes2 = axesY;
		} else {
			unit1.set(0,1,0);
			unit2.set(0,0,1);
			axes1 = axesY;
			axes2 = axesZ;
		}
		rot1.crossVectors(unit1, axes1).normalize();
		if (rot1.equals(0)) {
			rot1.addScalar(0.0001).crossVectors(unit1, axes1).normalize();
		}
		let angle1 = unit1.angleTo(axes1);
		tmp.copy(unit2).applyAxisAngle(rot1, angle1);
		rot2.crossVectors(tmp, axes2).normalize();
		if (rot2.equals(0)) {
			rot2.addScalar(0.0001).crossVectors(tmp, axes2).normalize();
		}
		let angle2 = tmp.angleTo(axes2);
		mat.multiplyMatrices(mat1.makeRotationAxis(rot1, angle1), mat);
		mat.multiplyMatrices(mat2.makeRotationAxis(rot2, angle2), mat);
		return mat;
	}
})();

