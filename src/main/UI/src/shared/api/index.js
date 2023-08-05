const defaultHeaders = {
    "Content-Type":"application/json"
};

const fileUploadHeaders = {
    "Context-Type":"multipart/form-data"
}

const api = (method,headers, url, params) =>
    new Promise((resolve,reject) => { // eslint-disable-line
        console.log('Params are: ' + params);
        fetch(`${url}`, {
            method,
            body: method !=='get' ? params : null,
            params: method === 'get' ? params: null,
            headers: headers
        })
            .catch(console.log)
            .then(response => response.json())
            .then(data => {
                resolve(data)
            })
    });

// Extend API to
export default {
    get: (...args) => api('get',defaultHeaders, ...args),
    post: (...args) => api('post',defaultHeaders, ...args),
    postFile: (...args) => api('post',fileUploadHeaders, ...args),
    put: (...args) => api('put',defaultHeaders, ...args),
    delete: (...args) => api('delete',defaultHeaders, ...args),
    patch: (...args) => api('patch',defaultHeaders, ...args)
};