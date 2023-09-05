import React, { useEffect } from 'react';
import {useParams} from "react-router-dom";
import {
    Container,
    Paper,
    Typography
} from "@mui/material";
import FindingList from "../../Finding/List";
import FindingRightDrawerView from "../../Finding/RightDrawerView";
const ProductFindings= () => {
    const { id } = useParams();
    const findingEndpoint = `/api/product/${id}/findings`

    useEffect(() => {

    },[]);

    return (
        <>
            <FindingRightDrawerView opened={false}/>
            <Container component={Paper} style={{marginBottom:10, padding:10}} maxWidth="100vw">
                <Typography variant="h5">
                    Findings
                </Typography>
                <Paper>
                    <FindingList endpoint={findingEndpoint} scope="product"/>
                </Paper>
            </Container>
        </>
    );
};

export default ProductFindings;