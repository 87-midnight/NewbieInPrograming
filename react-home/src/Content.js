import React, { Component } from 'react';

class Contents extends Component {

    query(){
        let state = this.props.location.state;
        return state.path;
    }
    render() {
        return (
            <div id="content">{this.query()}hello,I'm ready to render readme files.</div>
        );
    }
}

export default Contents;