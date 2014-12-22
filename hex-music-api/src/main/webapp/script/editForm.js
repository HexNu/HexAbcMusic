var TuneEditor = function (tune) {
    if (tune === null) {
        tune = emptyTune;
    }
    this.form = new element.Form('edit-form');
    this.idField = new element.HiddenField('id');
    if (tune.id !== null) {
        this.idField.setValue(tune.id);
    }
    testar = this;
    this.form.addElement(this.idField.getElement());
    this.createRow(tune, ['title']);
    this.createRow(tune, ['subheader']);
    this.createRow(tune, ['composer']);
    this.createRow(tune, ['source']);
    this.createRow(tune, ['rythm']);
    this.createRow(tune, ['region']);
    this.createRow(tune, ['history']);
    this.createRow(tune, ['notes']);
    this.createRow(tune, ['transcriber']);
    this.createRow(tune, ['bibliography']);
    this.createRow(tune, ['discography']);
    this.createRow(tune, ['uri']);
    this.createRow(tune, ['meter', 'unitNoteLength', 'tempo']);
    this.createRow(tune, ['key', 'clef', 'transpose']);
    for (var i = 0; i < tune.voices.length; i++) {
        var voiceNumber = i + 1;
        this.createVoiceHeaderRow('Stämma ' + voiceNumber, 6);
        this.createRow(tune.voices[i], ['voiceId', 'name', 'subname']);
        this.createRow(tune.voices[i], ['clef', 'transpose', 'voiceIndex']);
        this.creatVoiceBodyRow(tune.voices[i].body, 6);
    }
};
TuneEditor.prototype = {
    getFieldWidth: function (wide) {
        if (wide === undefined || wide === null) {
            wide = true;
        }
        return wide ? '100%' : '6em';
    },
    createVoiceHeaderRow: function (header, colSpan) {
        this.row = new element.FormRow();
        this.header = new element.FormHeader(header);
        this.header.setColSpan(colSpan - 2);
        this.header.setStyle('text-align: left; padding-left: 1.2em;');
        this.row.addElement(this.header.getElement());
        this.voiceMenu = new element.VoiceMenu();
        this.voiceMenu.setColSpan(2);
        this.voiceMenu.setStyle('text-align: right; padding-right: 1.2em;');
        this.addButton = new element.IconButton('add', 'Lägg till en stämma');
        this.addButton.addIconClickedAction(function () {
            testar.createVoiceHeaderRow('Ny stämma', 6);
            testar.createRow(emptyVoice, ['voiceId', 'name', 'subname']);
            testar.createRow(emptyVoice, ['clef', 'transpose', 'voiceIndex']);
            testar.createTextArea(emptyVoice.body, 6);
        });
        this.voiceMenu.addElement(this.addButton.getElement());
        this.row.addElement(this.voiceMenu.getElement());
        this.form.addRow(this.row.getElement());
    },
    creatVoiceBodyRow: function (body, colSpan) {
        this.row = new element.FormRow();
        this.cell = new element.FormCell();
        this.cell.setColSpan(colSpan);
        this.textArea = new element.TextArea('body', 10, 90);
        this.textArea.setStyle('width: 100%; font-family: monospace; font-size: 10px;');
        this.textArea.setValue(body);
        this.cell.addElement(this.textArea.getElement());
        this.row.addElement(this.cell.getElement());
        this.form.addRow(this.row.getElement());
    },
    createRow: function (jsonData, keys) {
        var singleFieldRow = 1;
        this.row = new element.FormRow();
        for (var i = 0; i < keys.length; i++) {
            var labelCell = new element.FormCell();
            var label = new element.Label(fields[keys[i]].label + ":", keys[i] + '-field');
            labelCell.addElement(label.getElement());
            labelCell.setTooltip(fields[keys[i]].tooltip);
            this.row.addElement(labelCell.getElement());
            var inputCell = new element.FormCell();
            if (keys.length === singleFieldRow) {
                inputCell.setColSpan(5);
            }
            var field = this.createInputField(keys[i], keys.length < 2);
            field.setValue(jsonData[keys[i]]);
            inputCell.addElement(field.getElement());
            inputCell.setTooltip(fields[keys[i]].tooltip);
            this.row.addElement(inputCell.getElement());
        }
        this.form.addRow(this.row.getElement());
    },
    getElement: function () {
        return this.form.getElement();
    },
    createInputField: function (key, wide) {
        var field = null;
        var list;
        if (fields[key].autocomplete !== null && fields[key].autocomplete !== undefined) {
            field = new element.DataList(key, key);
            list = hex.lists[fields[key].autocomplete];
            if (list !== null && list.length > 0) {
                field.setDataList(list);
            }
            field.setStyle('width: ' + this.getFieldWidth(wide));
        }
        if (field === null) {
            if (fields[key].numeric) {
                field = new element.NumberChooserField(key);
                field.setId(key);
                field.setStyle('width: ' + this.getFieldWidth(wide) + '; text-align: right');
            }
        }
        if (field === null) {
            if (fields[key].multirow) {
                field = new element.TextArea(key, 3, 90);
                field.setId(key);
                field.setStyle('width: ' + this.getFieldWidth(wide) + '; height: 3.6em');
            }
        }
        if (field === null) {
            field = new element.TextField(key);
            field.setId(key);
            field.setStyle('width: ' + this.getFieldWidth(wide));
        }
        return field;
    }
};
var fields = {
    'title': {
        'label': 'Titel',
        'tooltip': 'Låtens huvudtitel'
    },
    'subheader': {
        'label': 'Undertitel',
        'tooltip': 'Eventuell undertitel'
    },
    'composer': {
        'label': 'Kompositör',
        'tooltip': 'Om låtens kompositör är känd, använd annars \"Källa\"',
        'autocomplete': 'composers'
    },
    'source': {
        'label': 'Källa',
        'tooltip': 'Ange vem låten är efter',
        'autocomplete': 'sources'
    },
    'rythm': {
        'label': 'Låttyp',
        'tooltip': 'Polska, vals, etc',
        'autocomplete': 'rythms'
    },
    'region': {
        'label': 'Ort',
        'tooltip': 'Delsbo, Hälsingland eller Jämtland',
        'autocomplete': 'regions'
    },
    'history': {
        'label': 'Historik',
        'tooltip': 'Historik om låten',
        'multirow': true
    },
    'notes': {
        'label': 'Anteckningar',
        'tooltip': 'Diverse information om låten som inte passar i andra fält',
        'multirow': true
    },
    'transcriber': {
        'label': 'ABC-kodning',
        'tooltip': 'Vem som gjort abc-notationen',
        'autocomplete': 'transcribers'
    },
    'bibliography': {
        'label': 'Bibliografi',
        'tooltip': 'Ange om det är en tryckt källa'
    },
    'discography': {
        'label': 'Diskografi',
        'tooltip': 'Eventuell skiva där låten finns'
    },
    'uri': {
        'label': 'URI',
        'tooltip': 'Fil där låten är tillgänglig på internet'
    },
    'meter': {
        'label': 'Taktart',
        'tooltip': 'Ange taktart (3/4, C, C| 11/8)',
        'autocomplete': 'meters'
    },
    'unitNoteLength': {
        'label': 'Notlängd',
        'tooltip': 'Ange vilket notvärde som ska vara standard i låten, 1/8 etc',
        'autocomplete': 'noteLengths'
    },
    'tempo': {
        'label': 'Tempo',
        'tooltip': 'Ange tempot. Ex 1/4 = 112'
    },
    'key': {
        'label': 'Tonart',
        'tooltip': 'Am, Bb, Ddor',
        'autocomplete': 'keys'
    },
    'clef': {
        'label': 'Klav',
        'tooltip': 'Vilken klav som är standard',
        'autocomplete': 'clefs'
    },
    'transpose': {
        'label': 'Transponering',
        'tooltip': 'Antal halvtoner som stämman ska transponeras. -12 för en oktav ner',
        'numeric': true
    },
    'middle': {
        'label': 'Mitt-ton',
        'tooltip': 'Vilket nottecken som ska gälla för mittlinjen.'
    },
    'voiceId': {
        'label': 'Stämm-id',
        'tooltip': 'Benämning för stämman, t ex S1 (för stämma 1), A (för alt)'
    },
    'name': {
        'label': 'Namn på stämman',
        'tooltip': 'Visas om det är flera stämmor före första systemet. T ex Violin 1'
    },
    'subname': {
        'label': 'Kortnamn',
        'tooltip': 'Förkortning av namnet, visas före de övriga systemen. T ex Vl1'
    },
    'voiceIndex': {
        'label': 'Stämmindex',
        'tooltip': 'Sorteringsordningen för stämman. Anges inget sorteras de efter den ordning de lagts in.',
        'numeric': false
    }
};
var emptyVoice = {
    "body": null,
    "name": null,
    "subname": null,
    "voiceId": null,
    "voiceIndex": null,
    "clef": null,
    "transpose": null,
    "middle": null
};
var emptyTune = {
    "title": null,
    "source": null,
    "rythm": null,
    "transcriber": null,
    "meter": null,
    "unitNoteLength": null,
    "key": null,
    "clef": null,
    "transpose": null,
    "middle": null,
    "voices": [
        emptyVoice
    ]
};
