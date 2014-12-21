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
        populateAllLists: function () {
            hex.actions.clearList();
            hex.actions.getAutoCompleteData();
        },
        getAutoCompleteData: function () {
            http.Get('resources/tunes/abc/composers', hex.actions.generateComposerList, http.Method.GET);
            http.Get('resources/tunes/abc/keys', hex.actions.generateKeyList, http.Method.GET);
            http.Get('resources/tunes/abc/clefs', hex.actions.generateClefList, http.Method.GET);
            http.Get('resources/tunes/abc/sources', hex.actions.generateSourceList, http.Method.GET);
            http.Get('resources/tunes/abc/regions', hex.actions.generateRegionList, http.Method.GET);
            http.Get('resources/tunes/abc/rythms', hex.actions.generateRythmList, http.Method.GET);
            http.Get('resources/tunes/abc/transcribers', hex.actions.generateTranscriberList, http.Method.GET);
        },
        clearEditorArea: function () {
            dom.clearNode('editor-area');
        },
        clearMenuArea: function () {
            dom.clearNode('menu-area');
        },
        clearList: function () {
            dom.clearNode('list');
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
        generateFwSearchResultList: function (jsonData) {
            hex.actions.clearList();
            $('list').appendChild(dom.createNode('h3', 'Sökresultat'));
            if (jsonData.length > 0) {
                var numberOfResultString = jsonData.length + ' träff';
                if (jsonData.length > 1) {
                    numberOfResultString += 'ar';
                }
                dom.setText(title, titleText + ' - Sökresultat från FolkWiki - ' + numberOfResultString);
                for (var i = 0; i < jsonData.length; i++) {
                    var itemNode = dom.createNode('dt', jsonData[i].title);
                    $('list').appendChild(itemNode);
                    var descriptionNode = dom.createNode('dd');
                    for (var j = 0; j < jsonData[i].links.length; j++) {
                        if (j > 0) {
                            dom.appendText(descriptionNode, ' | ');
                        }
                        var linkNode = dom.createNode('a', '');
                        if (jsonData[i].links[j].rel === 'view-page') {
                            dom.appendText(linkNode, 'FolkWiki');
                            linkNode.setAttribute('href', jsonData[i].links[j].uri);
                            linkNode.setAttribute('target', 'FW');
                            linkNode.setAttribute('title', 'Gå till låtens sida på FolkWiki');
                        } else {
                            dom.appendText(linkNode, 'Hämta');
                            linkNode.setAttribute('href', 'javascript:void(0)');
                            linkNode.setAttribute('title', 'Ladda hem låten från FolkWiki');
                            linkNode.setAttribute('link', jsonData[i].links[j].uri);
                            linkNode.addEventListener('click', function (event) {
                                hex.actions.edit(event.target.getAttribute('link'));
                            });
                        }
                        linkNode.setAttribute('class', 'list-link');
                        descriptionNode.appendChild(linkNode);
                    }
                    $('list').appendChild(descriptionNode);
                }
            }
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
        createMenu: function () {
            dom.clearNode('menu-area');
            hex.menu.create();
        },
        createTuneEditor: function (jsonData) {
            hex.actions.clearEditorArea();
            var editForm = new TuneEditor(jsonData);
            $('editor-area').appendChild(editForm.getElement());
        },
        downloadAll: function () {
            location.href = 'resources/tunes/abc/download';
        },
        edit: function (url) {
            hex.actions.clearEditorArea();
            if (url !== undefined && url !== null) {
                http.Get(url, hex.actions.createTuneEditor);
            } else {
                hex.actions.createTuneEditor(null);
            }
        },
        listFwSearchResults: function () {
            hex.actions.clearList();
            var queryString = $('search-box').value;
            http.Get('resources/tunes/fw/search?q=' + queryString, hex.actions.generateFwSearchResultList);
        },
        listTunes: function () {
            hex.actions.clearList();
            http.Get('resources/tunes/abc', hex.actions.generateList);
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
            var searchFwButton = new element.IconButton('magnifier_fw', 'FW-sök');
            searchFwButton.setTooltip('Sök efter låtar på FolkWiki');
            searchFwButton.getElement().setAttribute('accesskey','f');
            searchFwButton.addIconClickedAction(function () {
                hex.actions.listFwSearchResults();
            });
            this.add(searchField.getElement());
            this.add(searchTuneButton.getElement());
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
            var exportTrigger = new element.IconButton('document_export', 'Exportera');
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
            var addTuneTrigger = new element.IconButton('add', 'Ny låt');
            addTuneTrigger.setTooltip('Lägg till en låt');
            addTuneTrigger.addIconClickedAction(function () {
                hex.actions.edit();
            });
            this.add(addTuneTrigger.getElement());
        }
    }
};
hex.actions.createMenu();
hex.actions.populateAllLists();
