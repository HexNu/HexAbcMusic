hex = {
    maps: {
        rel: {
        }
    },
    lists: {
        clefs: null,
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
        sources: null,
        regions: null,
        rythms: null,
        transcribers: null
    },
    actions: {
        updateTune: function (tuneJson) {
            http.PutJson('resources/tunes/abc/' + tuneJson.id, tuneJson, null, true);
        },
        saveNewTune: function (tuneJson) {
            http.PostJson('resources/tunes/abc/', tuneJson, null, true);
        },
        populateAutoCompleteLists: function () {
            hex.actions.clearList();
            hex.actions.getAutoCompleteData();
        },
        getAutoCompleteData: function () {
            http.GetJson('resources/tunes/abc/composers', hex.actions.generateComposerList);
            http.GetJson('resources/tunes/abc/keys', hex.actions.generateKeyList);
            http.GetJson('resources/tunes/abc/clefs', hex.actions.generateClefList);
            http.GetJson('resources/tunes/abc/sources', hex.actions.generateSourceList);
            http.GetJson('resources/tunes/abc/regions', hex.actions.generateRegionList);
            http.GetJson('resources/tunes/abc/rythms', hex.actions.generateRythmList);
            http.GetJson('resources/tunes/abc/transcribers', hex.actions.generateTranscriberList);
        },
        clearEditorArea: function () {
            dom.clearNode('editor-area');
            $('editor-area').style.visibility = 'hidden';
        },
        clearMenuArea: function () {
            dom.clearNode('menu-area');
        },
        clearList: function () {
            dom.clearNode('list-area');
        },
        generateComposerList: function (jsonData) {
            hex.lists.composers = jsonData;
        },
        generateSourceList: function (jsonData) {
            hex.lists.sources = jsonData;
        },
        generateRegionList: function (jsonData) {
            hex.lists.regions = jsonData;
        },
        generateRythmList: function (jsonData) {
            hex.lists.rythms = jsonData;
        },
        generateTranscriberList: function (jsonData) {
            hex.lists.transcribers = jsonData;
        },
        generateClefList: function (jsonData) {
            hex.lists.clefs = jsonData;
        },
        generateKeyList: function (jsonData) {
            hex.lists.keys = jsonData;
        },
        generateList: function (jsonData) {
            var resultListing = new ResultListing(jsonData);
            resultListing.setTitle('Låtar');
            $('list-area').appendChild(resultListing.getElement());
        },
        generateFwSearchResultList: function (jsonData) {
            var resultListing = new ResultListing(jsonData);
            resultListing.setTitle('Sökresultat');
            $('list-area').appendChild(resultListing.getElement());
        },
        createMenu: function () {
            dom.clearNode('menu-area');
            hex.menu.create();
        },
        createTuneEditor: function (jsonData) {
            hex.actions.clearEditorArea();
            var editForm = new TuneEditor(jsonData);
            $('editor-area').appendChild(editForm.getElement());
            $('editor-area').style.visibility = 'visible';
        },
        downloadAll: function () {
            location.href = 'resources/tunes/abc/download';
        },
        edit: function (url) {
            hex.actions.clearEditorArea();
            if (url !== undefined && url !== null) {
                http.GetJson(url, hex.actions.createTuneEditor);
            } else {
                hex.actions.createTuneEditor(null);
            }
        },
        listFwSearchResults: function () {
            hex.actions.clearList();
            var queryString = $('search-box').value;
            http.GetJson('resources/tunes/fw?q=' + queryString, hex.actions.generateFwSearchResultList);
        },
        listSearchResults: function () {
            hex.actions.clearList();
            var queryString = $('search-box').value;
            http.GetJson('resources/tunes/abc?q=' + queryString, hex.actions.generateList);
        },
        listNoteSearchResults: function () {
            hex.actions.clearList();
            var queryString = $('search-box').value;
            http.GetJson('resources/tunes/abc?notes=' + queryString, hex.actions.generateList);
        },
        listTunes: function (uri) {
            hex.actions.clearList();
            uri = uri || 'resources/tunes/abc';
            http.GetJson(uri, hex.actions.generateList);
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
            searchField.setId('search-box');
            searchField.setCssClass('search-box');
            var searchTuneButton = new element.IconButton('magnifier', 'Sök');
            searchTuneButton.setTooltip('Sök efter låtar på HexAbc.');
            searchTuneButton.getElement().setAttribute('accesskey', 'x');
            searchTuneButton.addIconClickedAction(function () {
                hex.actions.listSearchResults();
            });
            var searchNotesButton = new element.IconButton('music_notes_magnify', 'Sök i noter');
            searchNotesButton.setTooltip('Sök med noter \"abc\" på HexAbc.');
            searchNotesButton.getElement().setAttribute('accesskey', 'n');
            searchNotesButton.addIconClickedAction(function () {
                hex.actions.listNoteSearchResults();
            });
            var searchFwButton = new element.IconButton('FW_search', 'FW-sök');
            searchFwButton.setTooltip('Sök efter låtar på FolkWiki');
            searchFwButton.getElement().setAttribute('accesskey', 'f');
            searchFwButton.addIconClickedAction(function () {
                hex.actions.listFwSearchResults();
            });
            this.add(searchField.getElement());
            this.add(searchTuneButton.getElement());
            this.add(searchNotesButton.getElement());
            this.add(searchFwButton.getElement());
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
            var exportTrigger = new element.IconButton('page_white_put_A', 'Exportera');
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
            var addTuneTrigger = new element.IconButton('music_notes_add', 'Ny låt');
            addTuneTrigger.setTooltip('Lägg till en låt');
            addTuneTrigger.addIconClickedAction(function () {
                hex.actions.edit();
            });
            this.add(addTuneTrigger.getElement());
        }
    }
};
hex.actions.createMenu();
alert('Laddar data...');
hex.actions.populateAutoCompleteLists();
alert('Klar!');