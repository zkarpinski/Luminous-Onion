import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';

const uData = [4000, 3000, 2000, 2780, 1890, 2390, 3490];
const pData = [2400, 1398, 9800, 3908, 4800, 3800, 4300];
const amtData = [2400, 2210, 2290, 2000, 2181, 2500, 2100];
const xLabels = [
    'Page A',
    'Page B',
    'Page C',
    'Page D',
    'Page E',
    'Page F',
    'Page G',
];

export default function StackedAreaChart() {
    return (
        <LineChart
            width={1200}
            height={500}
            series={[
                { data: uData, label: 'Low', area: true, stack: 'total', color: '#BBBBBB' },
                { data: pData, label: 'Medium', area: true, stack: 'total', color: '#FFBF00' },
                {
                    data: amtData,
                    label: 'High',
                    area: true,
                    stack: 'total',
                    color: '#FF0000'
                },
                { data: pData, label: 'Critical', area: true, stack: 'total', color: '#880808' },
            ]}
            xAxis={[{ scaleType: 'point', data: xLabels }]}
            sx={{
                '.MuiLineElement-root, .MuiMarkElement-root': {
                    display: 'none',
                },
            }}
        />
    );
}
