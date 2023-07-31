import SideNavBar from "../sections/SideNavbar";
import Header from "../sections/Header";
import React from "react";
import {Box, styled, useTheme} from "@mui/material";
import {headerHeight, sideNavBarWidth} from "../shared/constants";


// Style main content
const Main = styled('main')(({ theme }) => ({
    width: `100%`,
    marginTop: `${headerHeight}px`,
    marginLeft: `${sideNavBarWidth}px`,
    padding: '15px'
}));

const Layout = ({children: content}) => {

    const theme = useTheme();

    return (
        <Box sx={{ display: 'flex' }}>
            <Header/>
            <SideNavBar/>
            <Main theme ={theme} >{content}</Main>
        </Box>
    )
}

export default Layout
