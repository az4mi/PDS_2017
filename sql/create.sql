/*
Created		18.10.2017
Modified		18.11.2017
Project		
Model		
Company		
Author		
Version		
Database		Oracle 9i 
*/


-- Create Tables section


Create table vozen (
	id_vozna Number(4,0) NOT NULL ,
	kod Number(4,0) NOT NULL ,
	v_prevadzke Char (1) NOT NULL ,
	id_spolocnosti Integer NOT NULL ,
primary key (id_vozna,kod) 
) 
;

Create table typ_vozna (
	rad Varchar2 (10) NOT NULL ,
	kod Number(4,0) NOT NULL ,
	interabilita Number(2,0) NOT NULL ,
	dlzka Float NOT NULL ,
	hmotnost Float NOT NULL ,
	loz_hmotnost Float,
	loz_dlzka Float,
	loz_sirka Float,
	loz_plocha Float,
	loz_vyska Float,
	loz_objem Float,
	poznamka Varchar2 (100),
	obrazok Blob,
primary key (kod) 
) 
;

Create table stanica (
	id_stanice Integer NOT NULL ,
	nazov Varchar2 (50) NOT NULL ,
	gps_sirka Float NOT NULL ,
	gps_dlzka Float NOT NULL ,
primary key (id_stanice) 
) 
;

Create table kolaj (
	cislo Integer NOT NULL ,
	dlzka Integer NOT NULL ,
	id_stanice Integer NOT NULL ,
primary key (cislo,id_stanice) 
) 
;

Create table pohyb_Vozna_Vlak (
	id_zaradenia Integer NOT NULL ,
	id_vozna Number(4,0) NOT NULL ,
	typ_pohybu Char (1) NOT NULL ,
	id_snimaca Integer NOT NULL ,
	kod Number(4,0) NOT NULL ,
	id_vlaku Integer NOT NULL ,
primary key (id_zaradenia) 
) 
;

Create table presun (
	id_presunu Integer NOT NULL ,
	id_vozna Number(4,0) NOT NULL ,
	id_snimaca_z Integer NOT NULL ,
	id_snimaca_na Integer NOT NULL ,
	kod Number(4,0) NOT NULL ,
primary key (id_presunu) 
) 
;

Create table snimac (
	id_snimaca Integer NOT NULL ,
	gps_sirka Float NOT NULL ,
	gps_dlzka Float NOT NULL ,
	cislo Integer NOT NULL ,
	id_stanice Integer NOT NULL ,
primary key (id_snimaca) 
) 
;

Create table pozivatel (
	id_pouzivatela Integer NOT NULL ,
	rod_cislo Char (11) NOT NULL ,
	meno Varchar2 (30) NOT NULL ,
	priezvisko Varchar2 (30) NOT NULL ,
primary key (id_pouzivatela) 
) 
;

Create table pohyb (
	id_pohybu Integer NOT NULL ,
	id_presunu Integer,
	id_zaradenia Integer,
	datum_od Timestamp(6) NOT NULL ,
primary key (id_pohybu) 
) 
;

Create table spolocnost (
	id_spolocnosti Integer NOT NULL ,
	nazov Varchar2 (30) NOT NULL ,
primary key (id_spolocnosti) 
) 
;

Create table zaznam (
	datum Timestamp(6) NOT NULL ,
	tabulka Varchar2 (20) NOT NULL ,
	id_pouzivatela Integer NOT NULL 
) 
;

Create table vlak (
	id_vlaku Integer NOT NULL ,
	start Integer NOT NULL ,
	ciel Integer NOT NULL ,
	typ Number(2,0) NOT NULL ,
primary key (id_vlaku) 
) 
;


-- Create Foreign keys section

Alter table pohyb_Vozna_Vlak add  foreign key (id_vozna,kod) references vozen (id_vozna,kod)  on delete cascade
;

Alter table presun add  foreign key (id_vozna,kod) references vozen (id_vozna,kod)  on delete cascade
;

Alter table vozen add  foreign key (kod) references typ_vozna (kod)  on delete cascade
;

Alter table kolaj add  foreign key (id_stanice) references stanica (id_stanice)  on delete cascade
;

Alter table vlak add  foreign key (start) references stanica (id_stanice) 
;

Alter table vlak add  foreign key (ciel) references stanica (id_stanice) 
;

Alter table snimac add  foreign key (cislo,id_stanice) references kolaj (cislo,id_stanice)  on delete cascade
;

Alter table pohyb add  foreign key (id_zaradenia) references pohyb_Vozna_Vlak (id_zaradenia)  on delete cascade
;

Alter table pohyb add  foreign key (id_presunu) references presun (id_presunu)  on delete cascade
;

Alter table presun add  foreign key (id_snimaca_z) references snimac (id_snimaca)  on delete cascade
;

Alter table presun add  foreign key (id_snimaca_na) references snimac (id_snimaca)  on delete cascade
;

Alter table pohyb_Vozna_Vlak add  foreign key (id_snimaca) references snimac (id_snimaca)  on delete cascade
;

Alter table zaznam add  foreign key (id_pouzivatela) references pozivatel (id_pouzivatela)  on delete cascade
;

Alter table vozen add  foreign key (id_spolocnosti) references spolocnost (id_spolocnosti)  on delete cascade
;

Alter table pohyb_Vozna_Vlak add  foreign key (id_vlaku) references vlak (id_vlaku) 
;


