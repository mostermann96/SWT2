exam(logik, "Logik").
exam(mathematik2, "Mathematik2").
exam(rechnernetze, "Rechnernetze").
modul(modulMathematik1, "Modul Mathematik 1").

/*person, exam, mark, trial*/
took_exam(bob, logik, 1.0, 1).
took_exam(bob, mathematik1_1, 3, 5).
took_exam(bob, mathematik1_2, 5, 1).
took_exam(bob, mathematik2, 3, 2).
took_exam(bob, rechnernetze, 2.0, 1).

/*person, exam*/
is_exam_passed(Person, Exam) :-
	took_exam(Person, Exam, Mark, Trial),
	Mark =< 4.0,
	Trial =< 3.

/*person, exams in modul, weights*/
is_modul_passed(Person, [], []).
is_modul_passed(Person, [Head1|Tail1], [Head2|Tail2]):-
	took_exam(Person, Head1, Mark, Trial),
	AverageSum is Mark*Head2,
	is_modul_passed2(Person, Tail1, Tail2, AverageSum, Head2).

/*person, exams in modul, average*/
is_modul_passed2(Person, [], [], AverageSum, Weights) :-
	AverageSum=<4.0*Weights.
is_modul_passed2(Person, [Head1|Tail1], [Head2|Tail2], AverageSum, Weights):-
	took_exam(Person, Head1, Mark, Trial),
	NewAverageSum is Mark*Head2+AverageSum,
	NewWeights is Weights+Head2,
	is_modul_passed2(Person, Tail1, Tail2, NewAverageSum, NewWeights).

/*person, exam, requirements*/
is_allowed_to_participate(Person, Exam, []).
is_allowed_to_participate(Person, Exam, [Head|Tail]) :-
	is_exam_passed(Person, Head),
	is_allowed_to_participate(Person, Exam, Tail).

/*person, exam, another exams in modul*/
is_allowed_to_repeat_exam(Person, Exam, []) :-
	took_exam(Person, Exam, Mark, Trial),
	Mark > 4.0,
	Trial =< 3.
is_allowed_to_repeat_exam(Person, Exam, [Head|Tail]) :-
	took_exam(Person, Head, Mark, Trial),
	is_allowed_to_repeat_exam_2(Person, Exam, Tail, Trial-1).
	
/*person, exam, another exams in modul, used repetition*/
is_allowed_to_repeat_exam_2(Person, Exam, [], Repetitions) :-
	took_exam(Person, Exam, Mark, Trial),
	Mark > 4.0,
	UsedRepetition is Repetitions+Trial-1,
	UsedRepetition =< 3.
is_allowed_to_repeat_exam_2(Person, Exam, [Head|Tail], Repetitions) :-
	took_exam(Person, Head, Mark, Trial),
	UsedRepetition is Repetitions+Trial-1,
	is_allowed_to_repeat_exam_2(Person, Exam, Tail, UsedRepetition).

/*person, exams*/
are_all_exams_passed(Person, []).
are_all_exams_passed(Person, [Head|Tail]) :-
	is_exam_passed(Person, Head),
	are_all_exams_passed(Person, Tail).