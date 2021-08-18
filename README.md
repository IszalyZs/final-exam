# T-Rex Kutyaotthon Alapítvány / Dog Shelter Foundation 

## The program uses mysql so you have to have two databases trex and trextest. If you don’t have mysql you just run with docker. Start: docker-compose up, stop: docker-compose down (The start is a slow process about 7 minutes, depend on hardware and net, because it also runs the all tests)
## You can connect to the database with the following settings:  port:3333, user: root, password: root, database:trex.
## The program uses the 8888 port. You can use http://localhost:8888/swagger-ui.html and http://localhost:8888/api-docs
## You can find three tables: dog, breed and the ranch. You can use CRUD operations for these tables.




## A program mysql-t használ, docker nélkül feltétel a mysql a gépen és két darab adatbázis trex és trextest létrehozása. 
## Az indítás: docker-compose up , leállítás: docker-compose down , kb. 7 perc a teljes program elindulása, mert előtte lefuttatja az összes tesztet is.
## A program használata röviden:
## A mysql adatbázis kívülről a 3333 porton érhető el, user: root , password: root, adatbázis: trex
## A http://localhost:8888/swagger-ui.html oldalon tesztelhető a program, vannak kezdő adatok is, 8888-ra van a port állítva. Az api dokumentáció itt érhető el: http://localhost:8888/api-docs
## Három adattábla módosítható, lekérhető és tesztelhető: a dog, a ranch és a breed.
## A ranch tartalmazza azokat a telephelyeket, ahol a kutyák éppen tartózkodnak. A címnek egyedinek kell lenni.
## A breed a kutyafajtákat tartalmazza, a fajtának egyedinek kell lenni.
## A dog felvitele esetén feltétel a breed és a ranch megléte, id alapján lehet ezeket felvinni, ha nem létező ranch vagy breed id lett megadva, akkor az elsőnél hibát dob. Chip kód 15 számjegy (magyar szabvány) szintén egyedinek kell lenni, az állat korát hónapban kell megadni, a neme nagybetűvel két fajta lehet (FEMALE, MALE). 


