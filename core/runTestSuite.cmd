:: Build & Test Back-end
call mvnw.cmd test

:: Test Front-end
npm run test --prefix .\src\main\UI
