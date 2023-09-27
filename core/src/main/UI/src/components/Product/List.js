import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import api from "../../shared/api";
import { Link } from "react-router-dom";
import { Paper, Typography } from "@mui/material";
import { headerHeight, outputDateFormat } from "../../shared/constants";
import dayjs from "dayjs";
export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [productCount, setProductCount] = useState(0);

  useEffect(() => {
    setLoading(true);

    // Get the product list
    api
      .get("/api/product")
      .then((data) => {
        setProducts(data);
        setProductCount(data.length);
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const columns = [
    {
      field: "id",
      headerName: "ID",
      width: 50,
      renderCell: (params) => (
        <Link to={`${params.value}`}>{params.value}</Link>
      ),
    },
    { field: "name", headerName: "Product Name", width: 160 },
    { field: "productOwner", headerName: "Product Owner", width: 300 },
    { field: "productTeam", headerName: "Product Team", width: 100 },
    {
      field: "createTimestamp",
      headerName: "Created",
      width: 100,
      valueFormatter: (params) => dayjs(params?.value).format(outputDateFormat),
    },
  ];

  return (
    <>
      <Typography variant="h4" component="div" gutterBottom>
        {productCount} Products
      </Typography>
      <Paper style={{ height: `calc(100% - ${headerHeight}px - 50px)` }}>
        <DataGrid
          loading={loading}
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
      </Paper>
    </>
  );
}
