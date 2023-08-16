import React from 'react';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import {Typography} from "@mui/material";


export default function LastUploadCard() {
    return (
        <Card>
            <CardContent>
                <Typography variant="h6" component="div">
                    Last Upload
                </Typography>
                <Typography variant="body2" component="div">
                    Last Upload
                </Typography>
            </CardContent>

        </Card>
    );
}