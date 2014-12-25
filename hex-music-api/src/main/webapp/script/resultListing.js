var ResultListing = function (jsonData) {
    var links = jsonData.links;
    var tunes = jsonData.tunes;
    this.domElement = dom.createNode('div');
    this.header = dom.createNode('h3');
    this.domElement.appendChild(this.header);
    this.topNavigation = null;
    if (links !== null) {
        this.topNavigation = new Navigation(links);
        this.domElement.appendChild(this.topNavigation.getElement());
    }
    this.tuneList = null;
    if (tunes !== null) {
        this.tuneList = new List(tunes);
        this.domElement.appendChild(this.tuneList.getElement());
    }
    this.bottomNavigation = null;
    if (links !== null) {
        this.bottomNavigation = new Navigation(links);
        this.domElement.appendChild(this.bottomNavigation.getElement());
    }
};
var List = function (tunes) {
    this.domElement = dom.createNode('dl');
    for (var i = 0; i < tunes.length; i++) {
        var itemNode = dom.createNode('dt', tunes[i].title || 'Utan titel');
        itemNode.setAttribute('style', 'padding-top: 3px; font-weight: bold; border-top: dashed grey 1px');
        this.domElement.appendChild(itemNode);
        var sourceInfo = null;
        if (hasValue(tunes[i].composer)) {
            sourceInfo = 'av ' + tunes[i].composer;
        } else if (hasValue(tunes[i].source)) {
            sourceInfo = tunes[i].source;
        }
        if (sourceInfo !== null) {
            var sourceInfoNode = dom.createNode('dd', sourceInfo);
            sourceInfoNode.setAttribute('style', 'font-style: italic');
            this.domElement.appendChild(sourceInfoNode);
        }
        var info = '';
        if (hasValue(tunes[i].rythm)) {
            info += tunes[i].rythm + ' - ';
        }
        if (hasValue(tunes[i].region)) {
            info += tunes[i].region + ' - ';
        }
        info += tunes[i].keySignature;
        var informationNode = dom.createNode('dd', info);
        this.domElement.appendChild(informationNode);
        var itemLinksNode = dom.createNode('dd');
        itemLinksNode.setAttribute('style', 'margin-bottom: 3px');
        for (var j = 0; j < tunes[i].links.length; j++) {
            if (j > 0) {
                dom.appendText(itemLinksNode, ' ');
            }
            var link;
            switch (tunes[i].links[j].rel) {
                case 'edit':
                    link = new element.IconButton('music_notes_edit', 'Redigera');
                    link.getElement().setAttribute('link', tunes[i].links[j].uri);
                    link.setTooltip('Redigera låten');
                    link.addIconClickedAction(function (event) {
                        hex.actions.edit(event.target.getAttribute('link'));
                    });
                    break;
                case 'download':
                    link = new element.IconLink(tunes[i].links[j].uri, 'music_notes_download', 'Ladda hem');
                    link.setTooltip('Ladda hem ABC-koden till din dator');
                    break;
                case 'view-abc':
                    link = new element.IconLink(tunes[i].links[j].uri, 'music_notes_link', 'Granska');
                    link.setTooltip('Visa ABC-koden');
                    link.setTarget('abc_preview');
                    break;
                case 'download-fw':
                    link = new element.IconButton('FW_put', 'Hämta');
                    link.setTooltip('Ladda hem låten från FolkWiki');
                    link.getElement().setAttribute('link', jsonData[i].links[j].uri);
                    link.addIconClickedAction(function (event) {
                        hex.actions.edit(event.target.getAttribute('link'));
                    });
                    break;
                case 'view-fw-page':
                    link = new element.IconButton('FW_put', 'Hämta');
                    link.setTooltip('Ladda hem låten från FolkWiki');
                    link.getElement().setAttribute('link', jsonData[i].links[j].uri);
                    link.addIconClickedAction(function (event) {
                        hex.actions.edit(event.target.getAttribute('link'));
                    });
                    break;
            }
            itemLinksNode.appendChild(link.getElement());
        }
        this.domElement.appendChild(itemLinksNode);
    }
};
var Navigation = function (links) {
    this.domElement = dom.createNode('div');
    this.domElement.setAttribute('style', 'padding-left: 10px');
    var nextUri = null;
    var previousUri = null;
    for (var i = 0; i < links.length; i++) {
        if (links[i].rel === 'previous') {
            previousUri = links[i].uri;
        } else if (links[i].rel === 'next') {
            nextUri = links[i].uri;
        }
    }
    this.previousButton;
    if (previousUri !== null) {
        this.previousButton = new element.IconButton('resultset_previous', 'Föregående');
        this.previousButton.setTooltip('Föregående resultat');
        this.previousButton.addIconClickedAction(function () {
            hex.actions.listTunes(previousUri);
        });
    } else {
        this.previousButton = new element.IconButton('resultset_previous_disabled', 'Föregående');
        this.previousButton.setStyle('opacity: 0.5');
    }
    this.spacer = new element.Spacer(16, 16);
    this.nextButton;
    if (nextUri !== null) {
        this.nextButton = new element.IconButton('resultset_next', 'Nästa');
        this.nextButton.setTooltip('Nästa resultat ' + nextUri);
        this.nextButton.addIconClickedAction(function () {
            hex.actions.listTunes(nextUri);
        });
    } else {
        this.nextButton = new element.IconButton('resultset_next_disabled', 'Nästa');
        this.nextButton.setStyle('opacity: 0.5');
    }
    this.domElement.appendChild(this.previousButton.getElement());
    this.domElement.appendChild(this.spacer.getElement());
    this.domElement.appendChild(this.nextButton.getElement());
};
ResultListing.prototype = {
    setTitle: function (text) {
        this.header.innerHTML = text;
    },
    getElement: function () {
        return this.domElement;
    }
};
List.prototype = {
    getElement: function () {
        return this.domElement;
    }
};
Navigation.prototype = {
    setSpacerWidth: function (width) {
        this.spacer.getElement().setAttribute('width', width);
    },
    setSpacerHeight: function (height) {
        this.spacer.getElement().setAttribute('height', height);
    },
    getElement: function () {
        return this.domElement;
    }
};
var hasValue = function (field) {
    return field !== undefined && field !== null && field !== '';
};