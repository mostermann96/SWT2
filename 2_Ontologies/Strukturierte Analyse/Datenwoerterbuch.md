# Datenwörterbuch
## Notation (siehe Balzert S.247):
| Symbol        | Bedeutung     |
| :-----------: |---------------|
| = | ist äquivalent zu |
| + | Sequenz (ohne Ordnung) |
| [A\|B] | Auswahl |
| () | optional |
| {} | Wiederholung (beliebig) |
| n{}m | Wiederholung von n bis m (inklusive) |

## Lehrstuhlbibliothek

#### Basis (feinste Datenströme in diese aufsplitten, notfalls ergänzen)
Medium = Medien-ID + 1{Autor}* + Titel + Erscheinungsjahr + Art des Mediums + (Auflage) + (Seitenanzahl) + (Länge) + (Inhalt)  
Nutzer = Nutzer-ID + Personenname + Lehrstuhlangehörigkeit + Gebührenstand + {Gruppen-ID} + {Ausleihe-ID}  
Gruppe = Gruppenname + 1{Rechte-ID}\* + {Nutzer-ID}  
Recht = Rechte-ID + Rechtbeschreibung  
Ausleihe = Ausleihe-ID + Medium-ID + Nutzer-ID + Ausleihdatum + Ausleihdauer  

Autor = Personenname + (akademischer Titel)  
Art des Mediums = ["Buch" | "Zeitschrift" | "Datenträger" | "Elektronisch" | "Broschüre" | "Anderes"]  
Inhalt = unterstützte Datenformate  
Lehrstuhlangehörigkeit = ["Student" | "Mitarbeiter"]  
Nutzer-ID = ID  
Gruppen-ID = ID  
Medien-ID = ID  
Ausleihe-ID = ID    
Rechte-ID = ID  
ID = Nummer  

#### Kontextdia (hier vorkommende Datenströme werden in Ströme der Ebene darunter (DFD-0) augesplittet)
Anfragen = Nutzer- und Gruppenanfragen + Medienanfragen + Mediendaten + Ausleihanfragen + Ausleihdaten  
Auskünfte = Nutzer- und Gruppenauskünfte + Ausleihauskünfte + Medienauskünfte  
Nutzeranfragen = Ausleihanfragen + Medienanfragen   
Nutzerauskünfte = Ausleihauskünfte + Medienauskünfte  

#### DFD-0
Nutzer- und Gruppenanfragen = ...



## Dictionary Florian (Nutzer & Gruppen):

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

## Dictionary Max	(Medien)

- Mediendaten  = [Mediendaten|Medienanfragen|Medienauskünfte]
- Medienanfragen = [Medienanfragen|Ausleihmedienanfragen]
- Medienauskünfte = [Medienauskünfte|Ausleihmedienauskünfte]
- Anfrage zur Änderung der Mediendaten = [Anfrage zum Einfügen von Medien|Anfrage zum Bearbeiten von Medien|Anfrage zum Löschen von Medien]
