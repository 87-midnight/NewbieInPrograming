hljs.initHighlightingOnLoad();

var store = {
    save:function (key,value) {
        localStorage.setItem(key,value);
    },
    delete:function (key) {
        localStorage.removeItem(key)
    },
    get:function (key) {
        return localStorage.getItem(key);
    }
};
//递归树遍历菜单
var menuTree = function(tree){
    var str = "<ul class='menu-list'>%s</ul>";
    var list = "";
    for(var item in tree){
        var li = "<li>";
        if(tree[item].children.length>0){
            li += menuTree(tree[item].children);
        }
        li += "<span>"+tree[item].name+"</span></li>";
        list = list + li;
    }
    str = str.replace(/%s/g,list);
    return str;
};

document.getElementById("menu").innerHTML = menuTree(files);

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