class App {
	constructor(fullWidth, fullHeight, container = document.body) {
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		const scene = new THREE.Scene();
		this.scene = scene;
		this.clock = new THREE.Clock();
		this.camera = null;
		
		if ((container instanceof HTMLElement)) {
			if ((container instanceof HTMLCanvasElement)) {
				
				console.log('container is canvas')
				this.renderer = new THREE.WebGLRenderer({canvas:container});
				
			} else {
		
				console.log('container is element')
				const renderer = new THREE.WebGLRenderer({
					antialias: true
				})
				container.appendChild( renderer.domElement );
				
				this.renderer = renderer;	
			}				
		} else {
			throw new Error("container is not a HTMLElement or Canvas ");
		}
		
	}
	__init(cbRender, cbCamera) {
		const {scene, camera, renderer, clock
			, fullWidth, fullHeight
		} = this;
		
		if (fullWidth > 0) {
			renderer.setSize(fullWidth, fullHeight);				
		}
		renderer.setPixelRatio(window.devicePixelRatio);
		renderer.gammaInput = true;
		renderer.gammaOutput = true;
		renderer.shadowMap.enabled = true;
		
		renderer.setAnimationLoop(()=>{
			const delta = clock.getDelta();
			if (this.needResize()) {
				if (cbCamera) cbCamera();
			}
			if (cbRender) cbRender(delta);
			else renderDefault();
		});
	}
	render(cbRender, cbCamera) {
		this.__init(cbRender, cbCamera);		
	}	
	renderDefault() {
		const {renderer, scene, camera} = this;
		if (camera) {
			renderer.render(scene, camera);	
		}
	}
	needResize() {
		const {renderer} = this;
		const canvas = renderer.domElement;
		let width = canvas.clientWidth, 
			height = canvas.clientHeight;
		const isChange = canvas.width !== width || canvas.height !== height;
		if (isChange) {
			console.log('------need resize', width, height);			
			this.fullWidth = width;
			this.fullHeight = height;
			renderer.setSize(width, height, false);
		}
		return isChange;
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
	getBox() {
		const {scene} = this;
		function visitObject3D(obj3d, box) {
			if (obj3d.children.length > 0) {
				obj3d.children.forEach(e=>visitObject3D(e, box));
			} else {
				if (obj3d.isMesh && obj3d.visible) {
					let geo = obj3d.geometry.clone();
					if (geo) {
						geo.applyMatrix(obj3d.matrixWorld);						
						geo.computeBoundingBox();						
						box.union(geo.boundingBox);
					}
				}
			}
		}
		function visitObject3D_2(obj3d) {
			var box = null;
			obj3d.traverse(obj=>{
				if (obj.matrixWorldNeedsUpdate) obj.updateMatrixWorld();
				let geo = obj.geometry;
				if (geo === undefined) return null;
				if (geo.boundingBox) {
					let workableBox = geo.boundingBox.clone();
					workableBox.applyMatrix4(obj.matrixWorld);
					if (box == null) {
						box = workableBox;
					} else {
						box.union(workableBox);
					}
				} else {
					let workableGeo = geo.clone();
					workableGeo.applyMatrix(obj.matrixWorld);
					workableGeo.computeBoundingBox();
					if (box == null) {
						box = workableGeo.boundingBox;
					} else {
						box.union(workableGeo.boundingBox);
					}
				}
			});
			return box;
		}
		let box = new THREE.Box3();
		visitObject3D(scene, box);
//		box = visitObject3D_2(scene);
//		console.log('--1-', box);
		let boxHelper = new THREE.BoxHelper(scene, 0xff0000);
		boxHelper.update();
		this.add(boxHelper);
//		boxHelper.geometry.computeBoundingBox();
//		box = boxHelper.geometry.boundingBox;
//		console.log('--2-', box);
		return box;
	}
	findMesh(name) {
		const {scene} = this;
		return scene.children.filter(e=>e.name==name)[0];
	}
}