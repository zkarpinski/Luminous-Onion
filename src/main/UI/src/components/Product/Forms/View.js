import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {
    Box,
    Container,
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
import NewSourcePopupComponent from "../../Source/Forms/New";
const ProductView= () => {
    const [loading, setLoading] = useState(false);
    const { id } = useParams();
    const [product, setProduct] = useState({name:''});
    const [findingSummary, setFSummary] = useState({critical:0,high:0,medium:0,low:0,informational:0});
    const [sources, setSources] = useState([]);


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
                });

            // Get the product sources
            api.get(`/api/product/${id}/sources`)
                .then(data => {
                    setSources(data);
                    setLoading(false)
                });
        }


    },[]);


    if (!loading) {
        // Print debugging
        console.log(findingSummary)
        console.log(sources)
    }


    return (
        <div>
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
                <Tabs value={0} centered aria-label="nav tabs">
                    <LinkTab label="Overview" href={`${id}`} />
                    <LinkTab label="Findings" href={`${id}/findings`} />
                    <LinkTab label="Settings" href={`${id}/settings`} />
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
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>Tool</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {sources.map((row) => (
                                <TableRow
                                    key={row.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell>{row.tool}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Paper>
            </Container>
        </div>
    );
};

export default ProductView;