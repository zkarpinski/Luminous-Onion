import React from 'react';
import FindingList from "../components/Finding/List";
import {useSearchParams} from "react-router-dom";

const Findings = () => {
    const [filterParams] = useSearchParams();
    return (
        <div>
            <FindingList filters={[... filterParams]}/>
        </div>
    )
}

export default Findings;