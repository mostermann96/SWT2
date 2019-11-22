# Klassenhierarchie
~~~
Thing
   Medium
      AnalogMedium
         BuchMedium
         ZeitschriftMedium
         BroschüreMedium
         DatenträgerMedium
         anderesMedium
      elektronischesMedium
   Nutzer
      Student
      Admin
      Gast
   Gruppe

~~~
# Properties



#### Medium:
- Titel:String
- Inhalt:String
- ausleihe:Ausleihe
- ausleihStatus: bool
- istAusgeliehenVon
- istVorbestelltVon
- ID
- anzahlAndererBücherDiesesTitels:int
- ausleihdauer : Duration
- ausgeliehenVon: Nutzer
- ausgeliehenBis: Date
- vorbestellt: bool
- vorbestelltVon: Nutzer
- +Untertypspezifische Properties (zB ISBN, Ausgabe, Herausgabedatum, Autor, Verlag etc.)

#### AnalogMedium:
- ?

#### Nutzer:
- gehörtZu:Gruppe
- Name:String
- ausgelieheneMedien: Medium

#### Student:
- Matrikelnummer

#### Gruppe:
- ausleihRecht:bool
- adminRecht:bool
- ausleihModifier:Duration

# noch offene Fragen:
- wenn man über den Inhalt von Dokumenten suchen können soll, muss dann der Inhalt nicht auch als Property irgendwo in den Medien auftauchen?
