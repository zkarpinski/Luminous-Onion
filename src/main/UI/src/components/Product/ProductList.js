import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [productCount, setProductCount] = useState(0);

    useEffect(() => {
        setLoading(true);

        // Fetch the findings
        fetch('/api/products')
            .catch(console.log)
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                setProductCount(data.length)
                setLoading(false);
            })
    },[]);

    const columns= [
        { field: 'id', headerName: 'ID', width: 150 },
        { field: 'name', headerName: 'Product Name', width: 160},
        { field: 'productOwner', headerName: 'Product Owner', width: 300 },
        { field: 'productTeam', headerName: 'Product Team', width: 100 },
        { field: 'createTimestamp', headerName: 'Created', width: 150} //TODO: Change to readable datetime format

    ];


    if (loading) {
        return <p>...</p>;
    }

    return (
        <>
        <div>
            <h1>{productCount} Products</h1>
        </div>
        <div style={{ height: '90%', width: '100%' }}>
            <DataGrid
                density="compact"
                rows={products}
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

export default ProductList;