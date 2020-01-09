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

modulliste([b_110, b_120, b_210, b_230, b_240, b_260, b_290, b_270, b_310, b_320, b_330, b_370, b_390, b_3A0, b_3B0, b_410, b_420, b_510, b_520, b_610]).

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

abgelegt(alice, theolog, 1.0, 1).
abgelegt(alice, mathe11, 1.0, 2).
abgelegt(alice, mathe12, 4.0, 1).
abgelegt(alice, mathe21, 1.0, 1).
abgelegt(alice, mathe22, 1.0, 1).
abgelegt(alice, aud, 1.0, 1).
abgelegt(alice, robolab, 1.0, 1).
abgelegt(alice, prog, 1.0, 1).
abgelegt(alice, ikt, 1.0, 1).
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
abgelegt(alice, ecg, 1.0, 1).
abgelegt(alice, liste1, 1.0, 1).
abgelegt(alice, liste2, 1.0, 1).
abgelegt(alice, vert1, 1.0, 1).
abgelegt(alice, vert2, 1.0, 1).
abgelegt(alice, spez1, 1.0, 1).
abgelegt(alice, spez2, 1.0, 1).

modul_bestanden(Person, ModulName):-

	modul(ModulName, [Head|Tail]),
	print(ModulName),
	versuch_berech(Person, [Head|Tail], 0),
	bestanden(Person, Head),
	modul_test(Person, Tail).

versuch_berech(Person, [Pruefung|Tail], Sum):-
	abgelegt(Person, Pruefung, Note, Attempt),
	Temp is Sum+Attempt,
	Temp =< 3,
	versuch_berech(Person, Tail, Temp).

versuch_berech(Person, [], Versuche).	


	
	

modul_test(Person, [Head|Tail]):-
	bestanden(Person, Head),
	modul_test(Person, Tail).
modul_test(Person, []).

bestanden(Person, Pruefung):-

	abgelegt(Person, Pruefung, Note, Versuch),
	Note =< 4.0,
	Versuch =< 3.

alle_bestanden(Person, [Head|Tail]) :-
	modul_bestanden(Person, Head),
	alle_bestanden(Person, Tail).

alle_bestanden(Person, []).