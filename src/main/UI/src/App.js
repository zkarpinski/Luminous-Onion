import React from 'react';
import './App.css';
import Layout from "./layouts/Layout";
import Main from "./Main";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import Login from "./pages/Login";
import Header from "./sections/Header";


const theme = createTheme({
    palette: {
        primary: {
            //main: '#5e35b1'
            main: '#BA03DB'
        },
        secondary: {
            main: '#dce9e6'
        },
        background: {
            default: '#f5f5f5'
        }
    }
});

function App() {
    const [token, setToken] = React.useState();

    if (token) { // TODO Build login logic
        return (
            <ThemeProvider theme={theme}>
                <CssBaseline>
                <Header/>
                    <Login setToken={setToken}/>
                </CssBaseline>
            </ThemeProvider>
        );
    }

  return (
      <ThemeProvider theme={theme}>
        <Layout>
            <CssBaseline />
            <Main/>
        </Layout>
      </ThemeProvider>
  );
}

export default App;
