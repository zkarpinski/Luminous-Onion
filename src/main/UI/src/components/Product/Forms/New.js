import {Box, Button, Grid, Modal, TextField, Typography} from "@mui/material";
import React, {useReducer} from "react";
import api from "../../../shared/api";

const style = {
    width: 500,
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    padding: 4,
};

const formReducer = (state,event) => {
    return {
        ...state,
        [event.name]: event.value
    }
}

function ProductNew ({open,handleClose}) {
    const [formData, setFormData] = useReducer(formReducer, {
        name: '',
        productOwner: '',
        productTeam: '',
        org:'1'
    });
    const [reload, setReload] = React.useState(false);


    // When values change, save them to the formData state
    const handleChange = event => {
        setFormData({
            name: event.target.name,
            value: event.target.value,
        });
    };

    const handleSubmit = event => {
        event.preventDefault();
        var createdID;

        // Create new product
        api.post('/api/product',JSON.stringify(formData))
            .then((response) => createdID = response.id )
            .catch((error) => console.error("Error:", error))
            .finally(() => {
                // TODO Create a toast when product is created.
                console.log("Product Created:" + createdID);
                handleClose();
            })
    }

    if(reload) {
        setReload(false);
    }

    return (
        <>
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                   Create new product
                </Typography>
                        <form onSubmit={handleSubmit}>
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                <TextField
                                    required
                                    id="name"
                                    name="name"
                                    label="Product Name"
                                    helperText="Enter product name"
                                    autoComplete="off"
                                    autoFocus
                                    value={formData.name}
                                    onChange={handleChange}
                                    fullWidth
                                />
                                </Grid>
                                <Grid item xs={12}>
                                <TextField
                                    id="productOwner"
                                    name="productOwner"
                                    label="Product Owner"
                                    autoComplete="off"
                                    value={formData.productOwner}
                                    onChange={handleChange}
                                    fullWidth
                                />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        id="productTeam"
                                        name="productTeam"
                                        label="Product Team"
                                        autoComplete="off"
                                        value={formData.productTeam}
                                        onChange={handleChange}
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Button type="submit">Create</Button>
                                </Grid>
                            </Grid>
                        </form>
            </Box>
        </Modal>
        </>
    );
}

export default ProductNew;