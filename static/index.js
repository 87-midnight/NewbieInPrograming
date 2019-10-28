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
var menuTree = function(tree,level){
    var str = "<ul class='menu-list %c'>%s</ul>";
    var list = "";
    for(var item in tree){
        var li = "<li>";
        if(tree[item].children.length>0){
            level = tree[item].level;
            li += menuTree(tree[item].children,level);
        }
        li += "<span>"+tree[item].name+"</span></li>";
        list = list + li;
    }
    if(level !== undefined && level === 1){
        str.replace(/%c/g,".hide");
    }
    str = str.replace(/%s/g,list);
    return str;
};

document.getElementById("menu").innerHTML = menuTree(files);
document.getElementById("menu").querySelectorAll("li");
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


var loadReadMeFile = function (name) {
    var xhr = new XMLHttpRequest(),
        okStatus = document.location.protocol === "file:" ? 0 : 200;
    xhr.open('GET', name, false);
    xhr.overrideMimeType("text/plain;charset=utf-8");//默认为utf-8
    xhr.send(null);
    return xhr.status === okStatus ? xhr.responseText : null;
};

var text = loadReadMeFile("./README.md");

document.getElementById("content").innerHTML = marked(text);