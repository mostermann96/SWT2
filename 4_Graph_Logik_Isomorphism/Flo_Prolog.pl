/* Faktenbasis schamlos von Max geklaut */
/* pruefung(Name_der_Pruefung) - Auflistung aller Prüfungen, die es in der Faktenbasis gibt. */
pruefung(theolog).
pruefung(mathe11).
pruefung(mathe12).
pruefung(mathe21).
pruefung(mathe22).
pruefung(aud).
pruefung(robolab).
pruefung(prog).
pruefung(ikt).
pruefung(fs).
pruefung(swt).
pruefung(swp).
pruefung(ra).
pruefung(db).
pruefung(rn).
pruefung(bus).
pruefung(tgi).
pruefung(hwp).
pruefung(soi).
pruefung(ins).
pruefung(emi).
pruefung(ecg).
pruefung(vert1).
pruefung(vert2).
pruefung(spez1).
pruefung(spez2).
pruefung(liste1).
pruefung(liste2).


/*modul(Name, Pruefungsliste[]) - Auflistung aller Module, die es in der Faktenbasis gibt. */
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

/* abgelegt(Person, Pruefung, Note, Versuch) */
/* Bob sollte bachelor bestanden haben, da er die Vorraussetzungen erfüllt hat. */
abgelegt(bob, theolog, 1.0, 1).
abgelegt(bob, mathe11, 1.0, 2).
abgelegt(bob, mathe12, 1.0, 1).
abgelegt(bob, mathe21, 2.7, 3).
abgelegt(bob, mathe22, 3.3, 1).
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

/* Alice sollte bachelor nicht bestanden haben, da sie in ecg 3x durchgefallen ist. */
abgelegt(alice, theolog, 1.0, 1).
abgelegt(alice, mathe11, 1.0, 2).
abgelegt(alice, mathe12, 4.0, 1).
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
abgelegt(alice, ecg, 5.0, 1).
abgelegt(alice, ecg, 5.0, 2).
abgelegt(alice, ecg, 5.0, 3).
abgelegt(alice, liste1, 1.0, 1).
abgelegt(alice, liste2, 1.0, 1).
abgelegt(alice, vert1, 1.0, 1).
abgelegt(alice, vert2, 1.0, 1).
abgelegt(alice, spez1, 1.0, 1).
abgelegt(alice, spez2, 1.0, 1).

/* Charlie sollte bachelor nicht bestanden haben, da er 2 Drittversuche im Modul B-120 brauchte. */
abgelegt(charlie, theolog, 1.0, 1).
abgelegt(charlie, mathe11, 1.0, 2).
abgelegt(charlie, mathe12, 4.0, 1).
abgelegt(charlie, mathe21, 1.0, 3).
abgelegt(charlie, mathe22, 1.0, 3).
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
abgelegt(charlie, rn, 1.0, 1).
abgelegt(charlie, db, 1.0, 1).
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

/* Theories: */

/* ein Modul ist bestanden, wenn alle Teilprüfungen bestanden sind, und maximal eine Teilprüfung einen Drittversuch benötigte. */
modul_bestanden(Person, Modul):-
	modul(Modul, List),
	teilpruefungen_bestanden(Person, List, 0).

/* Teilprüfung ist bestanden, wenn Note 4.0 oder besser ist. Zählt ausßerdem, wie viele Drittversuche in diesem Modul benutzt wurden. */
/* folgend ist der Fall, dass zum bestehen der Teilprüfung kein Drittversuch benutzt wurde. */
teilpruefungen_bestanden(Person, [Head|Tail], Anzahl_Drittversuche):-
	pruefung(Head),
	abgelegt(Person, Head, Note, Versuch),
	Note =< 4.0,
	Versuch =< 2,
	teilpruefungen_bestanden(Person, Tail, Anzahl_Drittversuche).

/* folgend ist der Fall, dass ein Drittversuch benutzt wurde. */
teilpruefungen_bestanden(Person, [Head|Tail], Anzahl_Drittversuche):-
	pruefung(Head),
	abgelegt(Person, Head, Note, Versuch),
	Note =< 4.0,
	Versuch == 3,
	Anzahl_Drittversuche_Plus1 is Anzahl_Drittversuche+1,
	Anzahl_Drittversuche_Plus1 =< 1,
	teilpruefungen_bestanden(Person, Tail, Anzahl_Drittversuche_Plus1).

/* Rekursionsende. Wenn keine Teilprüfungen mehr in der Liste, dann als bestanden gewertet. */
teilpruefungen_bestanden(Person, [], _).

/* alle_bestanden(Person, Liste) testet, ob die Person alle Module der Liste bestanden hat. */
alle_bestanden(Person, [Head|Tail]) :-
	modul_bestanden(Person, Head),
	alle_bestanden(Person, Tail).

/* Rekusionsbasisfall. */
alle_bestanden(Person, []).

/* bachelor_bestanden(Person) testet, ob die Person die Vorraussetzungen zum Bacehlorabschluss erreicht hat. */
bachelor_bestanden(Person):- alle_bestanden(Person, [b_110, b_120, b_210, b_230, b_240, b_260, b_290, b_270, b_310, b_320, b_330, b_370, b_390, b_3A0, b_3B0, b_410, b_420, b_510, b_520, b_610]).