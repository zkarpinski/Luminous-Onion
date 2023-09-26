import React, { useEffect, useState } from "react";
import api from "../../shared/api";
import { Paper } from "@mui/material";
import { SlickgridReact } from "slickgrid-react";
import "@slickgrid-universal/common/dist/styles/css/slickgrid-theme-material.css";
const ReportingView = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    // Get the product list
    api
      .get("/api/product")
      .then((data) => {
        setProducts(data);
      })
      .catch((error) => {
        console.log(error);
      })
      .finally(() => {
        setLoading(false);
        console.log(loading);
      });
  }, []);

  const columns = [
    {
      id: "name",
      name: "Product Name",
      field: "name",
      width: 40,
      excludeFromExport: true,
      maxWidth: 70,
      resizable: true,
      filterable: true,
      selectable: false,
      focusable: false,
    },
    {
      id: "name2",
      name: "Product Name",
      field: "name",
      width: 40,
      excludeFromExport: true,
      maxWidth: 70,
      resizable: true,
      filterable: true,
      selectable: false,
      focusable: false,
    },
  ];

  const gridOptions = {
    autoResize: {
      container: "#gridContainer",
      rightPadding: 10,
    },
    enableFiltering: true,
    // you could debounce/throttle the input text filter if you have lots of data
    // filterTypingDebounce: 250,
    enableGrouping: true,
    enableExcelExport: true,
    enableTextExport: true,
    excelExportOptions: { sanitizeDataExport: true },
    textExportOptions: { sanitizeDataExport: true },
  };

  return (
    <>
      <Paper id="gridContainer">
        <SlickgridReact
          gridId="grid13"
          gridOptions={gridOptions}
          columnDefinitions={columns}
          dataset={products}
        />
      </Paper>
    </>
  );
};

export default ReportingView;
