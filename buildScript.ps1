$env:Path = "C:\Program Files\Java\jdk-25.0.4.1\bin;" + $env:Path
Write-Host "Long build on VM, 25"
$global:repo = Join-Path $env:USERPROFILE ".m2\repository"
$global:h2 = Join-Path $repo "com\h2database\h2\2.4.240\h2-2.4.240.jar"
$global:jack = Join-Path $repo "com\healthmarketscience\jackcess\jackcess\4.0.5\jackcess-4.0.5.jar"
$global:cpCompile = "$h2;$jack"
$global:cpRun = "target\classes;$h2;$jack"
if (Test-Path target\classes) { Remove-Item -Recurse -Force target\classes }
New-Item -ItemType Directory -Force target\classes | Out-Null
javac --release 25 -cp "$cpCompile" -d target\classes -sourcepath src\main\java src\main\java\com\whatchamacallitwharf\WW.java