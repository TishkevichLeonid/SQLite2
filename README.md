# SQLite2
Работа с базой данных SQLite, разбор метода query, сортировка, группировка данных, выборка по условию
### **Параметры query:**
    columns - список полей которые нужно получить
    selection - строка условия WHERE
    selectionArgs - массив аргументов для selection (можно использовать ? который будет заменен значениями)
    groupBy - группировка
    having - использование условий для агрегатных функций
    orderBy - сортировка

#### При загрузке приложения выводятся все записи

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/1.png)

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/1.1.png)

#### Использование агрегатной функции, показывает количество записей

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/2.png)

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/2.1.png)
  
#### Выберем страны с населением больше 100 млн

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/3.png)

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/3.1.png)

#### Население регионов

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/4.png)

#### Отобразим регионы где население больше 500 млн

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/5.png)

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/5.1.png)

#### Сортировка по возрастанию населения 

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/6.png)

![Image alt](https://github.com/TishkevichLeonid/SQLite2/raw/master/screenshots/6.1.png)
