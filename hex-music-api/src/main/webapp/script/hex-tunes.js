hex.tunes = {
    clearList: function () {
        dom.clearNode('list');
    },
    clearEditArea: function () {
        dom.clearNode('edit-area');
    },
    generateList: function (jsonData) {
        hex.tunes.clearList();
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
                        hex.view.edit(event.target.getAttribute('link'));
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
    }
};
hex.view = {
    list: function () {
        http.Get('resources/tunes/abc', hex.tunes.generateList);
    },
    edit: function (url) {
        http.Get(url, hex.tunes.createTuneEditForm);
    }
};
http.Get('resources/tunes/abc', hex.tunes.generateList);