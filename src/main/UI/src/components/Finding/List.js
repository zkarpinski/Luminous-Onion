import React, { useEffect, useState } from 'react';
import {DataGrid, GridToolbar} from '@mui/x-data-grid';
import api from "../../shared/api";
import {Paper} from "@mui/material";
import {Link} from "react-router-dom";
const FindingList = ({filters}) => {

    const [findings, setFindings] = useState([]);
    const [loading, setLoading] = useState(false);
    const [findingsCount, setFindingsCount] = useState(0);
    //TODO use filters
    console.log({filters})

    useEffect(() => {
        setLoading(true);

        // Fetch the findings
        api.get('/api/findings')
            .catch(console.log)
            .then(data => {
                setFindings(data);
                setFindingsCount(data.length)
                setLoading(false);
            })
    },[]);

    const columns= [
        { field: 'product', headerName: 'Product', width: 150,
            valueGetter: (params) => params.row?.source?.product?.name,
        },
        { field: 'title', headerName: 'Title', width: 350,
            renderCell: (params) => (<Link to={`${params.id}`}>{params.value}</Link>)
        },
        { field: 'status', headerName: 'Status'},
        { field: 'tool', headerName: 'Tool', width: 125,
            valueGetter: (params) => params.row?.source?.tool
        },
        { field: 'severity', headerName: 'Severity', width: 100},
        { field: 'findingIdentifier', headerName: 'Vul ID', width: 150 },
        { field: 'fullPackage', headerName: 'Package', description: 'Package and version.',
            sortable: true,
            width: 160,
            valueGetter: (params) =>
                `${params.row.packageName || ''}:${params.row.packageVersionFound || ''}`,
        },

        { field: 'packageVersionFixed', headerName: 'Fixed Version', width: 100 },
        { field: 'age', headerName: 'Age', type: 'number', width: 90 },
        { field: 'createTimestamp', headerName: 'Created', width: 150}, //TODO: Change to readable datetime format
        { field: 'id', headerName: 'ID'}

    ];


    if (loading) {
        return <p>...</p>;
    }

    return (
        <>
            <div>
                <h1>{findingsCount} Findings</h1>
            </div>
            <Paper>
                <DataGrid
                    density="compact"
                    rows={findings}
                    columns={columns}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 50 },
                        },
                    }}
                    pageSizeOptions={[10, 50, 100]}
                    checkboxSelection
                    slots={{ toolbar: GridToolbar }}
                    slotProps={{
                        toolbar: {
                            showQuickFilter: true,
                            quickFilterProps: {debounceMs: 500},
                        }
                    }}
                />
            </Paper>
        </>
    );
};

export default FindingList;