import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import {
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import FindingList from "../../Finding/List";
import FindingRightDrawerView from "../../Finding/RightDrawerView";
const ProductFindings= () => {
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const [product, setProduct] = useState({name:''});
    const findingEndpoint = `/api/product/${id}/findings`

    useEffect(() => {
        setLoading(true);

        if (id!==null) {
            // Get the product
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                    setLoading(false);
                });

        }

    },[]);

    return (
        <>
            <FindingRightDrawerView opened={false}/>
            <Container component={Paper} style={{marginTop: 10, marginBottom:10, padding:10}}>
                <Typography variant="h4">
                    {loading ? "--" : product.name }
                </Typography>
                <Paper>
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center" colSpan={2}>
                                    <h3>Product Details</h3>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        <TableRow>
                            <TableCell width={150}><b>Name</b></TableCell>
                            <TableCell>{product.name}</TableCell>
                        </TableRow>
                            <TableRow>
                                <TableCell>Product Owner</TableCell>
                                <TableCell>{product.productOwner}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Product Name</TableCell>
                                <TableCell>{product.productName}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </Paper>
            </Container>
            <Container component={Paper} style={{marginBottom:10, padding:10}} maxWidth="100vw">
                <Typography variant="h5">
                    Findings
                </Typography>
                <Paper>
                    <FindingList endpoint={findingEndpoint}/>
                </Paper>
            </Container>
        </>
    );
};

export default ProductFindings;