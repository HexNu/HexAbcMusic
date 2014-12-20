hex = {
    lists: {
        composers: null,
        keys: null,
        meters: [
            {'name': '2/4'},
            {'name': '3/4'},
            {'name': '4/4'},
            {'name': 'C'},
            {'name': 'C|'},
            {'name': '5/4'},
            {'name': '6/4'},
            {'name': '7/4'},
            {'name': '8/4'},
            {'name': '9/4'},
            {'name': '1/4'},
            {'name': '5/8'},
            {'name': '6/8'},
            {'name': '7/8'},
            {'name': '8/8'},
            {'name': '9/8'},
            {'name': '10/8'},
            {'name': '11/8'},
            {'name': '12/8'},
            {'name': '13/8'},
            {'name': '14/8'},
            {'name': '15/8'},
            {'name': '16/8'},
            {'name': '2/2'},
            {'name': '3/2'},
            {'name': '4/2'},
            {'name': 'none'}
        ],
        noteLengths: [
            {'name': '1/8'},
            {'name': '1/4'},
            {'name': '1/2'},
            {'name': '1/1'},
            {'name': '1/16'},
            {'name': '1/32'},
            {'name': '1/64'},
            {'name': '1/128'}
        ],
        originators: null,
        regions: null,
        rythms: null
    },
    actions: {
        populateAllLists: function () {
            http.Get('resources/tunes/abc', hex.actions.generateList, http.Method.GET);
            hex.actions.getAutoCompleteData();
        },
        getAutoCompleteData: function() {
            http.Get('resources/tunes/abc/composers', hex.actions.generateComposerList, http.Method.GET);
            http.Get('resources/tunes/abc/keys', hex.actions.generateKeyList, http.Method.GET);
            http.Get('resources/tunes/abc/originators', hex.actions.generateOriginatorList, http.Method.GET);
            http.Get('resources/tunes/abc/rythms', hex.actions.generateRythmList, http.Method.GET);
            http.Get('resources/tunes/abc/regions', hex.actions.generateRegionList, http.Method.GET);
        },
        clearEditorArea: function () {
            dom.clearNode('editor-area');
        },
        clearMenuArea: function () {
            dom.clearNode('menu-area');
        },
        clearList: function () {
            dom.clearNode('list');
        },
        generateComposerList: function (jsonData) {
            hex.lists.composers = jsonData;
        },
        generateOriginatorList: function (jsonData) {
            hex.lists.originators = jsonData;
        },
        generateRegionList: function (jsonData) {
            hex.lists.regions = jsonData;
        },
        generateRythmList: function (jsonData) {
            hex.lists.rythms = jsonData;
        },
        generateKeyList: function (jsonData) {
            hex.lists.keys = jsonData;
        },
        generateList: function (jsonData) {
            var tunes = jsonData.tunes;
            dom.setText(title, titleText + ' - Låtlista - ' + tunes.length + ' låtar');
            $('list').appendChild(dom.createNode('h3', 'Låtlista'));
            for (var i = 0; i < tunes.length; i++) {
                var itemNode = dom.createNode('dt', tunes[i].title + ' - ' + tunes[i].keySignature);
                $('list').appendChild(itemNode);
                var descriptionNode = dom.createNode('dd');
                for (var j = 0; j < tunes[i].links.length; j++) {
                    if (j > 0) {
                        dom.appendText(descriptionNode, ' | ');
                    }
                    var linkNode = dom.createNode('a', tunes[i].links[j].rel);
                    if (tunes[i].links[j].rel === 'edit') {
                        linkNode.setAttribute('href', 'javascript:void(0)');
                        linkNode.setAttribute('link', tunes[i].links[j].uri);
                        linkNode.addEventListener('click', function (event) {
                            hex.actions.edit(event.target.getAttribute('link'));
                        });
                    } else {
                        linkNode.setAttribute('href', tunes[i].links[j].uri);
                    }
                    linkNode.setAttribute('class', 'list-link');
                    descriptionNode.appendChild(linkNode);
                }
                $('list').appendChild(descriptionNode);
            }
        },
        createMenu: function (buttons) {
            dom.clearNode('menu-area');
            hex.menu.create(buttons);
        },
        createTuneEditForm: function (jsonData) {
            hex.actions.clearEditorArea();
            hex.editor.create(jsonData);
        },
        downloadAll: function () {
            location.href = 'resources/tunes/abc/download';
        },
        edit: function (url) {
            if (url !== undefined && url !== null) {
                http.Get(url, hex.actions.createTuneEditForm);
            } else {
                hex.actions.createTuneEditForm(null);
            }
        },
        listTunes: function () {
            hex.actions.clearList();
            http.Get('resources/tunes/abc', hex.actions.generateList);
        }
    },
    editor: {
        create: function (jsonData) {
            hex.actions.populateAllLists();
            var tuneDto = new dto.in.Tune(jsonData);
            var method = tuneDto.isNew() ? http.Method.POST : http.Method.PUT;
            var editorForm = new element.Form('edit-form');
            editorForm.setAction('resources/abc');
            editorForm.setCssClass('editor');
            editorForm.setMethod(method);
            editorForm.setEncodignType(http.MediaType.MULTIPART_FORM_DATA);
            editorForm.addElement(hex.editor.elements.titleRow(tuneDto.getTitle()));
            editorForm.addElement(hex.editor.elements.subheaderRow(tuneDto.getSubheader()));
            editorForm.addElement(hex.editor.elements.composerRow(tuneDto.getComposer()));
            editorForm.addElement(hex.editor.elements.originatorRow(tuneDto.getOriginator()));
            editorForm.addElement(hex.editor.elements.rythmRow(tuneDto.getRythm()));
            editorForm.addElement(hex.editor.elements.regionRow(tuneDto.getRegion()));
            editorForm.addElement(hex.editor.elements.historyRow(tuneDto.getHistory()));
            editorForm.addElement(hex.editor.elements.notesRow(tuneDto.getNotes()));
            editorForm.addElement(hex.editor.elements.transcriberRow(tuneDto.getTranscriber()));
            editorForm.addElement(hex.editor.elements.sourceRow(tuneDto.getSource()));
            editorForm.addElement(hex.editor.elements.meterNoteLengthKeyRow(tuneDto));
            for (var i = 0; i < tuneDto.getNumberOfVoices(); i++) {
                editorForm.addElement(hex.editor.elements.voicEditor(tuneDto.getVoice(i)));
            }
            $('editor-area').appendChild(editorForm.getElement());
        },
        elements: {
            longTextFieldRow: function (value, text, tuneField) {
                var result = dom.createNode('div');
                var label = new element.Label(text + ':', tuneField + '-field');
                var textField = new element.TextField(tuneField);
                textField.setId(tuneField + '-field');
                textField.setCssClass('long-text-field');
                textField.setValue(value);
                result.appendChild(label.getElement());
                result.appendChild(textField.getElement());
                return result;
            },
            longDataListRow: function (value, text, tuneField, items) {
                var result = dom.createNode('div');
                var label = new element.Label(text + ':', tuneField + '-field');
                var dataListField = new element.DataList(tuneField, tuneField + '-field');
                dataListField.setCssClass('long-text-field');
                dataListField.setValue(value);
                dataListField.setDataList(items);
                result.appendChild(label.getElement());
                result.appendChild(dataListField.getElement());
                return result;
            },
            titleRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Title', 'title', true);
            },
            subheaderRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Undertitel', 'subheader');
            },
            composerRow: function (value) {
                return hex.editor.elements.longDataListRow(value, 'Kompositör', 'composer', hex.lists.composers);
            },
            originatorRow: function (value) {
                return hex.editor.elements.longDataListRow(value, 'Efter', 'originator', hex.lists.originators);
            },
            rythmRow: function (value) {
                return hex.editor.elements.longDataListRow(value, 'Låttyp', 'rythm', hex.lists.rythms);
            },
            regionRow: function (value) {
                return hex.editor.elements.longDataListRow(value, 'Plats', 'region', hex.lists.regions);
            },
            historyRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Historik', 'history');
            },
            notesRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Anteckningar', 'notes');
            },
            transcriberRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'ABC-kodning', 'transcriber');
            },
            sourceRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Källa', 'source');
            },
            meterNoteLengthKeyRow: function (tuneDto) {
                var result = dom.createNode('div');
                result.setAttribute('class', 'short-fields-row');
                var table = dom.createNode('table');
                var row = dom.createNode('tr');
                var meterLabelContainer = dom.createNode('td');
                meterLabelContainer.setAttribute('class', 'label');
                var meterLabel = new element.Label('Taktart:', 'meter-field');
                meterLabelContainer.appendChild(meterLabel.getElement());
                var meterContainer = dom.createNode('td');
                var meterTextField = new element.DataList('meter', 'meter-field');
                meterTextField.setCssClass('short-text-field');
                meterTextField.setValue(tuneDto.getMeter());
                meterContainer.appendChild(meterTextField.getElement());
                meterTextField.setDataList(hex.lists.meters);
                var lengthLabelContainer = dom.createNode('td');
                lengthLabelContainer.setAttribute('class', 'label');
                var defaultLengthLabel = new element.Label('Notlängd:', 'unit-note-length-field');
                lengthLabelContainer.appendChild(defaultLengthLabel.getElement());
                var lengthContainer = dom.createNode('td');
                var defaultLengthTextField = new element.DataList('unit-note-length', 'unit-note-length-field');
                defaultLengthTextField.setCssClass('short-text-field');
                defaultLengthTextField.setValue(tuneDto.getUnitNoteLength());
                defaultLengthTextField.setDataList(hex.lists.noteLengths);
                lengthContainer.appendChild(defaultLengthTextField.getElement());
                var keyLabelContainer = dom.createNode('td');
                keyLabelContainer.setAttribute('class', 'label');
                var keyLabel = new element.Label('Tonart:', 'key-field');
                keyLabelContainer.appendChild(keyLabel.getElement());
                var keyContainer = dom.createNode('td');
                var keyTextField = new element.DataList('key-signature', 'key-field');
                keyTextField.setCssClass('short-text-field');
                keyTextField.setValue(tuneDto.getKey().getSignature());
                keyTextField.setDataList(hex.lists.keys);
                keyContainer.appendChild(keyTextField.getElement());
                row.appendChild(meterLabelContainer);
                row.appendChild(meterContainer);
                row.appendChild(lengthLabelContainer);
                row.appendChild(lengthContainer);
                row.appendChild(keyLabelContainer);
                row.appendChild(keyContainer);
                table.appendChild(row);
                result.appendChild(table);
                return result;
            },
            voiceIdAndIndexRow: function (voiceDto) {
                var result = dom.createNode('div');
                var voiceNumber = voiceDto.getIndex() + 1;
                result.setAttribute('class', 'short-fields-row');
                var voiceIdContainer = dom.createNode('div');
                var voiceIdLabel = new element.Label('StämmId:', 'voice-id-field-' + voiceNumber);
                voiceIdContainer.appendChild(voiceIdLabel.getElement());
                voiceIdContainer.setAttribute('class', 'left-form-container');
                var voiceIdTextField = new element.TextField('voice-id');
                voiceIdTextField.setId('voice-id-field-' + voiceDto.getIndex());
                voiceIdTextField.setCssClass('short-text-field');
                voiceIdTextField.setValue(voiceDto.getVoiceId());
                voiceIdContainer.appendChild(voiceIdTextField.getElement());
                var indexContainer = dom.createNode('div');
                indexContainer.setAttribute('class', 'right-form-container');
                var voiceIndexLabel = new element.Label('Index:', 'voice-index-field-' + voiceDto.getIndex());
                indexContainer.appendChild(voiceIndexLabel.getElement());
                var voiceIndexNumberField = new element.NumberChooserField('voice-index-' + voiceDto.getIndex());
                voiceIndexNumberField.setCssClass('short-text-field');
                voiceIndexNumberField.setId('voice-index-field-' + voiceDto.getIndex());
                voiceIndexNumberField.setValue(parseInt(voiceDto.getIndex()));
                voiceIndexNumberField.setMin(0);
                indexContainer.appendChild(voiceIndexNumberField.getElement());
                result.appendChild(voiceIdContainer);
                result.appendChild(indexContainer);
                return result;
            },
            voiceNamesRow: function (voiceDto) {
                var result = dom.createNode('div');
                result.setAttribute('class', 'short-fields-row');
                var voiceNameContainer = dom.createNode('div');
                var voiceNameLabel = new element.Label('Namn:', 'voice-name-field-' + voiceDto.getIndex());
                voiceNameContainer.appendChild(voiceNameLabel.getElement());
                voiceNameContainer.setAttribute('class', 'left-form-container');
                var voiceNameTextField = new element.TextField('voice-name');
                voiceNameTextField.setId('voice-name-field-' + voiceDto.getIndex());
                voiceNameTextField.setCssClass('short-text-field');
                voiceNameTextField.setValue(voiceDto.getName());
                voiceNameContainer.appendChild(voiceNameTextField.getElement());
                var voiceSubnameContainer = dom.createNode('div');
                voiceSubnameContainer.setAttribute('class', 'right-form-container');
                var voiceSubnameLabel = new element.Label('Kortnamn:', 'voice-subname-field-' + voiceDto.getIndex());
                voiceSubnameContainer.appendChild(voiceSubnameLabel.getElement());
                var voiceSubnameNumberField = new element.TextField('voice-subname');
                voiceSubnameNumberField.setId('voice-subname-field-' + voiceDto.getIndex());
                voiceSubnameNumberField.setCssClass('short-text-field');
                voiceSubnameNumberField.setValue(voiceDto.getSubname());
                voiceSubnameContainer.appendChild(voiceSubnameNumberField.getElement());
                result.appendChild(voiceNameContainer);
                result.appendChild(voiceSubnameContainer);
                return result;
            },
            voicEditor: function (voiceDto) {
                var voiceNumber = voiceDto.getIndex() + 1;
                var border = new element.Border('Stämma ' + voiceNumber, 'short-fields-row');
                border.setId('voice-editor');
                var voiceIndexRow = hex.editor.elements.voiceIdAndIndexRow(voiceDto);
                border.addChild(voiceIndexRow);
                var voiceNamesRow = hex.editor.elements.voiceNamesRow(voiceDto);
                border.addChild(voiceNamesRow);
                var voiceBodyEditorArea = new element.TextArea('voice-body', 10, 105);
                voiceBodyEditorArea.setId('voice-body-field-' + voiceDto.getIndex());
                voiceBodyEditorArea.setText(voiceDto.getBody());
                border.addChild(voiceBodyEditorArea.getElement());
                return border.getElement();
            }
        }
    },
    menu: {
        add: function (element) {
            $('menu-area').appendChild(element);
        },
        create: function () {
            this.addSearchBox();
            this.addTuneListTrigger();
            this.addExportTrigger();
            this.addImportTrigger();
            this.addNewTunewButton();
        },
        addSearchBox: function () {
            var searchField = new element.SearchField('titles');
            searchField.setId('tune-title-list');
            searchField.setCssClass('search-box');
            var searchTuneButton = new element.IconButton('magnifier', 'Sök');
            this.add(searchField.getElement());
            this.add(searchTuneButton.getElement());
        },
        addTuneListTrigger: function () {
            var tuneListTrigger = new element.IconButton('directory_listing', 'Låtlista');
            tuneListTrigger.setTooltip('Visa eller uppdatara låtlistan.');
            tuneListTrigger.addIconClickedAction(function () {
                hex.actions.listTunes();
            });
            this.add(tuneListTrigger.getElement());
        },
        addExportTrigger: function () {
            var exportTrigger = new element.IconButton('document_export', 'Exportera');
            exportTrigger.setTooltip('Ladda ner alla låtar som en abc-fil till din dator.');
            exportTrigger.addIconClickedAction(function () {
                hex.actions.downloadAll();
            });
            this.add(exportTrigger.getElement());
        },
        addImportTrigger: function () {
            var importTrigger = new element.FileUploader('file', 'resources/tunes/abc/upload');
            importTrigger.setTooltip('Ladda upp en abc-fil till servern.');
            this.add(importTrigger.getElement());
        },
        addNewTunewButton: function () {
            var addTuneTrigger = new element.IconButton('add', 'Ny låt');
            addTuneTrigger.setTooltip('Lägg till en låt');
            addTuneTrigger.addIconClickedAction(function () {
                hex.actions.edit();
            });
            this.add(addTuneTrigger.getElement());
        }
    }
};
alert('Välkommen');
hex.actions.createMenu();
hex.actions.populateAllLists();
