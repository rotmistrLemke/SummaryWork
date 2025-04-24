import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AnimalRegistry {
    private List<Animal> animals;

    public AnimalRegistry() {
        animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void listAnimalsByBirthDate() {
        animals.sort((a1, a2) -> a1.getBirthDate().compareTo(a2.getBirthDate()));
        System.out.println("\nСписок животных по дате рождения:");
        for (Animal animal : animals) {
            System.out.printf("%s: %s, Дата рождения: %s%n",
                    animal.getType(), animal.getName(), animal.getBirthDate());
        }
    }

    public void listCommands(Animal animal) {
        System.out.printf("\nКоманды для %s '%s':%n", animal.getType(), animal.getName());
        for (String command : animal.getCommands()) {
            System.out.println("- " + command);
        }
    }

    public Animal findAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    public void printTotalAnimalCount() {
        System.out.printf("\nОбщее количество животных: %d%n", Animal.getTotalCount());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Реестр домашних животных ===");
            System.out.println("1. Добавить новое животное");
            System.out.println("2. Список команд животного");
            System.out.println("3. Обучить животное новой команде");
            System.out.println("4. Вывести список животных по дате рождения");
            System.out.println("5. Показать общее количество животных");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addNewAnimal(scanner);
                    break;
                case 2:
                    listAnimalCommands(scanner);
                    break;
                case 3:
                    teachNewCommand(scanner);
                    break;
                case 4:
                    listAnimalsByBirthDate();
                    break;
                case 5:
                    printTotalAnimalCount();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
        scanner.close();
    }

    private void addNewAnimal(Scanner scanner) {
        System.out.println("\nВыберите тип животного:");
        System.out.println("1. Собака");
        System.out.println("2. Кошка");
        System.out.println("3. Хомяк");
        System.out.println("4. Лошадь");
        System.out.println("5. Верблюд");
        System.out.println("6. Осел");
        System.out.print("Ваш выбор: ");

        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Введите имя животного: ");
        String name = scanner.nextLine();

        System.out.print("Введите дату рождения (гггг-мм-дд): ");
        String dateStr = scanner.nextLine();
        Date birthDate = java.sql.Date.valueOf(dateStr);

        Animal animal = null;
        switch (typeChoice) {
            case 1:
                animal = new Dog(name, birthDate);
                break;
            case 2:
                animal = new Cat(name, birthDate);
                break;
            case 3:
                animal = new Hamster(name, birthDate);
                break;
            case 4:
                animal = new Horse(name, birthDate);
                break;
            case 5:
                animal = new Camel(name, birthDate);
                break;
            case 6:
                animal = new Donkey(name, birthDate);
                break;
            default:
                System.out.println("Неверный выбор типа животного.");
                return;
        }

        addAnimal(animal);
        System.out.printf("%s '%s' успешно добавлен(а) в реестр!%n", animal.getType(), animal.getName());
    }

    private void listAnimalCommands(Scanner scanner) {
        System.out.print("\nВведите имя животного: ");
        String name = scanner.nextLine();

        Animal animal = findAnimalByName(name);
        if (animal != null) {
            listCommands(animal);
        } else {
            System.out.println("Животное с таким именем не найдено.");
        }
    }

    private void teachNewCommand(Scanner scanner) {
        System.out.print("\nВведите имя животного: ");
        String name = scanner.nextLine();

        Animal animal = findAnimalByName(name);
        if (animal != null) {
            System.out.print("Введите новую команду: ");
            String command = scanner.nextLine();
            animal.learnCommand(command);
            System.out.printf("Команда '%s' успешно добавлена для %s '%s'%n",
                    command, animal.getType(), animal.getName());
        } else {
            System.out.println("Животное с таким именем не найдено.");
        }
    }
}
