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

#### data
Implementuje usługę realizującą operacje na danych.

#### local-backup
Implementuje usługę realizującą import/eksport do pliku JSON/CSV.

#### remote-backup
Implementuje usługę realizującą import/eksport na Dysk Google

#### app
Interfejs graficzny uruchamiający przypadki użycia i prezentujący ich wynik.
