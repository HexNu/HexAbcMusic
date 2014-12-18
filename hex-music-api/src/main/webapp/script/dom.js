var $ = function (id) {
    return document.getElementById(id);
};
var dom = {
    clearNode: function(nodeId) {
        $(nodeId).innerHTML = '';
    },
    createNode: function (name, text) {
        var node = document.createElement(name);
        if (text !== undefined && text !== null) {
            dom.appendText(node, text);
        }
        return node;
    },
    getChild: function (node, name) {
        var result = node.getElementsByTagName(name)[0];
        return result !== undefined ? result : null;
    },
    getChildren: function (node, name) {
        if (name !== undefined && name !== null) {
            return node.getElementsByTagName(name);
        } else {
            node.childNodes;
        }
    },
    appendText: function (node, text) {
        var textNode = document.createTextNode(text);
        node.appendChild(textNode);
    },
    getText: function (node) {
        return node.childNodes[0].nodeValue;
    },
    setText: function (node, text) {
        for (i = 0; i < node.childNodes.length; i++) {
            if (node.childNodes[0].nodeType === 3) {
                node.removeChild(node.childNodes[0]);
            }
        }
        dom.appendText(node, text);
    }
};