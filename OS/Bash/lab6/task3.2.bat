net stop "Dnscache"
set hr=%TIME:~0,2%
set /a mn=%TIME:~3,2%+1
:: check minutes; greater than 60? add n/60 to hour, n%60 to mins
if %mn% geq 60 set /a hr=hr+mn/60 && set /a mn=mn%%60
if %hr% geq 24 set /a h=0
:: pad zeros if necessary
if %mn% leq 9 set mn=0%mn%
if %hr% leq 9 set hr=0%hr%
schtasks /Create /SC ONCE /TN "Starting DNS" /TR task3.2.2.bat /ST %hr%:%mn%
pause