bezüglich Max' Lösung:
- Predikat pruefung(\_) wird nie benutzt
- Predikat modulliste(\_) wird nie benutzt
- Gewichtung zum Berechnen ob Modul bestanden mit einbeziehen?
- "?-modul_bestanden(bob, b-240)." Evaluiert zu falsch, sollte aber eigentlich True sein
- bei abgelegt(\_) sollten die nicht bestandenen Versuche auch mit hingeschrieben werden? (eher Designfrage)


Änderungen bei meiner Version (im Vergleich zu Max' Lösung):
- Modulliste aus Faktenbasis entfernt
- Theories: bis auf alle_bestanden(\_) alles neu geschrieben
- Theories: mit bachelor_bestanden kann man jetzt prüfen, ob alle Vorraussetzungen zum Bachelorabschluss erfüllt sind
- Faktenbasis: charlie hinzugefügt. Der sollte auch durchgefallen sein, weil er 2x drittversuche in b-120 gebraucht hat
