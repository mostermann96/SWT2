### Übersicht über alle von uns gefundenen Fehler
* mit -o wird falsche Ausgabedatei wird geschrieben, Beispiele:
    * test.eins.zwei.wsdl -> test_impl.wsdl
    * test.wsdl -> test_impl.wsdl
    * testtest -> StringIndexOutOfBoundsException  
* ohne -o wirft das Programm eine NullPointerException
  
__Ausnahme__: Bei Angabe von -O (mit oder ohne -o) sind die Dateinamen korrekt 

* in `wsdl:definitions`: `xmlns:apachesoap="http://xml.apache.org/xml-soap2"` statt `"xmlns:apachesoap=http://xml.apache.org/xml-soap"`
* `Built on` Datum unterscheidet sich, auch Zeitzone
* in `wsdlsoap:binding`: `transport="http://schemas.xmlsoap.org/soap/http11"` statt `transport="http://schemas.xmlsoap.org/soap/http"`
* in `wsdl:service`: `name="URLEndpointService_Impl"` statt `name="URLEndpointService"`