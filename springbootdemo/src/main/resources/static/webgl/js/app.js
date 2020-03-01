class App {
	constructor(fullWidth, fullHeight, container = document.body) {
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		const scene = new THREE.Scene();
		this.scene = scene;
		this.clock = new THREE.Clock();
		this.camera = null;
		this.cbRender = null;
		this.cbResize = null;
		if (signals && signals.Signal) {
			this.animateHandle = new signals.Signal();
		}
		
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
	__init() {
		const {scene, camera, renderer, clock
			, fullWidth, fullHeight
			, control, cbRender, cbResize
		} = this;
		
		if (fullWidth > 0) {
			renderer.setSize(fullWidth, fullHeight);				
		}
		
		renderer.setAnimationLoop(()=>{
			const delta = clock.getDelta();
			if (this.needResize()) {
				if (cbResize) cbResize();
			}
			if (cbRender) cbRender(delta);
			else renderDefault();
			if (control) control.update();
		});
	}
	controlUpdate() {
		const {control} = this;
		function update() {
			requestAnimationFrame(update);
			if (control) control.update();	
		}
		update();
	}
	renderFrame() {
		const { cbResize, cbRender } = this;
		
		const delta = this.clock.getDelta();
		
		if (this.needResize()) {
			if (cbResize) cbResize();
		}
		
		if (this.animateHandle) {
			this.animateHandle.dispatch();				
		}
		
		if (cbRender) cbRender(delta);
		else this.renderDefault();
	}
	
	renderCallback(cbRender, cbResize) {
		const {renderer} = this;
		this.cbRender = cbRender;
		this.cbResize = cbResize;
		renderer.setPixelRatio(window.devicePixelRatio);
		renderer.gammaInput = true;
		renderer.gammaOutput = true;
		renderer.shadowMap.enabled = true;		
	}
	renderLoop() {
		this.__init();
	}	
	renderDefault() {
		const {renderer, scene, camera} = this;
		if (camera) {
			renderer.clear();
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
			//console.log('------need resize', width, height);			
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
	setControl(control) {
		this.control = control;
	}
	setRenderOption(opt) {
		
	}
	add(obj3D) {
		const {scene} = this;
		scene.add(obj3D);
	}
	remove(obj3D) {
		const {scene} = this;
		scene.remove(obj3D);
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
//		let boxHelper = new THREE.BoxHelper(scene, 0xff0000);
//		boxHelper.update();
//		this.add(boxHelper);
//		boxHelper.geometry.computeBoundingBox();
//		box = boxHelper.geometry.boundingBox;
//		console.log('--2-', box);
		
		let center = new THREE.Vector3(), size = new THREE.Vector3();
		box.getCenter(center);
		box.getSize(size);
		
		return {center:center, size:size};
	}
	findMesh(name) {
		const {scene} = this;
		return scene.children.filter(e=>e.name==name)[0];
	}
	updateCameraType(camera, side, fov) {
		  fov = fov || 45;
		  const disFactor = Math.sqrt(3); // sqrt3 or sqrt2
		  const {center, size} = app.getBox();
		  let isPerspective = camera.isPerspectiveCamera;
		  let maxLen = Math.max(size.x, Math.max(size.y, size.z));
		  let distance =  isPerspective ? maxLen / (2 * mj.tanHalf(fov)) : maxLen * 2;
		
			let newPos = new THREE.Vector3(center.x, center.y, center.z),
		  	newUp = new THREE.Vector3(0,1,0),
		  	viewDir = new THREE.Vector3();
		  
		  if (side == "front") {
			  // x/y plane				
			  viewDir.set(0,0,1);
			  newPos.z = viewDir.multiplyScalar(distance).z;
			  if (isPerspective) newPos.z *= disFactor;
		  } else if (side == "back") {
			// x/y plane				
			  viewDir.set(0,0,-1);
			  newPos.z = viewDir.multiplyScalar(distance).z;
			  if (isPerspective) newPos.z *= disFactor;
		  } else if (side == "left") {
			  // z/y plane
			  viewDir.set(-1, 0, 0);
			  newPos.x = viewDir.multiplyScalar(distance).x;
			  if (isPerspective) newPos.x *= disFactor;
		  } else if (side == "right") {
			// z/y plane
			  viewDir.set(1, 0, 0);
			  newPos.x = viewDir.multiplyScalar(distance).x;
			  if (isPerspective) newPos.x *= disFactor;
		  } else if (side == "top") {
			  // z/x plane
			  viewDir.set(0, 1, 0);
			  newUp.set(0,1,0);
			  newPos.y = viewDir.multiplyScalar(distance).y;
			  if (isPerspective) newPos.y *= disFactor;
		  } else if (side == "bottom") {
			// z/x plane
			  viewDir.set(0, -1, 0);
			  newUp.set(0,1,0);
			  newPos.y = viewDir.multiplyScalar(distance).y;
			  if (isPerspective) newPos.y *= disFactor;
		  }		
		  
			camera.position.copy(newPos);
			camera.up.copy(newUp);
			//camera.lookAt(center.x,  center.y, center.z);	
			camera.updateProjectionMatrix();
	  }
	addDefault() {
		{
		    const cubeSize = 5;
		    const cubeGeo = new THREE.BoxBufferGeometry(cubeSize, cubeSize, cubeSize);
		    const cubeMat = new THREE.MeshPhongMaterial({color: '#8AC'});
		    const mesh = new THREE.Mesh(cubeGeo, cubeMat);
		    mesh.position.set(2.5, 0, 0);
		    this.add(mesh);
		}
		{
		    const sphereRadius = 3;
		    const sphereWidthDivisions = 32;
		    const sphereHeightDivisions = 16;
		    const sphereGeo = new THREE.SphereBufferGeometry(sphereRadius, sphereWidthDivisions, sphereHeightDivisions);
		    const sphereMat = new THREE.MeshPhongMaterial({color: '#CA8'});
		    const mesh = new THREE.Mesh(sphereGeo, sphereMat);
		    mesh.position.set(-5, 0, 0);
		    this.add(mesh);
		  }

		  {
		    const color = 0xFFFFFF;
		    const intensity = 1;
		    const light = new THREE.DirectionalLight(color, intensity);
		    light.position.set(0, 10, 0);
		    light.target.position.set(-5, 0, 0);
		    this.add(light);
		    this.add(light.target);
		  }
	}
}