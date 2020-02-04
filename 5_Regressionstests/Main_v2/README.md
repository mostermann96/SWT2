### Abgabe / Für Martin
* Skript `run_tests.sh` führt alle Tests aus, die Ausgabe ist leider nicht besonders nützlich
* Für besseren Output ist wahrscheinlich ein Import in eine IDE mit JUnit-Support der beste Weg. (Sorry.)

### Benutzung
* __axis-1_4__-Verzeichnis und __axis-modified.jar__ hier ablegen
* Skripte/Batches __run_tests__ und __compile_tests__ von hier ausführen
    * sind hauptsächlich für Abgabe gedacht
* Bei IDE-Support für JUnit: Version 4.13 benutzen
#### Mit IntelliJ Idea:
* Rechts-Click auf Projekt -> `Open Module Settings`   
-> `Libraries` -> Plus-Symbol -> `Java`   
-> in der Verzeichnisstruktur den `lib` Ordner unter `Main` auswählen  
-> `Apply` -> `Ok`
* Rechts-Click auf Verzeichnis `test` -> `Mark Directory As` -> `Test Sources Root`
* Rechts-Click auf Projekt -> `Run all tests` (danach auch oben rechts mit dem grünen Pfeil)

### Ein-/Ausgabe
* Eingabe: Java-Interfaces werden in __test_input__ abgelegt
* Ausgabe: .wsdl-Dateien werden in __test_output__ abgelegt

repack axis_lib.zip