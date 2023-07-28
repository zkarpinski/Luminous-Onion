import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Findings from './pages/Findings';
import Sources from "./pages/Sources";
import Products from "./pages/Products";

const Main = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/findings' element={<Findings/>}></Route>
            <Route exact path='/sources' element={<Sources/>}></Route>
            <Route exact path='/products' element={<Products/>}></Route>
        </Routes>
    );
}

export default Main;