play :- 
    nl,
    write('======'), nl,
    write('sowing'), nl,
    playAskColor.

playAskColor :- 
    nl, write('Color for human player ? (x or o)'), nl,
    read(Player), nl,
    (
        Player \= o, Player \= x, !,     % If not x or o -> not a valid color
        write('Error : not a valid color !'), nl,
        playAskColor                     % Ask again
        ;
        Board = [[6,6,6,6,6,6], 0, [6,6,6,6,6,6], 0],
        show(Board), nl,

        play([x, play, Board], Player)
    ).

play([Player, play, Board], Player) :- !,
    write('You Play  : '),
    nl, write('Next move ?'), nl,
    read(Pos), nl,
    (
        humanMove([Player, play, Board], [NextPlayer, State, NextBoard], Pos), !,
        show(NextBoard),
        (
            State = win, !,
            nl, write('End of game : '),
            write(Player), write(' win !'), nl, nl
            ;
            State = lose, !,
            nl, write('End of game : '),
            write(Player),write(' losed !'), nl, nl
            ;
            State = draw, !,
            nl, write('End of game : '),
            write('draw !'), nl, nl
            ;
            play([NextPlayer, play, NextBoard], Player)
        )
        ;
        write(' -> Bad Move !'), nl,
        play([Player, play, Board], Player)
    ).

    
nextP(o, x).
nextP(x, o).
nextPlayer(x, [Lstx, _, _, _], Pos, x) :-
    nth0(Pos, Lstx, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 =:= 6, !.
nextPlayer(x, [Lstx, _, Lsto, _], Pos, x) :-
    nth0(Pos, Lstx, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 > 6, P1 < 12,
    P2 is P1 - 6,
    nth0(P2, Lsto, E1),
    P3 is E1 + P1 + 1,
    P4 is mod(P3, 13),
    P4 =:= 6, !.
nextPlayer(x, [Lstx, _, Lsto, _], Pos, X) :-
    nth0(Pos, Lstx, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 =:= 12, !,
    nextPlayer(x, [Lstx, _, Lsto, _], 0, X).
nextPlayer(o, [_, _, Lsto, _], Pos, o) :-
    nth0(Pos, Lsto, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 =:= 6, !.
nextPlayer(o, [Lstx, _, Lsto, _], Pos, o) :-
    nth0(Pos, Lsto, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 > 6, P1 < 12,
    P2 is P1 - 6,
    nth0(P2, Lstx, E1),
    P3 is E1 + P1 + 1,
    P4 is mod(P3, 13),
    P4 =:= 6, !.
nextPlayer(o, [Lstx, _, Lsto, _], Pos, X) :-
    nth0(Pos, Lsto, Elem),
    P is Pos + Elem,
    P1 is mod(P, 13),
    P1 =:= 12, !,
    nextPlayer(o, [Lstx, _, Lsto, _], 0, X).
nextPlayer(X1, _, _, X2) :-
    nextP(X1, X2).
winPos(P, [[X1, X2, X3, X4, X5, X6], XStore, [O1, O2, O3, O4, O5, O6], OStore]) :-
    equal(X1, X2, X3, X4, X5, X6, 0), P = x, XStore > OStore ;
    equal(O1, O2, O3, O4, O5, O6, 0), P = o, OStore > XStore ;
    equal(X1, X2, X3, X4, X5, X6, 0), P = o, XStore < OStore ;
    equal(O1, O2, O3, O4, O5, O6, 0), P = x, OStore < XStore.
losePos(P, [[X1, X2, X3, X4, X5, X6], XStore, [O1, O2, O3, O4, O5, O6], OStore]) :-
    equal(X1, X2, X3, X4, X5, X6, 0), P = x, XStore < OStore ;
    equal(O1, O2, O3, O4, O5, O6, 0), P = o, OStore < XStore ;
    equal(X1, X2, X3, X4, X5, X6, 0), P = o, XStore > OStore ;
    equal(O1, O2, O3, O4, O5, O6, 0), P = x, OStore > XStore.
drawPos(_, [[X1, X2, X3, X4, X5, X6], _, [O1, O2, O3, O4, O5, O6], _]) :-
    equal(X1, X2, X3, X4, X5, X6, 0);
    equal(O1, O2, O3, O4, O5, O6, 0).

equal(X, X, X, X, X, X, X).



play([Player, play, Board], HumanPlayer) :-
    nl, write('Opponent play : '), nl, nl,
    write('Next move ?    '),
    minimax([Player, play, Board], HumanPlayer, 3, BestVal, Pos),
    write(Pos), nl,
    %read(Pos), nl,
    (
        humanMove([Player, play, Board], [NextPlayer, State, BestSuccBoard], Pos), !,
        show(BestSuccBoard),
        (
            State = win, !,
            nl, write('End of game : '),
            write(Player), write(' win !'), nl, nl
            ;
            State = lose, !,
            nl, write('End of game : '),
            write(Player),write(' losed !'), nl, nl
            ;
            State = draw, !,
            nl, write('End of game : '),
            write('draw !'), nl, nl
            ;
            play([NextPlayer, play, BestSuccBoard], HumanPlayer)
        )
        ;
        write('--> Bad'), nl
    ).


set1(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]) :- 
    number(P),
    P < 6, !,
    nth0(P, Lsto, Elem),
    number(Elem),
    (
        Elem \= 0, !,
        replace(Lsto, P, 0, Lsto3),
        set2(P, o, [Lstx, XSt, Lsto3, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem)
        ;
        set2(2, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0)
    ).

set1(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]) :- 
    number(P),
    P1 is mod(P,14),
    P1 > 6, !,
    P2 is P1 - 7,
    nth0(P2, Lstx, Elem),
    number(Elem),
    (
        Elem \= 0, !,
        replace(Lstx, P2, 0, Lstx3),
        set2(P, o, [Lstx3, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem)
        ;
        set2(2, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0)
    ).

set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0) :- 
    P2 is mod(P, 14),
    P2 =:= 12, !,
    set1(0, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]).

set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx, XSt, Lsto, OSt], 0) :- 
    P2 is mod(P, 14),
    P2 =< 6, !.


set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0) :- 
    P1 is P + 1,
    P2 is mod(P, 14),
    P2 > 6,
    set1(P1, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]).


set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 < 6, !,
    nth0(P2, Lsto, E),
    E1 is E + 1,
    replace(Lsto, P2, E1, Lsto3),
    Elem1 is Elem - 1,
    set2(P1, o, [Lstx, XSt, Lsto3, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem1).

set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 =:= 6, !,
    OSt3 is OSt + 1,
    Elem1 is Elem - 1,
    set2(P1, o, [Lstx, XSt, Lsto, OSt3], [Lstx2, XSt2, Lsto2, OSt2], Elem1).

set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 =:= 13, !,
    set2(P1, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem).

set2(P, o, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1,14),
    P2 > 6, !,
    P3 is P2 - 7,
    nth0(P3, Lstx, E),
    E1 is E + 1,
    replace(Lstx, P3, E1, Lstx3),
    Elem1 is Elem - 1,
    set2(P1, o, [Lstx3, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem1).

    

humanMove([X1, play, Board], [X2, State, NextBoard], Pos) :- 
    nextPlayer(X1, Board, Pos, X2),
    set1(Pos, X1, Board, NextBoard),
    (
        winPos(X1, NextBoard), !, State = win;
        losePos(X1, NextBoard), !, State = lose;
        drawPos(X1, NextBoard), !, State = draw;
        State = play
    ).


set1(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]) :- 
    number(P),
    P < 6, !,
    nth0(P, Lstx, Elem),
    number(Elem),
    (
        Elem \= 0, !,
        replace(Lstx, P, 0, Lstx3),
        set2(P, x, [Lstx3, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem)
        ;
        set2(2, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0)
    ).

set1(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]) :- 
    number(P),
    P1 is mod(P,14),
    P1 > 6, !,
    P2 is P1 - 7,
    nth0(P2, Lsto, Elem),
    number(Elem),
    (
        Elem \= 0, !,
        replace(Lsto, P2, 0, Lsto3),
        set2(P, x, [Lstx, XSt, Lsto3, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem)
        ;
        set2(2, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0)
    ).

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0) :- 
    P2 is mod(P, 14),
    P2 =:= 12, !,
    set1(0, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]).

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx, XSt, Lsto, OSt], 0) :- 
    P2 is mod(P, 14),
    P2 =< 6, !.

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], 0) :- 
    P1 is P + 1,
    P2 is mod(P, 14),
    P2 > 6,
    set1(P1, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2]).


set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 < 6, !,
    nth0(P2, Lstx, E),
    E1 is E + 1,
    replace(Lstx, P2, E1, Lstx3),
    Elem1 is Elem - 1,
    set2(P1, x, [Lstx3, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem1).

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 =:= 6, !,
    XSt3 is XSt + 1,
    Elem1 is Elem - 1,
    set2(P1, x, [Lstx, XSt3, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem1).

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1, 14),
    P2 =:= 13, !,
    set2(P1, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem).

set2(P, x, [Lstx, XSt, Lsto, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem) :-
    P1 is P + 1,
    P2 is mod(P1,14),
    P2 > 6, !,
    P3 is P2 - 7,
    nth0(P3, Lsto, E),
    E1 is E + 1,
    replace(Lsto, P3, E1, Lsto3),
    Elem1 is Elem - 1,
    set2(P1, x, [Lstx, XSt, Lsto3, OSt], [Lstx2, XSt2, Lsto2, OSt2], Elem1).



replace([_|T], 0, X, [X|T]).
replace([H|T], I, X, [H|R]):- I > -1, NI is I-1, replace(T, NI, X, R), !.
replace(L, _, _, L).

show([[X1, X2, X3, X4, X5, X6], XStore, [O1, O2, O3, O4, O5, O6], OStore]) :-
    write('     '), write(O6),
    write('     '), write(O5),
    write('     '), write(O4),
    write('     '), write(O3),
    write('     '), write(O2),
    write('     '), write(O1),
    nl,
    write(OStore), write('                                      '), write(XStore),
    nl,
    write('     '), write(X1),
    write('     '), write(X2),
    write('     '), write(X3),
    write('     '), write(X4),
    write('     '), write(X5),
    write('     '), write(X6),
    nl.

    
    
    
    
    
evaluate([x, S, [[X0,X1,X2,X3,X4,X5], XSt, [O0,O1,O2,O3,O4,O5], OSt]], Opp, BestVal) :- !,
    BestVal is XSt - OSt.
evaluate([o, S, [[X0,X1,X2,X3,X4,X5], XSt, [O0,O1,O2,O3,O4,O5], OSt]], Opp, BestVal) :- 
    BestVal is OSt - XSt.

minimax([Player, S, Board], Opp, 0, BestVal, _) :- !,
    evaluate([Player, S, Board], Opp, BestVal).
    
minimax([Player, S, Board], Opp, Depth, BestVal, BestPos) :-
    Player \= Opp, !,
    BV is -100,
    children([Player, S, Board], [Child0, Child1, Child2, Child3, Child4, Child5]),
    D1 is Depth - 1,
    
    minimax(Child0, Opp, D1, BV0, _),
    max(BV0, BV, BeV0),
    
    minimax(Child1, Opp, D1, BV1, _),
    max(BeV0, BV1, BeV1),
    
    minimax(Child2, Opp, D1, BV2, _),
    max(BeV1, BV2, BeV2),
    
    minimax(Child3, Opp, D1, BV3, _),
    max(BeV2, BV3, BeV3),
    
    minimax(Child4, Opp, D1, BV4, _),
    max(BeV3, BV4, BeV4),
    
    minimax(Child5, Opp, D1, BV5, _),
    max(BeV4, BV5, BeV5),
    
    maxlist([BeV0, BeV1, BeV2, BeV3, BeV4, BeV5], BestVal),
    nth0(BestPos, [BeV0, BeV1, BeV2, BeV3, BeV4, BeV5], BestVal).
    


minimax([Player, S, Board], Player, Depth, BestVal, BestPos) :- !,    % player = opponent
    BV is 200,
    children([Player, S, Board], [Child0, Child1, Child2, Child3, Child4, Child5]),
    D1 is Depth - 1,
    
    minimax(Child0, Opp, D1, BV0, _),
    min(BV0, BV, BeV0),
    
    minimax(Child1, Opp, D1, BV1, _),
    min(BeV0, BV1, BeV1),
    
    minimax(Child2, Opp, D1, BV2, _),
    min(BeV1, BV2, BeV2),
    
    minimax(Child3, Opp, D1, BV3, _),
    min(BeV2, BV3, BeV3),
    
    minimax(Child4, Opp, D1, BV4, _),
    min(BeV3, BV4, BeV4),
    
    minimax(Child5, Opp, D1, BV5, _),
    min(BeV4, BV5, BeV5),
    
    minlist([BeV0, BeV1, BeV2, BeV3, BeV4, BeV5], BestVal),
    nth0(BestPos, [BeV0, BeV1, BeV2, BeV3, BeV4, BeV5], BestVal).


children([Player, S, Board], [Child0, Child1, Child2, Child3, Child4, Child5]) :-
    humanMove([Player, S, Board], Child0, 0),
    humanMove([Player, S, Board], Child1, 1),
    humanMove([Player, S, Board], Child2, 2),
    humanMove([Player, S, Board], Child3, 3),
    humanMove([Player, S, Board], Child4, 4),
    humanMove([Player, S, Board], Child5, 5).

maxlist([X], X).
maxlist([X,Y|Rest], Max) :-
    maxlist([Y| Rest], MaxRest),
    max(X, MaxRest, Max).
max(X, Y, X) :- X >= Y.
max(X, Y, Y) :- X < Y.

minlist([X], X).
minlist([X,Y|Rest], Min) :-
    minlist([Y| Rest], MinRest),
    min(X, MinRest, Min).
min(X, Y, X) :- X =< Y.
min(X, Y, Y) :- X > Y.
