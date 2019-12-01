DFD_0 Verwalte Nutzer und Rechte (Eklaerung nur fuer ein Prozess)
Eingehende Pfeile: 1. Nuter- und Rechnteanfragen (von draussen)
			2. Nuter/Rechtedaten (von draussen)
			3. Anfrage nach Nutzer/Rechtedaten (aus dem Prozess Verwalte Nutzer und fuehre Monitorung)
So, unter 1. Pfeile - Z.B. Jemand wollte gern eine auskunft über besstimmten Nutzern, deren Rechnte oder ueber die art 
der Rechten (ich nenne die spaeter in DFD_1 Gruppenrechte), die ins System gibt, bekommen
2. Pfeile - Z.b Admin wollte gern neuen Nutzer hizufuegen oder loeschen, aendern....
Ausgehende Pfeile: 1. Nuzter- und Rechteauskuenfte (antworte auf die 1.,3. Anfragen (eingehende Pfeilen))
Und plus dazu Verwalte Nutzer und Rechte Prozess hat Verbindung (beideseitige) mit der Datenbank (Datenbasis Nutzer und Rechte).
 
DFD_1 Verwalte Nutzer und Rechte
Eklaerung fuer Prozess - Verwalte Nutzer:
Eingehende Pfeile: 1. Anfragen zum Verwalten der Nuztzerdaten 
(zum Löschen/Einfügen der Nutzer und zur Änderung der Nutzerdaten)
		   2. Anfragen nach Nutzerdaten (nur fuer Auskunfte als Antwort zu bekommen)
Ausgehende Pfeile: 1. Auskuenfte ueber Nutzerdaten
Der Prozess Verwalte Nutzer ist auch mit der Datenbank - Datenbasis Nutzer und Rechte - verbunden (beideseitig)
Eklaerung fuer Prozess - Verwalte Rechte:
Eingehende Pfeile: 1. Anfragen nach Äuskünften über Gruppenrechte //z.b welche ueberhaupt hat das System
		2. Rechteanfragen (zur Erteilung/Änderung der Nutzerrechte) //ich denke, es soll klar sein, wass ich damit meinte
		3. Anfragen nach Informationen über Nutzerrechte //z.b welche Rechte hat ein bestimmter Nutzer
Ausgehende Pfeile: 1. Auskünfte über Gruppenrechte //Antwort auf eing. Pfeil 1.
		2. Auskünfte über Nutzerrechte //Antwort auf eing. Pfeil 3.
Der Prozess Verwalte Rechte hat auch beideseitige Verbindungen mit der Datenbank "Datenbasis Nutzer und Rechte"
 (1. Gruppenrechtedaten, 2. Nutzerrechtedaten)

DFD_2 Verwalte Nuzter hat Prozesse:
1. Ändere Mitarbeiter/Gastdaten // ich hab die zusammengefasst, weil nach Ontlogie diese Nutzergruppen
 haben aenliche Attribute)
2. Beantworte Anfragen nach Nutzerdaten
3. Ändere Studentendaten //wie der 1. Prozess, aber jetzt nur fuer Studenten
4. Lösche/Erfasse Nutzerdaten
Eingehende Anfragen zu den Prozessen 1., 3. und 4. sehen z.b zo aus - Anftage zur Änderung der Studentendaten. Und diese Prozesse
haben keine ausgehenden Pfeile.
Prozess 2. hat eingehenden Pfeil: Anffragen nach Nutzendaten und ausgehenden Pfeil: Nutzerdatenauskünfte

DFD_2 Verwalte Rechte hat Prozesse:
1. Beantworte Rechteanfragen //ist nur fuer Beantworten auf Anfragen ueber Rechte der Nutzer zustaendig
2. Zuweise/Lösche/Ändere Rechte der Nutzer
3. Erstelle/Lösche/Ändere Gruppenrechte 
4. Beantworte Anfragen zur Daten der Gruppenrechte //ist nur fuer Antwort auf welche Gruppenrechte gibt es ueberhaupt da

1. und 2. Prozesse sind mit der Datenbank "Datenbasis Nutzer und Rechte" beideseitig verbunden. Die Pfeile heissen Rechtedaten der Nutzer 
3. und 4. Prozesse sind auch mit der Datenbank "Datenbasis Nutzer und Rechte" beideseitig verbunden. Die Pfeile heissen Gruppenrechtedaten



 


