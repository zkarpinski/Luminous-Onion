import { render, screen } from '@testing-library/react'
import {BrowserRouter} from "react-router-dom";
import FindingSummarySmall from "./FindingSummarySmall";

const mockFindingData = {
    "critical":1000,
    "high":500,
    "medium":250,
    "low":100,
    "informational":25
}


test("FindingSummarySmall renders successfully with each Severity Type and Value", () => {
    const {container} = render(<BrowserRouter>
        <FindingSummarySmall {...mockFindingData}/></BrowserRouter>);
    const element = screen.getByText("C");
    expect(element).toBeInTheDocument();

    const value = screen.getByText(1000);
    expect(value).toBeInTheDocument();

    expect(container.getElementsByClassName("SeverityTile").length).toBe(5);
})

test("FindingSummarySmall renders without data", () => {
    const {container} = render(<BrowserRouter>
        <FindingSummarySmall/></BrowserRouter>);
    const element = screen.getByText("C");
    expect(element).toBeInTheDocument();

    const value = screen.getAllByText(0);
    expect(value).toHaveLength(5);
})

test("FindingSummarySmall renders disabled", () => {
    const {container} = render(<BrowserRouter>
        <FindingSummarySmall {...mockFindingData} disabled/></BrowserRouter>);

    expect(container.getElementsByClassName("SeverityTileDisabled").length).toBe(5);
})