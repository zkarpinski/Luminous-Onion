import {Box, Button, Grid, MenuItem, Modal, TextField, Typography} from "@mui/material";
import React, {useEffect} from "react";
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

const NewSourcePopupComponent = () => {
    const [open, setOpen] = React.useState(false);
    const [products, setProducts] = React.useState([]);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [formD, setFormData] = React.useState({product:"",file:null});
    const [uploadFile, setUploadFile] = React.useState("");

    useEffect(() => {
       api.get("/api/product")
           .then((data) => setProducts(data));
    },[]);

    const handleFileChange = (event) => {
        console.log("File changed!")
        setUploadFile(event.target.files[0])
    };

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData((prevFormData) => ({ ...prevFormData, [name]: value }));
    };

    const handleSubmit = event => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('file',uploadFile);
        formData.append('productId',1); //TODO: Change from hardcode
        formData.append('sourceTool',"AQUA_TRIVY");
        formData.append("test",formD.product)

        api.postFile('/upload',formData)
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error));
        handleClose();
    }

    return (
        <>
        <Grid>
            <Button onClick={handleOpen} variant="contained" color="secondary" size="medium">New Source</Button>
        </Grid>
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                    New Source
                </Typography>
                    <Grid>
                        <form onSubmit={handleSubmit}>
                            <Grid xs={12} container spacing={1}>
                                <Grid item xs={12}>
                                    <TextField required label="Label" placeholder="Enter label for new source" sx={{ width: '100%' }}
                                               onChange={handleChange}>
                                    </TextField>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField select required label="Product" placeholder="Select Product" sx={{ width: '100%' }}
                                    onChange={handleChange}>
                                        {products.map((product) => (
                                            <MenuItem key={product.id} value={product.id}>
                                                {product.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField select required label="Source Type" placeholder="Select Source Type"  sx={{ width: '100%' }}>
                                        {products.map((product) => (
                                            <MenuItem key={product.id} value={product.id}>
                                                {product.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <input
                                        accept="application/JSON"
                                        id="upload-json-button"
                                        name="file"
                                        type="file"
                                        onChange={handleFileChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField multiline rows={4} label="Details" placeholder="Enter details" sx={{ width: '100%' }}
                                               onChange={handleChange}>
                                    </TextField>
                                </Grid>
                                <Grid item xs={2}>
                                    <Button type="submit">Submit</Button>
                                </Grid>
                            </Grid>
                        </form>
                    </Grid>
            </Box>
        </Modal>
        </>
    );
};

export default NewSourcePopupComponent;