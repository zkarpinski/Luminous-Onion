import React from 'react';
import FindingList from "../components/Finding/List";
import {useSearchParams} from "react-router-dom";

const Findings = () => {
    const [filterParams] = useSearchParams();
    return (
        <>
            <FindingList filters={[... filterParams]}/>
        </>
    )
}

export default Findings;