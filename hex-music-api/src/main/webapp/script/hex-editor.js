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
            result.setAttribute('class', 'short-fields-row')
            var meterValue = jsonData.meter !== undefined && jsonData.meter !== null ? jsonData.meter : '';
            var defaultLengthValue = jsonData.unitNoteLength !== undefined && jsonData.unitNoteLength !== null ? jsonData.unitNoteLength : '';
            var meterContainer = dom.createNode('div');
            var meterLabel = form.Label('Taktart:', 'meter-field');
            meterContainer.appendChild(meterLabel);
            meterContainer.setAttribute('class','left-form-container');
            var meterTextField = form.TextField('meter', 'meter-field', 'short-text-field', meterValue);
            meterContainer.appendChild(meterTextField);
            var lengthContainer = dom.createNode('div');
            lengthContainer.setAttribute('class','right-form-container');
            var defaultLengthLabel = form.Label('Notlängd:', 'unit-note-length-field');
            lengthContainer.appendChild(defaultLengthLabel);
            var defaultLengthTextField = form.TextField('unit-note-length', 'unit-note-length-field', 'short-text-field', defaultLengthValue);
            lengthContainer.appendChild(defaultLengthTextField);
            result.appendChild(lengthContainer)
            result.appendChild(meterContainer);
            return result;
        }
    }
};