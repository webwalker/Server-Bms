@echo off
title MAVEN命令操作中心
mode con: cols=60 lines=30
color 0a

:main
echo.请选择执行的命令:
echo [Eclipse项目初始化] 请按 1
echo [发   布   项   目] 请按 2
echo [退             出] 请按 `
echo.
set /p choice=你的选择：
echo.
if "%choice%"=="1"  goto init
if "%choice%"=="2"  goto publish
if "%choice%"=="`"  goto end

goto main

:init
echo  Eclipse项目初始化开始....
start mvn eclipse:eclipse
echo  项目初始化完成
echo.
echo.
goto main

:publish
echo  项目打包开始....
start mvn clean install
echo  项目打包完成
echo.
echo.
goto main

：end














