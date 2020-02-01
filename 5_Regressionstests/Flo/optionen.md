

| option    | tags to test | done | Testname|
|-----------|--------------|------|---------|
| -h    |  Keine; sondern output txt Datei            |done | testOptionHelp
| -i <dummy-wsdl>  |  sowohl in input vorhandene, also auch neue   |done, aber geht noch nicht |testOptionInput|
| -o | ist location richtig?  |implizit done |
| -l | wsdlsoap:address + wsdl:service name=inClassFileName   |done | testLocationOption
| -l with porttype overwritten by -s | wsdl:port|done|testPortNameOverwrite
| -P \<name> |  wsdl:portType name=\<name> |done|testPortTypeNameOverwrite
| -b \<name> |  wsdl:binding name=\<name>   |done|testBindingNameOverwrite
| -S \<name>  |  wsdl:service name=\<name>   |done|testServiceElementNameOverwrite
| -n \<target namespace>   | wsdl:definitions |done  |testTargetNameSpaceOverwrite
| -p \<package> \<namespace>   |  ??            |
| -p \<pack1> \<name1> -p \<pack2> \<name2> |  ??            |
| -m w/ one method that exists | TODO |done            |testMethodsOnce
| -m w/ two methods that exist | TODO  |done         |testMethodsTwice
|-m with a method that doesn't exist | TODO | done| testNonExistentMethod
| -a with an extended class  | misc    | done | testClassExtension
| -w with All  |              |done|testOutputModeAll
| -w with Interface |              |done|testOutputModeInterface
| -w with Implementation and -L  |   |done|testOutputModeImplementation
| -N \<name>|   |done|testImplementationNameSpace
| -O \<File>  |   |done|testOutputImplName
| -i \<impl-class>  |   |
| -x with one method  |   |done|testExcludeMethodsOnce
| -x with two methods  |   |done|testExcludeMethodsTwice
| -c \<List>  |   |done|testStopClasses
| -T \<1.1>  |   |done|testVersion1_1
| -T \<1.2>  |   |done|testVersion1_2
| -A with DEFAULT  |   |
| -A with OPERATION |   |
| -A with NONE  |   |
| -y RPC | |
| -y DOCUMENT | |
| -y WRAPPED | |
| -u LITERAL | |
| -u ENCODED | |
| -e ? | |
| -c ? | |
| -X ? | |
| -d ? | |
