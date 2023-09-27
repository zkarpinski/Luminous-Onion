import React, { useEffect } from "react";
import api from "../../../shared/api";
import { useParams } from "react-router-dom";
import { Container, Paper, Typography } from "@mui/material";

const FindingView = () => {
  const { id } = useParams();
  const [finding, setFinding] = React.useState({
    name: "",
    id: "",
    description: "",
    packagePath: "",
    status: "",
    source: { id: "", product: { name: "" } },
  });
  const [statuses, setStatuses] = React.useState([]);

  useEffect(() => {
    if (id !== null) {
      // Get the finding
      api.get(`/api/findings/${id}`).then((resp) => {
        setFinding(resp.data);
      });

      api
        .get("/api/resources/findingstatuses")
        .then((data) => setStatuses(data))
        .then(console.log(statuses));
    }
  }, []);

  return (
    <div>
      <Container
        component={Paper}
        style={{ marginTop: 10, marginBottom: 10, padding: 10 }}
      >
        <Typography variant="h5">
          {" "}
          {finding.packageName}:{finding.packageVersionFound}{" "}
        </Typography>
        <Typography>
          Vulnerability: {finding.findingIdentifier} | Severity:{" "}
          {finding.severity} |{" "}
        </Typography>
        <Typography>Path: {finding.packagePath}</Typography>
        <Typography variant="body2">{finding.description} </Typography>
      </Container>
    </div>
  );
};

export default FindingView;
