param([int]$Port = 8081)

$env:BOOKVERSE_SMTP_USER = 'kimneju@gmail.com'
mvn "-Dmaven.repo.local=$PSScriptRoot\.m2\repository" spring-boot:run "-Dspring-boot.run.arguments=--server.port=$Port"
exit $LASTEXITCODE
