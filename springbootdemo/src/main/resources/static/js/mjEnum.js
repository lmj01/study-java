/*
 * 可以对JavaScript的enum进行模拟，使用两次，两个enum使用同一数字，一个做key，一个做value
 * 再分别对key和value构造一个map对象，这样就可以调整到对应的映射了。
 * */
function mjEnum(kvMap) {
	this._kvMap = kvMap;
	this._v2kMap = {};
	Object.keys(kvMap).forEach((v)=>{
		this._v2kMap[kvMap[v]] = v;
	});
}
mjEnum.prototype = Object.assign(Object.create({}), {
	constructor: mjEnum,
	toV: function(k) {
		if (this._v2kMap.hasOwnProperty(k)) {
			return this._v2kMap[k];
		}
		throw Error('Enum instance has no ' + k + ' field');
	},
	toK: function(v) {
		if (this._kvMap.hasOwnProperty(v)) {
			return this._kvMap[v];
		}
		throw Error('Enum instance has no ' + v + ' field');
	},
	toIdx: function(val) {
		let idx = -1;
		Object.keys(this._kvMap).forEach((v,i)=>{
			if (v==val) idx = i;
		})
		return idx;
	},
	byIdx: function(idx) {
		let res = undefined;
		Object.keys(this._kvMap).forEach((v,i)=>{
			if (i==idx) res = this._kvMap[v];
		});
		return res;
	}
});