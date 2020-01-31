### Wichtige Änderungen

* Alle WSDL Ausgabe-Dateinamen können jetzt übergeben werden (-o, -O), endgültige Dateinamen werden davon abgeleitet
* Wenn man nicht will, dass die Ausgabenamen geändert werden, dann `inferOutFilenames = false` setzen 
* Es gibt neue Methoden, um Dateinamen abzufragen:
   * getWsdlOut (-o) (nur bei `inferOutFilenames = true`)
   * getWsdlImplOut (-O) (nur bei `inferOutFilenames = true`)
   * getConsoleOut
   * findModWsdlOut - wsdl-Ausgabedatei von modifierter Java2WSDL
* Alte Methoden funktionieren weiterhin um nichts kaputt zu machen