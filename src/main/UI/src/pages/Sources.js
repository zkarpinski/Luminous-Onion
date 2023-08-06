import React from 'react';
import SourceList from "../components/SourceList";
import NewSourcePopupComponent from "../components/Source/Forms/New";


const Sources = () => {

    return (
        <div>
            <NewSourcePopupComponent/>
            <SourceList/>
        </div>
    )
}

export default Sources;