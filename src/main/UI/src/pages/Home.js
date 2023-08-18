import React, {useEffect, useState} from 'react';
import VulnerabilityCard, {CardType} from "../components/VulnerabilityCard";
import {Box, Grid, Paper} from "@mui/material";
import LastUploadCard from "../components/LastUploadCard";
import api from "../shared/api";
import ProductSourceGrid from "../components/Home/ProductSourceGrid";

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
                <Grid item xs={12} md={6} lg={8}>
                    <Grid container spacing={1.5}>
                        <Grid item xs={12} sm={6}  lg={3}>
                            <VulnerabilityCard cardType={CardType.Critical} value={fSummary.CRITICAL}/>
                        </Grid>
                        <Grid item xs={12} sm={6}  lg={3}>
                            <VulnerabilityCard cardType={CardType.High} value={fSummary.HIGH}/>
                        </Grid>
                        <Grid item xs={12} sm={6} lg={3}>
                            <VulnerabilityCard cardType={CardType.Medium} value={fSummary.MEDIUM} />
                        </Grid>
                        <Grid item xs={12} sm={6} lg={3}>
                            <VulnerabilityCard cardType={CardType.Low} value={fSummary.LOW}/>
                        </Grid>
                    </Grid>
                    <Grid item sm={12}>
                        <Box style={{'height':'300px'}} component={Paper}>

                        </Box>
                    </Grid>
                    <Grid item xs={12} xl={10}>
                        <LastUploadCard/>
                    </Grid>
                </Grid>
                <Grid item xs={12} md={6} lg={4}>
                    <Grid item xs={12}>
                        <ProductSourceGrid/>
                    </Grid>


                </Grid>
            </Grid>
        </>
    );
};

export default Home;