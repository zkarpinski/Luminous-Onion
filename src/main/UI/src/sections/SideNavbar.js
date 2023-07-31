import React from 'react';
import HomeIcon from '@mui/icons-material/Home';
import FactoryIcon from '@mui/icons-material/Factory';
import CategoryIcon from '@mui/icons-material/Category';
import {Assessment, BugReport} from "@mui/icons-material";
import Tooltip from '@mui/material/Tooltip';
import { Link } from "react-router-dom";
import {Box} from "@mui/material";

// Refer to icons here: https://mui.com/material-ui/material-icons/
const SideNavbar = () => {

    return (
        <Box component="nav" className="sidenavbar">
            <Link to="/">
                <Tooltip title="Home">
                    <HomeIcon color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/product">
                <Tooltip title="Products">
                    <CategoryIcon color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/finding">
                <Tooltip title="Findings">
                    <BugReport color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/source">
                <Tooltip title="Sources">
                    <FactoryIcon color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/report">
                <Tooltip title="Reports">
                    <Assessment color="secondary"/>
                </Tooltip>
            </Link>
        </Box>
    );
};

export default SideNavbar;