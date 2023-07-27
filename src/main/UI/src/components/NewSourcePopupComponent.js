import {Box, Button, FormControl, Grid, Modal, Typography} from "@mui/material";
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

const NewSourcePopupComponent = () => {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

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
                <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                    <div>
                        <FormControl>
                            <input
                                accept="application/JSON"
                                id="upload-json-button"
                                single
                                type="file"
                            />
                        </FormControl>
                    </div>
                </Typography>
            </Box>
        </Modal>
        </>
    );
};

export default NewSourcePopupComponent;