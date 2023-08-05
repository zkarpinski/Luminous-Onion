import SideNavBar from "../sections/SideNavbar";
import Header from "../sections/Header";
import React from "react";
import { styled, useTheme } from "@mui/material";
import { headerHeight, sideNavBarWidth } from "../shared/constants";


// Style main content
const Main = styled('main')(({ theme }) => ({
    width: `100%`,
    marginTop: `${headerHeight}px`,
    marginLeft: `${sideNavBarWidth}px`,
    backgroundColor: theme.palette.background.default,
    padding: '15px',
    flexGrow:1
}));


const RootContainer = styled('div')(() => ({
    display: 'flex',
    flexDirection: 'row',
    flexGrow: 1,
    height: '100vh',
    zIndex: 1
}));

const Layout = ({children: content}) => {

    const theme = useTheme();

    return (
        <RootContainer>
            <Header/>
            <SideNavBar/>
            <Main theme ={theme} >{content}</Main>
        </RootContainer>
    )
}

export default Layout
