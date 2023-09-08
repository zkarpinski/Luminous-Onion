import React from 'react';
import FindingListv2 from "../components/Finding/Listv2";
import {useSearchParams} from "react-router-dom";

const Findings = () => {
    const [filterParams] = useSearchParams();
    return (
        <>
            <FindingListv2 filters={[... filterParams]}/>
        </>
    )
}

export default Findings;