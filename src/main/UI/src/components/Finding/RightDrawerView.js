import React from "react";
import { Drawer } from "@mui/material";

const drawerWidth = 300;
/** TODO: Create right side drawer that pops out with the findings details
 *
 * @param opened
 * @returns {Element}
 * @constructor
 */
const FindingRightDrawerView = ({ opened }) => {
  return (
    <Drawer
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        "& .MuiDrawer-paper": {
          width: drawerWidth,
          zIndex: 2,
        },
      }}
      variant="persistent"
      anchor="right"
      open={opened}
    ></Drawer>
  );
};

export default FindingRightDrawerView;
