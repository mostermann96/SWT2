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
- ID:UUID
- anzahlAndererMedienDiesesTitels:int
- ausleihdauer : Duration
- istAusgeliehen: bool
- istAusgeliehenVon: Nutzer
- ausgeliehenBis: Date
- istVorbestellt: bool
- istVorbestelltVon: Nutzer
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
