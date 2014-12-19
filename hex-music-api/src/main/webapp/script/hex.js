hex = {
    actions: {
        clearEditorArea: function () {
            dom.clearNode('editor-area');
        },
        clearMenuArea: function () {
            dom.clearNode('menu-area');
        },
        clearList: function () {
            dom.clearNode('list');
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
        list: function () {
            hex.actions.clearList();
            http.Get('resources/tunes/abc', hex.actions.generateList);
        }
    },
    editor: {
        create: function (jsonData) {
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
            titleRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Title', 'title', true);
            },
            subheaderRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Undertitel', 'subheader');
            },
            composerRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Kompositör', 'composer');
            },
            originatorRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Efter', 'originator');
            },
            rythmRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Låttyp', 'rythm');
            },
            regionRow: function (value) {
                return hex.editor.elements.longTextFieldRow(value, 'Plats', 'region');
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
                var meterTextField = new element.TextField('meter');
                meterTextField.setId('meter-field');
                meterTextField.setCssClass('short-text-field');
                meterTextField.setValue(tuneDto.getMeter());
                meterContainer.appendChild(meterTextField.getElement());
                var lengthLabelContainer = dom.createNode('td');
                lengthLabelContainer.setAttribute('class', 'label');
                var defaultLengthLabel = new element.Label('Notlängd:', 'unit-note-length-field');
                lengthLabelContainer.appendChild(defaultLengthLabel.getElement());
                var lengthContainer = dom.createNode('td');
                var defaultLengthTextField = new element.TextField('unit-note-length');
                defaultLengthTextField.setId('unit-note-length');
                defaultLengthTextField.setCssClass('unit-note-length-field');
                defaultLengthTextField.setValue(tuneDto.getUnitNoteLength());
                lengthContainer.appendChild(defaultLengthTextField.getElement());
                var keyLabelContainer = dom.createNode('td');
                keyLabelContainer.setAttribute('class', 'label');
                var keyLabel = new element.Label('Tonart:', 'key-field');
                keyLabelContainer.appendChild(keyLabel.getElement());
                var keyContainer = dom.createNode('td');
                var keyTextField = element.DataList('key-signature', 'key-field', 'short-text-field', tuneDto.getKey().getSignature());
                keyContainer.appendChild(keyTextField);
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
        create: function () {
            this.addSearchBox();
            this.addSearchTuneTrigger();
            this.addTuneListTrigger();
            this.addExportTrigger();
            this.addImportTrigger();
            this.addNewTunewButton();
        },
        addSearchBox: function () {
            var searchField = new element.SearchField('titles');
            searchField.setId('tune-title-list');
            searchField.setCssClass('search-box');
            $('menu-area').appendChild(searchField.getElement());
        },
        addSearchTuneTrigger: function () {
            var searchTuneButton = new element.IconButton('magnifier', 'Sök');
            $('menu-area').appendChild(searchTuneButton.getElement());
        },
        addTuneListTrigger: function () {
            var tuneListTrigger = new element.IconButton('directory_listing', null, null, 'Låtlista');
            tuneListTrigger.setTooltip('Visa eller uppdatara låtlistan.');
            tuneListTrigger.addIconClickedAction(function () {
                hex.actions.list();
            });
            $('menu-area').appendChild(tuneListTrigger.getElement());
        },
        addExportTrigger: function () {
            var exportTrigger = new element.IconButton('document_export', null, null, 'Exportera');
            exportTrigger.setTooltip('Ladda ner alla låtar som en abc-fil till din dator.');
            exportTrigger.addIconClickedAction(function () {
                hex.actions.downloadAll();
            });
            $('menu-area').appendChild(exportTrigger.getElement());
        },
        addImportTrigger: function () {
            var importTrigger = new element.FileUploader('file', 'resources/tunes/abc/upload');
            importTrigger.setTooltip('Ladda upp en abc-fil till servern.');
            $('menu-area').appendChild(importTrigger.getElement());
        },
        addNewTunewButton: function () {
            var addTuneTrigger = new element.IconButton('add', 'Ny låt');
            addTuneTrigger.setTooltip('Lägg till en låt');
            addTuneTrigger.addIconClickedAction(function () {
                hex.actions.edit();
            });
            $('menu-area').appendChild(addTuneTrigger.getElement());
        }
    }

};
alert('Laddar låtar');
hex.actions.createMenu();
http.Get('resources/tunes/abc', hex.actions.generateList, http.Method.GET);
