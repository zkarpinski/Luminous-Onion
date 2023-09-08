import React, { useEffect, useState } from 'react';
import {DataGrid, GridToolbar} from '@mui/x-data-grid';
import api from "../../shared/api";
import {Paper} from "@mui/material";
import {Link} from "react-router-dom";
import dayjs from "dayjs";
import {outputDateFormat} from "../../shared/constants";
const FindingList = ({filters, endpoint,scope}) => {

    const [findings, setFindings] = useState([]);
    const [loading, setLoading] = useState(false);
    const [findingsCount, setFindingsCount] = useState(0);

    const severitySortNumber = (s) => {
        if (s === "CRITICAL") return 5;
        if (s === "HIGH") return 4;
        if (s === "MEDIUM") return 3;
        if (s === "LOW") return 2;
        if (s === "INFORMATIONAL") return 1;
        return 0;
    }

    const severitySortComparator = (v1, v2) => severitySortNumber(v1) - severitySortNumber(v2);

    const [columnVisibilityModel, setColumnVisibilityModel] = React.useState({

    });

    const productScope = {
        product: false,
        sourceLabel: true
    }



    useEffect(() => {
        setLoading(true);
        //TODO use filters
        console.log({filters})

        const apiEndpoint =  endpoint || '/api/findings';

        // Set the column visibility based on scope
        switch (scope) {
            case "product":
                setColumnVisibilityModel(productScope);
                break;
            default:
                break;
        }


        // Fetch the findings
        api.get(apiEndpoint)
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
        { field: 'sourceLabel', headerName: 'Source', width: 150,
            valueGetter: (params) => params.row?.source?.label,
        },

        { field: 'title', headerName: 'Title', width: 350,
            renderCell: (params) => (<Link to={`${params.id}`}>{params.value}</Link>)
        },
        { field: 'status', headerName: 'Status'},
        { field: 'tool', headerName: 'Tool', width: 125,
            valueGetter: (params) => params.row?.source?.tool
        },
        { field: 'severity', headerName: 'Severity', width: 100, sortComparator: severitySortComparator},
        { field: 'findingIdentifier', headerName: 'Vul ID', width: 150 },
        { field: 'fullPackage', headerName: 'Package', description: 'Package and version.',
            sortable: true,
            width: 160,
            valueGetter: (params) =>
                `${params.row.packageName || ''}:${params.row.packageVersionFound || ''}`,
        },

        { field: 'packageVersionFixed', headerName: 'Fixed Version', width: 150 },
        { field: 'age', headerName: 'Age', type: 'number', width: 90 },
        { field: 'createTimestamp', headerName: 'Created', width: 100,
            valueFormatter: (params) => dayjs(params?.value).format(outputDateFormat)},
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
                    loading={loading}
                    density="compact"
                    rows={findings}
                    columns={columns}
                    columnVisibilityModel={columnVisibilityModel}
                    onColumnVisibilityModelChange={(newModel) =>
                        setColumnVisibilityModel(newModel)
                    }
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 50 },
                        },
                        sorting: {
                            sortModel: [{ field: 'severity', sort: 'desc' }],
                        },
                        filter: {
                            filterModel: {
                                items: [ {
                                    field: 'productId',
                                    operator: 'equals',
                                    value:'x'
                                }]
                            }
                        }
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