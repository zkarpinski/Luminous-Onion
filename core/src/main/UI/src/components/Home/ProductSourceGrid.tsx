/* eslint-disable no-undef */
import * as React from "react";
import { Grid, Paper, Typography } from "@mui/material";

export default function ProductSourceGrid() {
  return (
    <Paper>
      <Grid container>
        <Grid item xs={12} sm={6}>
          <Grid container spacing={1}>
            <Grid item>
              <Typography variant="h5" align="center">
                A
              </Typography>
            </Grid>
            <Grid xs item>
              <Typography variant="h5" align="center">
                10
              </Typography>
              <Typography variant="h5" align="center">
                Projects
              </Typography>
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={12} sm={6}>
          <Grid container>
            <Grid item>
              <Typography variant="h5" align="center">
                A
              </Typography>
            </Grid>
            <Grid xs item>
              <Typography variant="h5" align="center">
                4
              </Typography>
              <Typography variant="h5" align="center">
                Sources
              </Typography>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
      <Grid container>
        <Grid item xs={12} sm={6}>
          C
        </Grid>
        <Grid item xs={12} sm={6}>
          D
        </Grid>
      </Grid>
    </Paper>
  );
}
