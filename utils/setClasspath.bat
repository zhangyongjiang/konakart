@echo off

rem 
rem Set the classpath
rem -----------------
rem

set KK_WEBAPPS_HOME=%~dp0\..\webapps

set KKADMIN_CLASSPATH=.;"%KK_WEBAPPS_HOME%\konakartadmin\WEB-INF\classes"
set KKADMIN_CLASSPATH=%KKADMIN_CLASSPATH%;"%KK_WEBAPPS_HOME%\konakartadmin\WEB-INF\lib\*"
