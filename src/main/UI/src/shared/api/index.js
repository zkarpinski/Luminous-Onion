const defaultHeaders = {
  "Content-Type": "application/json",
};

const fileUploadHeaders = {
  "Context-Type": "multipart/form-data",
};

const api = (method, headers, url, params) =>
  new Promise((resolve, reject) => {
    // eslint-disable-line
    console.log("Calling endpoint: " + url);
    fetch(`${url}`, {
      method,
      body: method !== "get" ? params : null,
      params: method === "get" ? params : null,
      headers: headers,
    })
      .then((response) => {
        if (!response.ok) throw new Error(response.status);
        else return response.json();
      })
      .then((data) => {
        resolve(data);
      })
      .catch((error) => {
        console.error(error);
      });
  });

// Extend API to
export default {
  get: (...args) => api("get", defaultHeaders, ...args),
  post: (...args) => api("post", defaultHeaders, ...args),
  postFile: (...args) => api("post", fileUploadHeaders, ...args),
  put: (...args) => api("put", defaultHeaders, ...args),
  delete: (...args) => api("delete", defaultHeaders, ...args),
  patch: (...args) => api("patch", defaultHeaders, ...args),
};
