import * as React from 'react';
import {Box, Stack} from "@mui/material";
import {SeverityType} from "../../shared/SeverityType";
import {FindingSeverity} from "../../shared/constants";

interface FindingSummarySmallProps {
    critical:number,
    high:number,
    medium:number,
    low:number,
    informational:number,
    disabled?:boolean;
}

const SeverityTile = (props) => {
    const { severity, value, disabled} = props;

    let bgColor, abbr, abbrColor, title;

    switch(severity) {
        case (SeverityType.Critical): {
            abbr = "C";
            title = "Critical findings"
            bgColor = FindingSeverity.Critical.color.light;
            abbrColor = FindingSeverity.Critical.color.base;
            break;
        }
        case (SeverityType.High): {
            abbr = "H";
            title = "High findings"
            bgColor = FindingSeverity.High.color.light;
            abbrColor = FindingSeverity.High.color.base;
            break;
        }
        case (SeverityType.Medium): {
            abbr = "M";
            title = "Critical findings"
            bgColor = FindingSeverity.Medium.color.light;
            abbrColor = FindingSeverity.Medium.color.base;
            break;
        }
        case (SeverityType.Low): {
            abbr = "L";
            title = "Critical findings"
            bgColor = FindingSeverity.Low.color.light;
            abbrColor  = FindingSeverity.Low.color.base;
            break;
        }
        case (SeverityType.Info): {
            abbr = "I";
            title = "Informational findings"
            bgColor = FindingSeverity.Info.color.light;
            abbrColor  = FindingSeverity.Info.color.base;
            break;
        }
        default: {
            abbr = "U";
            title = "Unknown findings"
            bgColor = "#000";
            abbrColor = "#000";
            break;
        }
    }

    // Override the colors if disabled
    if (disabled) { bgColor = FindingSeverity.Low.color.light; abbrColor=FindingSeverity.Low.color.base; }

    return (
        <Box
            display="flex"
            justifyContent="center"
            alignItems="center"
            width={60}
            className={disabled ? "SeverityTileDisabled" : "SeverityTile"}
        >
            <Stack direction="row" justifyContent="center" width="100%">
                <Box width="100%" padding="1px" sx={{backgroundColor:bgColor, textAlign:"center"}}><span style={{fontSize:"12px", fontWeight:"bold"}}>{value}</span></Box>
                <Box width={25} padding="1px" sx={{backgroundColor:abbrColor , textAlign:"center"}}><abbr style={{color:"white", fontWeight:"bold"}} title={title}>{abbr}</abbr></Box>
            </Stack>
        </Box>
    )
};

const FindingSummarySmall = ({critical= 0,high=0,medium=0,low=0,informational=0,disabled=false}:FindingSummarySmallProps) => {
    const totalFindings:number = critical + high + medium + low + informational;

    return (
    <Stack direction="row" spacing={1}>
        <SeverityTile severity={SeverityType.Critical} value={critical} disabled={disabled}/>
        <SeverityTile severity={SeverityType.High} value={high} disabled={disabled}/>
        <SeverityTile severity={SeverityType.Medium} value={medium} disabled={disabled}/>
        <SeverityTile severity={SeverityType.Low} value={low} disabled={disabled}/>
        <SeverityTile severity={SeverityType.Info} disabled={disabled} value={totalFindings}/>
    </Stack>
)
};

export default FindingSummarySmall;