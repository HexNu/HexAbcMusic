var form = {
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
     * @returns {form.DataList.dataListContainer|Element}
     */
    DataList: function (name, id, cssClass) {
        var dataListContainer = dom.createNode('div');
        var list = dom.createNode('input');
        list.setAttribute('name', name);
        list.setAttribute('list', id);
        if (cssClass !== undefined && cssClass !== null) {
            dataListContainer.setAttribute('class', cssClass);
        }
        dataListContainer.appendChild(list);
        var dataList = dom.createNode('datalist');
        dataList.setAttribute('id', id);
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
     * @param {type} text
     * @param {type} forId
     * @param {type} cssClass
     * @returns {form.Label.result|Element}
     */
    Label: function (text, forId, cssClass) {
        var result = dom.createNode('label', text);
        if (forId !== undefined && forId !== null) {
            result.setAttribute('for', forId);
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
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @param {type} value
     * @returns {form.TextField.result|Element}
     */
    TextField: function (name, id, cssClass, value) {
        var result = dom.createNode('input');
        result.setAttribute('type', 'text');
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
    },
    /**
     * 
     * @param {type} name
     * @param {type} id
     * @param {type} cssClass
     * @param {type} rows
     * @param {type} cols
     * @param {type} value
     * @returns {Element|form.TextArea.result}
     */
    TextArea: function (name, id, cssClass, rows, cols, value) {
        var result = dom.createNode('textarea', text);
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        result.setAttribute('rows', rows !== undefined && rows !== null ? rows : 5);
        result.setAttribute('cols', cols !== undefined && cols !== null ? cols : 30);
        if (value !== undefined && value !== null) {
            dom.appendText(result, value);
        }
        return result;
    }
};
