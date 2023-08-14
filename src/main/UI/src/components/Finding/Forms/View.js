import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import {
    Container,
    Paper,
    Typography
} from "@mui/material";

const FindingView= () => {
    const { id } = useParams();
    const [finding, setFinding] = useState({name:'', id:'',description:'',packagePath:'', source:{id:'',product:{name:''}}});


    useEffect(() => {

        if (id!==null) {
            // Get the source
            api.get(`/api/findings/${id}`)
                .then(resp => {
                    setFinding(resp.data);
                });


        }


    },[]);

    return (
        <div>
            <Container component={Paper} style={{marginTop: 10, marginBottom:10, padding:10}}>
                <Typography variant="h5"> {finding.packageName}:{finding.packageVersionFound} </Typography>
                <Typography>Vulnerability: {finding.findingIdentifier} | Severity: {finding.severity} | </Typography>
                <Typography>Path: {finding.packagePath}</Typography>
                <Typography variant="body2">{finding.description} </Typography>
            </Container>
        </div>
    );
};

export default FindingView;