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

