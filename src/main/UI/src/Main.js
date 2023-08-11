import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Findings from './pages/Findings';
import Sources from "./pages/Sources";
import Products from "./pages/Products";
import Product from "./pages/Product";
import NotFound from "./pages/NotFound";
import ProductFindings from "./components/Product/Forms/Findings";
import ProductSettings from "./components/Product/Forms/Settings";

const Main = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/finding' element={<Findings/>}></Route>
            <Route exact path='/source' element={<Sources/>}></Route>
            <Route exact path='/product' element={<Products/>}></Route>
            <Route exact path='/product/:id' element={<Product/>}></Route>
            <Route exact path='/product/:id/edit' element={<Product/>}></Route>
            <Route exact path='/product/:id/findings' element={<ProductFindings/>}></Route>
            <Route exact path='/product/:id/settings' element={<ProductSettings/>}></Route>
            <Route path='*' element={<NotFound />}/>
        </Routes>
    );
}

export default Main;