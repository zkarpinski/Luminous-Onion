import * as React from "react";
import { Box, Stack } from "@mui/material";
import { SeverityType, FindingSeverity } from "../../shared/SeverityType";

interface FindingSummarySmallProps {
  critical: number;
  high: number;
  medium: number;
  low: number;
  informational: number;
  disabled?: boolean;
}

const SeverityTile = (props) => {
  const { severity, value, disabled } = props;

  let sevType, bgColor, abbr, abbrColor, title;

  switch (severity) {
    case SeverityType.Critical: {
      sevType = FindingSeverity.Critical;
      break;
    }
    case SeverityType.High: {
      sevType = FindingSeverity.High;
      break;
    }
    case SeverityType.Medium: {
      sevType = FindingSeverity.Medium;
      break;
    }
    case SeverityType.Low: {
      sevType = FindingSeverity.Low;
      break;
    }
    case SeverityType.Info: {
      sevType = FindingSeverity.Info;
      break;
    }
    default: {
      sevType = FindingSeverity.Info;
      break;
    }
  }

  abbr = sevType.abbr;
  title = sevType.text;
  bgColor = sevType.color.light;
  abbrColor = sevType.color.base;

  // Override the colors if disabled
  if (disabled) {
    bgColor = FindingSeverity.Low.color.light;
    abbrColor = FindingSeverity.Low.color.base;
  }

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      width={60}
      className={disabled ? "SeverityTileDisabled" : "SeverityTile"}
    >
      <Stack direction="row" justifyContent="center" width="100%">
        <Box
          width="100%"
          padding="1px"
          sx={{ backgroundColor: bgColor, textAlign: "center" }}
        >
          <span style={{ fontSize: "12px", fontWeight: "bold" }}>{value}</span>
        </Box>
        <Box
          width={25}
          padding="1px"
          sx={{ backgroundColor: abbrColor, textAlign: "center" }}
        >
          <abbr style={{ color: "white", fontWeight: "bold" }} title={title}>
            {abbr}
          </abbr>
        </Box>
      </Stack>
    </Box>
  );
};

const FindingSummarySmall = ({
  critical = 0,
  high = 0,
  medium = 0,
  low = 0,
  informational = 0,
  disabled = false,
}: FindingSummarySmallProps) => {
  const totalFindings: number = critical + high + medium + low + informational;

  return (
    <Stack direction="row" spacing={1}>
      <SeverityTile
        severity={SeverityType.Critical}
        value={critical}
        disabled={disabled}
      />
      <SeverityTile
        severity={SeverityType.High}
        value={high}
        disabled={disabled}
      />
      <SeverityTile
        severity={SeverityType.Medium}
        value={medium}
        disabled={disabled}
      />
      <SeverityTile
        severity={SeverityType.Low}
        value={low}
        disabled={disabled}
      />
      <SeverityTile
        severity={SeverityType.Info}
        disabled={disabled}
        value={totalFindings}
      />
    </Stack>
  );
};

export default FindingSummarySmall;
