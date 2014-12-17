var hex = {};
var head = document.getElementsByTagName('head')[0];
var title = document.getElementsByTagName('title')[0];
var titleText = title.childNodes[0].nodeValue;
var body = document.getElementsByTagName('body')[0];
var init = function () {
    head.appendChild(createScriptNode('script/dom-node.js'));
    head.appendChild(createScriptNode('script/form.js'));
    head.appendChild(createScriptNode('script/http.js'));
    head.appendChild(createScriptNode('script/hex-tunes.js'));
};
createScriptNode = function(url) {
    var node = document.createElement('script');
    node.setAttribute('type','text/javascript');
    node.setAttribute('src', url);
    return node;
};
init();
