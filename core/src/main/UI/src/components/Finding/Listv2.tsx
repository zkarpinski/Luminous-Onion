import React, { useEffect, useState } from "react";
import api from "../../shared/api";
import { Button, Grid, Paper } from "@mui/material";
import {
  SlickgridReactInstance,
  SlickgridReact,
  Grouping,
  FieldType,
  Formatters,
} from "slickgrid-react";
import { ExcelExportService } from "@slickgrid-universal/excel-export";
import "@slickgrid-universal/common/dist/styles/css/slickgrid-theme-material.css";
import { SeverityType, FindingSeverity } from "../../shared/SeverityType";
// eslint-disable-next-line no-unused-vars
import { FindingData } from "../../types";

export default function FindingListv2({ filters, endpoint }) {
  const [findings, setFindings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [findingsCount, setFindingsCount] = useState(0);
  const [reactGrid, setReactGrid] = useState<
    SlickgridReactInstance | undefined
  >(undefined);
  const excelExportService = new ExcelExportService();

  const severitySortNumber = (s) => {
    if (s === "CRITICAL") return 5;
    if (s === "HIGH") return 4;
    if (s === "MEDIUM") return 3;
    if (s === "LOW") return 2;
    if (s === "INFORMATIONAL") return 1;
    return 0;
  };

  const severitySortComparator = (v1, v2) => {
    console.log("COMPARE" + v1);
    return severitySortNumber(v1) - severitySortNumber(v2);
  };

  function reactGridReady(grid) {
    setReactGrid(grid);
  }

  function clearGroupBy() {
    reactGrid.dataView.setGrouping([]);
  }
  function groupByPackage() {
    reactGrid.dataView.setGrouping({
      getter: "packageName",
      collapsed: true,
      formatter: (g) =>
        `<b>Package: ${g.value}</b> <span style="color:red">(${g.count} findings)</span>`,
      comparer: (a, b) => {
        return b.count - a.count;
      },
    } as Grouping);
    reactGrid.slickGrid.invalidate(); // invalidate all rows and re-render
  }

  function groupBySeverity() {
    reactGrid.dataView.setGrouping({
      getter: "severity",
      collapsed: true,
      formatter: (g) => {
        let color: string;
        switch (g.value) {
          case SeverityType.Critical:
            color = FindingSeverity.Critical.color.base;
            break;
          case SeverityType.High:
            color = FindingSeverity.High.color.base;
            break;
          case SeverityType.Medium:
            color = FindingSeverity.Medium.color.base;
            break;
          case SeverityType.Low:
            color = FindingSeverity.Low.color.base;
            break;
          case SeverityType.Info:
            color = FindingSeverity.Info.color.base;
            break;
          default:
            color = "black";
            break;
        }
        return `Severity: <span style="color:${color};font-weight:700">${g.value} (${g.count} findings)</span>`;
      },
      comparer: (a, b) => {
        return severitySortComparator(b.value, a.value); // this order ensure sorts descending
      },
    } as Grouping);
    reactGrid.slickGrid.invalidate();
  }

  function groupByProductSeverity() {
    reactGrid.dataView.setGrouping([
      {
        getter: (finding: FindingData) => {
          return finding.source.product.name;
        },
        collapsed: false,
        formatter: (g) =>
          `<span style="font-weight:700">${g.value} (${g.count}</span>)`,
      },
      {
        getter: "severity",
        collapsed: true,
        formatter: (g) => {
          let color: string;
          switch (g.value) {
            case SeverityType.Critical:
              color = FindingSeverity.Critical.color.base;
              break;
            case SeverityType.High:
              color = FindingSeverity.High.color.base;
              break;
            case SeverityType.Medium:
              color = FindingSeverity.Medium.color.base;
              break;
            case SeverityType.Low:
              color = FindingSeverity.Low.color.base;
              break;
            case SeverityType.Info:
              color = FindingSeverity.Info.color.base;
              break;
            default:
              color = "black";
              break;
          }
          return `Severity: <span style="color:${color};font-weight:700">${g.value} (${g.count} findings)</span>`;
        },
        comparer: (a, b) => {
          return severitySortComparator(b.value, a.value);
        },
      },
    ] as Grouping);
    reactGrid.slickGrid.invalidate();
  }

  function groupByProductPackage() {
    reactGrid.dataView.setGrouping([
      {
        getter: (finding: FindingData) => {
          return finding.source.product.name;
        },
        collapsed: false,
        formatter: (g) =>
          `<span style="font-weight:700">${g.value} (${g.count}</span>)`,
      },
      {
        getter: "packageName",
        collapsed: true,
        formatter: (g) =>
          `<b>Package: ${g.value}</b> <span style="color:red">(${g.count} findings)</span>`,
        comparer: (a, b) => {
          return b.count - a.count;
        },
      },
    ] as Grouping);
    reactGrid.slickGrid.invalidate();
  }

  useEffect(() => {
    setLoading(true);
    //TODO use filters
    console.log({ filters });

    const apiEndpoint = endpoint || "/api/findings";

    // Fetch the findings
    api
      .get(apiEndpoint)
      .catch(console.log)
      .then((data) => {
        setFindings(data);
        setFindingsCount(data.length);
        setLoading(false);
      });
  }, []);

  const gridOptions = {
    autoResize: {
      container: "#gridContainer",
    },
    enableFiltering: true,
    // throttle the input text filter if you have lots of data
    // filterTypingDebounce: 250,
    enableGrouping: true,
    enableCellNavigation: true,
    enableExcelExport: true,
    enableTextExport: true,
    excelExportOptions: { sanitizeDataExport: true },
    textExportOptions: { sanitizeDataExport: true },
    dataItemColumnValueExtractor: getItemColumnValue,
    registerExternalResources: [excelExportService],
  };

  // Custom column logic, iterate json object, seperated by dots "."
  function getItemColumnValue(item, column) {
    let keys: string[] = column.field.split(".");
    let node = item;
    keys.forEach((key) => {
      if (key in node) node = node[key];
    });
    return node;
  }

  const columns = [
    {
      id: "product",
      name: "Product Name",
      field: "source.product.name",
      width: 150,
    },
    { id: "sourceLabel", name: "Source", field: "source.label", width: 150 },
    {
      id: "title",
      name: "Title",
      field: "title",
      width: 350,
      sortable: true,
      filterable: true,
    },
    { id: "tool", name: "Tool", field: "source.tool" },
    {
      id: "severity",
      name: "Severity",
      field: "severity",
      sortable: true,
      filterable: true,
      comparer: severitySortComparator,
    },
    { id: "findingIdentifier", name: "Vul ID", field: "findingIdentifier" },
    {
      id: "packageName",
      name: "Package",
      field: "packageName",
      sortable: true,
      filterable: true,
    },
    {
      id: "createTimestamp",
      name: "Created",
      field: "createTimestamp",
      sortable: true,
      type: FieldType.date,
      formatter: Formatters.dateIso,
    },
    { id: "id", name: "id", field: "id" },
  ];

  if (loading) {
    return <p>...</p>;
  }

  return (
    <>
      <div>
        <h1>{findingsCount} Findings</h1>
      </div>
      <Grid container>
        <Button onClick={clearGroupBy} variant={"outlined"} size={"small"}>
          Reset
        </Button>
        <Button onClick={groupByPackage} variant={"outlined"} size={"small"}>
          Group by Package
        </Button>
        <Button onClick={groupBySeverity} variant={"outlined"} size={"small"}>
          Group by Severity
        </Button>
        <Button
          onClick={groupByProductSeverity}
          variant={"outlined"}
          size={"small"}
        >
          Group by Product & Severity
        </Button>
        <Button
          onClick={groupByProductPackage}
          variant={"outlined"}
          size={"small"}
        >
          Group by Product & Package
        </Button>
      </Grid>
      <Paper id="gridContainer">
        <SlickgridReact
          gridId="findingGrid"
          gridOptions={gridOptions}
          columnDefinitions={columns}
          dataset={findings}
          onReactGridCreated={($event) => reactGridReady($event.detail)}
        />
      </Paper>
    </>
  );
}
