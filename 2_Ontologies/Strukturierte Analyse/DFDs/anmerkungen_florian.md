# Anmerkungen:
#### aus DFDs resultierende BNF:
- Nutzer- und Gruppendaten = [Nutzerdaten|Gruppendaten|Gruppenzugehörigkeiten von Nutzern]
- Nutzerdaten = [Studentendaten|Mitarbeiter/Gastdaten]
- Nutzer- und Gruppenanfragen = [Anfragen von Gruppendaten|Anfragen zum Verwalten von Gruppen
 und Gruppenzugehörigkeiten|Anfragen über Gruppenzugehörigkeit von Nutzern|Anfragen zur Verwaltung der Nutzerdaten|Anfragen von Nutzerdaten]
- Anfragen zum Verwalten von Gruppen
 und Gruppenzugehörigkeiten = [Anfragen zum Zuweisen/Löschen/Ändern der
 Gruppenzugehörigkeit von Nutzern|Anfragen zum Erstellen/Ändern/Löschen der Gruppendaten]
- Anfragen zur Verwaltung der Nutzerdaten = [Anfragen zur Änderung der Mitarbeiter-/Gastdaten|Anfragen zur Änderung der Studentendaten|Anfragen zum
Löschen/Einfügen von Nutzerdaten]
- Nutzer- und Gruppenauskünfte = [Auskünfte über Gruppendaten|Auskünfte über Gruppenzugehörigkeit von Nutzern|Auskünfte über Nutzerdaten]


## Änderung der DFDs um dictionary auszubalancieren (siehe VL Uni Leipzig):
#### DFD0:
- Nutzer- und gruppendaten von draußen nach Prozess 1 gelöscht (weil in DFD1 nicht mehr da)

#### DFD1:
Gruppenzugehörigkeit von Nutzern_ um konsistent mit Bezeichnung im DFD2 zu sein
- _Anfragen von Gruppenzugehörigkeit von Nutzern_ umbeannt in _Anfragen über Gruppenzugehörigkeit von Nutzern_ um konsistent mit Bezeichnung im DFD2 zu sein
- _Erteilung/Änderung der Gruppenzugehörigkeit von Nutzern_ umbenannt in _Anfragen zum Verwalten von Gruppen
 und Gruppenzugehörigkeiten_. Das wird dann im DFD2 weiter aufgesplittet. Aber sonst passt das nicht richtig mit DFD2 zusammen.
- _Anfragen zum Verwalten der Nutzerdaten (zum Löschen/Anlegen von Nutzern und zur Änderung der Nutzerdaten)_ umbenannt in _Anfragen zur Verwaltung der Nutzerdaten_

####DFD2, Nutzer:
- _Nutzerdatenauskünfte_ umbenannt in _Auskünfte über Nutzerdaten_ um mit DFD1 konsistent zu sein
