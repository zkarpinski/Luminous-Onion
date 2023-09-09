import React from 'react';
import ProductList from "../components/Product/List";
import ProductNew from "../components/Product/Forms/New";

const Products = () => {

    return (
        <>
            <ProductNew/>
            <ProductList/>
        </>
    )
}

export default Products;