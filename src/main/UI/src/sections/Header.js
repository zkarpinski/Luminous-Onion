import React from 'react';
import AppBar from '@mui/material/AppBar';
import {Container, Toolbar, Typography} from "@mui/material";

const Header = () => {
    return (
        <AppBar position="fixed">
            <Container maxWidth="xl">
                <Toolbar disableGutters variant="dense">
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        sx={{
                            display: { xs: 'flex'},
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.1rem',
                            color: 'inherit',
                            textDecoration: 'none',
                        }}
                    > Luminous Onion
                    </Typography>
                </Toolbar>
            </Container>
        </AppBar>
    )
}

export default Header