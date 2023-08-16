import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Paper } from "@mui/material";
import api from "../../shared/api";
import { headerHeight, outputDateFormat } from "../../shared/constants";
import dayjs from "dayjs";
const SourceList = () => {

    const [sources, setSources] = useState([]);
    const [sourcesCount, setSourcesCount] = useState(0);
    const [loading, setLoading] = useState(false);

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
        api.delete(`/sources/${row.id}`)
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
                    }}
                    pageSizeOptions={[10, 50, 100]}
                    checkboxSelection
                />
            </Paper>
        </>
    );
};

export default SourceList;