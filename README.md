# T-Rex Kutyaotthon Alapítvány 

## A program mysql-t használ, docker nélkül feltétel a mysql a gépen és két darab adatbázis trex és trextest létrehozása. Ezután indíthatóvá válnak az integrációs tesztek is. 
## Docker használata esetén az integrációs tesztek nem lesznek elérhetők. Az indítás: docker-compose up , leállítás: docker compose-down , kb. 5 perc a teljes program elindulása.

## A program bármilyen változtatása után a kötelező parancs: mvn package -DskipTests

## A program használata röviden:
## A mysql adatbázis kívülről a 3333 porton érhető el, user: root , password: root
## A http://localhost:8888/swagger-ui.html oldalon tesztelhető a program, vannak kezdő adatok is. 8888-ra van a port állítva az ütközés elkerülése végett. Az api dokumentáció itt érhető el: http://localhost:8888/api-docs
## Három adattábla módosítható, lekérhető és tesztelhető: a dog, a ranch és a breed.
## A ranch tartalmazza azokat a telephelyeket, ahol a kutyák éppen tartózkodnak. A címnek egyedinek kell lenni.
## A breed a kutyafajtákat tartalmazza, a fajtának egyedinek kell lenni.
## A dog felvitele esetén feltétel a breed és a ranch megléte, id alapján lehet ezeket felvinni, ha nem létező ranch vagy breed id lett megadva, akkor az elsőnél hibát dob. Chip kód 15 számjegy (magyar szabvány) szintén egyedinek kell lenni, az állat korát hónapban kell megadni, a neme nagybetűvel két fajta lehet (FEMALE, MALE). 


