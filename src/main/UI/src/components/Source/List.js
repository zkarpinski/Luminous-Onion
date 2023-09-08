import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Paper} from "@mui/material";
import api from "../../shared/api";
import { headerHeight, outputDateFormat } from "../../shared/constants";
import dayjs from "dayjs";
const SourceList = () => {

    const [sources, setSources] = useState([]);
    const [sourcesCount, setSourcesCount] = useState(0);
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = React.useState(false);
    const [selectedRow, setSelectedRow] = React.useState();

    const handleClose = () => {
        setOpen(false);
    };

    const handleDeleteSource = () => {
        api.delete(`/api/sources/${selectedRow.id}`)
        handleClose();
    }

    useEffect(() => {

        // Fetch the findings
        api.get('/api/sources')
            .catch(console.log)
            .then(data => {
                setSources(data);
                setSourcesCount(data.length)
                setLoading(false);
            })
    },[]);

    const onDeleteClick = (e, row) => {
        e.stopPropagation();
        setSelectedRow(row);
        setOpen(true);
    };

    const columns= [
        { field: 'product', headerName: 'Product', width: 100,
            valueGetter: (params) => params.row?.product?.name
        },
        { field: 'tool', headerName: 'Tool', width: 150 },
        { field: 'toolVersion', headerName: 'Version', width: 160},
        { field: 'target', headerName: 'Target', width: 300 },
        { field: 'targetType', headerName: 'Target Type', width: 100 },
        { field: 'createTimestamp', headerName: 'Created', width: 100,
            valueFormatter: (params) => dayjs(params?.value).format(outputDateFormat)},
        { field: 'actions', headerName: 'Actions', width: 400, renderCell: (params) => {
                return (
                    <Button
                        onClick={(e) => onDeleteClick(e, params.row)}
                        variant="contained"
                        size={"small"}
                    >
                        Delete
                    </Button>
                );
            } }

    ];

    return (
        <>
            <div>
                <h1>{sourcesCount} Sources</h1>
            </div>
            <Paper style={{'height':`calc(100% - ${headerHeight}px - 50px)`}}>
                <DataGrid
                    loading={loading}
                    density="compact"
                    rows={sources}
                    columns={columns}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 50 },
                        },
                        sorting: {
                            sortModel: [{ field: 'createTimestamp', sort: 'desc' }],
                        },
                    }}
                    pageSizeOptions={[10, 50, 100]}
                    checkboxSelection
                />
            </Paper>
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
                        Are you sure you want to permanently delete the source <strong>?</strong>?
                        <br/>
                        This cannot be undone.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleDeleteSource}
                            autoFocus variant="contained">Yes
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default SourceList;