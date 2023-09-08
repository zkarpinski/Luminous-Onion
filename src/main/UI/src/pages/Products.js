import React, {useState} from 'react';
import ProductList from "../components/Product/List";
import ProductNew from "../components/Product/Forms/New";
import {Button, Grid} from "@mui/material";

const Products = () => {
    const [newProductVisible, setNewProductVisible] = useState(false);
    const handleOpen = () => setNewProductVisible(true);
    const handleClose = () => {
        setNewProductVisiblefalse(false)
        //TODO Refresh page
        setReload(true);
    };    return (
        <>
            <Grid>
                <Button onClick={handleOpen} variant="contained" color="primary" size="medium">Create</Button>
            </Grid>
            <ProductList/>
            {newProductVisible && (<ProductNew open={newProductVisible} onClose={handleClose}/>)}
        </>
    )
}

export default Products;