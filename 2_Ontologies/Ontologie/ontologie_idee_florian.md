## Klassenhierarchie
~~~
Thing
   Medium
      BuchMedium
      ZeitschriftMedium
      DatenträgerMedium
      elektronischesMedium
      BroschüreMedium
      anderesMedium

   Nutzer

   Gruppe
      AusleihGruppe (bzw. StudentGruppe)
      Admingruppe
      GastGruppe

   Ausleihe
~~~
## Properties

#### Medium:
- Name:String
- ausgeliehen:bool
- Inhalt:String
- ausleihe:Ausleihe
- +Untertypspezifische Properties (zB ISBN, Ausgabe, Herausgabedatum etc.)

#### Nutzer:
- gehörtZu:Gruppe
- Name:String

#### Gruppe:
- ausleihRecht:bool
- adminRecht:bool

#### Ausleihe:
- ausgeliehenesMedium: Medium
- ausgeliehenVon: Nutzer
- bis: Date
- vorbestellt: bool
- vonbestelltVon: Nutzer
