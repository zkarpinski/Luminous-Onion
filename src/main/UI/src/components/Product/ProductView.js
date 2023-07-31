import React, { useEffect, useState } from 'react';
import api from '../../shared/api'
import {useParams} from "react-router-dom";
const ProductView= () => {
    const { id } = useParams();

    const [product, setProduct] = useState(null);
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

        </>
    );
};

export default ProductView;