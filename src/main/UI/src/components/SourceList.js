import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
const SourceList = () => {

    const [sources, setSources] = useState([]);
    const [loading, setLoading] = useState(false);
    const [sourcesCount, setSourcesCount] = useState(0);

    useEffect(() => {
        setLoading(true);

        // Fetch the findings
        fetch('/api/sources')
            .catch(console.log)
            .then(response => response.json())
            .then(data => {
                setSources(data);
                setSourcesCount(data.length)
                setLoading(false);
            })
    },[]);

    const columns= [
        { field: 'tool', headerName: 'Tool', width: 150 },
        { field: 'toolVersion', headerName: 'Version', width: 160},
        { field: 'target', headerName: 'Target', width: 300 },
        { field: 'targetType', headerName: 'Target Type', width: 100 },
        { field: 'createTimestamp', headerName: 'Created', width: 150} //TODO: Change to readable datetime format

    ];


    if (loading) {
        return <p>...</p>;
    }

    return (
        <>
        <div>
            <h1>{sourcesCount} Sources</h1>
        </div>
        <div style={{ height: '100%', width: '100%' }}>
            <DataGrid
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
        </div>
        </>
    );
};

export default SourceList;