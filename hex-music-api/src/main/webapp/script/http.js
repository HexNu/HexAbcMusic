var http = {
    xmlHttpRequest: function (url, action, method, async) {
        if (async === undefined || async === null) {
            async = true;
        }
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                if (action !== undefined && action !== null) {
                    action(JSON.parse(xmlHttp.responseText));
                }
            }
        };
        xmlHttp.open(method, url, async);
        xmlHttp.send();
    },
    Get: function (url, action, async) {
        http.xmlHttpRequest(url, action, this.Method.GET, async);
    },
    Put: function (url, action, formData) {
        http.xmlHttpRequest(url, action, this.Method.PUT);
    },
    Post: function (url, action, formData) {
        http.xmlHttpRequest(url, action, this.Method.POST);
    },
    Head: function (url, action) {
        http.xmlHttpRequest(url, action, this.Method.HEAD);
    },
    Delete: function (url, action) {
        http.xmlHttpRequest(url, action, this.Method.DELETE);
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
