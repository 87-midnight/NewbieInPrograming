
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
import Contents from "./Content";
import list from "./mdInfo";
import React from 'react';
import {
    BrowserRouter as Router,
    Route,
    Link
}from 'react-router-dom';
const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;
const a = 'https://github.com/87-midnight/NewbieInJava/基础篇/README.md';
class SiderHome extends React.Component {
    state = {
        collapsed: false,
    };
    query = {
        pathname:`/content?source=${a}`,
        // pathname:"/content",
        // state:{path:"/NewbieInJava/基础篇/README.md"},
    };
    onCollapse = collapsed => {
        console.log(collapsed);
        this.setState({ collapsed });
    };
    onClick = event => {
        console.log(event);
    };
    tree = node =>{
        if (node.children !== undefined && node.children.length >0){
            for (let c of node.children){
                return (<SubMenu key={node.id} title={<span>{node.name}</span>}>
                        {this.tree(c)}
                    </SubMenu>)
            }
        }
        return(<Menu.Item key={node.id}>{node.name}</Menu.Item>);
    };
    render() {

        let s = list.files.map(s=>{
            return this.tree(s);
        });
        let menu = list.files.map(
            function (s) {
                return(<Menu.Item key={s.id}>{s.name}</Menu.Item>)
            }
        );
        return (
            <Router>
            <Layout style={{ minHeight: '100vh' }}>
                <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
                    <div className="logo" />
                    <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                        <Menu.Item key="1" onClick = {(e)=>this.onClick(e)}>
                            <Icon type="pie-chart" />
                            <span><Link to={this.query}>Option 1</Link></span>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <Icon type="desktop" />
                            <span>Option 2</span>
                        </Menu.Item>
                        <SubMenu
                            key="sub1"
                            title={
                                <span>
                  <Icon type="user" />
                  <span>User</span>
                </span>
                            }
                        >
                            {s}
                        </SubMenu>
                        <SubMenu
                            key="sub2"
                            title={
                                <span>
                  <Icon type="team" />
                  <span>Team</span>
                </span>
                            }
                        >
                            <Menu.Item key="6">Team 1</Menu.Item>
                            <Menu.Item key="8">Team 2</Menu.Item>
                        </SubMenu>
                        <Menu.Item key="9">
                            <Icon type="file" />
                            <span>File</span>
                        </Menu.Item>
                    </Menu>
                </Sider>
                <Layout>
                    <Header style={{ background: '#fff', padding: 0 }} />
                    <Content style={{ margin: '0 16px' }}>
                        <Breadcrumb style={{ margin: '16px 0' }}>
                            <Breadcrumb.Item>User</Breadcrumb.Item>
                            <Breadcrumb.Item>Bill</Breadcrumb.Item>
                        </Breadcrumb>
                        <div style={{ padding: 24, background: '#fff', minHeight: 360 }}><Route path="/content" component={Contents}></Route></div>
                    </Content>
                    <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
                </Layout>
            </Layout>
            </Router>
        );
    }
}
export default SiderHome;
