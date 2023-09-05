import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import {
    Box, Button,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import NewSourcePopupComponent from "../../Source/Forms/New";
import EditIcon from '@mui/icons-material/Edit';
import FindingSummarySmall from "../../Finding/FindingSummarySmall";
import dayjs from "dayjs";
import {outputDateFormat} from "../../../shared/constants";
const ProductView= () => {
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const editURI = `${id}/edit`;
    const [product, setProduct] = useState({name:''});
    const [sources, setSources] = useState([]);

    useEffect(() => {
        setLoading(true);

        if (id!==null) {
            // Get the product
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                });

            // Get the product sources
            api.get(`/api/product/${id}/sources`)
                .then(data => {
                    data.sort((a,b) => a.createTimestamp > b.createTimestamp).reverse();
                    setSources(data);
                    setLoading(false)
                });
        }


    },[]);


    if (!loading) {
        console.log(sources)
    }


    return (
        <>
            <Container component={Paper} style={{marginTop: 10, marginBottom:10, padding:10}}>
                <Box
                    component="span"
                    m={1}
                    display="flex"
                    justifyContent="space-between"
                    alignItems="center"
                >
                    <Typography variant="h5"></Typography>
                    <Button variant="contained" color="secondary" startIcon={<EditIcon/>} href={editURI}>Edit</Button>
                </Box>
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
            <Container component={Paper} style={{marginBottom:10, padding:10}}>
                <Box
                    component="span"
                    m={1}
                    display="flex"
                    justifyContent="space-between"
                    alignItems="center"
                >
                    <Typography variant="h5">Sources</Typography>
                    <NewSourcePopupComponent productID={id}/>
                </Box>
                <Paper>
                    <Table size="small" stickyHeader>
                        <TableHead>
                            <TableRow>
                                <TableCell>Label</TableCell>
                                <TableCell>Tool</TableCell>
                                <TableCell align="center" width={200}>Findings</TableCell>
                                <TableCell>Date</TableCell>
                                <TableCell>Archived?</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {sources.map((row) => (
                                <TableRow
                                    key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell>{row.label}</TableCell>
                                    <TableCell>{row.tool}</TableCell>
                                    <TableCell>
                                        <FindingSummarySmall
                                            critical={row.findingsSummary.CRITICAL}
                                            high={row.findingsSummary.HIGH}
                                            medium={row.findingsSummary.MEDIUM}
                                            low={row.findingsSummary.LOW}
                                            informational={row.findingsSummary.INFORMATIONAL}
                                            disabled={row.archived}
                                        />
                                    </TableCell>
                                    <TableCell>{dayjs(row.createTimestamp).format(outputDateFormat)}</TableCell>
                                    <TableCell>{row.archived.toString()}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Paper>
            </Container>
        </>
    );
};

export default ProductView;