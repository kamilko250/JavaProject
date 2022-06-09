Autorzy:
Kamil Kośko
Cezary Hodun
Łukasz Milewski

Nazwa Projektu: 
Store Everything - to store important data

Funkcjonalności:
-dodawanie/usuwanie/edycja przez siebie wybranych informacji, walidacja formularzy
-dodawanie nowych kategorii
-wyswietlanie udostepnionych przez innych informacji (dla wszystkich, dla konkretnego użytkownika)
-wyswietlanie swoich informacji, sortowanie w obu kierunkach po dacie, kategorii lub alfabetycznie
-zapamietanie kierunkow i kryteriow sortowania
-filtrowanie wedlug daty, kategorii
-rejestracja uzytkownika, logowanie, wylogowywanie
-wyswietlanie listy uzytkownikow, zarzadzanie ich rolami, uwierzytelnianie, autoryzacja
-logger
-unit testy - kilka podstawowych testów obiektów ORM

Instrukcja zbudowania:
1. Application.properties dodać/zmienić connection string do lokalnej bazy, dane do logowania.
2. maven build.
-serwer db - postgres 14.2.2
-java 18
