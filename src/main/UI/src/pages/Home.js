import React from 'react';
import VulnerabilityCard, {CardType} from "../components/VulnerabilityCard";
import {Box, Grid, Paper} from "@mui/material";
import LastUploadCard from "../components/LastUploadCard";

const Home = () => {
    return (
        <>
            <Grid container spacing="10px">
                    <VulnerabilityCard cardType={CardType.Critical} value={100}/>
                    <VulnerabilityCard cardType={CardType.High} value={200}/>
                    <VulnerabilityCard cardType={CardType.Medium} value={300} />
                    <VulnerabilityCard cardType={CardType.Low} value={777}/>
            </Grid>
            <Box style={{'height':'300px'}} component={Paper}>

            </Box>
            <LastUploadCard/>
        </>
    );
};

export default Home;