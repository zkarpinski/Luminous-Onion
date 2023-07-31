import React from 'react';
import './App.css';
import Layout from "./layouts/Layout";
import Main from "./Main";
import {CssBaseline} from "@mui/material";

function App() {

  return (
    <Layout>
        <CssBaseline />
        <Main/>
    </Layout>
  );
}

export default App;
