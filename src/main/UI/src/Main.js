import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Findings from './pages/Findings';
import Sources from "./pages/Sources";
import Products from "./pages/Products";
import NotFound from "./pages/NotFound";
import ProductFindings from "./components/Product/Forms/Findings";
import ProductSettings from "./components/Product/Forms/Settings";
import SourceView from "./components/Source/Forms/View";
import ProductBase from "./components/Product/Forms/ProductBase";
import ProductEdit from "./components/Product/Forms/Edit";
import FindingView from "./components/Finding/Forms/View";
import ProductView from "./components/Product/Forms/View";
import Login from "./pages/Login";

const Main = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/login' element={<Login/>}></Route>
            <Route exact path='/finding' element={<Findings/>}></Route>
            <Route exact path='/finding/:id' element={<FindingView/>}></Route>
            <Route exact path='/source' element={<Sources/>}></Route>
            <Route exact path='/source/:id' element={<SourceView/>}></Route>
            <Route exact path='/product' element={<Products/>}></Route>
            <Route exact path='/product/:id' element={<ProductBase><ProductView/></ProductBase>}></Route>
            <Route exact path='/product/:id/edit' element={<ProductBase><ProductEdit/></ProductBase>}></Route>
            <Route exact path='/product/:id/settings' element={<ProductBase><ProductSettings/></ProductBase>}></Route>
            <Route exact path='/product/:id/findings' element={<ProductBase><ProductFindings/></ProductBase>}></Route>
            <Route exact path='/product/:id/findings/:fid' element={<ProductBase></ProductBase>}></Route>
            <Route path='*' element={<NotFound />}/>
        </Routes>
    );
}

export default Main;