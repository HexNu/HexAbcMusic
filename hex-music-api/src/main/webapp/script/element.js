var element = {
    /**
     * 
     * @param {int} width
     * @param {int} height
     * @returns {element.SpacerElement}
     */
    Spacer: function(width, height) {
        this.domElement = dom.createNode('img');
        this.domElement.setAttribute('src', 'layout/images/spacer.png');
        this.domElement.setAttribute('width', width);
        this.domElement.setAttribute('height', height);
        this.domElement.setAttribute('alt','');
    },
    /**
     * 
     * @param {String} url
     * @param {String} imageName
     * @param {String} altText
     * @returns {element.IconLink}
     */
    IconLink: function (url, imageName, altText) {
        this.domElement = dom.createNode('a');
        this.domElement.setAttribute('href', decodeURIComponent(url));
        this.image = dom.createNode('img');
        this.image.setAttribute('src', 'layout/images/icons/16x16/' + imageName + '.png');
        this.image.setAttribute('width', '16');
        this.image.setAttribute('height', '16');
        this.setStyle('border:none;margin-right:3px;');
        if (altText) {
            this.image.setAttribute('alt', altText);
        }
        this.domElement.appendChild(this.image);
    },
    /**
     * 
     * @param {String} name
     * @returns {element.NumberChooserField}
     */
    NumberChooserField: function (name) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('type', 'number');
        this.domElement.setAttribute('name', name);
    },
    /**
     * 
     * @param {String} name
     * @param {String} value
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
     * @param {String} id
     * @returns {element.Form}
     */
    Form: function (id) {
        this.domElement = dom.createNode('form');
        this.table = dom.createNode('table');
        this.table.setAttribute('style', 'border-collapse: collapse; border-spacing: 0; margin-right: 10px;');
        this.domElement.appendChild(this.table);
        this.setId(id);
    },
    /**
     * 
     * @returns {element.FormTitleMenuBar}
     */
    FormTitleMenuBar: function () {
        this.domElement = dom.createNode('thead');
        this.headerRow = dom.createNode('tr');
        this.titleCell = dom.createNode('th');
        this.titleCell.setAttribute('style','text-align: left; padding-left: 1.2em;');
        this.headerRow.appendChild(this.titleCell);
        this.menuCell = dom.createNode('td');
        this.menuCell.setAttribute('style', 'text-align: right; padding-rignt: 3px');
        this.headerRow.appendChild(this.menuCell);
        this.domElement.appendChild(this.headerRow);
    },
    /**
     * 
     * @returns {element.FormRow}
     */
    FormRow: function () {
        this.domElement = dom.createNode('tr');
    },
    /**
     * 
     * @param {String} header
     * @returns {element.FormHeader}
     */
    FormHeader: function (header) {
        this.domElement = dom.createNode('th', header);
    },
    /**
     * 
     * @returns {element.FormCell}
     */
    FormCell: function () {
        this.domElement = dom.createNode('td');
    },
    /**
     * 
     * @returns {element.VoiceMenu}
     */
    VoiceMenu: function () {
        this.domElement = dom.createNode('td');
    },
    /**
     * 
     * @param {String} fieldName
     * @param {Function} action
     * @param {String} iconName
     * @returns {element.FileUploader}
     */
    FileUploader: function (fieldName, action, iconName) {
        this.iconName = iconName || 'page_white_get_A';
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
        this.fileChooserTrigger = new element.IconButton(this.iconName, 'Importera');
        this.fileChooserTrigger.addIconClickedAction(function () {
            $(fileChooserId).click();
        });
        this.domElement.appendChild(this.fileChooserTrigger.getElement());
    },
    /**
     * 
     * @param {String} name
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
     * @param {String} name
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
     * @param {String} name
     * @returns {element.TextField}
     */
    TextField: function (name) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('type', 'text');
        this.domElement.setAttribute('name', name);
    },
    /**
     * 
     * @param {String} name
     * @param {int} rows
     * @param {int} cols
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
     * @param {String} text
     * @param {String} targetId
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
     * @param {String} legend
     * @param {String} cssClass
     * @returns {element.Border}
     */
    Border: function (legend, cssClass) {
        this.domElement = dom.createNode('fieldset');
        this.setLegend(legend);
        this.setCssClass(cssClass);
    },
    /**
     * 
     * @param {String} imageName
     * @param {String} altText
     * @returns {element.IconButton}
     */
    IconButton: function (imageName, altText) {
        this.setImageUrl('layout/images/icons/16x16/' + imageName + '.png');
        this.domElement = dom.createNode('img');
        this.domElement.setAttribute('src', this.getImageUrl());
        this.domElement.setAttribute('width', '16');
        this.domElement.setAttribute('height', '16');
        this.setStyle('cursor:pointer;margin-right:3px;');
        if (altText) {
            this.domElement.setAttribute('alt', altText);
        }
    },
    /**
     * 
     * @param {String} name
     * @param {String} id
     * @returns {element.DataList}
     */
    DataList: function (name, id) {
        // Här kan det vara buggigt...
        this.domElement = dom.createNode('div');
        this.domElement.setAttribute('id', id + '-row');
        this.list = dom.createNode('input');
        this.list.setAttribute('name', name);
        this.list.setAttribute('id', id);
        this.list.setAttribute('list', id + '-datalist');
        this.domElement.appendChild(this.list);
        this.dataList = dom.createNode('datalist');
        this.dataList.setAttribute('id', id + '-datalist');
        this.select = dom.createNode('select');
        this.select.setAttribute('id', id + '-select');
        this.dataList.appendChild(this.select);
        this.domElement.appendChild(this.dataList);
    },
    /**
     * 
     * @param {String} name
     * @returns {element.HiddenField}
     */
    HiddenField: function (name) {
        this.domElement = dom.createNode('input');
        this.domElement.setAttribute('name', name);
        this.domElement.setAttribute('type', 'hidden');
    }
};

/**********  PROTOTYPE **********/
element.Spacer.prototype = {
    getElement: function() {
        return this.domElement;
    }
};
element.HiddenField.prototype = {
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    getValue: function () {
        return this.value;
    },
    setValue: function (value) {
        this.value = value !== null && value !== undefined ? value : '';
        this.domElement.setAttribute('value', this.value);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.DataList.prototype = {
    setValue: function (value) {
        this.value = value !== null && value !== undefined ? value : '';
        this.list.setAttribute('value', this.value);
    },
    /**
     * 
     * @param {JSOON} items "[{ 'name': 'namn', 'description','beskrivning'}]"
     * @returns {void}
     */
    setDataList: function (items) {
        this.items = items || null;
        if (this.items !== null) {
            this.clearDataList();
            for (var i = 0; i < this.items.length; i++) {
                var opt = dom.createNode('option');
                if (this.items[i].description !== null && this.items[i].description !== undefined) {
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
            this.list.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.list.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.list.setAttribute('title', tooltip);
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
    /**
     * 
     * @param {int} min
     * @returns {void}
     */
    setMin: function (min) {
        this.min = min || 0;
        this.domElement.setAttribute('min', this.min);
    },
    /**
     * 
     * @param {int} max
     * @returns {void}
     */
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.Form.prototype = {
    /**
     * 
     * @param {element.FormTitleMenuBar} menuTitleBar
     * @returns {void}
     */
    setTitleMenuBar: function (menuTitleBar) {
        this.table.appendChild(menuTitleBar);
    },
    addElement: function (childElement) {
        this.domElement.appendChild(childElement);
    },
    addBody: function (body) {
        this.table.appendChild(body);
    },
    addRow: function (formRow, body) {
        if (body !== undefined && body !== null) {
            body.appendChild(formRow);
        } else {
            this.table.appendChild(formRow);
        }
    },
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setEncodingType: function (enctype) {
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
    /**
     * 
     * @param {Function} action
     * @returns {void}
     */
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.FormTitleMenuBar.prototype = {
    setTitleColSpan: function (colSpan) {
        this.titleCell.setAttribute('colspan', colSpan);
    },
    setTitle: function (title) {
        this.titleCell.innerHTML = title || '';
    },
    setMenuColSpan: function (colSpan) {
        this.menuCell.setAttribute('colspan', colSpan);
    },
    addMenuElement: function (element) {
        this.menuCell.appendChild(element);
    },
    getElement: function() {
        return this.domElement;
    }
};
element.FormRow.prototype = {
    addElement: function (element) {
        this.domElement.appendChild(element);
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.FormHeader.prototype = {
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    setColSpan: function (colSpan) {
        this.colSpan = colSpan;
        if (this.colSpan !== undefined && this.colSpan !== null && parseInt(this.colSpan) !== NaN) {
            this.domElement.setAttribute('colspan', this.colSpan);
        }
    },
    setRowSpan: function (rowSpan) {
        this.rowSpan = rowSpan;
        if (this.rowSPan !== undefined && this.rowSpan !== null && parseInt(this.rowSpan) !== NaN) {
            this.domElement.setAttribute('rowspan', this.rowSpan);
        }
    },
    getElement: function () {
        return this.domElement;
    }
};
element.FormCell.prototype = {
    /**
     * 
     * @param {Element} element
     * @returns {void}
     */
    addElement: function (element) {
        this.domElement.appendChild(element);
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    /**
     * 
     * @param {int} colSpan
     * @returns {void}
     */
    setColSpan: function (colSpan) {
        this.colSpan = colSpan;
        if (this.colSpan !== undefined && this.colSpan !== null && parseInt(this.colSpan) !== NaN) {
            this.domElement.setAttribute('colspan', this.colSpan);
        }
    },
    /**
     * 
     * @param {int} rowSpan
     * @returns {void}
     */
    setRowSpan: function (rowSpan) {
        this.rowSpan = rowSpan;
        if (this.rowSPan !== undefined && this.rowSpan !== null && parseInt(this.rowSpan) !== NaN) {
            this.domElement.setAttribute('rowspan', this.rowSpan);
        }
    },
    getElement: function () {
        return this.domElement;
    }
};
element.VoiceMenu.prototype = {
    /**
     * 
     * @param {Element} element
     * @returns {void}
     */
    addElement: function (element) {
        this.domElement.appendChild(element);
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setColSpan: function (colSpan) {
        this.colSpan = colSpan;
        if (this.colSpan !== undefined && this.colSpan !== null && parseInt(this.colSpan) !== NaN) {
            this.domElement.setAttribute('colspan', this.colSpan);
        }
    },
    getElement: function () {
        return this.domElement;
    }
};
element.FileUploader.prototype = {
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
    },
    /**
     * 
     * @param {Function} action
     * @returns {void}
     */
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
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
    /**
     * 
     * @param {Function} action
     * @returns {void}
     */
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
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
    setValue: function (value) {
        this.value = value || null;
        if (this.value !== null) {
            this.domElement.value = this.value;
        }
    },
    getName: function () {
        return this.name;
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.Label.prototype = {
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.image.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
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
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    addChild: function (element) {
        this.domElement.appendChild(element);
    },
    getElement: function () {
        return this.domElement;
    }
};
element.IconLink.prototype = {
    setId: function (id) {
        this.id = id || null;
        if (this.id !== null) {
            this.domElement.setAttribute('id', this.id);
        }
    },
    setTarget: function (target) {
        this.domElement.setAttribute('target', target);
    },
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    setAccessKey: function(accesskey) {
        this.domElement.setAttribute('accesskey', accesskey);
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
    setCssClass: function (cssClass) {
        this.cssClass = cssClass || null;
        if (this.cssClass !== null) {
            this.domElement.setAttribute('class', this.cssClass);
        }
    },
    setStyle: function (style) {
        this.domElement.setAttribute('style', style);
    },
    setTooltip: function (tooltip) {
        this.domElement.setAttribute('title', tooltip);
    },
    setAccessKey: function(accesskey) {
        this.domElement.setAttribute('accesskey', accesskey);
    },
    /**
     * 
     * @param {Function} action
     * @returns {void}
     */
    addIconClickedAction: function (action) {
        this.domElement.addEventListener('click', action);
    },
    getElement: function () {
        return this.domElement;
    }
};
