import React from 'react';
import HomeIcon from '@mui/icons-material/Home';
import FactoryIcon from '@mui/icons-material/Factory';
import {Assessment, BugReport} from "@mui/icons-material";
import Tooltip from '@mui/material/Tooltip';
import { Link } from "react-router-dom";

// Refer to icons here: https://mui.com/material-ui/material-icons/
const SideNavbar = () => {

    return (
        <div className="sidenavbar">
            <Link to="/">
                <Tooltip title="Home">
                    <HomeIcon color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/findings">
                <Tooltip title="Findings">
                    <BugReport color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/sources">
                <Tooltip title="Sources">
                    <FactoryIcon color="secondary"/>
                </Tooltip>
            </Link>
            <Link to="/reports">
                <Tooltip title="Reports">
                    <Assessment color="secondary"/>
                </Tooltip>
            </Link>
        </div>
    );
};

export default SideNavbar;