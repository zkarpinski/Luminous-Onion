import React, { createContext, useEffect, useState } from "react";
import api from "../../../shared/api";
import { Link, useParams } from "react-router-dom";
import ProductFindingSummary from "../FindingSummary";
import { Container, Paper, Tabs, Typography } from "@mui/material";
import LinkTab from "../../LinkTab";
import Card from "@mui/material/Card";
import ProductView from "./View";
import ProductFindings from "./Findings";
import ProductSettings from "./Settings";

export const ProductContext = createContext({ "": "" });

const ProductBase = ({ children: content }) => {
  const { id } = useParams();
  const [product, setProduct] = useState({ name: "" });
  const [findingSummary, setFSummary] = useState({
    CRITICAL: 0,
    HIGH: 0,
    MEDIUM: 0,
    LOW: 0,
    INFORMATIONAL: 0,
  });
  const [barValue, setBarValue] = useState(0);

  useEffect(() => {
    if (id !== null) {
      // Get the product
      api.get(`/api/product/${id}`).then((data) => {
        setProduct(data);
        console.log(product);
      });

      // Get the product finding summary
      api.get(`/api/product/${id}/findings/summary`).then((data) => {
        setFSummary(data.data);
      });
    }

    /*
        Determine the menu selection based on the child content
        //TODO: fix this to highlight the correct tab
         */
    if (content != null) {
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
          critical={findingSummary.CRITICAL}
          high={findingSummary.HIGH}
          medium={findingSummary.MEDIUM}
          low={findingSummary.LOW}
          informational={findingSummary.INFORMATIONAL}
        />
      </Paper>
      <Card>
        <Tabs value={barValue} centered aria-label="nav tabs">
          <LinkTab label="Overview" component={Link} to={`/product/${id}`} />
          <LinkTab
            label="Findings"
            component={Link}
            to={`/product/${id}/findings`}
          />
          <LinkTab
            label="Settings"
            component={Link}
            to={`/product/${id}/settings`}
          />
        </Tabs>
      </Card>
      <Container style={{ marginTop: 10, marginBottom: 10, padding: 0 }}>
        <Card variant="outlined" style={{ border: "1px solid darkgray" }}>
          <Typography variant="h4" textAlign="center">
            {product.name}
          </Typography>
        </Card>
      </Container>
      <ProductContext.Provider value={product}>
        {content}
      </ProductContext.Provider>
    </div>
  );
};

export default ProductBase;
