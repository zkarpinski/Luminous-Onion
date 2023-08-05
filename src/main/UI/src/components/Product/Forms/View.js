import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {Container, Paper, Typography} from "@mui/material";
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
            </Container>
        </>
    );
};

export default ProductView;