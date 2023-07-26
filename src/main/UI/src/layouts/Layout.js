import SideNavBar from "../sections/SideNavbar";
import Header from "../sections/Header";
import React from "react";
const Layout = ({children: content}) => {
    return (
        <div>
            <Header/>
            <SideNavBar/>
        <div className="App-content">{content}</div>
        </div>
    )
}

export default Layout
