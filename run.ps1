param([int]$Port = 8081)

$env:BOOKVERSE_SMTP_USER = 'kimneju@gmail.com'
if ([string]::IsNullOrWhiteSpace($env:BOOKVERSE_SMTP_PASSWORD)) {
    Write-Error 'Chưa cấu hình BOOKVERSE_SMTP_PASSWORD. Xem mục OTP trong README.md.'
    exit 1
}
mvn "-Dmaven.repo.local=$PSScriptRoot\.m2\repository" spring-boot:run "-Dspring-boot.run.arguments=--server.port=$Port"
exit $LASTEXITCODE
