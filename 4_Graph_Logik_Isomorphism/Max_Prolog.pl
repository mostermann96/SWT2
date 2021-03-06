/*Facts*/
/* Pruefung(Kürzel, Gewicht im Modul) */
pruefung(theolog, 1).
pruefung(mathe11, 1).
pruefung(mathe12, 2).
pruefung(mathe21, 1).
pruefung(mathe22, 2).
pruefung(aud, 1).
pruefung(robolab, 1).
pruefung(prog, 1).
pruefung(ikt, 1).
pruefung(fs, 1).
pruefung(swt, 1).
pruefung(swp, 1).
pruefung(ra, 1).
pruefung(db, 1).
pruefung(rn, 1).
pruefung(bus, 1).
pruefung(tgi, 1).
pruefung(hwp, 1).
pruefung(soi, 1).
pruefung(ins, 1).
pruefung(emi, 1).
pruefung(ecg, 1).
pruefung(vert1, 1).
pruefung(vert2, 1).
pruefung(spez1, 1).
pruefung(spez2, 1).
pruefung(liste1, 1).
pruefung(liste2, 1).

/*modul(Name, Prüfungsliste[]) */
modul(b_110, [mathe11, mathe12]).
modul(b_120, [mathe21, mathe22]).
modul(b_210, [aud]).
modul(b_230, [robolab]).
modul(b_240, [prog]).
modul(b_260, [ikt]).
modul(b_290, [theolog]).
modul(b_270, [fs]).
modul(b_310, [swt]).
modul(b_320, [swp]).
modul(b_330, [ra]).
modul(b_370, [db, rn]).
modul(b_380, [bus]).
modul(b_390, [tgi, hwp]).
modul(b_3A0, [soi]).
modul(b_3B0, [ins]).
modul(b_410, [emi]).
modul(b_420, [ecg]).
modul(b_510, [vert1, vert2]).
modul(b_520, [spez1, spez2]).
modul(b_610, [liste1, liste2]).

/*abgelegt(Student, Pruefung, Note, Versuch)*/
/* bob besteht durch die Anzahlsregel nicht */
abgelegt(bob, theolog, 1.0, 1).
abgelegt(bob, mathe11, 1.0, 2).
abgelegt(bob, mathe12, 1.0, 1).
abgelegt(bob, mathe21, 1.0, 3).
abgelegt(bob, mathe22, 1.0, 1).
abgelegt(bob, aud, 1.0, 1).
abgelegt(bob, robolab, 1.0, 1).
abgelegt(bob, prog, 1.0, 1).
abgelegt(bob, ikt, 1.0, 1).
abgelegt(bob, tgi, 1.0, 1).
abgelegt(bob, fs, 1.0, 1).
abgelegt(bob, swt, 1.0, 1).
abgelegt(bob, swp, 1.0, 0).
abgelegt(bob, hwp, 1.0, 0).
abgelegt(bob, ra, 1.0, 1).
abgelegt(bob, rn, 1.0, 1).
abgelegt(bob, db, 1.0, 1).
abgelegt(bob, bus, 1.0, 1).
abgelegt(bob, soi, 1.0, 1).
abgelegt(bob, ins, 1.0, 1).
abgelegt(bob, emi, 1.0, 1).
abgelegt(bob, ecg, 1.0, 1).
abgelegt(bob, liste1, 1.0, 1).
abgelegt(bob, liste2, 1.0, 1).
abgelegt(bob, vert1, 1.0, 1).
abgelegt(bob, vert2, 1.0, 1).
abgelegt(bob, spez1, 1.0, 1).
abgelegt(bob, spez2, 1.0, 1).

/* Alice besteht, obwohl sie die Nikolausklausur nicht besteht, da Gewicht 1:2*/
abgelegt(alice, theolog, 1.0, 1).
abgelegt(alice, mathe11, 5.0, 1).
abgelegt(alice, mathe12, 1.0, 2).
abgelegt(alice, mathe21, 1.0, 1).
abgelegt(alice, mathe22, 1.0, 1).
abgelegt(alice, aud, 1.0, 1).
abgelegt(alice, robolab, 1.0, 1).
abgelegt(alice, prog, 1.0, 1).
abgelegt(alice, ikt, 4.0, 1).
abgelegt(alice, tgi, 1.0, 1).
abgelegt(alice, fs, 1.0, 1).
abgelegt(alice, swt, 1.0, 1).
abgelegt(alice, swp, 1.0, 0).
abgelegt(alice, hwp, 1.0, 0).
abgelegt(alice, ra, 1.0, 1).
abgelegt(alice, rn, 1.0, 1).
abgelegt(alice, db, 1.0, 1).
abgelegt(alice, bus, 1.0, 1).
abgelegt(alice, soi, 1.0, 1).
abgelegt(alice, ins, 1.0, 1).
abgelegt(alice, emi, 1.0, 1).
abgelegt(alice, ecg, 4.0, 1).
abgelegt(alice, liste1, 1.0, 1).
abgelegt(alice, liste2, 1.0, 1).
abgelegt(alice, vert1, 1.0, 1).
abgelegt(alice, vert2, 1.0, 1).
abgelegt(alice, spez1, 1.0, 1).
abgelegt(alice, spez2, 1.0, 1).

/* geklaut von Florian, Charlie besteht nicht, da das Modul RN&DB einen Schnitt > 4 hat */
abgelegt(charlie, theolog, 1.0, 1).
abgelegt(charlie, mathe11, 1.0, 2).
abgelegt(charlie, mathe12, 4.0, 1).
abgelegt(charlie, mathe21, 1.0, 1).
abgelegt(charlie, mathe22, 1.0, 1).
abgelegt(charlie, aud, 1.0, 1).
abgelegt(charlie, robolab, 1.0, 1).
abgelegt(charlie, prog, 1.0, 1).
abgelegt(charlie, ikt, 4.0, 1).
abgelegt(charlie, tgi, 1.0, 1).
abgelegt(charlie, fs, 1.0, 1).
abgelegt(charlie, swt, 1.0, 1).
abgelegt(charlie, swp, 1.0, 0).
abgelegt(charlie, hwp, 1.0, 0).
abgelegt(charlie, ra, 1.0, 1).
abgelegt(charlie, rn, 3.3, 1).
abgelegt(charlie, db, 5.0, 1).
abgelegt(charlie, bus, 1.0, 1).
abgelegt(charlie, soi, 1.0, 1).
abgelegt(charlie, ins, 1.0, 1).
abgelegt(charlie, emi, 1.0, 1).
abgelegt(charlie, ecg, 1.0, 1).
abgelegt(charlie, liste1, 1.0, 1).
abgelegt(charlie, liste2, 1.0, 1).
abgelegt(charlie, vert1, 1.0, 1).
abgelegt(charlie, vert2, 1.0, 1).
abgelegt(charlie, spez1, 1.0, 1).
abgelegt(charlie, spez2, 1.0, 1).


/* Theories */

/* Testen einzelner Module, inkl Versuchsanzahl */
modul_bestanden(Person, ModulName):-

	modul(ModulName, [Head|Tail]),
	print(ModulName),
	versuchsanzahl(Person, [Head|Tail], 0),
	modulnote(Person, [Head|Tail], 0, 0).
	
/* Teste Versuchsanzahl <4 pro Modul
versuchsanzahl(Person, Prüfungsliste[], Summe der Versuche)*/
versuchsanzahl(Person, [Pruefung|Tail], Sum):-
	abgelegt(Person, Pruefung, Note, Attempt),
	Temp is Sum+Attempt,
	Temp =< 3,
	versuchsanzahl(Person, Tail, Temp).

versuchsanzahl(Person, [], Versuche).	

modulnote(Person, [Pruefung|Tail], GesNote, GesGewicht):-
	abgelegt(Person, Pruefung, Note, Attempt),
	pruefung(Pruefung, Gewicht),
	TempNote is (Note*Gewicht) + GesNote,
	TempGewicht is Gewicht + GesGewicht,
	modulnote(Person, Tail, TempNote, TempGewicht).
	
modulnote(Person, [], GesNote, GesGewicht):-
	(GesNote / GesGewicht) =< 4 .	
	

/* alle_bestanden(Person, modulliste[]) teste alle Module in Liste*/

alle_bestanden(Person, [Head|Tail]) :-
	modul_bestanden(Person, Head),
	alle_bestanden(Person, Tail).

alle_bestanden(Person, []).

/*bei nicht bestehen bleibt output am betroffenen modul stehen*/
bachelor_bestanden(Person):-
	alle_bestanden(Person, [b_110, b_120, b_210, b_230, b_240, b_260, b_290, b_270, b_310, b_320, b_330, b_370, b_390, b_3A0, b_3B0, b_410, b_420, b_510, b_520, b_610]).
