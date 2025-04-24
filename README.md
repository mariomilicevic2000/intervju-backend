#  Unos terenskog tehničara u bazu (HT) - Backend

Ovaj projekt čini backend dio tehničkog zadatka za intervju za poziciju Web Developera.

## Specifikacija zadatka
- Postoji više grupa, od kojih svaka ima svoga voditelja.
- Potrebno je izraditi web formu za unos tehničara s jasno definiranim poljima
- Pohraniti podatke u bazu podataka.
- Prikazati sve tehničare koji su trenutno pohranjeni u bazi.
- Implementirati cron job koji se izvršava svakih 1 minutu i:

    - provjerava postoje li tehničari u grupi 200,
    - automatski ih prebacuje u grupu 300.

![Dijagram sustava](https://res.cloudinary.com/dta8blqe6/image/upload/v1745489202/sys_diagram_cblq8w.png)


## Tech stack

Na backendu je korišten Spring Boot (Java 17). Izabran je zbog svoje robusnosti i bogatog ekosistema koji omogućuje jednostavno projektiranje i gradnju API servisa s čistom arhitekturom.

Korišteni dependency-ji:

- Spring Web - stvaranje RESTful API-ja
- Spring Data JPA - apstrakcijski sloj, jednostavnije rukovanje i interakcija s bazom podataka koristeći Hibernate ORM, omogućuje rukovanje podacima na objektno orijentirani način umjesto "golog" SQL-a, povećan type safety
- PostgreSQL baza podataka - JDBC driver za spajanjebackenda s PostgreSQL, robusna produkcijska baza podataka, deployana na Neon.tech servis preko Vercela
- Spring Validation - validacija podataka primljenih sa frontenda

Dodatni alati
- H2 Database - in-memory baza podataka koja ima minimalni setup, idealna za brz development, lakša za korištenje od većih baza podataka
- Postman - vanjska aplikacija, pogodna za slanje zahtjeva na API endpointe i testiranje
- Spring Boot DevTools - za funkcionalnost hot reloada i live refresha, smanjuje čekanje i eliminira potrebu za restartiranjem aplikacije kada se kod promijeni

![Dijagram backenda](https://res.cloudinary.com/dta8blqe6/image/upload/v1745489288/back_diagram_gmptpd.png)

## Arhitektura podataka

Podaci su definirani kao dio Model sloja: sadrži definiciju podataka, njihova ograničenja i metode za njihovu manipulaciju.

Definirani su sljedeći podaci:

- UUID tehničara - njegov primarni ključ i identifikacijska oznaka
- KP broj - broj između 3 i 10 znamenaka, spremljen kao string, ljudski čitljiv identifikacijski broj
- Ime - string između 2 i 50 znakova
- Prezime - string između 2 i 50 znakova
- MFG grupa - strani ključ koji referencira grupu iz vanjske tablice
- Kontakt telefon - string, validira se regularnim izrazom koji prihvaća sve moguće oblike hrvatskog mobilnog broja
- Kontakt email - string, validiran ugrađenim validatorom u obliku anotacije @Email
- Ime ulice - string, validiran regularnim izrazom
- Kućni broj - string, validiran regularnim izrazom koji prihvaća broj (1-5 znamenaka), broj s jednim slovom (npr. 12a) ili bez broja (bb)
- Poštanski broj - string, peteroznamenkasti broj gdje je prva znamenka 1-5 (ispravni hrvatski poštanski brojevi), validiran regularnim izrazom
- Mjesto - string, validiran regularnim izrazom

## Arhitektura backenda

Projekt prati standardne konvencije imenovanja (reverzna domena - com.ht.interview) i građen je po MVC (Model View Controller) arhitekturi i mikroservisima.

Arhitektura se sastoji od sljedećih slojeva:

- Controller sloj (Web sloj) - sloj koji služi kao interakcija sa vanjskim svijetom, definira API rute i tipove zahtjeva koje te rute prihvaćaju, radi validaciju zahtjeva i podatke iz zahtjeva preusmjerava odgovarajućim servisima, te pri obradi zahtjeva pošiljatelju vraća pripadajući odgovor sa statusnim HTTP kodom koji označava ishod obrade zahtjeva
- Service sloj (Business logika) - enkapsulira business logiku, interakcija sa repozitorijima (bazom podataka), implementirana kao Java klase s anotacijom @Service
- Model sloj (Entiteti) - definira strukture podataka i njihove validacijske opise koji se mapiraju u bazu podataka kao stupci tablice, klasa je definirana pomoću anotacije @Entity, definira primarne i strane ključeve kao i veze između različitih podataka između tablica
- Repository sloj (Data sloj) - interfacei građeni na JpaRepository, omogućuju apstrahiranu interakciju sa bazom podataka, spremaju i dohvaćaju podatke, metode za baratenje podacima definirane su deklarativno

## Kretanje podataka spremanja tehničara

1. Klijentski zahtjev - korisnik (frontend) šalje HTTP zahtjev po specifikaciji u Controlleru npr. zahtjev za spremanjem novog tehničara: POST /api/admin/technicians sa podacima u obliku JSON-a u body-u zahtjeva
2. Controller prima zahtjev - validira ga koristeći podatke o tehničaru definirane u njegovom modelu, validira ih na temelju ograničenja u modelu, te ih šalje Service sloju na daljnju obradu
3. Service sloj prima podatke - obrađuje ih, transformira po potrebi i poziva Repository sloj za spremanje podataka
4. Repository sloj prima podatke - translatira poziv metode u SQL zahtjev koristeći JPA/Hibernate, šalje upit na bazu podataka
5. PostgreSQL baza podataka prima upit - izvršava se upit i podaci se spremaju u bazu

## Kretanje podataka ispisa liste tehničara

1. Klijentski zahtjev - frontend šalje HTTP zahtjev po specifikaciji Controllera, npr. zahtjev za paginated listu svih tehničara: GET /api/technicians?page=${page}&size=5, koja u prijevodu znači da frontend traži trenutnu stranicu paginacije liste tehničara (dinamički određena od strane frontenda), gdje je veličina stranice 5 stavki
2. Controller prima zahtjev - zathjev je primljen s API endpointa definiranog ovdje, podatke šalje Service sloju koji izvršava business logiku, nakon obrade zahtjeva frontendu šalje podatke i odgovarajući statusni HTTP kod
3. Service sloj prima podatke - apstrakcijom Pageable podaci se pakiraju u odgovarajući odgovor i na temelju parametara odabiru se podaci koji se šalju
4. Repository sloj prima podatke - na temelju pageable parametara slaže upit za bazu podataka
5. PostgreSQL baza podataka - izvršava upit i vraća odgovarajuće podatke backendu
6. Podaci u potrebnom formatu kreću "unazad" po ovim koracima i šalju se frontendu na obradu i ispis

## Kretanje podataka provjere KP broja
1. Klijentski zahtjev - frontend šalje HTTP zathjev po specifikaciji Controllera, zahtjev za provjeru postoji li navedeni KP broj u bazi, ondosno uniqueness check tehničara: GET /api/admin/technicians/check-kp/${kpNumber}, gdje je argument KP broj koji je korisnik trenutno unio.
2. Controller prima zahtjev - prosljeđuje argument (KP broj) Service sloju, na povratku vraća boolean vrijednost u odgovoru, skupa sa status kodom
3. Service sloj prima podatak - prosljeđuje argument Repository sloju
4. Repository sloj prima podatak - deklarativnu funkciju existsByKpNumber(kpNumber) prevodi u SQL upit koji prosljeđuje bazi
5. PostgreSQL baza - izvršava upit i vraća odgovarajući boolean podatak servisu
6. Podatak u potrebnom formatu kreću "unazad" po ovim koracima i šalju se frontendu na obradu i ispis

## Kretanje podataka grupa i voditelja

Budući da su grupe i njihovi voditelji dinamičan podatak koji podilazi vanjskim promjenama i nije dio funkcionalnosti forme za upis tehničara, potrebno ih je dohvatiti iz druge tablice koja sadržava te podatke. S obzirom na to, da bi se uopće korisniku na frontendu mogla pokazati forma za upis novog tehničara, potrebno je dohvatiti te podatke kako bi se dropdown za odabir grupe mogao uopće populirati podacima.

1. Klijentski zahtjev - frontend šalje HTTP zahtjev po specifikaciji Controllera, GET /api/admin/groupmanagers
2. Controller prima zahtjev - podaci se šalju Service sloju na obradu
3. Service sloj prima podatke - podaci se šalju Repository sloju na spremanje
4. Repository sloj prima podatke - šalje upit bazi podataka
5. Baza podataka - izvršava upit i vraća listu grupa i pripadajućih voditelja
6. Podaci u potrebnom formatu kreću "unazad" i šalju se frontendu na ispis

## Kretanje podataka cron job-a

Zadano je da se realizira cron job koji svako minutu premješta tehničare iz grupe 200 u grupu 300 ako postoje.
Ovaj zadatak se izvršava periodično i stoga nema svoj API endpoint. Sukladno tome, nema svoju Controller implementaciju.

1. Service sloj - funkcija s anotacijom @Scheduled(cron = "0 */1 * * * *") (zadatak se izvršava svake minute, cron notacija), šalje repozitoriju upit za tehničarima iz grupe 200 pomoću deklarativne metode definirane u repozitoriju
2. Repozitorij prima poziv metode, pretvara je u SQL upit i dohvaća iz baze retke tehničara koji zadovoljavaju uvjet
3. Service sloj iterira kroz listu dohvaćenih tehničara i svakome mijenja ID grupe iz 200 u 300, nakon čega sprema podatke nazad u repozitorij, koji ih sprema u bazu
4. Podaci o tome što se premješta i broju premještenih tehničara se prilikom izvođenja logira u konzolu backenda.

## Moguće nadogradnje

- Implementirati sustav autentifikacije i autorizacije koji omogućuje kontrolu pristupa baziranog na ulogama, u smislu da primjerice admin može mijenijati dodavati tehničara u svaku grupu, dok voditelj na temelju svog pristupnog tokena može dodavati tehničare samo u svoju grupu