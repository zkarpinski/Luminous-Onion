import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {createTheme, ThemeProvider} from "@mui/material";
import {BrowserRouter} from "react-router-dom";

const theme = createTheme({
    palette: {
        primary: {
            //main: '#5e35b1'
            main: '#BA03DB'
        },
        secondary: {
            main: '#dce9e6'
        }
    }
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
      <BrowserRouter>
          <ThemeProvider theme={theme}>
            <App />
          </ThemeProvider>
      </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
