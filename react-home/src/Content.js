import React, { Component } from 'react';
import marked from 'marked'
import hljs from "highlight.js";
import 'highlight.js/styles/monokai-sublime.css';

marked.setOptions({

    renderer: new marked.Renderer(),
    gfm: true,
    pedantic: false,
    sanitize: false,
    tables: true,
    breaks: true,
    smartLists: true,
    smartypants: true,
    highlight: function (code) {
        return hljs.highlightAuto(code).value;
    }

});

const load = path=>{
    let xhr = new XMLHttpRequest(),
        okStatus = document.location.protocol === "file:" ? 0 : 200;
    xhr.open('GET', path, false);
    xhr.overrideMimeType("text/plain;charset=utf-8");//默认为utf-8
    xhr.send(null);
    return xhr.status === okStatus ? xhr.responseText : null;
};

class Contents extends Component {

    parseHtml(){
        let text = load(this.query());
        return marked(text);
    }
    query(){
        let state = this.props.location.state;
        console.log(state);
        return state.path;
    }
    render() {
        return (
            <div id="content" dangerouslySetInnerHTML = {{__html: this.parseHtml()}}></div>
        );
    }
}

export default Contents;