import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
const FindingList = () => {

    const [findings, setFindings] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        // Fetch the findings
        fetch('/api/findings')
            .then(response => response.json())
            .then(data => {
                setFindings(data);
                setLoading(false);
            })
    },[]);

    const columns= [
        { field: 'VulnerabilityID', headerName: 'Vul ID', width: 150 },
        { field: 'fullPackage', headerName: 'Package', description: 'Package and version.',
            sortable: true,
            width: 160,
            valueGetter: (params) =>
                `${params.row.PkgName || ''}:${params.row.InstalledVersion || ''}`,
        },
        { field: 'Title', headerName: 'Title', width: 300 },
        { field: 'FixedVersion', headerName: 'Fixed Version', width: 100 },
        { field: 'age', headerName: 'Age', type: 'number', width: 90 },
        { field: 'sourceTool', headerName: 'Source', width: 100 },
        { field: 'createTimestamp', headerName: 'Created', width: 150} //TODO: Change to readable datetime format

    ];


    if (loading) {
        return <p>...</p>;
    }

    return (
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
            />
        </div>
    );
};

export default FindingList;