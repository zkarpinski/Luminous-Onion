import {Box, Button, FormGroup, FormLabel, Grid, Modal, TextField, Typography} from "@mui/material";
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

const ProductNew = () => {
    const [formData, setFormData] = useReducer(formReducer, {
        name: '',
        productOwner: '',
        productTeam: ''
    });
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    // When values change, save them to the formData state
    const handleChange = event => {
        setFormData({
            name: event.target.name,
            value: event.target.value,
        });
    };

    const handleSubmit = event => {
        event.preventDefault();

        // Create new product
        api.post('/api/product',JSON.stringify(formData))
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error))
        // TODO Create a toast when product is created.
        handleClose();
    }

    return (
        <>
        <Grid>
            <Button onClick={handleOpen} variant="contained" color="primary" size="medium">Create</Button>
        </Grid>
        <Modal
            open={open}
            onClose={handleClose}
        >
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                   Create new product
                </Typography>
                    <div>
                        <form onSubmit={handleSubmit}>
                            <FormLabel>Enter details for new product below</FormLabel>
                            <FormGroup sx ={{padding:2}}>
                                <TextField
                                    required
                                    id="name"
                                    name="name"
                                    label="Product Name"
                                    helperText="Enter product name"
                                    autoComplete="off"
                                    value={formData.name}
                                    onChange={handleChange}
                                />
                                <TextField
                                    id="productOwner"
                                    name="productOwner"
                                    label="Product Owner"
                                    value={formData.productOwner}
                                    onChange={handleChange}
                                />
                                <TextField
                                    id="productTeam"
                                    name="productTeam"
                                    label="Product Team"
                                    value={formData.productTeam}
                                    onChange={handleChange}
                                />
                                <Button type="submit">Create</Button>
                            </FormGroup>
                        </form>
                    </div>
            </Box>
        </Modal>
        </>
    );
};

export default ProductNew;