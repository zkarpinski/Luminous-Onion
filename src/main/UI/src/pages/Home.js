import React, {useEffect, useState} from 'react';
import VulnerabilityCard, {CardType} from "../components/VulnerabilityCard";
import {Box, Grid, Paper} from "@mui/material";
import LastUploadCard from "../components/LastUploadCard";
import api from "../shared/api";

const Home = () => {

    const [fSummary, setFSummary] = useState({CRITICAL: 0, HIGH: 0, MEDIUM: 0, LOW: 0, INFORMATIONAL: 0});

    useEffect(() => {
        // Get the product
        api.get(`/api/orgs/1/findings/summary`)
            .then(data => {
                setFSummary(data.data);
            });
    },[]);

    return (
        <>
            <Grid container spacing="10px">
                    <VulnerabilityCard cardType={CardType.Critical} value={fSummary.CRITICAL}/>
                    <VulnerabilityCard cardType={CardType.High} value={fSummary.HIGH}/>
                    <VulnerabilityCard cardType={CardType.Medium} value={fSummary.MEDIUM} />
                    <VulnerabilityCard cardType={CardType.Low} value={fSummary.LOW}/>
                    <VulnerabilityCard cardType={CardType.Info} value={fSummary.INFORMATIONAL}/>
                <Grid item sm={12}>
                    <Box style={{'height':'300px'}} component={Paper}>

                    </Box>
                </Grid>
                <Grid item xs={12} lg={6}>
                    <LastUploadCard/>
                </Grid>
            </Grid>

        </>
    );
};

export default Home;