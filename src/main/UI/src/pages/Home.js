import React from 'react';
import VulnerabilityCard from "../components/VulnerabilityCard";
import {Grid} from "@mui/material";

const Home = () => {
    return (
        <div>
            <Grid container spacing="10px">
                    <VulnerabilityCard CardType='Critical' Value={100}/>
                    <VulnerabilityCard CardType='High' Value={200}/>
                    <VulnerabilityCard CardType='Medium' Value={300} />
                    <VulnerabilityCard CardType='Low' Value={777}/>
            </Grid>
        </div>
    );
};

export default Home;