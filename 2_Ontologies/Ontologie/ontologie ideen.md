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
- anzahlandererBücherDiesesTitels:int
- ausleihdauer:Duration
- +Untertypspezifische Properties (zB ISBN, Ausgabe, Herausgabedatum, Autor, Verlag etc.)

####AnalogMedium:
- TODO

####DigitalMedium
- TODO

#### Nutzer:
- gehörtZu:Gruppe
- Name:String

#### Gruppe:
- ausleihRecht:bool
- adminRecht:bool
- ausleihModifier:Duration

#### Ausleihe:
_entfernen, in Medium und Nutzer einordnen_
- ausgeliehenesMedium: Medium
- ausgeliehenVon: Nutzer
- bis: Date
- vorbestellt: bool
- vonbestelltVon: Nutzer
