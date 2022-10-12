@echo off

set SRC=..\kabusapisrv\src\main\java\server\model
dir %SRC%

pause

set DST=..\kabusapp\src\main\java\server\model
dir %DST%

pause

copy %SRC%\*.java %DST%
