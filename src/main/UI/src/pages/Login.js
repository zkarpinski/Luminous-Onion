import React from 'react';
import {Box, Paper} from "@mui/material";

export default function Login() {
    return(
        <Box>
            <Paper className="login-wrapper">
                <h1>Login</h1>
                <form>
                    <label>
                        <p>Username</p>
                        <input type="text" />
                    </label>
                    <label>
                        <p>Password</p>
                        <input type="password" />
                    </label>
                    <div>
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </Paper>
        </Box>
    )
}