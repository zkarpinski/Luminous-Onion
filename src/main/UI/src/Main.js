import React from 'react';
import { Routes, Route } from 'react-router-dom';

import Home from './pages/Home';
import Findings from './pages/Findings';

const Main = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Home/>}></Route>
            <Route exact path='/findings' element={<Findings/>}></Route>
        </Routes>
    );
}

export default Main;