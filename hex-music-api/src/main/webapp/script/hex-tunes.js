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
                linkNode.setAttribute('href', tunes[i].links[j].uri);
                linkNode.setAttribute('class', 'list-link');
                descriptionNode.appendChild(linkNode);
            }
            $('list').appendChild(descriptionNode);
        }
    },
    createTuneEditForm: function (isNew) {
        this.clearEditArea();
        var method = isNew ? http.Method.PUT : http.Method.POST;
        var editForm = form.Form('edit-form', 'resources/abc', method, http.MediaType.MULTIPART_FORM_DATA);
        var nameTextField = new form.TextField('name');
        editForm.appendChild(nameTextField);
        $('edit-area').appendChild(editForm);
    }
};
hex.view = {
    list: function () {
        http.Get('resources/tunes/abc', hex.tunes.generateList);
    },
    edit: function (isNew) {
        hex.tunes.createTuneEditForm(isNew);
    }
};
http.Get('resources/tunes/abc', hex.tunes.generateList);