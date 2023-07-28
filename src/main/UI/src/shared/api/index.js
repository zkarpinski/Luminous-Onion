import React from 'react'

const api = (method, url, params) =>
    new Promise((resolve,reject) => {
        console.log(params);
        fetch(`${url}`, {
            method,
            body: method !=='get' ? JSON.stringify(params) : null,
            params: method === 'get' ? params: null,
            headers: {
                "Content-Type": "application/json"
            }
        })
            .catch(console.log)
            .then(response => response.json())
            .then(data => {
                resolve(data)
            })
    });

// Extend API to
export default {
    get: (...args) => api('get',...args),
    post: (...args) => api('post', ...args),
    put: (...args) => api('put', ...args),
    delete: (...args) => api('delete', ...args),
    patch: (...args) => api('patch', ...args)
};