import React from 'react';
import VulnerabilityCard from "../components/VulnerabilityCard";
import {Grid, Paper} from "@mui/material";
import StackedAreaChart from "../components/Graph";

const Home = () => {
    return (
        <div>
            <Grid container spacing="10px">
                    <VulnerabilityCard cardType='Critical' value={100}/>
                    <VulnerabilityCard cardType='High' value={200}/>
                    <VulnerabilityCard cardType='Medium' value={300} />
                    <VulnerabilityCard cardType='Low' value={777}/>
            </Grid>
            <Paper>
                <StackedAreaChart/>
            </Paper>
        </div>
    );
};

export default Home;