-- Создание базы данных
CREATE DATABASE IF NOT EXISTS HumanFriends;
USE HumanFriends;

-- 1. Создание таблиц согласно диаграмме классов

-- Основная таблица животных
CREATE TABLE Animals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    animal_type ENUM('Pet', 'PackAnimal') NOT NULL,
    name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица домашних животных (Pets)
CREATE TABLE Pets (
    animal_id INT PRIMARY KEY,
    pet_type ENUM('Dog', 'Cat', 'Hamster') NOT NULL,
    FOREIGN KEY (animal_id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- Таблица вьючных животных (PackAnimals)
CREATE TABLE PackAnimals (
    animal_id INT PRIMARY KEY,
    pack_animal_type ENUM('Horse', 'Camel', 'Donkey') NOT NULL,
    load_capacity INT, -- грузоподъемность в кг
    FOREIGN KEY (animal_id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- Таблица команд животных
CREATE TABLE AnimalCommands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    animal_id INT NOT NULL,
    command VARCHAR(50) NOT NULL,
    FOREIGN KEY (animal_id) REFERENCES Animals(id) ON DELETE CASCADE
);

-- 2. Заполнение таблиц данными

-- Добавляем домашних животных
INSERT INTO Animals (animal_type, name, birth_date) VALUES
('Pet', 'Бобик', '2020-05-15'),
('Pet', 'Мурка', '2021-02-20'),
('Pet', 'Хома', '2022-10-10'),
('Pet', 'Шарик', '2019-11-30'),
('Pet', 'Васька', '2020-08-25'),
('Pet', 'Пушистик', '2022-03-12');

INSERT INTO Pets (animal_id, pet_type) VALUES
(1, 'Dog'), (2, 'Cat'), (3, 'Hamster'),
(4, 'Dog'), (5, 'Cat'), (6, 'Hamster');

-- Добавляем вьючных животных
INSERT INTO Animals (animal_type, name, birth_date) VALUES
('PackAnimal', 'Буян', '2018-04-05'),
('PackAnimal', 'Саврас', '2019-07-15'),
('PackAnimal', 'Горбун', '2017-12-10'),
('PackAnimal', 'Иа', '2020-09-01'),
('PackAnimal', 'Спирит', '2021-06-20'),
('PackAnimal', 'Тузик', '2019-03-18');

INSERT INTO PackAnimals (animal_id, pack_animal_type, load_capacity) VALUES
(7, 'Horse', 120), (8, 'Horse', 100), (9, 'Camel', 200),
(10, 'Donkey', 80), (11, 'Horse', 110), (12, 'Donkey', 70);

-- Добавляем команды животным
INSERT INTO AnimalCommands (animal_id, command) VALUES
(1, 'Сидеть'), (1, 'Лежать'), (1, 'Голос'),
(2, 'Кис-кис'), (2, 'Есть'), 
(3, 'Бегать'), (3, 'Спать'),
(4, 'Фас'), (4, 'Место'),
(5, 'Ловить мышей'), 
(7, 'Галоп'), (7, 'Шагом'),
(8, 'Прыжок'), (9, 'Идти'), (9, 'Стоять'),
(10, 'Возить'), (11, 'Рысь'), (12, 'Нести');

-- 3. Удаление верблюдов и объединение лошадей и ослов

-- Удаляем верблюдов
DELETE FROM Animals WHERE id IN (
    SELECT animal_id FROM PackAnimals WHERE pack_animal_type = 'Camel'
);

-- Создаем объединенную таблицу лошадей и ослов
CREATE TABLE HorsesAndDonkeys AS
SELECT a.id, a.name, a.birth_date, 
       pa.pack_animal_type AS type,
       pa.load_capacity
FROM Animals a
JOIN PackAnimals pa ON a.id = pa.animal_id
WHERE pa.pack_animal_type IN ('Horse', 'Donkey');

-- 4. Таблица животных от 1 до 3 лет с точным возрастом

CREATE TABLE YoungAnimals AS
SELECT 
    id,
    name,
    birth_date,
    animal_type,
    TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) AS age_months,
    CONCAT(
        FLOOR(TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) / 12), ' года ',
        TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) % 12, ' месяцев'
    ) AS age_formatted
FROM Animals
WHERE TIMESTAMPDIFF(MONTH, birth_date, CURDATE()) BETWEEN 12 AND 36;

-- 5. Объединенная таблица всех животных с информацией о типе

CREATE TABLE AllAnimals AS
SELECT 
    a.id,
    a.name,
    a.birth_date,
    'Pet' AS category,
    p.pet_type AS type,
    NULL AS load_capacity
FROM Animals a
JOIN Pets p ON a.id = p.animal_id

UNION ALL

SELECT 
    a.id,
    a.name,
    a.birth_date,
    'PackAnimal' AS category,
    pa.pack_animal_type AS type,
    pa.load_capacity
FROM Animals a
JOIN PackAnimals pa ON a.id = pa.animal_id;

-- Проверочные запросы
SELECT * FROM Pets;
SELECT * FROM PackAnimals;
SELECT * FROM AnimalCommands;
SELECT * FROM HorsesAndDonkeys;
SELECT * FROM YoungAnimals;
SELECT * FROM AllAnimals;