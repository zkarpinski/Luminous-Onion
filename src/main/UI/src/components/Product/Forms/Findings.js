import React, { useEffect } from 'react';
import {useParams, useSearchParams} from "react-router-dom";
import {
    Container,
    Paper,
    Typography
} from "@mui/material";
import FindingList from "../../Finding/List";
import FindingRightDrawerView from "../../Finding/RightDrawerView";

const ProductFindings= () => {
    const { id } = useParams();
    const [filterParams] = useSearchParams();
    const findingEndpoint = `/api/product/${id}/findings`

    const myParam = filterParams.get('sourceId');
    console.log(myParam)

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
                    <FindingList endpoint={findingEndpoint} scope="product" filters={[...filterParams]}/>
                </Paper>
            </Container>
        </>
    );
};

export default ProductFindings;