hljs.initHighlightingOnLoad();

var localstorage;

var menu_tree = function(){
};
var rendererMD = new marked.Renderer();
marked.setOptions({
    renderer: rendererMD,
    gfm: true,
    tables: true,
    breaks: false,
    pedantic: false,
    sanitize: false,
    smartLists: true,
    smartypants: false
});
marked.setOptions({
    highlight: function (code) {
        return hljs.highlightAuto(code).value;
    }
});


var load = function (name) {
    var xhr = new XMLHttpRequest(),
        okStatus = document.location.protocol === "file:" ? 0 : 200;
    xhr.open('GET', name, false);
    xhr.overrideMimeType("text/plain;charset=utf-8");//默认为utf-8
    xhr.send(null);
    return xhr.status === okStatus ? xhr.responseText : null;
};

var text = load("./README.md");

document.getElementById("content").innerHTML = marked(text);