import React from 'react';
import HomeIcon from '@mui/icons-material/Home';
import FactoryIcon from '@mui/icons-material/Factory';
import CategoryIcon from '@mui/icons-material/Category';
import { BugReport } from "@mui/icons-material";
import Tooltip from '@mui/material/Tooltip';
import { Box } from "@mui/material";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";


function SideNavBarLink(props) {
    const { icon, primary, to } = props;

    return (
            <Link to={to} aria-label={primary}>
                <Tooltip title={primary}>
                    {icon}
                </Tooltip>
            </Link>
    );
}

SideNavBarLink.propTypes = {
    to: PropTypes.string.isRequired,
    primary: PropTypes.string.isRequired,
    icon: PropTypes.element,
};


// Refer to icons here: https://mui.com/material-ui/material-icons/
const SideNavbar = () => {

    return (
        <Box component="nav" className="sidenavbar">
            <SideNavBarLink to="/" primary="Home" icon={<HomeIcon color="secondary" />}/>
            <SideNavBarLink to="/product" primary="Products" icon={<CategoryIcon color="secondary" />}/>
            <SideNavBarLink to="/source" primary="Sources" icon={<FactoryIcon color="secondary" />}/>
            <SideNavBarLink to="/finding" primary="Findings" icon={<BugReport color="secondary" />}/>
        </Box>
    );
};

export default SideNavbar;