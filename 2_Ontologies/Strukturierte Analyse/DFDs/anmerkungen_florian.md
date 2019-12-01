# Anmerkungen:

## Allgemein:
- Kontextdiagramm fehlt noch; wer macht das?
- Funktionsbaum analog
- Nummerierung von DFDs: macht es da nicht sinn, sowas wie 2.1 zu nehmen um die Diagramme einer Hierarchieebene auseinander halten zu können?

### DFD 0:
- Nutzer/Rechtedaten -> Nutzer- und Rechtedaten
- Ausleihmediumanfragen -> Anfragen zu ausgeliehenen Medien
- Ausleihmediumauskünfte -> Auskünfte über ausgeliehene Medien
- Mediumdaten -> Mediendaten

### DFD 1(Nutzer & Rechte):
- Bei verwalte Nutzer: statt _zum Löschen/Einfügen der Nutzer_ lieber _zum Löschen/Anlegen von Nutzern_. Klingt besser :)
- _Anfragen nach Nutzerdaten_ -> _Anfragen von Nutzerdaten_
- es gibt laut Ontologie keine Nutzerrechte. Nur Gruppen haben Rechte. Die Rechte eines Nutzer sind also nur von seiner Gruppe abhängig. Also können wir alles mit Nutzerrechten entfernen und stattdessen irgendwie darstellen, dass man die Gruppe von einem Nutzer ableiten kann. Genauer also:
   * Ich würde noch von _Verwalte Rechte_ einen Datenfluss nach _Verwalte Nutzer_ hinzufügen mit Name `Anfragen von Gruppenzugehörigkeit von Nutzern`
   * und von _Verwalte Nutzer_ nach _Verwalte Rechte_ einen Datenfluss mit Name `Auskünfte über Gruppenzugehörigkeit von Nutzern`


### DFD 2(Nutzer):
- bei Prozess 3: `Anftage` -> `Anfragen`
- bei Prozess 2: `Anffragen` -> `Anfragen`
- Ich würde noch ein Prozess einfügen: `ändere Gruppenzugehörigkeit von Nutzern` mit den offensichtlichen Datenflüssen davon

### DFD 2(Rechte):
- bei Prozess 3: `Anftagen` -> `Anfragen`
- hier würde ich Prozess 1 und 2 (und dessen Datenflüsse) löschen, denn es gibt laut Ontologie ja keine Nutzerrechte
- vielleicht könnten wir dafür noch ein Prozess "Erstelle/Lösche/Ändere Gruppen" hinzufügen?
