var http = {
    Get: function (url, action) {
        var httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === 4 && httpRequest.status === 200) {
                if (action !== undefined && action !== null) {
                    action(JSON.parse(httpRequest.responseText));
                }
            }
        };
        httpRequest.open(http.Method.GET, url, true);
        httpRequest.send();
    },
    JsonHTTPRequest: function (url, jsonData, method, action, async) {
        var jsonString = JSON.stringify(jsonData);
        var httpRequest = new XMLHttpRequest();
        httpRequest.setRequestHeader("Content-Type", "application/json");
        httpRequest.setRequestHeader("Content-Length", jsonString.length);
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === 4 && httpRequest.status === 200) {
                alert(httpRequest.responseText);
                if (action !== undefined && action !== null) {
                    action(JSON.parse(httpRequest.responseText));
                }
            }
        };
        httpRequest.open(method, url, async || true);
        httpRequest.send(jsonString);
    },
    PostJson: function (url, jsonData, action, async) {
        http.JsonHTTPRequest(url, jsonData, http.Method.POST, action, async);
    },
    PutJson: function (url, jsonData, action, async) {
        http.JsonHTTPRequest(url, jsonData, http.Method.PUT, action, async);
    },
    Method: {
        GET: 'GET',
        POST: 'POST',
        PUT: 'PUT',
        HEAD: 'HEAD',
        DELETE: 'DELETE'
    },
    MediaType: {
        APPLICATION_FORM_URLENCODED: 'application/x-www-form-urlencoded',
        APPLICATION_JSON: 'application/json',
        APPLICATION_OCTET_STREAM: 'application/octet-stream',
        APPLICATION_SVG_XML: 'application/svg+xml',
        APPLICATION_XHTML_XML: 'application/xhtml+xml',
        APPLICATION_XML: 'application/xml',
        MULTIPART_FORM_DATA: 'multipart/form-data',
        TEXT_HTML: 'text/html',
        TEXT_PLAIN: 'text/plain'
    }
};
