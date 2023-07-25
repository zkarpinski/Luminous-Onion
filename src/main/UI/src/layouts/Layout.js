import SideNavBar from "../sections/SideNavbar";
import Header from "../sections/Header";
import React from "react";
const Layout = ({children: content}) => {
    return (
        <>
            <Header/>
            <SideNavBar/>
        <div className="App-content">{content}</div>
        </>
    )
}

export default Layout
