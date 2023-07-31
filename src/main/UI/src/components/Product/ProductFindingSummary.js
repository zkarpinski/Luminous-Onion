import React, { useEffect, useState } from 'react';
import api from '../../shared/api'
import {useParams} from "react-router-dom";
import {makeStyles, Paper, Table, TableBody, TableCell, TableContainer, TableRow, Typography} from "@mui/material";

const ProductFindingSummary = ({critical= 0,high=0,medium=0,low=0,informational=0}) => {
    const { id } = useParams();


    return (
        <>
            <TableContainer component={Paper}>
            <Table size="small" style={{ tableLayout: 'fixed' }}>
                <TableBody>
                    <TableRow>
                        <TableCell sm={2} align="center" style={{ backgroundColor:"darkred", color:"white"}} >
                            <Typography className='findingCount'>
                                {critical}
                            </Typography>
                            <Typography sx={{display: { xs: 'none', sm: 'block'}}}>
                                Critical
                            </Typography>
                        </TableCell>
                        <TableCell sm={2} align="center" style={{ backgroundColor:"darkorange", color:"white"}}>
                            <Typography className='findingCount'>
                                {high}
                            </Typography>
                            <Typography sx={{display: { xs: 'none', sm: 'block'}}}>
                                High
                            </Typography>
                        </TableCell>
                        <TableCell sm={2}  align="center" style={{ backgroundColor:"#E4AB10", color:"white"}}>
                            <Typography className='findingCount'>
                                {medium}
                            </Typography>
                            <Typography sx={{display: { xs: 'none', sm: 'block'}}}>
                               Medium
                            </Typography>
                        </TableCell>
                        <TableCell sm={2}  align="center" style={{ backgroundColor:"darkgrey", color:"white"}}>
                            <Typography className='findingCount'>
                                {low}
                            </Typography>
                            <Typography sx={{display: { xs: 'none', sm: 'block'}}}>
                                Low
                            </Typography>
                        </TableCell>
                        <TableCell sm={2}  align="center" style={{ backgroundColor:"lightgray", color:"white"}}>
                            <Typography className='findingCount'>
                                {informational}
                            </Typography>
                            <Typography sx={{display: { xs: 'none', sm: 'block'}}}>
                                Info
                            </Typography>
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
            </TableContainer>

        </>
    );
};

export default ProductFindingSummary;