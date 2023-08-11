import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {
    Button,
    CardActions,
    Container, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Tabs,
    Typography
} from "@mui/material";
import LinkTab from "../../LinkTab";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
const ProductSettings= () => {
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const [product, setProduct] = useState({name:''});
    const [findingSummary, setFSummary] = useState({critical:0,high:0,medium:0,low:0,informational:0});
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleDeleteProduct = () => {
        if (id!==null) {
            // Get the product
            api.delete(`/api/product/${id}`)
                .then(resp => {
                    console.log(resp);
                });
        }
        // TODO: Navigate back to home
        handleClose();

    }

    const handleClose = () => {
        setOpen(false);
    };


    useEffect(() => {
        setLoading(true);

        if (id!==null) {
            // Get the product
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                });

            // Get the product finding summary
            api.get(`/api/product/${id}/findings/summary`)
                .then(data => {
                    setFSummary(data.data);
                    setLoading(false);
                });

        }


    },[]);


    if (loading) {
        return "..."
    }

    return (
        <>
            <Paper>
                <ProductFindingSummary
                critical={findingSummary.critical}
                high={findingSummary.high}
                medium={findingSummary.medium}
                low={findingSummary.low}
                informational={findingSummary.informational}
                />
            </Paper>
            <Card>
                <Tabs value={2} centered aria-label="nav tabs">
                    <LinkTab label="Overview" href={`../${id}`} />
                    <LinkTab label="Findings" href={`../${id}/findings`} />
                    <LinkTab label="Settings" href={`../${id}/settings`} />
                </Tabs>
            </Card>
            <Container component={Paper} style={{marginTop: 10, marginBottom:10, padding:10}}>
                <Typography variant="h4">
                    {product.name}
                </Typography>
                <Paper>
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center" colSpan={2}>
                                    <h3>Product Details</h3>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                        <TableRow>
                            <TableCell width={150}><b>Name</b></TableCell>
                            <TableCell>{product.name}</TableCell>
                        </TableRow>
                            <TableRow>
                                <TableCell>Product Owner</TableCell>
                                <TableCell>{product.productOwner}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>Product Name</TableCell>
                                <TableCell>{product.productName}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </Paper>
            </Container>
            <Container style={{marginBottom:10, padding:10}}>
                <Card variant="outlined"  style={{ border: "1px solid red" }}>
                    <CardContent>
                        <Typography variant="h6" component="div">
                            <strong>Delete product</strong>
                        </Typography>
                        <Typography sx={{ mb: 1.5 }} color="text.secondary">
                            Delete the product and all the resources associated with it.
                        </Typography>
                        <Typography variant="body2">
                            Deleting a product means the following:
                        </Typography>
                            <ul>
                                <li>First item</li>
                                <li>Second item</li>
                                <li>Third item</li>
                            </ul>
                    </CardContent>
                    <CardActions>
                        <Button variant="contained" color="error" onClick={handleClickOpen}>Delete product</Button>
                        <Typography>
                            Warning - This cannot be undone!
                        </Typography>
                    </CardActions>
                </Card>
            </Container>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    Delete confirmation
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to permanently delete the project <strong>{product.name}</strong>?
                        <br/>
                        This cannot be undone.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleDeleteProduct}
                            autoFocus variant="contained">Yes
                    </Button>
                </DialogActions>
            </Dialog>

        </>
    );
};

export default ProductSettings;