import {Box, Button, FormControl, Grid, Modal, TextField, Typography} from "@mui/material";
import React from "react";
import api from "../../shared/api";

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

const ProductNew = () => {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const [formData, setFormData] = React.useState({"name":"", productOwner:"",productTeam:""});

    // When values change, save them to the formData state
    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
    };

    const handleSubmit = event => {
        event.preventDefault();

        // Create new product
        api.post('/api/product',formData)
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error));
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
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                   Create Product
                </Typography>
                    <div>
                        <form onSubmit={handleSubmit}>
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
                        </form>
                    </div>
            </Box>
        </Modal>
        </>
    );
};

export default ProductNew;