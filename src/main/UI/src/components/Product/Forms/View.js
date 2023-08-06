import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {Container, Paper, Table, TableBody, TableCell, TableHead, TableRow, Typography} from "@mui/material";
const ProductView= () => {
    const { id } = useParams();

    const [product, setProduct] = useState({name:''});
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        if (id!==null) {

            // Get the product list
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                    setLoading(false)
                });
        }


    },[]);


    if (loading) {
        return "..."
    }


    return (
        <>
            <ProductFindingSummary
            critical={0}
            high={1}
            medium={0}
            low={2}
            informational={3}
            />
            <Container component={Paper}>
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
        </>
    );
};

export default ProductView;