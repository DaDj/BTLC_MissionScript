@echo off
setlocal

del /Q streamed\*.*
cd ..\data
sc.exe -cmdlist -missionout -san_andreas_pc -f ..\scripts\main.sc -close

if ERRORLEVEL 1 goto err

xcopy /Y /D ..\Scripts\main.scm ..\data\script\main.scm
goto streamed


:streamed
del script\script.img
endlocal
cdimagemaker ..\data\script\script.img streamed\*.scm

goto end


:err
@ECHO Error compiling script
goto end


:end
pause