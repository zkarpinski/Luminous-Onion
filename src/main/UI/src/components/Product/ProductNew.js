import {Box, Button, FormControl, Grid, Modal, TextField, Typography} from "@mui/material";
import React from "react";

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

    const [name, setName] = React.useState("");
    const [productOwner, setProductOwner] = React.useState("");

    const handleSubmit = event => {
        event.preventDefault();

        fetch("/api/products/new", {
            method: "POST",
            body: JSON.stringify({
                name: name,
                productOwner: productOwner
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then((response) => response.json())
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error));

        alert('You have submitted the form.');
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
                                id="productName"
                                label="Product Name"
                                helperText="Enter product name"
                                autoComplete="off"
                            />
                            <TextField
                                id="productOwner"
                                label="Product Owner"
                            />
                            <TextField
                                id="productTeam"
                                label="Product Team"
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