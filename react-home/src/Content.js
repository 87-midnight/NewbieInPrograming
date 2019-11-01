import React, { Component } from 'react';
import marked from 'marked'
import hljs from "highlight.js";
import 'highlight.js/styles/monokai-sublime.css';
import axios from "axios";
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

class Contents extends Component {

    state = {
      html:"<div>default</div>"
    };
    parseHtml(path){
        axios.get(path).then(s=>{
            let res = marked(s.data);
            console.log(res);
            this.setState({html:res});
        });
    }
    query(){
        let state = this.props.location.state;
        console.log(state);
        return state.path;
    }
    render() {
        this.parseHtml(this.query());
        return (
            <div id="content" dangerouslySetInnerHTML = {{__html:this.state.html }}></div>
        );
    }
}

export default Contents;