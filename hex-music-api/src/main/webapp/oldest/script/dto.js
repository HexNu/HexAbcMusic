dto = {
    in: {
        Clef: function (jsonData) {
            if (jsonData) {
                this.id = parseInt(jsonData.id);
                this.type = jsonData.type || 'G-klav';
                this.transpose = jsonData.transpose || 0;
                this.middle = jsonData.middle || '';
            } else {
                this.id = null;
                this.type = 'G-klav';
                this.transpose = 0;
                this.middle = '';
            }
        },
        Key: function (jsonData) {
            if (jsonData) {
                this.id = parseInt(jsonData.id);
                this.signature = jsonData.signature;
                this.clef = new dto.in.Clef(jsonData.clef);
            } else {
                this.id = null;
                this.signature = '';
                this.clef = new dto.in.Clef();
            }
        },
        Tune: function (jsonData) {
            if (jsonData) {
                this.id = parseInt(jsonData.id);
                this.header = jsonData.title;
                this.subheader = jsonData.subheader || '';
                this.composer = jsonData.composer || '';
                this.originator = jsonData.originator || '';
                this.rythm = jsonData.rythm || '';
                this.region = jsonData.region || '';
                this.history = jsonData.history || '';
                this.notes = jsonData.notes || '';
                this.transcriber = jsonData.transcriber || '';
                this.source = jsonData.source || '';
                this.meter = jsonData.meter || '';
                this.unitNoteLength = jsonData.unitNoteLength || '';
                this.key = new dto.in.Key(jsonData.key);
                this.voices = [];
                for (var i = 0; i < jsonData.voices.length; i++) {
                    this.voices[i] = new dto.in.Voice(jsonData.voices[i], i);
                }
            } else {
                this.id = null;
                this.header = '';
                this.subheader = '';
                this.composer = '';
                this.originator = '';
                this.rythm = '';
                this.region = '';
                this.history = '';
                this.notes = '';
                this.transcriber = '';
                this.source = '';
                this.meter = '';
                this.unitNoteLength = '';
                this.key = new dto.in.Key();
                this.voices = [];
                this.voices[0] = new dto.in.Voice();
            }
        },
        Voice: function (jsonData) {
            if (jsonData) {
                this.id = parseInt(jsonData.id);
                this.name = jsonData.name;
                this.subname = jsonData.subname;
                this.voiceId = jsonData.voiceId;
                this.voiceIndex = parseInt(jsonData.voiceIndex);
                this.clef = new dto.in.Clef(jsonData.clef);
                this.body = jsonData.body;
            } else {
                this.id = null;
                this.name = '';
                this.subname = '';
                this.voiceId =  'V1';
                this.voiceIndex = 0;
                this.clef = new dto.in.Clef();
                this.body = '';
            }
        }
    },
    out: {
    }
};
dto.in.Clef.prototype = {
    getId: function () {
        return this.id;
    },
    getType: function () {
        return this.type;
    },
    getTranspose: function () {
        return this.transpose;
    },
    getMiddle: function () {
        return this.middle;
    }
};
dto.in.Key.prototype = {
    getId: function () {
        return this.id;
    },
    getSignature: function () {
        return this.signature;
    },
    getClef: function () {
        return this.clef;
    }
};
dto.in.Tune.prototype = {
    getId: function () {
        return this.id;
    },
    getTitle: function () {
        return this.header;
    },
    getSubheader: function () {
        return this.subheader;
    },
    getComposer: function () {
        return this.composer;
    },
    getOriginator: function () {
        return this.originator;
    },
    getRythm: function () {
        return this.rythm;
    },
    getRegion: function () {
        return this.region;
    },
    getHistory: function () {
        return this.history;
    },
    getNotes: function () {
        return this.notes;
    },
    getTranscriber: function () {
        return this.transcriber;
    },
    getSource: function () {
        return this.source;
    },
    getMeter: function () {
        return this.meter;
    },
    getUnitNoteLength: function () {
        return this.unitNoteLength;
    },
    getKey: function () {
        return this.key;
    },
    getVoices: function () {
        return this.voices;
    },
    getVoice: function(index) {
        return this.voices[index];
    },
    getNumberOfVoices: function() {
        return this.voices.length;
    },
    isNew: function () {
        return this.id === '';
    }
};
dto.in.Voice.prototype = {
    getId: function () {
        return this.id;
    },
    getName: function () {
        return this.name;
    },
    getSubname: function () {
        return this.subname;
    },
    getVoiceId: function () {
        return this.voiceId;
    },
    getIndex: function() {
        return this.voiceIndex;
    },
    getClef: function () {
        return this.clef;
    },
    getBody: function () {
        return this.body;
    }
};