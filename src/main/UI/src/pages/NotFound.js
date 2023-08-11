import React from 'react';
import {Link} from "react-router-dom";
import {Box, Container} from "@mui/material";

export default function NotFound() {
    return (
        <>
            <Container  sx={{ display: 'flex', alignItems: 'center', height: '90vh'}}>
                <Box>
                    <h1>404</h1>
                    <h2>Oops! You are outside of the onion.</h2>
                    Click to return back <Link to='/'>home</Link>
                </Box>
            </Container>
        </>
    );
}