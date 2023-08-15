import React, { useEffect } from 'react';
import {useParams} from "react-router-dom";
const ProductEdit = () => {
    const { id } = useParams();

    // TODO: Add Product Edit feature

    useEffect(() => {
    },[]);

    return (
        <div>
            <h1>Product {id} edit page. Coming soon..</h1>
        </div>
    );
};

export default ProductEdit;