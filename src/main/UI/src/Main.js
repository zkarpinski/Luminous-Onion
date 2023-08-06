import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Findings from './pages/Findings';
import Sources from "./pages/Sources";
import Products from "./pages/Products";
import Product from "./pages/Product";

const Main = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/finding' element={<Findings/>}></Route>
            <Route exact path='/source' element={<Sources/>}></Route>
            <Route exact path='/product' element={<Products/>}></Route>
            <Route exact path='/product/:id' element={<Product/>}></Route>
            <Route exact path='/product/:id/edit' element={<Product/>}></Route>
        </Routes>
    );
}

export default Main;