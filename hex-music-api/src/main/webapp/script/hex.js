hex = {
    actions: {
        clearEditorArea: function () {
            dom.clearNode('editor-area');
        },
        clearMenuArea: function () {
            dom.clearNode('menu-area');
        },
//        clearAutoCompleteList: function () {
//            dom.clearNode('tune-title-list');
//        },
        clearList: function () {
            dom.clearNode('list');
        },
//        populateAutoComplete: function (jsonData) {
//            hex.actions.clearAutoCompleteList();
//            var tunes = jsonData.tunes;
//            var list = $('tune-title-list');
//            for (var i = 0; i < tunes.length; i++) {
//                var option = dom.createNode('option');
//                option.jsonData = tunes[i];
//                var optLabel = tunes[i].title;
//                if (tunes[i].subheader !== null && tunes[i].subheader !== undefined && tunes[i].subheader !== "") {
//                    optLabel += " - " + tunes[i].subheader;
//                }
//                if (tunes[i].rythm !== null && tunes[i].rythm !== undefined && tunes[i].rythm !== "") {
//                    optLabel += " - " + tunes[i].rythm;
//                }
//                if (tunes[i].originator !== null && tunes[i].originator !== undefined && tunes[i].originator !== "") {
//                    optLabel += " - " + tunes[i].originator;
//                }
//                if (tunes[i].key !== null && tunes[i].key !== undefined && tunes[i].key !== "") {
//                    optLabel += " - " + tunes[i].key;
//                }
//                if (tunes[i].region !== null && tunes[i].region !== undefined && tunes[i].region !== "") {
//                    optLabel += " - " + tunes[i].region;
//                }
//                if (tunes[i].composer !== null && tunes[i].composer !== undefined && tunes[i].composer !== "") {
//                    optLabel += " - " + tunes[i].composer;
//                }
//                option.setAttribute('label', optLabel);
//                option.setAttribute('value', tunes[i].title);
//                list.appendChild(option);
//            }
//        },
        generateList: function (jsonData) {
            hex.actions.clearList();
            var tunes = jsonData.tunes;
            dom.setText(title, titleText + ' - Låtlista - ' + tunes.length + ' låtar');
            $('list').appendChild(dom.createNode('h3', 'Låtlista'));
            for (var i = 0; i < tunes.length; i++) {
                var itemNode = dom.createNode('dt', tunes[i].title);
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
                    editorForm.appendChild(hex.editor.elements.voicEditor(null, i));
                } else {
                    editorForm.appendChild(hex.editor.elements.voicEditor(jsonData.voices[i], i));
                }
            }
            $('editor-area').appendChild(editorForm);
        },
        elements: {
            longTextFieldRow: function (value, text, tuneField) {
                var fieldValue = value !== undefined && value !== null ? value : '';
                var result = dom.createNode('div');
                var label = element.Label(text + ':', tuneField + '-field');
                result.appendChild(label);
                var textField = element.TextField(tuneField, tuneField + '-field', 'long-text-field', fieldValue);
                result.appendChild(textField);
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
//                table.setAttribute('border','0');
//                table.setAttribute('cellspacing', '0');
//                table.setAttribute('cellpadding', '0');
//                table.setAttribute('rowspacing', '0');
                var row = dom.createNode('tr');
                var meterValue = jsonData.meter !== undefined && jsonData.meter !== null ? jsonData.meter : '';
                var defaultLengthValue = jsonData.unitNoteLength !== undefined && jsonData.unitNoteLength !== null ? jsonData.unitNoteLength : '';
                var keyValue = jsonData.key !== undefined && jsonData.key !== null ? jsonData.key.signature : '';
                var meterLabelContainer = dom.createNode('td');
                meterLabelContainer.setAttribute('class', 'label');
                var meterLabel = element.Label('Taktart:', 'meter-field');
                meterLabelContainer.appendChild(meterLabel);
                var meterContainer = dom.createNode('td');
                var meterTextField = element.TextField('meter', 'meter-field', 'short-text-field', meterValue);
                meterContainer.appendChild(meterTextField);
                var lengthLabelContainer = dom.createNode('td');
                lengthLabelContainer.setAttribute('class', 'label');
                var defaultLengthLabel = element.Label('Notlängd:', 'unit-note-length-field');
                lengthLabelContainer.appendChild(defaultLengthLabel);
                var lengthContainer = dom.createNode('td');
                var defaultLengthTextField = element.TextField('unit-note-length', 'unit-note-length-field', 'short-text-field', defaultLengthValue);
                lengthContainer.appendChild(defaultLengthTextField);
                var keyLabelContainer = dom.createNode('td');
                keyLabelContainer.setAttribute('class', 'label');
                var keyLabel = element.Label('Tonart:', 'key-field');
                keyLabelContainer.appendChild(keyLabel);
                var keyContainer = dom.createNode('td');
                var keyTextField = element.TextField('key', 'key-field', 'short-text-field', keyValue);
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
                var voiceIdLabel = element.Label('StämmId:', 'voice-id-field-' + index);
                voiceIdContainer.appendChild(voiceIdLabel);
                voiceIdContainer.setAttribute('class', 'left-form-container');
                var voiceIdTextField = element.TextField('voice-id', 'voice-id-field-' + index, 'short-text-field', voiceIdValue);
                voiceIdContainer.appendChild(voiceIdTextField);
                var indexContainer = dom.createNode('div');
                indexContainer.setAttribute('class', 'right-form-container');
                var voiceIndexLabel = element.Label('Index:', 'voice-index-field-' + index);
                indexContainer.appendChild(voiceIndexLabel);
                var voiceIndexNumberField = element.NumberChooserField('voice-index', 'voice-index-field-' + index, 'short-text-field', voiceIndexValue, 0);
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
                var voiceNameLabel = element.Label('Namn:', 'voice-name-field-' + index);
                voiceNameContainer.appendChild(voiceNameLabel);
                voiceNameContainer.setAttribute('class', 'left-form-container');
                var voiceNameTextField = element.TextField('voice-name', 'voice-name-field-' + index, 'short-text-field', voiceNameValue);
                voiceNameContainer.appendChild(voiceNameTextField);
                var voiceSubnameContainer = dom.createNode('div');
                voiceSubnameContainer.setAttribute('class', 'right-form-container');
                var voiceSubnameLabel = element.Label('Kortnamn:', 'voice-subname-field-' + index);
                voiceSubnameContainer.appendChild(voiceSubnameLabel);
                var voiceSubnameNumberField = element.TextField('voice-subname', 'voice-subname-field-' + index, 'short-text-field', voiceSubnameValue);
                voiceSubnameContainer.appendChild(voiceSubnameNumberField);
                result.appendChild(voiceNameContainer);
                result.appendChild(voiceSubnameContainer);
                return result;
            },
            voicEditor: function (jsonVoiceData, index) {
                var voiceNumber = index + 1;
                var border = element.Border('Stämma ' + voiceNumber, "voice-editor");
                border.setAttribute('class', 'short-fields-row');
                var voiceIndexRow = hex.editor.elements.voiceIdAndIndexRow(jsonVoiceData, index);
                border.appendChild(voiceIndexRow);
                var voiceNamesRow = hex.editor.elements.voiceNamesRow(jsonVoiceData, index);
                border.appendChild(voiceNamesRow);
                var voiceBody = jsonVoiceData === null ? '' : jsonVoiceData.body;
                var voiceBodyEditorArea = element.TextArea('voice-body', 'voice-body-field-' + index, null, voiceBody, 10, 105);
                border.appendChild(voiceBodyEditorArea);
                return border;
            }
        }
    },
    menu: {
        create: function (buttons) {
//            $('menu-area').appendChild(hex.menu.elements.searchBox());
            $('menu-area').appendChild(hex.menu.elements.tuneListTrigger(buttons));
            $('menu-area').appendChild(hex.menu.elements.importTrigger(buttons));
            $('menu-area').appendChild(hex.menu.elements.exportTrigger(buttons));
            $('menu-area').appendChild(hex.menu.elements.addTuneTrigger(buttons));
        },
        elements: {
            addTuneTrigger: function (buttons) {
                var result = null;
                if (buttons) {
                    result = dom.createNode('button', 'Ny låt');
                    result.setAttribute('type', 'button');
                } else {
                    result = element.IconButton('add', null, null, 'Ny låt');
                }
                result.setAttribute('title', 'Lägg in en ny låt.');
                result.addEventListener('click', function () {
                    hex.actions.edit();
                });
                return result;

            },
            exportTrigger: function (buttons) {
                var result = null;
                if (buttons) {
                    result = dom.createNode('button', 'Exportera');
                    result.setAttribute('type', 'button');
                } else {
                    result = element.IconButton('document_export', null, null, 'Exportera');
                }
                result.setAttribute('title', 'Ladda ner alla låtar som en abc-fil till din dator.');
                result.addEventListener('click', function () {
                    hex.actions.downloadAll();
                });
                return result;
            },
            importTrigger: function (buttons) {
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
                var fileChooserTrigger = null;
                if (buttons) {
                    fileChooserTrigger = dom.createNode('button', 'Importera');
                    fileChooserTrigger.setAttribute('type', 'button');
                } else {
                    fileChooserTrigger = element.IconButton('document_import', null, null, 'Importera');
                }
                fileChooserTrigger.setAttribute('title', 'Ladda upp en abc-fil till servern.');
                fileChooserTrigger.addEventListener('click', function () {
                    $('file-upload').click();
                });
                result.appendChild(fileChooserTrigger);
                return result;
            },
//            searchBox: function () {
//                var result = element.DataList('titles', 'tune-title-list', 'search-box');
//                result.setAttribute('title', 'Sök på låttitel');
//                var searchButton = element.IconButton('application_form_edit', 'search-trigger', null, 'Sök');
//                searchButton.addEventListener('click', function () {
//                    alert('Sök efter \"' + $('tune-title-list-input').value + '\"');
//                });
//                result.appendChild(searchButton);
//                return result;
//            },
            tuneListTrigger: function (buttons) {
                var result = null;
                if (buttons) {
                    result = dom.createNode('button', 'Låtlista');
                    result.setAttribute('type', 'button');
                } else {
                    result = element.IconButton('directory_listing', null, null, 'Låtlista');
                }
                result.setAttribute('title', 'Visa eller uppdatara låtlistan.');
                result.addEventListener('click', function () {
                    hex.actions.list();
                });
                return result;
            }
        }
    }

};
alert('Laddar låtar');
hex.actions.createMenu();
http.Get('resources/tunes/abc', hex.actions.generateList);
//http.Get('resources/tunes/abc', hex.actions.populateAutoComplete);
