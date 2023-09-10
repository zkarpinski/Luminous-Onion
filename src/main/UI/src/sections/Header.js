import React from "react";
import AppBar from "@mui/material/AppBar";
import { Box, Container, IconButton, Toolbar, Typography } from "@mui/material";

const Header = () => {
  return (
    <AppBar position="fixed">
      <Container maxWidth="100%">
        <Toolbar disableGutters variant="dense">
          <Box sx={{ flexGrow: 0 }}>
            <IconButton sx={{ p: 0 }}>
              <img
                alt="logo"
                src="/static/img/luminous-onion-logo-small.png"
                height="40"
              />
            </IconButton>
          </Box>
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              display: { xs: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".1rem",
              color: "inherit",
              textDecoration: "none",
              paddingLeft: 1,
            }}
          >
            {" "}
            Luminous Onion
          </Typography>
        </Toolbar>
      </Container>
    </AppBar>
  );
};

export default Header;
