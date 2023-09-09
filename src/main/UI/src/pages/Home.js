import React, {useEffect, useState} from 'react';
import VulnerabilityCard, {CardType} from "../components/VulnerabilityCard";
import {Box, Grid, Paper, Typography} from "@mui/material";
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
            <Grid container spacing={2}>
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

                    <Grid item xs={12}>
                        <Box component={Paper} padding={1}>
                            <Typography variant="h4" textAlign="center"> Welcome to Luminous Onion!</Typography>
                            <br/>
                            <Typography variant="body1">Luminous Onion is a cutting-edge web application designed to revolutionize vulnerability management by seamlessly ingesting security reports from a variety of 3rd party tools. With its intuitive interface and powerful features, Luminous Onion empowers organizations to take charge of their cybersecurity posture like never before.
                            </Typography>
                            <br/>
                            <Typography variant="body1"><b>Why Choose Luminous Onion:</b></Typography>
                                <ul><li>Simplicity: Luminous Onions user-friendly interface makes vulnerability management accessible to both technical and non-technical stakeholders.</li>
                                <li>Efficiency: Automated risk assessment, prioritization, and collaborative features accelerate the vulnerability remediation process.</li>
                                <li>Insight: Visualizations and analytics offer deep insights into vulnerabilities, aiding strategic decision-making.</li>
                                <li>Accuracy: Advanced algorithms ensure accurate risk scoring, enhancing the precision of your vulnerability management strategy.</li>
                                <li>Agility: Real-time updates and integration capabilities enable rapid responses to evolving threats.</li></ul>
                        </Box>
                    </Grid>
                    </Grid>
                </Grid>
                <Grid item xs={12} md={6} lg={4}>
                    <Grid item xs={12}>
                        <Box style={{'height':'300px'}} component={Paper} padding="20%">
                            <Typography variant="h4"> Coming soon...</Typography>
                        </Box>
                    </Grid>
                </Grid>
            </Grid>
        </>
    );
};

export default Home;