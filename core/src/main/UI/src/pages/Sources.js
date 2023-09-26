import React from "react";
import SourceList from "../components/Source/List";
import NewSourcePopupComponent from "../components/Source/Forms/New";

const Sources = () => {
  return (
    <>
      <NewSourcePopupComponent />
      <SourceList />
    </>
  );
};

export default Sources;
