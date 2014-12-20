var element = {
    /**
     * 
     * @param {type} name
     * @returns {element.NumberChooserField}
     */
    NumberChooserField: function (name) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('type', 'number');
        this.domElement.setAttribute('name', name);
    },
    /**
     * 
     * @param {type} name
     * @param {type} value
     * @returns {element.SearchField}
     */
    SearchField: function (name, value) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('type', 'search');
        this.domElement.setAttribute('name', name);
        this.setValue(value);
    },
    /**
     * 
     * @param {type} id
     * @returns {element.Form}
     */
    Form: function (id) {
        this.domElement = dom.createNode('form');
        this.setId(id);
    },
    /**
     * 
     * @param {type} fieldName
     * @param {type} action
     * @returns {element.FileUploader}
     */
    FileUploader: function (fieldName, action) {
        var fileChooserId = fieldName + '-chooser';
        var formId = fieldName;
        this.id = formId;
        this.action = action;
        this.domElement = dom.createNode('form');
        this.domElement.setAttribute('style', 'display:inline');
        this.domElement.setAttribute('id', this.id);
        this.domElement.setAttribute('action', this.action);
        this.domElement.setAttribute('method', http.Method.POST);
        this.domElement.setAttribute('enctype', http.MediaType.MULTIPART_FORM_DATA);
        this.domElement.setAttribute('target', 'upload-target');
        this.target = dom.createNode('iframe');
        this.target.setAttribute('src', '_blank');
        this.target.setAttribute('style', 'display:none');
        this.target.setAttribute('name', 'upload-target');
        this.domElement.appendChild(this.target);
        this.fileChooser = new element.FileChooser(this.id);
        this.fileChooser.setId(fileChooserId);
        this.fileChooser.addChangeAction(function () {
            $(formId).submit();
        });
        this.fileChooser.getElement().setAttribute('style', 'display:none');
        this.domElement.appendChild(this.fileChooser.getElement());
        this.fileChooserTrigger = new element.IconButton('document_import', 'Importera');
        this.fileChooserTrigger.addIconClickedAction(function () {
            $(fileChooserId).click();
        });
        this.domElement.appendChild(this.fileChooserTrigger.getElement());
    },
    /**
     * 
     * @param {type} name
     * @returns {element.FileChooser}
     */
    FileChooser: function (name) {
        this.name = name;
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('name', this.name);
        this.domElement.setAttribute('type', 'file');
    },
    /**
     * 
     * @param {type} name
     * @returns {element.SelectList}
     */
    SelectList: function (name) {
        this.domElement = dom.createNode('select');
        this.name = name || null;
        if (this.name !== null) {
            this.domElement.setAttribute('name', name);
        }
    },
    /**
     * 
     * @param {type} name
     * @returns {element.TextField}
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
     * @returns {element.TextArea}
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
     * @returns {element.Label}
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
     * @returns {element.Border}
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
     * @returns {element.IconButton}
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
     * @param {type} name
     * @param {type} id
     * @returns {element.DataList}
     */
    DataList: function (name, id) {
        // Här kan det vara buggigt...
        this.domElement = dom.createNode('div');
        this.domElement.setAttribute('id', id);
        this.list = dom.createNode('input');
        this.list.setAttribute('name', name);
        this.list.setAttribute('id', id + '-input');
        this.list.setAttribute('list', id + '-datalist');
        this.domElement.appendChild(this.list);
        this.dataList = dom.createNode('datalist');
        this.dataList.setAttribute('id', id + '-datalist');
        this.select = dom.createNode('select');
        this.select.setAttribute('id', id + '-select');
        this.dataList.appendChild(this.select);
        this.domElement.appendChild(this.dataList);
    }
};
element.DataList.prototype = {
    setValue: function (value) {
        this.value = value !== null && value !== undefined ? value : '';
        this.list.setAttribute('value', this.value);
    },
    setDataList: function (items) {
        this.items = items || null;
        if (this.items !== null) {
            this.clearDataList();
            for (var i = 0; i < this.items.length; i++) {
                var opt = dom.createNode('option');
                if (this.items[i].description !== undefined && this.items[i].description !== null) {
                    opt.setAttribute('label', this.items[i].description);
                }
                opt.setAttribute('value', this.items[i].name);
                this.select.appendChild(opt);
            }
        }
    },
    clearDataList: function () {
        this.select.innerHTML = '';
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    getElement: function () {
        return this.domElement;
    }
},
element.NumberChooserField.prototype = {
    setValue: function (value) {
        this.value = value || 0;
        this.domElement.setAttribute('value', this.value);
    },
    setMin: function (min) {
        this.min = min || 0;
        this.domElement.setAttribute('min', this.min);
    },
    setMax: function (max) {
        this.max = max || 500;
        this.domElement.setAttribute('min', this.max);
    },
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
    getElement: function () {
        return this.domElement;
    }
};
element.SearchField.prototype = {
    setValue: function (value) {
        this.value = value || null;
        if (this.value !== null) {
            this.domElement.setAttribute('value', this.value);
        }
    },
    getValue: function () {
        return this.domElement.value;
    },
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
    getElement: function () {
        return this.domElement;
    }
};
element.Form.prototype = {
    addElement: function (childElement) {
        this.domElement.appendChild(childElement);
    },
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setEncodignType: function (enctype) {
        this.enctype = enctype || null;
        if (this.enctype !== null) {
            this.domElement.setAttribute('enctype', this.enctype);
        }
    },
    setMethod: function (method) {
        this.method = method || null;
        if (this.method !== null) {
            this.domElement.setAttribute('method', this.method);
        }
    },
    setAction: function (action) {
        this.action = action || null;
        if (this.action !== null) {
            this.domElement.setAttribute('action', this.action);
        }
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    getElement: function () {
        return this.domElement;
    }
};
element.FileUploader.prototype = {
    getElement: function () {
        return this.domElement;
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    }
};
element.FileChooser.prototype = {
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
    getElement: function () {
        return this.domElement;
    },
    addChangeAction: function (action) {
        this.domElement.addEventListener('change', action);
    }
};
element.SelectList.prototype = {
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
    setSelectedIndex: function (selectedIndex) {
        this.selectedIndex = selectedIndex || null;
        if (this.selectedIndex !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    getElement: function () {
        return this.domElement;
    },
    addChangeAction: function (action) {
        this.domElement.addEventListener('change', action);
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
