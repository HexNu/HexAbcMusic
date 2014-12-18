hex.actions = {
    clearList: function () {
        dom.clearNode('list');
    },
    clearEditArea: function () {
        dom.clearNode('edit-area');
    },
    generateList: function (jsonData) {
        hex.actions.clearList();
        var i;
        var tunes = jsonData.tunes;
        dom.setText(title, titleText + ' - Låtlista - ' + tunes.length + ' låtar');
        $('list').appendChild(dom.createNode('h3', 'Låtlista'));
        for (i = 0; i < tunes.length; i++) {
            var itemNode = dom.createNode('dt', tunes[i].title);
            $('list').appendChild(itemNode);
            var descriptionNode = dom.createNode('dd');
            for (j = 0; j < tunes[i].links.length; j++) {
                if (j > 0) {
                    dom.appendText(descriptionNode, ' | ');
                }
                var linkNode = dom.createNode('a', tunes[i].links[j].rel);
                if (tunes[i].links[j].rel === 'edit') {
                    linkNode.setAttribute('href', '#');
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
    createTuneEditForm: function (jsonData) {
        dom.clearNode('edit-area');
        $('edit-area').appendChild(hex.editor.create(jsonData));
    },
    createMenu: function (buttons) {
        var listing = hex.elements.Listing(buttons);
        $('menu').appendChild(listing);
        var importer = hex.elements.Importer(buttons);
        $('menu').appendChild(importer);
        var exporter = hex.elements.Exporter(buttons);
        $('menu').appendChild(exporter);
        var addTune = hex.elements.Add(buttons);
        $('menu').appendChild(addTune);
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

};
hex.elements = {
    Importer: function (buttons) {
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
            fileChooserTrigger = dom.createNode('button','Importera');
            fileChooserTrigger.setAttribute('type', 'button');
//            dom.appendText(fileChooserTrigger, 'Importera');
        } else {
            fileChooserTrigger = dom.createNode('img');
            fileChooserTrigger.setAttribute('src', 'layout/images/icons/16x16/document_import.png');
            fileChooserTrigger.setAttribute('width', '16');
            fileChooserTrigger.setAttribute('hight', '16');
            fileChooserTrigger.setAttribute('alt', 'Exportera');

        }
        fileChooserTrigger.setAttribute('title', 'Ladda upp en abc-fil till servern.');
        fileChooserTrigger.addEventListener('click', function () {
            $('file-upload').click();
        });
        result.appendChild(fileChooserTrigger);
        return result;
    },
    Exporter: function (buttons) {
        var result = null;
        if (buttons) {
            result = dom.createNode('button', 'Exportera');
            result.setAttribute('type', 'button');
        } else {
            result = dom.createNode('img');
            result.setAttribute('src', 'layout/images/icons/16x16/document_export.png');
            result.setAttribute('width', '16');
            result.setAttribute('hight', '16');
            result.setAttribute('alt', 'Exportera');
        }
        result.setAttribute('title', 'Ladda ner alla låtar som en abc-fil till din dator.');
        result.addEventListener('click', function () {
            hex.actions.downloadAll();
        });
        return result;
    },
    Listing: function (buttons) {
        var result = null;
        if (buttons) {
            result = dom.createNode('button', 'Låtlista');
            result.setAttribute('type', 'button');
        } else {
            result = dom.createNode('img');
            result.setAttribute('src', 'layout/images/icons/16x16/directory_listing.png');
            result.setAttribute('width', '16');
            result.setAttribute('hight', '16');
            result.setAttribute('alt', 'Låtlista');
        }
        result.setAttribute('title', 'Visa eller uppdatara låtlistan.');
        result.addEventListener('click', function () {
            hex.actions.list();
        });
        return result;
    },
    Add: function (buttons) {
        var result = null;
        if (buttons) {
            result = dom.createNode('button', 'Ny låt');
            result.setAttribute('type', 'button');
        } else {
            result = dom.createNode('img');
            result.setAttribute('src', 'layout/images/icons/16x16/add.png');
            result.setAttribute('width', '16');
            result.setAttribute('hight', '16');
            result.setAttribute('alt', 'Ny låt');
        }
        result.setAttribute('title', 'Lägg in en ny låt.');
        result.addEventListener('click', function () {
            hex.actions.edit();
        });
        return result;

    }

};
hex.actions.createMenu(true);
http.Get('resources/tunes/abc', hex.actions.generateList);