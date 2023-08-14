import React, { useEffect, useState } from 'react';
import api from '../../../shared/api'
import {useParams} from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import {
    Paper,
    Tabs,
} from "@mui/material";
import LinkTab from "../../LinkTab";
import Card from "@mui/material/Card";
import ProductView from "./View";
import ProductFindings from "./Findings";
import ProductSettings from "./Settings";
const ProductBase= ({children: content}) => {
    const {id} = useParams();
    const [product, setProduct] = useState({name: ''});
    const [findingSummary, setFSummary] = useState({critical: 0, high: 0, medium: 0, low: 0, informational: 0});
    const [barValue, setBarValue] = useState(0);

    useEffect(() => {

        if (id !== null) {
            // Get the product
            api.get(`/api/product/${id}`)
                .then(data => {
                    setProduct(data);
                    console.log(product);
                });

            // Get the product finding summary
            api.get(`/api/product/${id}/findings/summary`)
                .then(data => {
                    setFSummary(data.data);
                });

        }

        /*
        Determine the menu selection based on the child content
         */
        if (content !=null) {
            switch (content.type) {
                case ProductView:
                    setBarValue(0);
                    break;
                case ProductFindings:
                    setBarValue(1);
                    break;
                case ProductSettings:
                    setBarValue(2);
                    break;
                default:
                    setBarValue(0);
            }
        }


    }, []);

    return (
        <div>
            <Paper>
                <ProductFindingSummary
                    critical={findingSummary.critical}
                    high={findingSummary.high}
                    medium={findingSummary.medium}
                    low={findingSummary.low}
                    informational={findingSummary.informational}
                />
            </Paper>
            <Card>
                <Tabs value={barValue} centered aria-label="nav tabs">
                    <LinkTab label="Overview" href={`/product/${id}`}/>
                    <LinkTab label="Findings" href={`/product/${id}/findings`}/>
                    <LinkTab label="Settings" href={`/product/${id}/settings`}/>
                </Tabs>
            </Card>
            {content}
        </div>
    );
}


export default ProductBase;