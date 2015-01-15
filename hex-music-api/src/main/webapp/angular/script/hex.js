var hex = {};
hex.tunes = {
    openListLink: function (e) {
        var uri = decodeURIComponent(e.target.options[e.target.selectedIndex].value);
        if (uri !== '') {
            alert(uri);
        }
    }
};