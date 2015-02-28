-- http://stackoverflow.com/questions/4953099/how-to-fix-error-when-inserting-datetime-into-db
-- yyyy-mm-dd hh:mm:ss[.nnnnnn]
delete from KONTO_TRANSAKTION;
delete from SPELBOLAG_SPELARE;
delete from TRANSAKTION;
delete from SPELARE;
delete from SPELBOLAG;
delete from KONTO;
update SEQUENCE set SEQ_COUNT=200 where SEQ_NAME ='SEQ_GEN_TABLE';




-- Lägg in spelbolag
insert into KONTO (id, kontonr) values (1, 1920);
insert into SPELBOLAG(id, insatsperomgang, namn, konto_id) values (1,50,'Roberts tipsgäng - The gamblers', 1);
-- omgang #1
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (1, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,1);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (2, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,2);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (3, 'Insättning av spelare brevtilllasse@gmail.com', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,3);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (4, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,4);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (5, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,5);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (6, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,6);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (7, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,7);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (8, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,8);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (9, 'Betala Måltips till Svenskaspel.', 0, 378, '2012-09-29 12:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,9);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (10, 'Betala Lången till Svenskaspel.', 0, 20, '2012-09-29 12:01:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,10);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (11, 'Vinst på Måltipset ifrån Svenskaspel.', 40, 0, '2012-09-29 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,11);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (12, 'Betala Lången till Svenskaspel.', 0, 40, '2012-10-03 12:01:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,12);

-- omgang #2
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (13, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,13);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (14, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,14);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (15, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,15);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (16, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,16);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (17, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,17);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (18, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,18);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (19, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,19);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (20, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,20);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (21, 'Betala Måltipset till Svenskaspel.', 0, 405, '2012-10-06 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,21);

-- omgang 3
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (24, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,24);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (25, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-10-13 13:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,25);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (26, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,26);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (27, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,27);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (28, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,28);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (29, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,29);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (30, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,30);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (31, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,31);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (32, 'Betala Måltipset till Svenskaspel.', 0, 405, '2012-10-13 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,32);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (33, 'Vinst på Måltipset ifrån Svenskaspel.', 316, 0, '2012-10-13 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,33);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (34, 'Betala Bomben till Svenskaspel.', 0, 144, '2012-10-16 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,34);

-- Omgang 4
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (36, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,36);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (37, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,37);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (38, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,38);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (39, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,39);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (40, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,40);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (41, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,41);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (42, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,42);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (43, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,43);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (44, 'Betala Måltipset till Svenskaspel.', 0, 493, '2012-10-20 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,44);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (45, 'Betala Lången till Svenskaspel.', 0, 80, '2012-10-20 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,45);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (46, 'Vinst på Måltipset ifrån Svenskaspel.', 10, 0, '2012-10-20 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,46);

-- Omgang 5
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (87, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,87);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (88, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,88);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (89, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,89);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (90, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,90);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (91, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,91);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (92, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,92);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (93, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,93);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (94, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,94);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (95, 'Betala Måltipset till Svenskaspel.', 0, 390, '2012-10-27 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,95);

-- Omgang 6
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (106, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,106);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (107, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,107);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (108, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,108);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (109, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,109);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (110, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,110);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (111, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,111);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (112, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,112);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (113, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,113);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (114, 'Betala Måltipset till Svenskaspel.', 0, 360, '2012-11-03 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,114);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (115, 'Betala Lången till Svenskaspel.', 0, 50, '2012-11-03 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,115);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (124, 'Vinst på Måltipset ifrån Svenskaspel.', 290, 0, '2012-11-03 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,124);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (125, 'Betala Lången till Svenskaspel.', 0, 40, '2012-11-08 20:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,125);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (126, 'Betala Bomben till Svenskaspel.', 0, 144, '2012-11-08 20:30:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,126);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (127, 'Betala Lången till Svenskaspel.', 0, 100, '2012-11-08 21:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,127);


-- Omgang 7
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (128, 'Insättning av spelare robert.georen@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,128);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (129, 'Insättning av spelare per.georen@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,129);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (130, 'Insättning av spelare brevtilllasse@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,130);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (131, 'Insättning av spelare johnnywesterberg@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,131);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (132, 'Insättning av spelare hempa98@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,132);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (133, 'Insättning av spelare mikaelxfahlander@gmail.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,133);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (134, 'Insättning av spelare tomas.lithner@mawell.com.', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,134);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (135, 'Insättning av spelare 24.an.rosander@telia.com', 50, 0, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,135);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (136, 'Betala Måltipset till Svenskaspel.', 0, 405, '2012-11-10 16:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,136);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (145, 'Vinst på Måltipset ifrån Svenskaspel.', 255, 0, '2012-11-10 18:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,145);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (149, 'Betala Matchen (Sverige-England), 5 st a 50 kr.', 0, 250, '2012-11-13 19:50:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (1,149);


-- Spelare: AnvandarId = Robert.georen@gmail.com
insert into KONTO (id, kontonr) values (2, 239203);
insert into SPELARE(id, userid, konto_id, administratorforspelbolag_id) values (1,'robert.georen@gmail.com',2, 1);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (22, 'Insättning.', 500, 0, '2012-09-28 14:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,22);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (23, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,23);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (35, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,35);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (47, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,47);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (48, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,48);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (96, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,96);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (116, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,116);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (137, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (2,137);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,1);

-- Spelare: AnvandarId = brevtilllasse@gmail.com
insert into KONTO (id, kontonr) values (3, 923);
insert into SPELARE(id, userid, konto_id) values (2,'brevtilllasse@gmail.com',3);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (54, 'Startvärde (fick 500 kr 2012-06-13 vilket get detta ingående saldo).', 100, 0, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,54);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (49, 'Insättning.', 500, 0, '2012-09-27 14:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,49);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (50, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,50);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (51, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,51);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (52, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,52);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (53, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,53);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (97, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,97);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (117, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,117);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (138, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (3,138);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,2);

-- Spelare: AnvandarId = per.georen@gmail.com
insert into KONTO (id, kontonr) values (4, 5453);
insert into SPELARE(id, userid, konto_id) values (3,'per.georen@gmail.com',4);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (55, 'Låg back sedan tidigare.', 0, 1250, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,55);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (56, 'Insättning.', 1000, 0, '2012-09-29 14:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,56);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (57, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,57);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (58, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,58);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (59, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,59);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (60, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,60);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (98, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,98);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (118, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,118);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (139, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (4,139);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,3);


-- Spelare: AnvandarId = johnnywesterberg@gmail.com
insert into KONTO (id, kontonr) values (5, 1241);
insert into SPELARE(id, userid, konto_id) values (4,'johnnywesterberg@gmail.com',5);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (61, 'Ingående saldo (fick 500 kr 2012-09-08 men då var det -3 gånger i skuld vilket nu ger detta ingående saldo).', 300, 0, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,61);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (62, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,62);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (63, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,63);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (64, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,64);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (65, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,65);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (146, 'Inbetalt via Internet med texten=Överföring', 1000, 0, '2012-10-25 17:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,146);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (99, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,99);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (119, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,119);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (140, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (5,140);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,4);

-- Spelare: AnvandarId = Falander
insert into KONTO (id, kontonr) values (6, 3221);
insert into SPELARE(id, userid, konto_id) values (5,'mikaelxfahlander@gmail.com',6);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (66, 'Ingående saldo (fick 500 kr 2012-08-21 vilket ger detta ingående saldo).', 150, 0, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,66);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (67, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,67);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (68, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,68);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (69, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,69);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (70, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,70);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (100, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,100);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (120, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,120);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (141, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,141);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (148, 'Inbetalt via Internet med texten=Micketips.', 700, 0, '2012-11-12 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (6,148);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,5);

-- Spelare: AnvandarId = Thomas lithner
insert into KONTO (id, kontonr) values (7, 871);
insert into SPELARE(id, userid, konto_id) values (6,'tomas.lithner@mawell.com',7);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (71, 'Ingående saldo (fick 500 kr 2012-09-17 vilket ger detta ingående saldo).', 150, 0, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,71);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (72, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,72);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (73, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,73);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (74, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,74);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (75, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,75);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (101, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,101);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (121, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,121);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (142, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,142);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (147, 'Inbetalt via Internet med texten=Lithner.', 500, 0, '2012-11-12 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (7,147);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,6);

-- Spelare: AnvandarId = Henrik lind
insert into KONTO (id, kontonr) values (8, 93203);
insert into SPELARE(id, userid, konto_id) values (7,'hempa98@gmail.com',8);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (76, 'Ingående saldo (fick 1 000kr 2012-04-17 vilket det är lite kvar av).', 200, 0, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,76);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (77, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,77);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (78, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,78);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (79, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,79);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (104, 'Inbetalt 500 kr (lunch på Sjökrogen).', 500, 0, '2012-10-17 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,104);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (80, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,80);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (102, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,102);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (122, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,122);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (143, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (8,143);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,7);

-- Spelare: AnvandarId = Jan Åke.
insert into KONTO (id, kontonr) values (9, 874);
insert into SPELARE(id, userid, konto_id) values (8,'24.an.rosander@telia.com',9);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (81, 'Ingående saldo (Fick senast 500 kr 2012-06-24 vilket ger detta ingående saldo).', 0, 100, '2012-09-27 09:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,81);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (82, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-09-29 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,82);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (83, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-06 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,83);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (84, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-13 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,84);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (85, 'Insättning.', 2000, 0, '2012-10-14 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,85);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (86, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-20 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,86);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (103, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-10-27 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,103);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (123, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-03 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,123);
insert into TRANSAKTION(id, beskrivning, debit, kredit, transaktionstid) values (144, 'Betala till spelbolaget Roberts tipsgäng - The gamblers.', 0, 50, '2012-11-10 11:00:00');
insert into KONTO_TRANSAKTION (KONTO_ID, TRANSAKTIONER_ID) values (9,144);
insert into SPELBOLAG_SPELARE (SPELBOLAG_ID, ALLABOLAGETSSPELARE_ID) values (1,8);

