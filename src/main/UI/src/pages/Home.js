import React from 'react';
import VulnerabilityCard from "../components/VulnerabilityCard";

const Home = () => {
    return (
        <div>
        <h1>Home sweet home</h1>

            <div className="vulRow">
                <VulnerabilityCard CardType='Critical' Value={100}/>
                <VulnerabilityCard CardType='High' Value={200}/>
                <VulnerabilityCard CardType='Medium' Value={300} />
                <VulnerabilityCard CardType='Low' Value={777}/>
            </div>
        </div>
    );
};

export default Home;