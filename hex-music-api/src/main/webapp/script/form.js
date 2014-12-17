var form = {
    /**
     * 
     * @param {type} id
     * @param {type} action
     * @param {type} method
     * @param {type} enctype
     * @returns {dom@call;createNode.Form.form}
     */
    Form: function (id, action, method, enctype) {
        var result = dom.createNode('form');
        result.setAttribute('id', id);
        result.setAttribute('action', action);
        result.setAttribute('method', method);
        result.setAttribute('enctype', enctype);
        return result;
    },
    DataList: function (name, id) {
        var dataListContainer = dom.createNode('div');
        var list = dom.createNode('input');
        list.setAttribute('name', name);
        list.setAttribute('list', id);
        dataListContainer.appendChild(list);
        var dataList = dom.createNode('datalist');
        dataList.setAttribute('id', id);
        dataListContainer.appendChild(dataList);
        return dataListContainer;

    },
    SelectList: function (name, id) {
        var result = dom.createNode('select');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        return result;
    },
    FileChooser: function (name, id) {
        var result = dom.createNode('input');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (required !== undefined && required !== null && required === true) {
            result.setAttribute('required');
        }
        return result;
    }
    ,
    Label: function (text, forId) {
        var result = dom.createNode('label', text);
        if (forId !== undefined && forId !== null) {
            result.setAttribute('for', forId);
        }
        return result;
    },
    NumberChooserField: function (name, id, value, min, max) {
        this.providedValue = value !== undefined && value !== null ? value : 0;
        this.min = min !== undefined && min !== null ? min > this.providedValue ? this.providedValue : min : null;
        this.max = max !== undefined && max !== null ? max < this.providedValue ? this.providedValue : max : null;
        var result = dom.createNode('input');
        result.setAttribute('type', 'number');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
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
    SearchField: function (name, id, value) {
        var result = dom.createNode('input');
        result.setAttribute('type', 'search');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (value !== undefined && value !== null) {
            result.setAttribute('value', value);
        }
        return result;
    },
    TextField: function (name, id, value) {
        var result = dom.createNode('input');
        result.setAttribute('type', 'text');
        result.setAttribute('name', name);
        if (id !== undefined && id !== null) {
            result.setAttribute('id', id);
        }
        if (value !== undefined && value !== null) {
            result.setAttribute('value', value);
        }
        return result;
    },
    TextArea: function (name, id, rows, cols, value) {
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
