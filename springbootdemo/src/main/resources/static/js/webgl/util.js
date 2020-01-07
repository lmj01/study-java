let mj = window.mj || {};

mj.rad2deg = function(rad) {
	return Math.PI / 180.0 * rad;
}

mj.deg2rad = function(deg) {
	return 180.0 / Math.PI * deg;
}

/*
 * https://stackoverflow.com/questions/2866350/move-camera-to-fit-3d-scene
 * http://www.realtimerendering.com/udacity/transforms.html
 */
mj.fitCamera = function(camera, geometry) {
	geometry.computeBoundingBox();
	let bbox = geometry.boundingBox;
	let center = new THREE.Vector3();
	bbox.getCenter(center);	
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

