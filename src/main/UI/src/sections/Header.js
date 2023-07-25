import React from 'react';
import AppBar from '@mui/material/AppBar';
import {Container, Toolbar, Typography} from "@mui/material";

const Header = () => {
    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters variant="dense">
                    <Typography
                        variant="h6"
                        noWrap
                        component="a"
                        href="/"
                        sx={{
                            display: { xs: 'none', md: 'flex' },
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
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