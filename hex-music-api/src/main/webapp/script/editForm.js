var currentTuneEditor;
var voiceCounter = 0;
var TuneEditor = function (tune) {
    voiceCounter = 0;
    if (tune === null) {
        tune = this.createEmptyTune();
    }
    currentTuneEditor = this;
    this.form = new element.Form('edit-form');
    this.menuTitleBar = new element.FormTitleMenuBar();
    this.menuTitleBar.setTitleColSpan(3);
    this.menuTitleBar.setMenuColSpan(3);
    this.menuTitleBar.setTitle(tune.title || 'Ny låt');
    this.saveButton = new element.IconButton('disk', 'Spara');
    this.saveButton.addIconClickedAction(function () {
        currentTuneEditor.save();
    });
    this.menuTitleBar.addMenuElement(this.saveButton.getElement());
    this.form.setTitleMenuBar(this.menuTitleBar.getElement());
    this.meta = dom.createNode('tbody');
    this.meta.setAttribute('id', 'meta-body');
    this.form.addBody(this.meta);
    currentTuneEditor.voices = [];
    this.idField = new element.HiddenField('tuneId');
    if (tune.id !== null) {
        this.idField.setValue(tune.id);
    }
    this.form.addElement(this.idField.getElement());
    this.keyIdField = new element.HiddenField('keyId');
    if (tune.keyId !== null) {
        this.keyIdField.setValue(tune.keyId);
    }
    this.form.addElement(this.keyIdField.getElement());
    this.clefIdField = new element.HiddenField('clefId');
    if (tune.clefId !== null) {
        this.clefIdField.setValue(tune.clefId);
    }
    this.form.addElement(this.clefIdField.getElement());
    this.createMetaRow(tune, ['title']);
    this.createMetaRow(tune, ['subheader']);
    this.createMetaRow(tune, ['composer']);
    this.createMetaRow(tune, ['source']);
    this.createMetaRow(tune, ['rythm']);
    this.createMetaRow(tune, ['region']);
    this.createMetaRow(tune, ['history']);
    this.createMetaRow(tune, ['notes']);
    this.createMetaRow(tune, ['transcriber']);
    this.createMetaRow(tune, ['bibliography']);
    this.createMetaRow(tune, ['discography']);
    this.createMetaRow(tune, ['uri']);
    this.createMetaRow(tune, ['meter', 'unitNoteLength', 'tempo']);
    this.createMetaRow(tune, ['key', 'clef', 'transpose']);
    if (tune.voices.length <= 0) {
        tune.voices[0] = this.createEmptyVoice();
    }
    for (var i = 0; i < tune.voices.length; i++) {
        this.addVoice(tune.voices[i], i);
    }
};
TuneEditor.prototype = {
    save: function () {
        var tf = this.getElement();
        var tuneToSave = {};
        tuneToSave.id = tf['tuneId'].value;
        tuneToSave.keyId = tf['keyId'].value;
        var setTuneFieldValue = function (field) {
            if (tf[field]) {
                tuneToSave[field] = tf[field].value;
            }
        };
        var setVoiceFieldValue = function (field, index) {
            var voiceNumber = index + 1;
            if (tf[field + '-' + voiceNumber]) {
                tuneToSave.voices[index][field] = tf[field + '-' + voiceNumber].value;
            }
        };
        for (var i = 0; i < tuneFields.length; i++) {
            setTuneFieldValue(tuneFields[i]);
        }
        tuneToSave.voices = [];
        for (var i = 0; i < voiceCounter; i++) {
            tuneToSave.voices[i] = {};
            for (var j = 0; j < voiceFields.length; j++) {
                setVoiceFieldValue(voiceFields[j], i);
            }
        }
        if (tuneToSave.id !== '') {
            hex.actions.updateTune(tuneToSave);
        } else {
            hex.actions.saveNewTune(tuneToSave);
        }
    },
    createEmptyTune: function () {
        var tune = {};
        for (var i = 0; i < tuneFields.length; i++) {
            tune[tuneFields[i]] = '';
        }
        tune.voices = [];
        tune.voices[0] = this.createEmptyVoice();
        return tune;
    },
    createEmptyVoice: function () {
        var voice = {};
        for (var i = 0; i < voiceFields.length; i++) {
            voice[voiceFields[i]] = '';
        }
        return voice;
    },
    addVoice: function (voice) {
        voiceCounter++;
        var tbody = dom.createNode('tbody');
        tbody.setAttribute('id', 'voice-' + voiceCounter);
        this.form.addBody(tbody);
        this.createVoiceHeaderRow(6, tbody);
        this.voiceIdField = new element.HiddenField('voiceId-' + voiceCounter);
        if (voice.voiceId !== null) {
            this.voiceIdField.setValue(voice.voiceId);
        }
        this.form.addElement(this.voiceIdField.getElement());
        this.clefIdField = new element.HiddenField('clefId-' + voiceCounter);
        if (voice.clefId !== null) {
            this.clefIdField.setValue(voice.clefId);
        }
        this.form.addElement(this.clefIdField.getElement());
        this.createVoiceRow(voice, ['voiceCode', 'name', 'subname'], tbody);
        this.createVoiceRow(voice, ['clef', 'transpose', 'voiceIndex'], tbody);
        this.createVoiceBodyRow(voice.body, 6, tbody);
    },
    getFieldWidth: function (wide) {
        if (wide === undefined || wide === null) {
            wide = true;
        }
        return wide ? '100%' : '6em';
    },
    createVoiceHeaderRow: function (colSpan, tbody) {
        this.row = new element.FormRow();
        this.header = new element.FormHeader('Stämma ' + voiceCounter);
        this.header.setColSpan(colSpan - 2);
        this.header.setStyle('text-align: left; padding-left: 1.2em;');
        this.row.addElement(this.header.getElement());
        this.voiceMenu = new element.VoiceMenu();
        this.voiceMenu.setColSpan(2);
        this.voiceMenu.setStyle('text-align: right; padding-right: 1.2em;');
        this.addButton = new element.IconButton('add', 'Lägg till en stämma');
        this.addButton.addIconClickedAction(function () {
            currentTuneEditor.addVoice(currentTuneEditor.createEmptyVoice());
        });
        this.voiceMenu.addElement(this.addButton.getElement());
        this.row.addElement(this.voiceMenu.getElement());
        this.form.addRow(this.row.getElement(), tbody);
    },
    createVoiceBodyRow: function (body, colSpan, tbody) {
        this.row = new element.FormRow();
        this.cell = new element.FormCell();
        this.cell.setColSpan(colSpan);
        this.textArea = new element.TextArea('body-' + voiceCounter, 10, 90);
        this.textArea.setStyle(TEXT_AREA_FONT + 'width: 100%;');
        this.textArea.setValue(body);
        this.cell.addElement(this.textArea.getElement());
        this.row.addElement(this.cell.getElement());
        this.form.addRow(this.row.getElement(), tbody);
    },
    createVoiceRow: function (jsonData, keys, tbody) {
        var singleFieldRow = 1;
        this.row = new element.FormRow();
        for (var i = 0; i < keys.length; i++) {
            var labelCell = new element.FormCell();
            var label = new element.Label(fields[keys[i]].label + ':', keys[i] + '-' + voiceCounter);
            labelCell.addElement(label.getElement());
            labelCell.setTooltip(fields[keys[i]].tooltip);
            this.row.addElement(labelCell.getElement());
            var inputCell = new element.FormCell();
            if (keys.length === singleFieldRow) {
                inputCell.setColSpan(5);
            }
            var field = this.createInputField(keys[i] + '-' + voiceCounter, keys.length < 2);
            field.setValue(jsonData[keys[i]]);
            inputCell.addElement(field.getElement());
            inputCell.setTooltip(fields[keys[i]].tooltip);
            this.row.addElement(inputCell.getElement());
        }
        this.form.addRow(this.row.getElement(), tbody);
    },
    createMetaRow: function (jsonData, keys) {
        var singleFieldRow = 1;
        this.row = new element.FormRow();
        for (var i = 0; i < keys.length; i++) {
            var labelCell = new element.FormCell();
            var label = new element.Label(fields[keys[i]].label + ':', keys[i]);
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
        this.meta.appendChild(this.row.getElement(), this.meta);
    },
    getElement: function () {
        return this.form.getElement();
    },
    createInputField: function (key, wide) {
        var field = null;
        var list;
        var fieldKey = key.split('-')[0];
        if (fields[fieldKey].hidden !== null && fields[fieldKey].hidden === true) {
            field = new element.HiddenField(key);
            field.setId(key);
        }
        if (field === null && fields[fieldKey].autocomplete !== null && fields[fieldKey].autocomplete !== undefined) {
            field = new element.DataList(key, key);
            list = hex.lists[fields[fieldKey].autocomplete];
            if (list !== null && list.length > 0) {
                field.setDataList(list);
            }
            field.setStyle(INPUT_FIELD_FONT + 'width: ' + this.getFieldWidth(wide));
        }
        if (field === null && fields[fieldKey].numeric) {
            field = new element.NumberChooserField(key);
            field.setId(key);
            field.setStyle(INPUT_FIELD_FONT + 'width: ' + this.getFieldWidth(wide) + '; text-align: right');
        }
        if (field === null && fields[fieldKey].multirow) {
            field = new element.TextArea(key, 3, 90);
            field.setId(key);
            field.setStyle(INPUT_FIELD_FONT + 'width: ' + this.getFieldWidth(wide) + '; height: 3.6em;');
        }
        if (field === null) {
            field = new element.TextField(key);
            field.setId(key);
            field.setStyle(INPUT_FIELD_FONT + 'width: ' + this.getFieldWidth(wide));
        }
        return field;
    }
};
var INPUT_FIELD_FONT = 'font-family: Verdana, Helvetica, sans-serif; font-size: 11px;';
var TEXT_AREA_FONT = 'font-family: monospace; font-size: 10px;';
var fields = {
    'id': {
        'hidden': true
    },
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
        'tooltip': 'Om låtens kompositör är känd, använd annars \'Källa\'',
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
    'keyId': {
        'hidden': true
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
        'hidden': true
    },
    'voiceCode': {
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
        'tooltip': 'Sorteringsordningen för stämman. Anges inget sorteras de efter den ordning de lagts in.'
    }
};
var tuneFields = ['title', 'subheader', 'composer', 'source', 'rythm',
    'region', 'history', 'notes', 'transcriber', 'bibliography', 'discography',
    'uri', 'meter', 'unitNoteLength', 'tempo', 'keyId', 'key', 'clefId', 'clef', 'transpose', 'middle'];
var voiceFields = ['voiceId', 'voiceCode', 'name', 'subname', 'clefId', 'clef', 'transpose', 'voiceIndex',
    'transpose', 'body', 'middle'];
