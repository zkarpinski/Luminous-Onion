// This file contains your Data Connector logic
[Version = "1.0.0"]
section LuminousOnion;

// Base API Url
BaseUrl = "https://localhost:8081/api";

[DataSource.Kind="LuminousOnion", Publish="LuminousOnion.Publish"]
shared LuminousOnion.Contents = Value.ReplaceType(LuminousOnion.navigation);

LuminousOnion.navigation = () => 
    let
        objects = #table(
            {"Name", "Data", "ItemKind", "ItemName", "IsLeaf"},
            {
                {"Sources", "Sources", "Folder", "Sources", false}
            }
        ),
        NavTable = Table.ToNavigationTable(objects, "Name", "Data", "ItemKind", "ItemName", "IsLeaf")
    in
        NavTable;

LuminousOnion.Sources =>
    let
        url = Uri.Combine('https://localhost:8081', 'api', "sources"),
        data = Json.Document(Web.Contents(url)),
        results = data,
        col_name = Record.FieldNames(List.First(results)),
        col_type = List.Transform(col_name, each type any),
        output = Table.FromList(results, Record.FieldValues, col_name)
    in
        output;
        

// Data Source Kind description
LuminousOnion = [
    Authentication = [
        // Key = [],
        // UsernamePassword = [],
        // Windows = [],
        Anonymous = []
    ]
];

// Data Source UI publishing description
LuminousOnion.Publish = [
    Beta = true,
    Category = "Other",
    ButtonText = { Extension.LoadString("ButtonTitle"), Extension.LoadString("ButtonHelp") },
    LearnMoreUrl = "https://powerbi.microsoft.com/",
    SourceImage = LuminousOnion.Icons,
    SourceTypeImage = LuminousOnion.Icons
];

LuminousOnion.Icons = [
    Icon16 = { Extension.Contents("LuminousOnion16.png"), Extension.Contents("LuminousOnion20.png"), Extension.Contents("LuminousOnion24.png"), Extension.Contents("LuminousOnion32.png") },
    Icon32 = { Extension.Contents("LuminousOnion32.png"), Extension.Contents("LuminousOnion40.png"), Extension.Contents("LuminousOnion48.png"), Extension.Contents("LuminousOnion64.png") }
];



// Load common library functions
Extension.LoadFunction = (name as text) =>
    let
        Source = Extension.Contents(name),
        Result = Expression.Evaluate(Source, #shared)
    in
        Result;
Table.ToNavigationTable = Extension.LoadFunction("Table.ToNavigationTable.pqm");