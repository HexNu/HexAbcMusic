var $ = function (id) {
    return document.getElementById(id);
};
var StringBuilder = function (string) {
    this.array = [];
    this.append(string);
};
StringBuilder.prototype = {
    append: function (string) {
        if (string !== undefined && string !== null) {
            this.array[this.array.length] = string;
        }
    },
    toString: function (delimiter) {
        delimiter = delimiter !== undefined && delimiter !== null ? delimiter : '';
        return this.array.join(delimiter);
    }
};
var dom = {
    /**
     * Clears the node with the provided id of content.
     * 
     * @param {String} nodeId
     * @returns {void}
     */
    clearNode: function (nodeId) {
        $(nodeId).innerHTML = '';
    },
    /**
     * Creates a dom node of the name type.
     * 
     * @param {String} name
     * @param {String} text
     * @returns {Element}
     */
    createNode: function (name, text) {
        var node = document.createElement(name);
        if (text !== undefined && text !== null) {
            dom.appendText(node, text);
        }
        return node;
    },
    /**
     * Returns the first child of the provided Element node with the name given.
     * 
     * @param {Element} node
     * @param {String} name
     * @returns {Element}
     */
    getChild: function (node, name) {
        var result = node.getElementsByTagName(name)[0];
        return result !== undefined ? result : null;
    },
    /**
     * Returns all children of the provided node with the name given.
     * 
     * @param {Element} node
     * @param {String} name
     * @returns {Element[]}
     */
    getChildren: function (node, name) {
        if (name !== undefined && name !== null) {
            return node.getElementsByTagName(name);
        } else {
            node.childNodes;
        }
    },
    /**
     * 
     * @param {Element} node
     * @param {String} text
     * @returns {void}
     */
    appendText: function (node, text) {
        var textNode = document.createTextNode(text);
        node.appendChild(textNode);
    },
    /**
     * 
     * @param {Element} node
     * @returns {String}
     */
    getText: function (node) {
        return node.childNodes[0].nodeValue;
    },
    /**
     * 
     * @param {Element} node
     * @param {String} text
     * @returns {void}
     */
    setText: function (node, text) {
        for (i = 0; i < node.childNodes.length; i++) {
            if (node.childNodes[0].nodeType === 3) {
                node.removeChild(node.childNodes[0]);
            }
        }
        dom.appendText(node, text);
    }
};