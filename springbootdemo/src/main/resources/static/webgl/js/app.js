class App {
	constructor(fullWidth, fullHeight, container = document.body) {
		if (!(container instanceof HTMLElement)) {
			throw new Error("container is not a HTMLElement");
		}
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		const scene = new THREE.Scene();
		this.scene = scene;
		this.clock = new THREE.Clock();
		this.camera = null;
		const renderer = new THREE.WebGLRenderer({
			antialias: true
		})
		container.appendChild( renderer.domElement );
		this.renderer = renderer;
	}
	__init(cb) {
		const {scene, camera, renderer, clock, fullWidth, fullHeight} = this;
		
		renderer.setSize(fullWidth, fullHeight);
		renderer.setPixelRatio(window.devicePixelRatio);
		renderer.gammaInput = true;
		renderer.gammaOutput = true;
		renderer.shadowMap.enabled = true;
		
		renderer.setAnimationLoop(()=>{
			const delta = clock.getDelta();
			renderer.render(scene, camera);
			if (cb) cb();			
		});
	}
	render(cb) {
		this.__init(cb);		
	}	
	updateSize(fullWidth, fullHeight) {
		const {camera, renderer} = this;
		
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		
		camera.aspect = fullWidth / fullHeight;
		camera.updateProjectionMatrix();

		renderer.setSize( fullWidth, fullHeight );
	}
	setCamera(camera) {
		this.camera = camera;
	}
	add(obj3D) {
		const {scene} = this;
//		if (obj3D.isObject3D) {
			scene.add(obj3D);	
//		} else {
//			throw new Error("not Object3D object");
//		}		
	}
	remove(obj3D) {
		const {scene} = this;
//		if (obj3D.isObject3D) {
			scene.remove(obj3D);	
//		} else {
//			throw new Error("not Object3D object");
//		}		
	}
	findMesh(name) {
		const {scene} = this;
		return scene.children.filter(e=>e.name==name)[0];
	}
}