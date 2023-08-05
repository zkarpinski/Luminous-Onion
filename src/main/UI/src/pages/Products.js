import React from 'react';
import ProductList from "../components/Product/ProductList";
import ProductNew from "../components/Product/Forms/New";

const Products = () => {

    return (
        <div>
            <ProductNew/>
            <ProductList/>
        </div>
    )
}

export default Products;