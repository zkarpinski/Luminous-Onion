import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {Container, Paper, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";
const ProductView= () => {
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const [product, setProduct] = useState({name:''});
    const [findingSummary, setFSummary] = useState({critical:0,high:0,medium:0,low:0,informational:0});
    const [sources, setSources] = useState([]);


    useEffect(() => {
        setLoading(true);

        if (id!==null) {
            // Get the product
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                });

            // Get the product finding summary
            api.get(`/api/product/${id}/findings/summary`)
                .then(data => {
                    setFSummary(data.data);
                });

            // Get the product sources
            api.get(`/api/product/${id}/sources`)
                .then(data => {
                    setSources(data);
                    setLoading(false)
                });
        }


    },[]);


    if (loading) {
        return "..."
    }
    else {
        // Print debugging
        console.log(findingSummary)
        console.log(sources)
    }



    return (
        <>
            <Paper>
                <ProductFindingSummary
                critical={findingSummary.critical}
                high={findingSummary.high}
                medium={findingSummary.medium}
                low={findingSummary.low}
                informational={findingSummary.informational}
                />
            </Paper>
            <Container component={Paper} style={{marginTop: 10, marginBottom:10, padding:10}}>
                <Typography variant="h4">
                    {product.name}
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
            <Container component={Paper} style={{marginBottom:10, padding:10}}>
                <Typography variant="h5">
                    Sources
                </Typography>
                <Paper>
                    <Table size="small">
                        <TableBody>
                            {sources.map((row) => (
                                <TableRow
                                    key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell>{row.tool}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Paper>
            </Container>
        </>
    );
};

export default ProductView;