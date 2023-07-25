import React from 'react';
import HomeIcon from '@mui/icons-material/Home';
import {Assessment, BugReport} from "@mui/icons-material";
import Tooltip from '@mui/material/Tooltip';
import { Link } from "react-router-dom";

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
            <Link to="/reports">
                <Tooltip title="Reports">
                    <Assessment color="secondary"/>
                </Tooltip>
            </Link>
        </div>
    );
};

export default SideNavbar;