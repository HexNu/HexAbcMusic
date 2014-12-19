var element = {
    /**
     * 
     * @param {type} name
     * @returns {undefined}
     */
    TextField: function (name) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('type', 'text');
        this.domElement.setAttribute('name', name);
    },
    /**
     * 
     * @param {type} name
     * @param {type} rows
     * @param {type} cols
     * @returns {undefined}
     */
    TextArea: function (name, rows, cols) {
        this.domElement = dom.createNode('textarea');
        this.name = name;
        this.domElement.setAttribute('name', this.name);
        this.domElement.setAttribute('rows', rows !== undefined && rows !== null ? rows : 10);
        this.domElement.setAttribute('cols', cols !== undefined && cols !== null ? cols : 50);
    },
    /**
     * 
     * @param {type} text
     * @param {type} targetId
     * @returns {undefined}
     */
    Label: function (text, targetId) {
        this.text = text || null;
        this.domElement = dom.createNode('label', text);
        if (targetId !== undefined && targetId !== null) {
            this.domElement.setAttribute('for', targetId);
        }
    },
    /**
     * 
     * @param {type} legend
     * @param {type} cssClass
     * @returns {undefined}
     */
    Border: function (legend, cssClass) {
        this.domElement = dom.createNode('fieldset');
        this.setLegend(legend);
        this.setCssClass(cssClass);
    },
    /**
     * 
     * @param {type} imageName
     * @param {type} altText
     * @returns {undefined}
     */
    IconButton: function (imageName, altText) {
        this.setImageUrl('layout/images/icons/16x16/' + imageName + '.png');
        this.domElement = dom.createNode('img');
        this.domElement.setAttribute('src', this.getImageUrl());
        this.domElement.setAttribute('width', '16');
        this.domElement.setAttribute('height', '16');
        if (altText) {
            this.domElement.setAttribute('alt', altText);
        }
    },
    /**
     * 
     * @param {type} id
     * @param {type} cssClass
     * @param {type} action
     * @param {type} method
     * @param {type} enctype
     * @returns {form.Form.result|Element}
     */
    Form: function (id, cssClass, action, method, enctype) {
        var result = dom.createNode('form');
        result.setAttribute('id', id);
        if (cssClass !== undefined && cssClass !== null) {
            result.setAttribute('class', cssClass);
        }
        result.setAttribute('action', action);
        result.setAttribute('method', method);
        result.setAttribute('enctype', enctype);
        return result;
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @param {type} value
     * @returns {form.DataList.dataListContainer|Element}
     */
    DataList: function (name, id, cssClass, value) {
        var dataListContainer = dom.createNode('div');
        var list = dom.createNode('input');
        list.setAttribute('name', name);
        list.setAttribute('id', id + '-input');
        list.setAttribute('list', id + '-datalist');
        var inputValue = value !== null && value !== undefined ? value : '';
        list.setAttribute('value', inputValue);
        if (cssClass !== undefined && cssClass !== null) {
            dataListContainer.setAttribute('class', cssClass);
        }
        dataListContainer.appendChild(list);
        var dataList = dom.createNode('datalist');
        dataList.setAttribute('id', id + '-datalist');
        var select = dom.createNode('select');
        select.setAttribute('id', id);
        dataList.appendChild(select);
        dataListContainer.appendChild(dataList);
        return dataListContainer;
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @returns {form.SelectList.result|Element}
     */
    SelectList: function (name, id, cssClass) {
        var result = dom.createNode('select');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (cssClass !== undefined && cssClass !== null) {
            result.setAttribute('class', cssClass);
        }
        return result;
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @returns {form.FileChooser.result|Element}
     */
    FileChooser: function (name, id, cssClass) {
        var result = dom.createNode('input');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (cssClass !== undefined && cssClass !== null) {
            result.setAttribute('class', cssClass);
        }
        if (required !== undefined && required !== null && required === true) {
            result.setAttribute('required');
        }
        return result;
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @param {type} value
     * @param {type} min
     * @param {type} max
     * @returns {form.NumberChooserField.result|Element}
     */
    NumberChooserField: function (name, id, cssClass, value, min, max) {
        this.providedValue = value !== undefined && value !== null ? value : 0;
        this.min = min !== undefined && min !== null ? min > this.providedValue ? this.providedValue : min : null;
        this.max = max !== undefined && max !== null ? max < this.providedValue ? this.providedValue : max : null;
        var result = dom.createNode('input');
        result.setAttribute('type', 'number');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (cssClass !== undefined && cssClass !== null) {
            result.setAttribute('class', cssClass);
        }
        if (value !== undefined && value !== null) {
            result.setAttribute('value', value);
        }
        if (this.min !== null) {
            result.setAttribute('min', this.min);
        }
        if (this.max !== null) {
            result.setAttribute('max', this.max);
        }
        return result;
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @param {type} value
     * @returns {form.SearchField.result|Element}
     */
    SearchField: function (name, id, cssClass, value) {
        var result = dom.createNode('input');
        result.setAttribute('type', 'search');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (cssClass !== undefined && cssClass !== null) {
            result.setAttribute('class', cssClass);
        }
        if (value !== undefined && value !== null) {
            result.setAttribute('value', value);
        }
        return result;
    }
};
element.TextField.prototype = {
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setValue: function (value) {
        this.value = value || '';
        this.domElement.setAttribute('value', this.value);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.TextArea.prototype = {
    getId: function () {
        return this.id;
    },
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setText: function (text) {
        this.text = text || null;
        if (this.text !== null) {
            this.domElement.value = this.text;
        }
    },
    getName: function () {
        return this.name;
    },
    getElement: function () {
        return this.domElement;
    }
};
element.Label.prototype = {
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', cssClass);
        }
    },
    getElement: function () {
        return this.domElement;
    }
},
element.Border.prototype = {
    setLegend: function (legend) {
        this.legend = legend || null;
        if (this.legend !== null) {
            var legendNode = dom.createNode('legend');
            this.domElement.appendChild(legendNode);
            dom.setText(legendNode, this.legend);
        }
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    addChild: function (element) {
        this.domElement.appendChild(element);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.IconButton.prototype = {
    getImageUrl: function () {
        return this.imageUrl;
    },
    setImageUrl: function (imageUrl) {
        this.imageUrl = imageUrl;
    },
    getId: function () {
        return this.id;
    },
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    setCssClass: function (cssClass) {
        this.domElement.setAttribute('class', cssClass);
    },
    getElement: function () {
        return this.domElement;
    },
    addIconClickedAction: function (action) {
        this.domElement.addEventListener('click', action);
    }
};
