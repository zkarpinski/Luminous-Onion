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

const Tile2 = (props) => {
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

    console.log(title);
    return (
        <Box
            display="flex"
            justifyContent="center"
            alignItems="center"
            width={70}
        >
            <Stack direction="row" justifyContent="center" width="100%">
                <Box width="100%" padding="1px" sx={{backgroundColor:bgColor, textAlign:"center"}}><span style={{fontSize:"12px"}}>{value}</span></Box>
                <Box width={25} padding="1px" sx={{backgroundColor:abbrColor , textAlign:"center"}}><span style={{color:"white", fontWeight:"bold"}}>{abbr}</span></Box>
            </Stack>
        </Box>
    )
};

const FindingSummarySmall = ({critical= 0,high=0,medium=0,low=0,informational=0,disabled=false}:FindingSummarySmallProps) => {
    const totalFindings:number = critical + high + medium + low + informational;

    return (
    <Stack direction="row" spacing={1}>
        <Tile2 severity={SeverityType.Critical} value={10} disabled={disabled}/>
        <Tile2 severity={SeverityType.High} value={9} disabled={disabled}/>
        <Tile2 severity={SeverityType.Medium} value={10} disabled={disabled}/>
        <Tile2 severity={SeverityType.Low} value={10} disabled={disabled}/>
        <Tile2 severity={SeverityType.Info} disabled={disabled} value={totalFindings}/>
    </Stack>
)
};

export default FindingSummarySmall;