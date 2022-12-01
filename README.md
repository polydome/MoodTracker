# MoodTracker

Elektroniczny dziennik nastroju

### Przypadki użycia
1. Zapisywanie obecnego nastroju w dzienniku
2. Przeglądanie dziennika
3. Importowanie/eksportowanie dziennika
	- Do pliku JSON/CSV
	- Na Dysk Google

## Moduły

#### domain
Wystawia interfejs pozwalający na realizację przypadków użycia.
Wykonał Jakub Dubrowski

#### data
Implementuje usługę realizującą operacje na danych.
Wykonał Rafał Cisek

#### local-backup
Implementuje usługę realizującą import/eksport do pliku JSON/CSV.
Wykonał Dawid Drzewiecki

#### remote-backup
Implementuje usługę realizującą import/eksport na Dysk Google
Wykonała Gabriela Błaszczak

#### app
Interfejs graficzny uruchamiający przypadki użycia i prezentujący ich wynik.
Wykonał Łukasz Burzak
