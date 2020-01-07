/*
 * code = LoadType.DRC
 * name = LoadType.values[code].name; 
 */
var LoadType = {    
    UNKNOWN:0,
    DRC:1,
    STL:2,
    PLY:3,
    values: {
        1: {name: "drc", value: 1},
        2: {name: "stl", value: 2},
        3: {name: "ply", value: 3},
        0: {name: "unknown", value: 0}
    }
};
Object.freeze(LoadType);

/*
 * 
 */
function mjLoader() {
    this.type = LoadType.UNKNOWN;
    this.loader = null;
}
mjLoader.prototype = Object.assign( Object.create({}), {
    constructor: mqLoader,
    setType: function(ltype) {
        if (this.type == ltype) return;
        if (ltype == LoadType.DRC) {
            THREE.DRACOLoader.setDecoderPath( '../../libs/threejs/draco/' );
            THREE.DRACOLoader.setDecoderConfig( { type: 'js' } );
            this.loader = new THREE.DRACOLoader();
        } else if (ltype == LoadType.STL) {
            if (this.type == LoadType.DRC) this.free();
            this.loader = new THREE.STLLoader();
        } else if (ltype == LoadType.PLY) {
            if (this.type == LoadType.DRC) this.free();
            this.loader = new THREE.PLYLoader();
        }        
        this.type = ltype;
    },
    load: function(url, onLoad, onProgress, onError) {
        if (this.loader == null) {
            throw new Error("call this before call setType");
        }
        this.loader.load(url, onLoad, onProgress, onError);
    },
    free: function() {
        if (this.type == LoadType.DRC) {
            THREE.DRACOLoader.releaseDecoderModule();
        }
    }
});
