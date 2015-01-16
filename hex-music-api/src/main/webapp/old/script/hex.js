var hex = {};
hex.tunes = {
    openListLink: function (e) {
        var uri = decodeURIComponent(e.target.options[e.target.selectedIndex].value);
        if (uri !== '') {
//            hex.view.setURL(uri);
        }
    }
};
hex.view = {
    url: '',
    getURL: function () {
        return url;
    },
    setURL: function (url) {
        this.url = url;
    }
};
//hex.about = 'Den här sidan är tänkt att husera folkmusik. \n\
//Det är också tanken att du ska kunna lägga upp låtlistor och få dessa utskrivna i diverse olika format.\n\
//Notationen sker med music abc, se http://abcnotation.com/wiki/abc:standard:v2.1 på abc wiki för mer information.'