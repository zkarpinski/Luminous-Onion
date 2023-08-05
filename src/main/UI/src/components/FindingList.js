import React, { useEffect, useState } from 'react';
import {DataGrid, GridToolbar} from '@mui/x-data-grid';
import api from "../shared/api";
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
        { field: 'tool', headerName: 'Tool', width: 100,
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
        { field: 'title', headerName: 'Title', width: 300 },
        { field: 'packageVersionFixed', headerName: 'Fixed Version', width: 100 },
        { field: 'age', headerName: 'Age', type: 'number', width: 90 },
        { field: 'sourceTool', headerName: 'Source', width: 100 },
        { field: 'createTimestamp', headerName: 'Created', width: 150} //TODO: Change to readable datetime format

    ];


    if (loading) {
        return <p>...</p>;
    }

    return (
        <>
        <div>
            <h1>{findingsCount} Findings</h1>
        </div>
        <div style={{ height: '100%', width: '100%' }}>
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
        </div>
        </>
    );
};

export default FindingList;