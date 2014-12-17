hex.editor = {
    create: function (jsonData) {
        var isNew = jsonData.id !== undefined && jsonData !== null && jsonData !== '';
        var method = isNew ? http.Method.PUT : http.Method.POST;
        var editForm = form.Form('edit-form', 'editor', 'resources/abc', method, http.MediaType.MULTIPART_FORM_DATA);
        editForm.appendChild(hex.editor.fields.titleRow(jsonData.title));
        editForm.appendChild(hex.editor.fields.subheaderRow(jsonData.subheader));
        editForm.appendChild(hex.editor.fields.composerRow(jsonData.composer));
        editForm.appendChild(hex.editor.fields.originatorRow(jsonData.originator));
        editForm.appendChild(hex.editor.fields.rythmRow(jsonData.rythm));
        editForm.appendChild(hex.editor.fields.regionRow(jsonData.region));
        editForm.appendChild(hex.editor.fields.historyRow(jsonData.history));
        editForm.appendChild(hex.editor.fields.notesRow(jsonData.notes));
        editForm.appendChild(hex.editor.fields.transcriberRow(jsonData.transcriber));
        editForm.appendChild(hex.editor.fields.sourceRow(jsonData.source));
        editForm.appendChild(hex.editor.fields.noteLengthRow(jsonData));

        for (var i = 0; i < jsonData.voices.length; i++) {
            editForm.appendChild(hex.editor.fields.voicEditor(jsonData.voices[i], i));
        }
        return editForm;
    },
    fields: {
        longTextFieldRow: function (value, text, tuneField, mandatory) {
            var fieldValue = value !== undefined && value !== null ? value : '';
            var result = dom.createNode('div');
            var label = form.Label(text + ':', tuneField + '-field');
            result.appendChild(label);
            var textField = form.TextField(tuneField, tuneField + '-field', 'long-text-field', fieldValue);
            result.appendChild(textField);
            if (mandatory !== undefined && mandatory !== null && mandatory) {
                result.appendChild(dom.createNode('sub', ' *'));
                textField.setAttribute('required', '');
            }
            return result;
        },
        titleRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Title', 'title', true);
        },
        subheaderRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Undertitel', 'subheader');
        },
        composerRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Kompositör', 'composer');
        },
        originatorRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Efter', 'originator');
        },
        rythmRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Låttyp', 'rythm');
        },
        regionRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Plats', 'region');
        },
        historyRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Historik', 'history');
        },
        notesRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Anteckningar', 'notes');
        },
        transcriberRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'ABC-kodning', 'transcriber');
        },
        sourceRow: function (value) {
            return hex.editor.fields.longTextFieldRow(value, 'Källa', 'source');
        },
        noteLengthRow: function (jsonData) {
            var result = dom.createNode('div');
            result.setAttribute('class', 'short-fields-row');
            var meterValue = jsonData.meter !== undefined && jsonData.meter !== null ? jsonData.meter : '';
            var defaultLengthValue = jsonData.unitNoteLength !== undefined && jsonData.unitNoteLength !== null ? jsonData.unitNoteLength : '';
            var meterContainer = dom.createNode('div');
            var meterLabel = form.Label('Taktart:', 'meter-field');
            meterContainer.appendChild(meterLabel);
            meterContainer.setAttribute('class', 'left-form-container');
            var meterTextField = form.TextField('meter', 'meter-field', 'short-text-field', meterValue);
            meterContainer.appendChild(meterTextField);
            var lengthContainer = dom.createNode('div');
            lengthContainer.setAttribute('class', 'right-form-container');
            var defaultLengthLabel = form.Label('Notlängd:', 'unit-note-length-field');
            lengthContainer.appendChild(defaultLengthLabel);
            var defaultLengthTextField = form.TextField('unit-note-length', 'unit-note-length-field', 'short-text-field', defaultLengthValue);
            lengthContainer.appendChild(defaultLengthTextField);
            result.appendChild(meterContainer);
            result.appendChild(lengthContainer);
            return result;
        },
        voiceIdAndIndexRow: function (jsonData, index) {
            var result = dom.createNode('div');
            result.setAttribute('class', 'short-fields-row');
            var voiceNumber = index + 1;
            var voiceIdValue = jsonData.voiceId !== undefined && jsonData.voiceId !== null ? jsonData.voiceId : 'V' + voiceNumber;
            var voiceIndexValue = jsonData.voiceIndex !== undefined && jsonData.voiceIndex !== null ? jsonData.voiceIndex : index;
            var voiceIdContainer = dom.createNode('div');
            var voiceIdLabel = form.Label('StämmId:', 'voice-id-field-' + index);
            voiceIdContainer.appendChild(voiceIdLabel);
            voiceIdContainer.setAttribute('class', 'left-form-container');
            var voiceIdTextField = form.TextField('voice-id', 'voice-id-field-' + index, 'short-text-field', voiceIdValue);
            voiceIdContainer.appendChild(voiceIdTextField);
            var indexContainer = dom.createNode('div');
            indexContainer.setAttribute('class', 'right-form-container');
            var voiceIndexLabel = form.Label('Index:', 'voice-index-field-' + index);
            indexContainer.appendChild(voiceIndexLabel);
            var voiceIndexNumberField = form.NumberChooserField('voice-index', 'voice-index-field-' + index, 'short-text-field', voiceIndexValue, 0);
            indexContainer.appendChild(voiceIndexNumberField);
            result.appendChild(voiceIdContainer);
            result.appendChild(indexContainer);
            return result;
        },
        voiceNamesRow: function (jsonData, index) {
            var result = dom.createNode('div');
            result.setAttribute('class', 'short-fields-row');
            var voiceNameValue = jsonData.name !== undefined && jsonData.name !== null ? jsonData.name : '';
            var voiceSubnameValue = jsonData.subname !== undefined && jsonData.subname !== null ? jsonData.subname : '';
            var voiceNameContainer = dom.createNode('div');
            var voiceNameLabel = form.Label('Namn:', 'voice-name-field-' + index);
            voiceNameContainer.appendChild(voiceNameLabel);
            voiceNameContainer.setAttribute('class', 'left-form-container');
            var voiceNameTextField = form.TextField('voice-name', 'voice-name-field-' + index, 'short-text-field', voiceNameValue);
            voiceNameContainer.appendChild(voiceNameTextField);
            var voiceSubnameContainer = dom.createNode('div');
            voiceSubnameContainer.setAttribute('class', 'right-form-container');
            var voiceSubnameLabel = form.Label('Kortnamn:', 'voice-subname-field-' + index);
            voiceSubnameContainer.appendChild(voiceSubnameLabel);
            var voiceSubnameNumberField = form.TextField('voice-subname', 'voice-subname-field-' + index, 'short-text-field', voiceSubnameValue);
            voiceSubnameContainer.appendChild(voiceSubnameNumberField);
            result.appendChild(voiceNameContainer);
            result.appendChild(voiceSubnameContainer);
            return result;
        },
        voicEditor: function (jsonVoiceData, index) {
            var voiceNumber = index + 1;
            var result = form.Border('Stämma ' + voiceNumber);
            result.setAttribute('class', 'short-fields-row');
            var voiceIndexRow = hex.editor.fields.voiceIdAndIndexRow(jsonVoiceData, index);
            result.appendChild(voiceIndexRow);
            var voiceNamesRow = hex.editor.fields.voiceNamesRow(jsonVoiceData, index);
            result.appendChild(voiceNamesRow);
            var voiceBodyEditorArea = form.TextArea('voice-body', 'voice-body-field-' + index, null, jsonVoiceData.body, 10, 105);
            result.appendChild(voiceBodyEditorArea);
            return result;
        }
    }
};