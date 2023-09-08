
export const enum SeverityType {
    Critical = "CRITICAL",
    High = "HIGH",
    Medium = "MEDIUM",
    Low = "LOW",
    Info = "INFORMATIONAL",
}

export const FindingSeverity = {
    Critical :  {
        abbr: "C",
        color: {
            base: "#8b0000",
            light:"#C57F7F"
        },
        text: "Critical"
    },
    High: {
        abbr: "H",
        color: {
            base: "#FF8C00",
            light: "#ffc57f"
        },
        text: "High"
    },
    Medium: {
        abbr: "M",
        color: {
            base: "#E4AB10",
            light: "#f1d587"
        },
        text: "Medium"
    },
    Low: {
        abbr: "L",
        color: {
            base: "#999999",
            light: "#cccccc"
        },
        text: "Low"
    },
    Info : {
        abbr: "I",
        color : {
            base: "#CCCCCC",
            light: "#eaeaea"
        },
        text: "Informational"
    }
}