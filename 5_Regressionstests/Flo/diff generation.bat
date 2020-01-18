@echo off

::this batch file must be in the same directory as the axis-modified.jar and the axis-1_4 folder that contains the normal axis binaries.

::makes sure there is a clean 'generated' folder
IF EXIST generated\ (
   DEL /S/Q generated\* > NUL
   for /D %%x in (generated\*) do @rd /s /q "%%x"
) ELSE (
   mkdir generated
)

::generates output of the normal axis.Java2WSDL library given a few test-classes
set CLASSPATH=axis-1_4/lib/*
echo generating output for normal axis...
mkdir generated\normal
java org.apache.axis.wsdl.Java2WSDL > generated/normal/no_args.txt
java org.apache.axis.wsdl.Java2WSDL -o generated/normal/URLEndpoint.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
::TODO add more classes here


::generates output of the modified axis.Java2WSDL version given the same test classes
set CLASSPATH=axis-modified.jar;axis-1_4/lib/*
echo generating output for modified axis...
mkdir generated\modified
java org.apache.axis.wsdl.Java2WSDL > generated/modified/no_args.txt
java org.apache.axis.wsdl.Java2WSDL -o generated/modified/URLEndpoint.wsdl -l"http://localhost:8080/axis/services/WidgetPrice" javax.xml.messaging.URLEndpoint
::TODO add more classes here

::finds differences between the outputs and collects them in a list
echo finding differences between the two versions...
IF NOT EXIST results\ (
   mkdir results
)
FC generated\normal\* generated\modified\* >results\result.txt

::just an output, doesn't do anything
echo . && echo all done. See result.txt for information.

::to stop the script from closing immediately when it's done
pause
