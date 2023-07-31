import {Box, Button, Grid, Modal, Typography} from "@mui/material";
import React from "react";
import api from "../shared/api";

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
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const [uploadFile, setUploadFile] = React.useState();


    const handleChange = (event) => {
        console.log("File changed!")
        setUploadFile(event.target.files[0])
    };

    const handleSubmit = event => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('file',uploadFile);


        api.postFile('/upload/grype/json',formData)
            .then((response) => console.log("Success:", JSON.stringify(response)))
            .catch((error) => console.error("Error:", error));
        handleClose();
    }

    return (
        <>
        <Grid>
            <Button onClick={handleOpen} variant="contained" color="secondary" size="medium">Upload</Button>
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
                    <div>
                        <form onSubmit={handleSubmit}>
                            <input
                                accept="application/JSON"
                                id="upload-json-button"
                                name="file"
                                single="single"
                                type="file"
                                onChange={handleChange}
                            />
                            <Button type="submit">Upload</Button>
                        </form>
                    </div>
            </Box>
        </Modal>
        </>
    );
};

export default NewSourcePopupComponent;