@echo off

set /a volume=5

echo Downloading NirCmd...

curl -LO https://www.nirsoft.net/utils/nircmd-x64.zip
powershell -Command "Expand-Archive -Path .\nircmd-x64.zip -DestinationPath .\nircmd"

echo Checking volume status...

for /f "tokens=4,5" %%a in ('nircmd.exe mutesysvolume2') do (
  if "%%a"=="mute" (
    echo Volume is muted. Unmuting...
    nircmd.exe mutesysvolume 0
  )
)

echo Increasing volume by %volume%...

for /L %%a in (1,1,%volume%) do (
  .\nircmd\nircmd.exe changesysvolume 6553
)

echo Volume increased.

echo Cleaning up...

del nircmd-x64.zip
rmdir /s /q nircmd

echo Done.

exit
