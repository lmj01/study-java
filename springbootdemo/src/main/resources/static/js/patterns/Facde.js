class API {
	constructor(authToken) {
		this.authToken = authToken;
	}
	constructHeaders() {
		const headers = new Headers();
		headers.set('Authorization', this.authToken);
		return headers;
	}
	handleResponse(response) {
		if (response.ok) {
			return response.json();
		} else {
			return Promise.reject({
				status: response.status,
				statusText: response.statusText
			});
		}
	}
	get(url,options) {
		return fetch(url, {
			headers: this.constructHeanders(),
			...options,
		}).then(this.handleResponse);
	}
	post(url, options) {
		return fetch(url, {
			method:'POST',
			headers: this.constructHeaders(),
			...options,
		}).then(this.handleResponse);
	}
	put(url, options) {
		return fetch(url, {
			method: 'PUT',
			headers: this.constructHeaders(),
			...options,
		}).then(this.handleResponse);
	}
	delete(url, options) {
		return fetch(url, {
			method: 'DELETE', 
			headers: this.constructHeaders(),
			...options,
		}).then(this.handleResponse);
	}
}

// example
const api = new API('my-auth-token');
api.get('https://xxx.com/users/1')
	.then(data=>{
		console.log('User data', data);
	})
	.catch(err=>{
		console.error(err);
	});
