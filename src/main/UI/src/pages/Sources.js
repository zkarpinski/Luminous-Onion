import React from 'react';
import SourceList from "../components/SourceList";
import NewSourcePopupComponent from "../components/NewSourcePopupComponent";


const Sources = () => {

    return (
        <>
            <NewSourcePopupComponent/>
            <SourceList/>
        </>
    )
}

export default Sources;