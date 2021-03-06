/**
 * 
 * @param {JSON} jsonData
 * @param {boolean} isHex
 * @returns {ResultListing}
 */
var ResultListing = function (jsonData, isHex) {
    var links = jsonData.links || null;
    var tunes = jsonData.tunes || null;
    this.domElement = dom.createNode('div');
    this.domElement.setAttribute('style', 'width: 300px; overflow: hidden');
    this.header = dom.createNode('h3');
    this.domElement.appendChild(this.header);
    this.topNavigation = new NavigationBar(links);
    this.domElement.appendChild(this.topNavigation.getElement());
    this.tuneList = new List(tunes);
    this.tuneList.setIsHex(isHex);
    this.domElement.appendChild(this.tuneList.getElement());
    this.bottomNavigation = new NavigationBar(links);
    this.bottomNavigation.setStyle('padding: 12px 5px 5px 5px; border-top: dashed grey 1px');
    this.domElement.appendChild(this.bottomNavigation.getElement());
};
/**
 * 
 * @param {JSON} tunes
 * @returns {List}
 */
var List = function (tunes) {
    var hasValue = function (field) {
        return field !== undefined && field !== null && field !== '';
    };
    this.domElement = dom.createNode('dl');
    if (tunes !== null) {
        for (var i = 0; i < tunes.length; i++) {
            var titleNode = dom.createNode('dt', tunes[i].title || 'Utan titel');
            titleNode.setAttribute('title', tunes[i].title || '');
            titleNode.setAttribute('style', 'padding-top: 3px; font-weight: bold; border-top: dashed grey 1px; white-space: nowrap');
            this.domElement.appendChild(titleNode);
            if (hasValue(tunes[i].subheader)) {
                var subheaderNode = dom.createNode('dt', tunes[i].subheader);
                subheaderNode.setAttribute('title', tunes[i].subheader);
                subheaderNode.setAttribute('style', 'white-space: nowrap');
                this.domElement.appendChild(subheaderNode);
            }
            var sourceInfo = null;
            if (hasValue(tunes[i].composer)) {
                sourceInfo = 'av ' + tunes[i].composer;
            } else if (hasValue(tunes[i].source)) {
                sourceInfo = tunes[i].source;
            }
            if (sourceInfo !== null) {
                var sourceInfoNode = dom.createNode('dd', sourceInfo);
                sourceInfoNode.setAttribute('title', sourceInfo);
                sourceInfoNode.setAttribute('style', 'font-style: italic; white-space: nowrap');
                this.domElement.appendChild(sourceInfoNode);
            }
            var info = '';
            if (hasValue(tunes[i].rythm)) {
                info += tunes[i].rythm + ' - ';
            }
            if (hasValue(tunes[i].region)) {
                info += tunes[i].region + ' - ';
            }
            if (hasValue(tunes[i].keySignature)) {
                info += tunes[i].keySignature;
            }
            if (info !== '') {
                var informationNode = dom.createNode('dd', info);
                informationNode.setAttribute('title', info);
                informationNode.setAttribute('style', 'white-space: nowrap');
                this.domElement.appendChild(informationNode);
            }
            var firstLineNode = dom.createNode('dt');
            var previewAsGifLink = dom.createNode('a');
            previewAsGifLink.setAttribute('href', getUriByRel(tunes[i].links, 'view-gif'));
            previewAsGifLink.setAttribute('target', 'view-gif');
            previewAsGifLink.setAttribute('title', 'Visa noterna (GIF)');
            var firstLineImage = dom.createNode('img');
            firstLineImage.setAttribute('src', getUriByRel(tunes[i].links, 'view-first-line'));
            firstLineImage.setAttribute('alt', 'Första raden');
            firstLineImage.setAttribute('width', '280');
            firstLineImage.setAttribute('height', '30');
            firstLineImage.setAttribute('border', '0');
            previewAsGifLink.appendChild(firstLineImage);
            firstLineNode.appendChild(previewAsGifLink);
            this.domElement.appendChild(firstLineNode);
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
                    case 'download-pdf':
                        link = new element.IconLink(tunes[i].links[j].uri, 'file_extension_pdf_down', 'Ladda hem PDF');
                        link.setTooltip('Ladda hem låten som PDF-fil till din dator');
                        break;
                    case 'download-abc':
                        link = new element.IconLink(tunes[i].links[j].uri, 'music_notes_down', 'Ladda hem ABC');
                        link.setTooltip('Ladda hem ABC-koden till din dator');
                        break;
                    case 'view-abc':
                        link = new element.IconLink(tunes[i].links[j].uri, 'music_notes_link', 'Granska ABC');
                        link.setTooltip('Visa ABC-koden');
                        link.setTarget('view-abc');
                        break;
                    case 'audio-midi':
                        link = new element.IconLink(tunes[i].links[j].uri, 'music_notes_listen', 'Lyssna (MIDI)');
                        link.setTooltip('Lyssna (MIDI)');
                        link.setTarget('audio-midi');
                        break;
                    case 'download-fw':
                        link = new element.IconButton('FW_put', 'Hämta');
                        link.setTooltip('Ladda hem låten från FolkWiki till HexAbc');
                        link.getElement().setAttribute('link', tunes[i].links[j].uri);
                        link.addIconClickedAction(function (event) {
                            hex.actions.edit(event.target.getAttribute('link'));
                        });
                        break;
                    case 'view-fw-page':
                        link = new element.IconLink(tunes[i].links[j].uri, 'FW_link', 'Hämta');
                        link.setTooltip('Visa låten på FolkWiki');
                        link.setTarget('view-fw-page');
                        break;
                }
                itemLinksNode.appendChild(link.getElement());
            }
            if (this.isHex()) {
                link = new element.IconButton('application_view_list_add', 'Lägg till lista');
                link.setTooltip('Lägg till låten i din lista.');
                link.getElement().setAttribute('tune-id', tunes[i].id);
                link.getElement().setAttribute('tune-title', tunes[i].title);
                link.addIconClickedAction(function (event) {
                    hex.actions.addToTuneCollection(event.target.getAttribute('tune-id'), event.target.getAttribute('tune-title'));
                });
                itemLinksNode.appendChild(link.getElement());
            }
            this.domElement.appendChild(itemLinksNode);
        }
    }
};
/**
 * 
 * @param {JSON} links
 * @returns {NavigationBar}
 */
var NavigationBar = function (links) {
    this.domElement = dom.createNode('div');
    this.domElement.setAttribute('style', 'padding: 5px 5px 0');
    var nextUri = null;
    var previousUri = null;
    if (links !== null) {
        for (var i = 0; i < links.length; i++) {
            if (links[i].rel === 'previous') {
                previousUri = links[i].uri;
            } else if (links[i].rel === 'next') {
                nextUri = links[i].uri;
            }
        }
    }
    this.previousButton;
    if (previousUri !== null) {
        this.previousButton = new element.IconButton('resultset_previous', 'Föregående');
        this.previousButton.setTooltip('Föregående låtar');
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
        this.nextButton.setTooltip('Nästa låtar');
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
    getTitle: function () {
        return this.header.innerHTML;
    },
    getElement: function () {
        return this.domElement;
    }
};
List.prototype = {
    setIsHex: function (isHex) {
        this.isHex = isHex;
    },
    isHex: function () {
        return this.isHex;
    },
    getElement: function () {
        return this.domElement;
    }
};
NavigationBar.prototype = {
    setSpacerWidth: function (width) {
        this.spacer.getElement().setAttribute('width', width);
    },
    setSpacerHeight: function (height) {
        this.spacer.getElement().setAttribute('height', height);
    },
    setStyle: function (style) {
        this.getElement().setAttribute('style', style);
    },
    getElement: function () {
        return this.domElement;
    }
};
var getUriByRel = function(jsonLinks, rel) {
    for (var i = 0; i < jsonLinks.length; i++) {
        if (jsonLinks[i].rel === rel) {
            return decodeURIComponent(jsonLinks[i].uri);
        }
    }
    return null;
};