import React from 'react';
import {Tab} from "@mui/material";

function LinkTab(props) {
    return (
        <Tab component="a"
            {...props}
        />
    );
}

export default LinkTab;