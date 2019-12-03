# Datenwörterbuch
## Notation
| Symbol        | Bedeutung     |
| :-----------: |---------------|
| = | ist äquivalent zu |
| + | Sequenz (ohne Ordnung) |
| [A\|B] | Auswahl |
| () | optional |
| {} | Wiederholung (beliebig) |
| n{}m | Wiederholung von n bis m (inklusive) |

## Lehrstuhlbibliothek
- Medium = Medien-ID + 1{Autor}* + Titel + Erscheinungsjahr + Art des Mediums + (Auflage) + (Seitenanzahl) + (Länge) + (Inhalt)  
- Student = Nutzer + Matrikelnummer
- Mitarbeiter = Nutzer + Personalnummer
- Nutzer = Nutzer-ID + Personenname + Lehrstuhlangehörigkeit + Gebührenstand + {Gruppen-ID} + {Ausleihe-ID}  
- Gruppe = Gruppenname + 1{Rechte-ID}* + {Nutzer-ID}  
- Recht = Rechte-ID + Rechtbeschreibung  
- Ausleihe = Ausleihe-ID + Medium-ID + Nutzer-ID + Ausleihdatum + Ausleihdauer  
- Warnung = Ausleihe-ID + Warnungsdatum  
- Mahnung = Ausleihe-ID + Mahnungsdatum  
- Vormerkung = Ausleihe-ID + Nutzer-ID + Vormerkungsdatum  
- Verlängerung = Ausleihe-ID + Verlängerungsdatum
- Autor = Personenname + (akademischer Titel)  
- Art des Mediums = ["Buch" | "Zeitschrift" | "Datenträger" | "Elektronisch" | "Broschüre" | "Anderes"]  
- Inhalt = unterstützte Datenformate  
- Lehrstuhlangehörigkeit = ["Student" | "Mitarbeiter"]  
- Nutzer-ID = ID  
- Gruppen-ID = ID  
- Medien-ID = ID  
- Ausleihe-ID = ID    
- Rechte-ID = ID  
- ID = Nummer  
- Matrikelnummer = 7{[0|1|2|3|4|5|6|7|8|9]}7
- Personalnummer = Nummer

- Anfragen = Nutzer- und Gruppenanfragen + Medienanfragen + Mediendaten + Ausleihanfragen + Monitoringanfragen    
- Auskünfte = Nutzer- und Gruppenauskünfte + Ausleihauskünfte + Medienauskünfte + Monitoringauskünfte    
- Nutzeranfragen = Ausleihanfragen + Medienanfragen   
- Nutzerauskünfte = Ausleihauskünfte + Medienauskünfte  
- Ausleihe- und Monitoringdaten = {Ausleihe} + {Warnung} + {Mahnung} + {Vormerkung}    
- Statusanfrage = Medium-ID  
- Statusauskunft = 0{Ausleihe-ID}1 + {Warnung} + {Mahnung} + {Vormerkung}  
- Warnungen = {Warnung}  
- Mahnungen = {Mahnung}  
- Warnungs- und Mahnungsanfragen = {Nutzer-ID}  
- Ausleihanfrage = 1{Medien-ID}* + (Nutzer-ID)  
- Ausleihauskunft = {Ausleihe}  
- Verlängerungsanfrage = 1{Ausleihe-ID}*   
- Verlängerungsauskunft = {Verlängerung}  
- Abgabeanfrage = 1{Ausleihe-ID}*  
- Abgabeauskunft = Gebührenstand  
- Vormerkungsanfrage = 1{Medien-ID}* + (Nutzer-ID)  
- Vormerkungsauskunft = {Vormerkung}  
- Warnungen und Mahnungen = Warnungen + Mahnungen  
- Ausleihanfragen = Ausleihanfrage + Verlängerungsanfrage + Abgabeanfrage + Vormerkungsanfrage  
- Ausleihauskünfte = Ausleihauskunft + Verlängerungsauskunft + Abgabeauskunft + Vormerkungsauskunft  
- Monitoringanfragen = Statusanfrage + Warnungs- und Mahnungsanfragen
- Monitoringauskünfte = Statusauskunft + Mahnungen und Warnungen
- Anfragen zu ausgeliehenen Medien = Ausleihmedienanfragen
- Auskünfte über ausgeliehene Medien = Ausleihmedienauskünfte
- Nutzer- und Gruppendaten = Nutzerdaten + Gruppendaten + Gruppenzugehörigkeiten von Nutzern
- Nutzerdaten = Studentendaten + Mitarbeiter/Gastdaten
- Nutzer- und Gruppenanfragen = Anfragen von Gruppendaten + Anfragen zum Verwalten von Gruppen
 und Gruppenzugehörigkeiten + Anfragen über Gruppenzugehörigkeit von Nutzern + Anfragen zur Verwaltung der Nutzerdaten + Anfragen von Nutzerdaten
- Anfragen zum Verwalten von Gruppen und Gruppenzugehörigkeiten = Anfragen zum Zuweisen/Löschen/Ändern der Gruppenzugehörigkeit von Nutzern + Anfragen zum Erstellen/Ändern/Löschen der Gruppendaten
- Anfragen zur Verwaltung der Nutzerdaten = Anfragen zur Änderung der Mitarbeiter-/Gastdaten + Anfragen zur Änderung der Studentendaten + Anfragen zum Löschen/Einfügen von Nutzerdaten
- Nutzer- und Gruppenauskünfte = Auskünfte über Gruppendaten + Auskünfte über Gruppenzugehörigkeit von Nutzern + Auskünfte über Nutzerdaten
- Gruppendaten = {Gruppe}
- Studentendaten = {Student}
- Mitarbeiter/Gastdaten = {[Mitarbeiter|Nutzer]}
- Gruppenzugehörigkeiten von Nutzern = {Nutzer + 1{Gruppe}\*}
- Anfragen von Gruppendaten = {Gruppe}
- Anfragen über Gruppenzugehörigkeit von Nutzern = {Nutzer + 1{Gruppe}\*}
- Anfragen zum Zuweisen/Löschen/Ändern der Gruppenzugehörigkeit von Nutzern = {Nutzer + 1{Gruppe}\*}
- Anfragen zum Erstellen/Ändern/Löschen der Gruppendaten = Gruppe
- Anfragen zur Änderung der Mitarbeiter-/Gastdaten = [Mitarbeiter|Nutzer]
- Anfragen zur Änderung der Studentendaten = Student
- Anfragen zum Löschen/Einfügen von Nutzerdaten = Nutzer
- Auskünfte über Gruppendaten = {Gruppe}
- Auskünfte über Gruppenzugehörigkeit von Nutzern = {Nutzer + 1{Gruppe}\*}
- Auskünfte über Nutzerdaten = {Nutzer}
- Datenbasis Nutzer und Gruppen = {Nutzer} + {Gruppe}
- Verwalte Medien = Mediendaten + Medienanfragen + Medienauskünfte
- Medienanfragen = Mediensuchanfragen + Ausleihmedienanfragen
- Medienauskünfte = Mediensuchauskünfte + Ausleihmedienauskünfte
- Mediendaten = {(Art des Mediums) + (Medium)+ (Inhalt)}
- Anfrage zur Änderung der Mediendaten = Anfrage zum Einfügen von Medien + Anfrage zum Bearbeiten von Medien + Anfrage zum Löschen von Medien
- Mediensuchanfragen = String
- Ausleihmedienanfragen = {Medien-ID}
- Mediensuchauskünfte = {Medium}  
- Ausleihmedienauskünfte = {Medium}
