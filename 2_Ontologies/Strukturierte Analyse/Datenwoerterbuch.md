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
Medium = Medien-ID + 1{Autor}* + Titel + Erscheinungsjahr + Art des Mediums + (Auflage) + (Seitenanzahl) + (Länge) + (Inhalt)  
Nutzer = Nutzer-ID + Personenname + Lehrstuhlangehörigkeit + {Gruppen-ID} + {Ausleihe-ID}  
Nutzergruppe = Nutzergruppenname + 1{Recht-ID}\*  
Recht = Rechte-ID + Rechtbeschreibung

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
