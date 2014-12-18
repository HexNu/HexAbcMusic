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
            var isNew = jsonData === undefined || jsonData === null || jsonData === '';
            var method = isNew ? http.Method.POST : http.Method.PUT;
            var editorForm = element.Form('edit-form', 'editor', 'resources/abc', method, http.MediaType.MULTIPART_FORM_DATA);
            editorForm.appendChild(hex.editor.elements.titleRow(isNew ? '' : jsonData.title));
            editorForm.appendChild(hex.editor.elements.subheaderRow(isNew ? '' : jsonData.subheader));
            editorForm.appendChild(hex.editor.elements.composerRow(isNew ? '' : jsonData.composer));
            editorForm.appendChild(hex.editor.elements.originatorRow(isNew ? '' : jsonData.originator));
            editorForm.appendChild(hex.editor.elements.rythmRow(isNew ? '' : jsonData.rythm));
            editorForm.appendChild(hex.editor.elements.regionRow(isNew ? '' : jsonData.region));
            editorForm.appendChild(hex.editor.elements.historyRow(isNew ? '' : jsonData.history));
            editorForm.appendChild(hex.editor.elements.notesRow(isNew ? '' : jsonData.notes));
            editorForm.appendChild(hex.editor.elements.transcriberRow(isNew ? '' : jsonData.transcriber));
            editorForm.appendChild(hex.editor.elements.sourceRow(isNew ? '' : jsonData.source));
            editorForm.appendChild(hex.editor.elements.meterNoteLengthKeyRow(isNew ? '' : jsonData));
            var numberOfVoices = isNew ? 1 : jsonData.voices.length;
            for (var i = 0; i < numberOfVoices; i++) {
                if (isNew) {
                    editorForm.appendChild(hex.editor.elements.voicEditor(null, i).getElement());
                } else {
                    editorForm.appendChild(hex.editor.elements.voicEditor(jsonData.voices[i], i).getElement());
                }
            }
            $('editor-area').appendChild(editorForm);
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
            meterNoteLengthKeyRow: function (jsonData) {
                var result = dom.createNode('div');
                result.setAttribute('class', 'short-fields-row');
                var table = dom.createNode('table');
                var row = dom.createNode('tr');
//                var meterValue = jsonData.meter !== undefined && jsonData.meter !== null ? jsonData.meter : '';
//                var defaultLengthValue = jsonData.unitNoteLength !== undefined && jsonData.unitNoteLength !== null ? jsonData.unitNoteLength : '';
                var keyValue = jsonData.key !== undefined && jsonData.key !== null ? jsonData.key.signature : '';
                var meterLabelContainer = dom.createNode('td');
                meterLabelContainer.setAttribute('class', 'label');
                var meterLabel = new element.Label('Taktart:', 'meter-field');
                meterLabelContainer.appendChild(meterLabel.getElement());
                var meterContainer = dom.createNode('td');
                var meterTextField = new element.TextField('meter');
                meterTextField.setId('meter-field');
                meterTextField.setCssClass('short-text-field');
                meterTextField.setValue(jsonData.meter);
                meterContainer.appendChild(meterTextField.getElement());
                var lengthLabelContainer = dom.createNode('td');
                lengthLabelContainer.setAttribute('class', 'label');
                var defaultLengthLabel = new element.Label('Notlängd:', 'unit-note-length-field');
                lengthLabelContainer.appendChild(defaultLengthLabel.getElement());
                var lengthContainer = dom.createNode('td');
                var defaultLengthTextField = new element.TextField('unit-note-length');
                defaultLengthTextField.setId('unit-note-length');
                defaultLengthTextField.setCssClass('unit-note-length-field');
                defaultLengthTextField.setValue(jsonData.unitNoteLength);
                lengthContainer.appendChild(defaultLengthTextField.getElement());
                var keyLabelContainer = dom.createNode('td');
                keyLabelContainer.setAttribute('class', 'label');
                var keyLabel = new element.Label('Tonart:', 'key-field');
                keyLabelContainer.appendChild(keyLabel.getElement());
                var keyContainer = dom.createNode('td');
                var keyTextField = element.DataList('key-signature', 'key-field', 'short-text-field', keyValue);
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
            voiceIdAndIndexRow: function (jsonData, index) {
                var isNew = jsonData === null || jsonData === undefined;
                var result = dom.createNode('div');
                result.setAttribute('class', 'short-fields-row');
                var voiceNumber = index + 1;
                var voiceIdValue = !isNew && jsonData.voiceId !== undefined && jsonData.voiceId !== null ? jsonData.voiceId : 'V' + voiceNumber;
                var voiceIndexValue = !isNew && jsonData.voiceIndex !== undefined && jsonData.voiceIndex !== null ? jsonData.voiceIndex : index;
                var voiceIdContainer = dom.createNode('div');
                var voiceIdLabel = new element.Label('StämmId:', 'voice-id-field-' + index);
                voiceIdContainer.appendChild(voiceIdLabel.getElement());
                voiceIdContainer.setAttribute('class', 'left-form-container');
                var voiceIdTextField = new element.TextField('voice-id');
                voiceIdTextField.setId('voice-id-field-' + index);
                voiceIdTextField.setCssClass('short-text-field');
                voiceIdTextField.setValue(voiceIdValue);
                voiceIdContainer.appendChild(voiceIdTextField.getElement());
                var indexContainer = dom.createNode('div');
                indexContainer.setAttribute('class', 'right-form-container');
                var voiceIndexLabel = new element.Label('Index:', 'voice-index-field-' + index);
                indexContainer.appendChild(voiceIndexLabel.getElement());
                var voiceIndexNumberField = element.NumberChooserField('voice-index', 'voice-index-field-' + index, 'short-text-field', voiceIndexValue, 0);
//                var voiceIndexNumberField = element.NumberChooserField('voice-index');
                indexContainer.appendChild(voiceIndexNumberField);
                result.appendChild(voiceIdContainer);
                result.appendChild(indexContainer);
                return result;
            },
            voiceNamesRow: function (jsonData, index) {
                var isNew = jsonData === null || jsonData === undefined;
                var result = dom.createNode('div');
                result.setAttribute('class', 'short-fields-row');
                var voiceNameValue = !isNew && jsonData.name !== undefined && jsonData.name !== null ? jsonData.name : '';
                var voiceSubnameValue = !isNew && jsonData.subname !== undefined && jsonData.subname !== null ? jsonData.subname : '';
                var voiceNameContainer = dom.createNode('div');
                var voiceNameLabel = new element.Label('Namn:', 'voice-name-field-' + index);
                voiceNameContainer.appendChild(voiceNameLabel.getElement());
                voiceNameContainer.setAttribute('class', 'left-form-container');
                var voiceNameTextField = new element.TextField('voice-name');
                voiceNameTextField.setId('voice-name-field-' + index);
                voiceNameTextField.setCssClass('short-text-field');
                voiceNameTextField.setValue(voiceNameValue);
                voiceNameContainer.appendChild(voiceNameTextField.getElement());
                var voiceSubnameContainer = dom.createNode('div');
                voiceSubnameContainer.setAttribute('class', 'right-form-container');
                var voiceSubnameLabel = new element.Label('Kortnamn:', 'voice-subname-field-' + index);
                voiceSubnameContainer.appendChild(voiceSubnameLabel.getElement());
                var voiceSubnameNumberField = new element.TextField('voice-subname');
                voiceSubnameNumberField.setId('voice-subname-field-' + index);
                voiceSubnameNumberField.setCssClass('short-text-field');
                voiceSubnameNumberField.setValue(voiceSubnameValue);
                
                voiceSubnameContainer.appendChild(voiceSubnameNumberField.getElement());
                result.appendChild(voiceNameContainer);
                result.appendChild(voiceSubnameContainer);
                return result;
            },
            voicEditor: function (jsonVoiceData, index) {
                var voiceNumber = index + 1;
                var border = new element.Border('Stämma ' + voiceNumber, 'short-fields-row');
                border.setId('voice-editor');
                var voiceIndexRow = hex.editor.elements.voiceIdAndIndexRow(jsonVoiceData, index);
                border.addChild(voiceIndexRow);
                var voiceNamesRow = hex.editor.elements.voiceNamesRow(jsonVoiceData, index);
                border.addChild(voiceNamesRow);
                var voiceBody = jsonVoiceData === null ? '' : jsonVoiceData.body;
                var voiceBodyEditorArea = new element.TextArea('voice-body', 10, 105);
                voiceBodyEditorArea.setId('voice-body-field-' + index);
                voiceBodyEditorArea.setText(voiceBody);
                border.addChild(voiceBodyEditorArea.getElement());
                return border;
            }
        }
    },
    menu: {
        create: function () {
            $('menu-area').appendChild(hex.menu.elements.searchBox());
            $('menu-area').appendChild(hex.menu.elements.tuneListTrigger());
            $('menu-area').appendChild(hex.menu.elements.importTrigger());
            $('menu-area').appendChild(hex.menu.elements.exportTrigger().getElement());
            $('menu-area').appendChild(hex.menu.elements.addTuneTrigger().getElement());
            $('menu-area').appendChild(hex.menu.elements.searchTuneTrigger().getElement());
        },
        elements: {
            searchTuneTrigger: function () {
                return new element.IconButton('magnifier', null, null, 'Sök');
            },
            addTuneTrigger: function () {
                var addTuneTrigger = new element.IconButton('add', null, null, 'Ny låt');
                addTuneTrigger.setTooltip('Lägg till en låt');
                addTuneTrigger.addIconClickedAction(function () {
                    hex.actions.edit();
                });
                return addTuneTrigger;

            },
            exportTrigger: function () {
                var exportTrigger = new element.IconButton('document_export', null, null, 'Exportera');
                exportTrigger.setTooltip('Ladda ner alla låtar som en abc-fil till din dator.');
                exportTrigger.addIconClickedAction(function () {
                    hex.actions.downloadAll();
                });
                return exportTrigger;
            },
            importTrigger: function () {
                var result = dom.createNode('form');
                result.setAttribute('id', 'file-upload-form');
                result.setAttribute('action', 'resources/tunes/abc/upload');
                result.setAttribute('method', http.Method.POST);
                result.setAttribute('enctype', http.MediaType.MULTIPART_FORM_DATA);
                result.setAttribute('target', 'new-page');
                var fileChooser = dom.createNode('input');
                fileChooser.setAttribute('id', 'file-upload');
                fileChooser.setAttribute('type', 'file');
                fileChooser.setAttribute('name', 'file');
                fileChooser.addEventListener('change', function () {
                    $('file-upload-form').submit();
                });
                result.appendChild(fileChooser);
                var fileChooserTrigger = new element.IconButton('document_import', null, null, 'Importera');
                fileChooserTrigger.setTooltip('Ladda upp en abc-fil till servern.');
                fileChooserTrigger.addIconClickedAction(function () {
                    $('file-upload').click();
                });
                result.appendChild(fileChooserTrigger.getElement());
                return result;
            },
            searchBox: function () {
                var result = element.SearchField('titles', 'tune-title-list', 'search-box');
                return result;
            },
            tuneListTrigger: function (buttons) {
                var tuneListTrigger = new element.IconButton('directory_listing', null, null, 'Låtlista');
                tuneListTrigger.setTooltip('Visa eller uppdatara låtlistan.');
                tuneListTrigger.addIconClickedAction(function () {
                    hex.actions.list();
                });
                return tuneListTrigger.getElement();
            }
        }
    }

};
alert('Laddar låtar');
hex.actions.createMenu();
http.Get('resources/tunes/abc', hex.actions.generateList, http.Method.GET);
