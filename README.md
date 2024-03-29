
# EventFinder

## Projektni tim
(svi članovi tima moraju biti iz iste seminarske grupe)

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Matej Desanić | mdesanic21@foi.hr | 0016155191 | mdesanic21 | G01
Darijo Bračić | dbracic21@foi.hr | 0016156370 | dbracic21-foi | G01
Ivan Juras | ijuras21@foi.hr | 0016156344 | ijuras21 | G01

## Opis domene
Primijetili smo da je trenutno vrlo teško pronaći informacije o događanjima, poput koncerata, radionica, festivala i sl. na jednom mjestu, te je naša ideja realizirati mobilnu aplikaciju koja će to omogućiti. Ideja je da se na jednom mjestu nalaze sve te stvari i da korisnik može selektirati lokaciju, poput grada, županije ili države, ili da može označiti na karti neki prostor za koji traži događanja, npr. označi čitavu obalu RH kako bi pronašao festivale na obali. Implementirali bi i mogućnost određivanja lokacije preko GPS-a mobitela, kako bi korisnik mogao odabrati radijus oko sebe za događaje. Želimo se ponašati kao "posrednici" između organizatora događanja i korisnika koji traži događanja, što znači da bismo prikazivali osnovne informacije o događaju, poput lokacije, vremena, cijene (ako postoji) itd., i prikazivali bi link na stranicu događanja ako nekog interesira nešto više. 

<!-- Umjesto ovih uputa opišite domenu ili problem koji pokrivate vašim projektom. Domena može biti proizvoljna, ali obratite pozornost da sukladno ishodima učenja, domena omogući primjenu zahtijevanih koncepata kako je to navedeno u sljedećem poglavlju. Priložite odgovarajuće skice gdje je to prikladno.-->

## Specifikacija projekta
<!--Umjesto ovih uputa opišite zahtjeve za funkcionalnošću mobilne aplikacije ili aplikacije za pametne uređaje. Pobrojite osnovne funkcionalnosti i za svaku naznačite ime odgovornog člana tima. Opišite osnovnu buduću arhitekturu programskog proizvoda. Obratite pozornost da mobilne aplikacije često zahtijevaju pozadinske servise. Također uzmite u obzir da bi svaki član tima trebao biti odgovoran za otprilike 3 funkcionalnosti, te da bi opterećenje članova tima trebalo biti ujednačeno. Priložite odgovarajuće dijagrame i skice gdje je to prikladno. Funkcionalnosti sustava bobrojite u tablici ispod koristeći predložak koji slijedi:-->

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Login\Registracija | Običan korisnik će moću pretraživati aplikaciju bez korisničkog računa. Ako želi personalizirati doživljaj, korisnik će se morati registrirati, a to će raditi unosom email-a, lozinke i korisničkog imena. Login se vrši unosom korisničkog imena ili emaila, te lozinke. | Darijo Bračić
F02 | Pretraživanje po lokaciji  | Korisnik će imati mogućnost odabira lokacije događaja preko karte ili upisom u tražilicu, gdje će onda prema odabiru korisnika biti prikazani svi događaji na odabranoj lokaciji. Zamislili smo i označavanje većeg područja pomoću alata za označavanje.  | Matej Desanić
F03 | Pretraživanje po izvođaču | Korisnik će imati mogućnost odabira izvođača upisom u tražilicu, gdje će onda prema odabiru korisnika biti prikazani svi događaji odabranog izvođača neovisno o lokaciji | Ivan Juras
F04 | Pregled događaja | Korisnik će imati mogućnost odabira vrste događaja, koji može biti zabavni (npr. festivali, večer u klubu), edukativni (npr. paneli, radionice), volonterski (npr. krvi). Filteri će biti ta tri naziva, a unutar njih se nalaze događaji koji sadržajem odgovaraju kategoriji. | Ivan Juras
F05 | Redirekcija plaćanja na stranice organizatora događaja | Korisnik će moći "kupiti" karte preko aplikacije, odnosno kada korisnik stisne za kupiti određenu kartu naša aplikacija će ga preusmjeriti na stranicu organizatora događaja gdje će korisnik moći kupiti kartu | Darijo Bračić
F06 | Push notifikacija | Za registrirane korisnike će biti moguće odabrati opciju primanja push notifikacija vezanih za korisnikove interese. | Ivan Juras
F07 | Personalizacija profila korisnika | Korisnik kada se prijavi u aplikaciju imat će opciju odabira za što se koristi aplikacija (muzika, edukacije, volontiranje, sport, kombinacija više njih). Onda se za svaku kategoriju otvara upitnik, za muziku vrsta muzike, prostor događanje, trajanje događanja (jedan dan ili par dana (festival)); za edukaciju područje edukacije, trajanje edukacije, lokacija (remote ili uživo); za sport vrsta sporta, trajanje; i za volontiranje neće postojati dodatan upitnik. Na kraju se u upitnik upisuje preferira li korisnik besplatne događaje ili događaje koji se plaćaju. Za svaku opciju će postojati mogućnost "preskakanja" odgovora. | Matej Desanić
F08 | Mogućnost favoriziranja događanja |  Korisnik će moći odabrati određeni događaj i staviti ih u svoje favorite, te će onda kasnije na svom profilu moći pronaći ta događanja. Koristilo bi se kako bi korisniku iskustvo bilo bolje, jer je lakše pregledati događanja za koja je korisnik zainteresiran na profilu, nego svaki put trebati pretraživati posebno. | Matej Desanić 
F09 | Recenziranje lokacije koja ima tu mogućnost na Google Review | Preko Google Review API-ja smo planirali omogućiti recenzrianje događanja i lokacija koje imaju tu mogućnost na Google Review-u.  |  Darijo Bračić

## Tehnologije i oprema
Izraditi ćemo aplikaciju u Kotlinu. Planiramo koristiti web scraping za dohvaćanje događanja te Google-ove API-ove za karte.

<!--Umjesto ovih uputa jasno popišite sve tehnologije, alate i opremu koju ćete koristiti pri implementaciji vašeg rješenja. Vaše rješenje može biti implementirano u bilo kojoj tehnologiji za razvoj mobilnih aplikacija ili aplikacija za pametne uređaje osim u hibridnim web tehnologijama kao što su React Native ili HTML+CSS+JS. Tehnologije koje ćete koristiti bi trebale biti javno dostupne, a ako ih ne budemo obrađivali na vježbama u vašoj dokumentaciji ćete morati navesti način preuzimanja, instaliranja i korištenja onih tehnologija koje su neopbodne kako bi se vaš programski proizvod preveo i pokrenuo. Pazite da svi alati koje ćete koristiti moraju imati odgovarajuću licencu. Što se tiče zahtjeva nastavnika, obvezno je koristiti git i GitHub za verzioniranje programskog koda, GitHub Wiki za pisanje jednostavne dokumentacije sukladno uputama mentora, a projektne zadatke je potrebno planirati i pratiti u alatu GitHub projects.-->

## Baza podataka i web server
Trebamo bazu podataka i pristup serveru za PHP skripte.

## .gitignore
Uzmite u obzir da je u mapi Software .gitignore konfiguriran za nekoliko tehnologija, ali samo ako će projekti biti smješteni direktno u mapu Software ali ne i u neku pod mapu. Nakon odabira konačne tehnologije i projekta obavezno dopunite/premjestite gitignore kako bi vaš projekt zadovoljavao kriterije koji su opisani u ReadMe.md dokumentu dostupnom u mapi Software.
