// Use this file to write queries to test your data connector
let
    //result = LuminousOnion.Contents(),
    Sources = LuminousOnion.Contents(){[Name="Sources"]}[Content],
    A = Sources
in
    A
