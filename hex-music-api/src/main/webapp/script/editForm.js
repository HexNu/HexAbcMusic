var TuneEditor = function (tune) {
    this.form = new element.Form('edit-form');
    this.idField = new element.HiddenField('id');
    this.idField.setValue(tune.id);
    this.form.addElement(this.idField.getElement());
    this.createHeader(tune.title, 6);
    this.createRow([{'label': 'Titel', 'id': 'title', 'value': tune.title}]);
    this.createRow([{'label': 'Undertitel', 'id': 'subheader', 'value': tune.subheader}]);
    this.createRow([{'label': 'Kompositör', 'id': 'composer', 'value': tune.composer}]);
    this.createRow([{'label': 'Källa', 'id': 'source', 'value': tune.source}]);
    this.createRow([{'label': 'Låttyp', 'id': 'rythm', 'value': tune.rythm}]);
    this.createRow([{'label': 'Ort', 'id': 'region', 'value': tune.region}]);
    this.createRow([{'label': 'Historik', 'id': 'history', 'value': tune.history}]);
    this.createRow([{'label': 'Anteckningar', 'id': 'notes', 'value': tune.notes}]);
    this.createRow([{'label': 'ABC-kodning', 'id': 'transcriber', 'value': tune.transcriber}]);
    this.createRow([{'label': 'Bibliografi', 'id': 'bibliography', 'value': tune.bibliography}]);
    this.createRow([{'label': 'Discografi', 'id': 'discography', 'value': tune.discography}]);
    this.createRow([{'label': 'URI', 'id': 'uri', 'value': tune.uri}]);
    this.createRow([{'label': 'Taktart', 'id': 'meter', 'value': tune.meter},
        {'label': 'Notlängd', 'id': 'unit-note-length', 'value': tune.unitNoteLength},
        {'label': 'Tempo', 'id': 'tempo', 'value': tune.tempo}]);
    this.createRow([{'label': 'Tonart', 'id': 'key', 'value': tune.key.signature},
        {'label': 'Klav', 'id': 'clef', 'value': tune.key.clef.type},
        {'label': 'Transponering', 'id': 'transpose', 'value': tune.key.clef.transpose}]);
    for (var i = 0; i < tune.voices.length; i++) {
        var voiceNumber = i + 1;
        this.createHeader('Stämma ' + voiceNumber, 6);
        this.createRow([{'label': 'Stämm-id', 'id': 'voiceId', 'value': tune.voices[i].voiceId},
            {'label': 'Klav', 'id': 'clef', 'value': tune.voices[i].clef.type},
            {'label': 'Transponering', 'id': 'transpose', 'value': tune.voices[i].clef.transpose}]);
        this.createRow([{'label': 'Namn', 'id': 'name', 'value': tune.voices[i].name},
            {'label': 'Kortnamn', 'id': 'subname', 'value': tune.voices[i].subname},
            {'label': 'Stämmindex', 'id': 'voiceIndex', 'value': tune.voices[i].voicIndex}]);
        this.createTextArea(tune.voices[i].body, 6);
    }

};
TuneEditor.prototype = {
    getFieldWidth: function (wide) {
        if (wide === undefined || wide === null) {
            wide = true;
        }
        return wide ? '100%' : '6em';
    },
    createHeader: function (header, colSpan) {
        this.row = new element.FormRow();
        this.header = new element.FormHeader(header);
        this.header.setColSpan(colSpan);
        this.header.setStyle('text-align: left; padding-left: 1.2em;');
        this.row.addElement(this.header.getElement());
        this.form.addRow(this.row.getElement());
    },
    createTextArea: function (body, colSpan) {
        this.row = new element.FormRow();
        this.cell = new element.FormCell();
        this.cell.setColSpan(colSpan);
        this.textArea = new element.TextArea('body', 10, 90);
        this.textArea.setStyle('font-family: monospace; font-size: 10px;');
        this.textArea.setText(body);
        this.cell.addElement(this.textArea.getElement());
        this.row.addElement(this.cell.getElement());
        this.form.addRow(this.row.getElement());
    },
    createRow: function (json) {
        var singleFieldRow = 1;
        this.row = new element.FormRow();
        for (var i = 0; i < json.length; i++) {
            var labelCell = new element.FormCell();
            var label = new element.Label(json[i].label + ":", json[i].id + '-field');
            labelCell.addElement(label.getElement());
            this.row.addElement(labelCell.getElement());
            var inputCell = new element.FormCell();
            if (json.length === singleFieldRow) {
                inputCell.setColSpan(5);
            }
            var field = this.createInputField(json[i].id, json.length === singleFieldRow);
            field.setValue(json[i].value);
            inputCell.addElement(field.getElement());
            this.row.addElement(inputCell.getElement());
        }
        this.form.addRow(this.row.getElement());
    },
    getElement: function () {
        return this.form.getElement();
    },
    createInputField: function (id, wide) {
        var field = null;
        var list;
        for (var i = 0; i < autocompleteMap.length; i++) {
            if (autocompleteMap[i].field === id) {
                field = new element.DataList(id, id);
                list = hex.lists[autocompleteMap[i].list];
                field.setDataList(list);
                field.setStyle('width: ' + this.getFieldWidth(wide));
                break;
            }
        }
        if (field === null) {
            for (var i = 0; i < numericFields.length; i++) {
                if (numericFields[i] === id) {
                    field = new element.NumberChooserField(id);
                    field.setId(id);
                    field.setStyle('width: ' + this.getFieldWidth(wide) + '; text-align: right');
                    break;
                }
            }
        }
        if (field === null) {
            field = new element.TextField(id);
            field.setId(id);
            field.setStyle('width: ' + this.getFieldWidth(wide));
        }
        return field;
    }
};
var numericFields = ['voiceIndex', 'transpose'];
var autocompleteMap = [
    {'field': 'clef', 'list': 'clefs'},
    {'field': 'composer', 'list': 'composers'},
    {'field': 'key', 'list': 'keys'},
    {'field': 'meter', 'list': 'meters'},
    {'field': 'region', 'list': 'regions'},
    {'field': 'rythm', 'list': 'rythms'},
    {'field': 'source', 'list': 'sources'},
    {'field': 'transcriber', 'list': 'transcribers'},
    {'field': 'unit-note-length', 'list': 'noteLengths'}
];