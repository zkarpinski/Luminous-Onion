import * as React from "react";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Typography,
} from "@mui/material";

interface ProductFindingSummaryProps {
  critical: number;
  high: number;
  medium: number;
  low: number;
  informational: number;
}

const ProductFindingSummary = ({
  critical = 0,
  high = 0,
  medium = 0,
  low = 0,
  informational = 0,
}: ProductFindingSummaryProps) => {
  const totalFindings: number = critical + high + medium + low + informational;

  return (
    <TableContainer component={Paper}>
      <Table size="small" style={{ tableLayout: "fixed" }}>
        <TableBody>
          <TableRow>
            <TableCell
              align="center"
              style={{ backgroundColor: "darkred", color: "white" }}
            >
              <Typography className="findingCount">{critical}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                Critical
              </Typography>
            </TableCell>
            <TableCell
              align="center"
              style={{ backgroundColor: "darkorange", color: "white" }}
            >
              <Typography className="findingCount">{high}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                High
              </Typography>
            </TableCell>
            <TableCell
              align="center"
              style={{ backgroundColor: "#E4AB10", color: "white" }}
            >
              <Typography className="findingCount">{medium}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                Medium
              </Typography>
            </TableCell>
            <TableCell
              align="center"
              style={{ backgroundColor: "darkgrey", color: "white" }}
            >
              <Typography className="findingCount">{low}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                Low
              </Typography>
            </TableCell>
            <TableCell
              align="center"
              style={{ backgroundColor: "lightgray", color: "white" }}
            >
              <Typography className="findingCount">{informational}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                Info
              </Typography>
            </TableCell>
            <TableCell
              align="center"
              style={{ backgroundColor: "lightgray", color: "white" }}
            >
              <Typography className="findingCount">{totalFindings}</Typography>
              <Typography sx={{ display: { xs: "none", sm: "block" } }}>
                Total
              </Typography>
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ProductFindingSummary;
