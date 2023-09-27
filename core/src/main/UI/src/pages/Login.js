import React, { useEffect, useState } from "react";
import { Button, Grid, TextField, Typography } from "@mui/material";
import CardContent from "@mui/material/CardContent";
import Card from "@mui/material/Card";

export default function Login() {
  const [state, setState] = useState({ username: "", password: "" });

  useEffect(() => {
    setState({username: "", password: ""});
  }, []);

  const handleChange = (event) => {
    setState({
      name: event.target.name,
      value: event.target.value,
    });
  };

  return (
    <Grid style={{ marginTop: "60px" }}>
      <Card style={{ maxWidth: 500, padding: "20px 5px", margin: "0 auto" }}>
        <CardContent>
          <Typography gutterBottom variant="h5">
            Login
          </Typography>
          <form>
            <Grid container spacing={1}>
              <Grid item xs={12}>
                <TextField
                  type="text"
                  placeholder="Enter username"
                  label="Username"
                  name="username"
                  variant="outlined"
                  fullWidth
                  required
                  value={state.username}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  type="password"
                  placeholder="Enter password"
                  label="Password"
                  name="password"
                  variant="outlined"
                  fullWidth
                  required
                  value={state.password}
                  onChange={handleChange}
                />
              </Grid>
              <Grid item xs={12}>
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                  fullWidth
                >
                  Submit
                </Button>
              </Grid>
            </Grid>
          </form>
        </CardContent>
      </Card>
    </Grid>
  );
}
