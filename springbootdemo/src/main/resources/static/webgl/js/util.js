let mj = window.mj || {};

/*
 * https://stackoverflow.com/questions/2866350/move-camera-to-fit-3d-scene
 * http://www.realtimerendering.com/udacity/transforms.html
 */
mj.fitCamera = function(camera, geometry) {
	geometry.computeBoundingBox();
	let bbox = geometry.boundingBox;
	let target = new THREE.Vector3();
	bbox.getCenter(target);	
	console.log('target position---', target);
	
	let distance = target.clone().distanceTo(camera.position);
	console.log('camera to target distnace---', distance);
	
	var vFOV = THREE.Math.degToRad(camera.fov);
	var height = 2 * Math.tan(vFOV / 2) * distance;
	var width = height * camera.aspect;
	console.log('fit camera-------',width, height);	
}

/*
 * export stl file
 * depend threejs stl export library
 */
mj.mesh2file = function(mesh, opt) {
	opt = opt || { binary: true };
	var exporter = new THREE.STLExporter();
	function stlExport(m) {
        var blob = new Blob([exporter.parse(m, opt)], { type: 'text/plain' });
        var link = document.createElement("a")
        link.href = URL.createObjectURL(blob);
        link.download = m.name;
        link.click();
	}
	stlExport(mesh);
}

