import {
    Box,
    Button,
    FormControlLabel,
    FormGroup,
    Grid,
    MenuItem,
    Modal,
    Switch,
    TextField,
    Typography
} from "@mui/material";
import React, {useEffect, useReducer} from "react";
import api from "../../../shared/api";
import {Add} from "@mui/icons-material";
import { PropTypes } from "prop-types";

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

const NewSourcePopupComponent = (props) => {
    const [tempFormData, setFormData] = useReducer(formReducer, {
        label: '',
        productId: '',
        sourceTool: '',
        archivePrevious: false
    });
    const [open, setOpen] = React.useState(false);
    const [products, setProducts] = React.useState([]);
    const [sourceTools, setSourceTools] = React.useState([]);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [uploadFile, setUploadFile] = React.useState("");

    useEffect(() => {
       api.get("/api/product")
           .then((data) => setProducts(data));
        api.get("/api/resources/sourcetools")
            .then((data) => setSourceTools(data));

        if (props.productID != null) {
            setFormData({name:"productId", value:props.productID});
        }
    },[]);

    const handleFileChange = (event) => {
        console.log("File changed!")
        setUploadFile(event.target.files[0])
    };

    const handleChange = event => {
        console.log(event.target);
        setFormData({
            name: event.target.name,
            value: event.target.value,
        });
    };

    const handleSubmit = event => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('file',uploadFile);
        formData.append('productId',tempFormData.productId); //TODO: Iterate over tempFormData
        formData.append('sourceTool',tempFormData.sourceTool);
        formData.append("archivePrevious",tempFormData.archivePrevious)
        console.log(formData);
        console.log(tempFormData);

        api.postFile('/upload',formData)
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error));
        handleClose();
    }

    return (
        <>
            <Button onClick={handleOpen} variant="contained" color="primary" startIcon={<Add />}>New</Button>
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
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                    <TextField required label="Label" id ="label" name="label" placeholder="Enter label for new source" sx={{ width: '100%' }}
                                               onChange={handleChange} autoFocus>
                                    </TextField>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField select required name="productId" label="Product" placeholder="Select Product" sx={{ width: '100%' }}
                                    onChange={handleChange} value={tempFormData.productId} disabled={props.productID != null}>
                                        {products.map((product) => (
                                            <MenuItem key={product.id} value={product.id}>
                                                {product.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField select required name="sourceTool" label="Source Type" placeholder="Select Source Type"
                                               sx={{ width: '100%' }} value={tempFormData.sourceTool} onChange={handleChange}>
                                        {sourceTools.map((tool) => (
                                            <MenuItem key={tool.value} value={tool.value}>
                                                {tool.label}
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
                                <Grid item xs={12}>
                                    <FormGroup style={{flexDirection:'row'}} >
                                        <FormControlLabel control={<Switch defaultChecked/>} label="Archive Results" name="archivePrevious" onChange={handleChange}/>
                                    </FormGroup>

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

NewSourcePopupComponent.propTypes = {
    productID:PropTypes.string
};

export default NewSourcePopupComponent;