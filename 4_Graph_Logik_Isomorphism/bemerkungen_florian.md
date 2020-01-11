bezüglich Max' Lösung:
- Praedikat pruefung(\_) wird nie benutzt
  -- hautpsaechlich zwecks übersicht eingefuehrt, eigentlich egal
- Praedikat modulliste(\_) wird nie benutzt
  -- gelöscht, durch bachelor_bestanden ersetzt
- Gewichtung zum Berechnen ob Modul bestanden mit einbeziehen?
 --ToDo

- "?-modul_bestanden(bob, b-240)." Evaluiert zu falsch, sollte aber eigentlich True sein
 -- modul_bestanden(bob, b_240). evaluiert zu true?
 
- bei abgelegt(\_) sollten die nicht bestandenen Versuche auch mit hingeschrieben werden? (eher Designfrage)
  -- abgelegt(Person, Pruefung, Note, #Versuche) anzahl der versuche ist an 4. Stelle

Änderungen bei meiner Version (im Vergleich zu Max' Lösung):
- Modulliste aus Faktenbasis entfernt
- Theories: bis auf alle_bestanden(\_) alles neu geschrieben
- Theories: mit bachelor_bestanden kann man jetzt prüfen, ob alle Vorraussetzungen zum Bachelorabschluss erfüllt sind
- Faktenbasis: charlie hinzugefügt. Der sollte auch durchgefallen sein, weil er 2x drittversuche in b-120 gebraucht hat
