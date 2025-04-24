import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Базовый класс Животное
abstract class Animal {
    private static int totalCount = 0;
    protected String name;
    protected Date birthDate;
    protected List<String> commands;

    public Animal(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.commands = new ArrayList<>();
        totalCount++;
    }

    public abstract String getType();

    public void learnCommand(String command) {
        commands.add(command);
    }

    public List<String> getCommands() {
        return new ArrayList<>(commands);
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public static int getTotalCount() {
        return totalCount;
    }
}

// Классы домашних животных
abstract class Pet extends Animal {
    public Pet(String name, Date birthDate) {
        super(name, birthDate);
    }
}

class Dog extends Pet {
    public Dog(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Собака";
    }
}

class Cat extends Pet {
    public Cat(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Кошка";
    }
}

class Hamster extends Pet {
    public Hamster(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Хомяк";
    }
}

// Классы вьючных животных
abstract class PackAnimal extends Animal {
    public PackAnimal(String name, Date birthDate) {
        super(name, birthDate);
    }
}

class Horse extends PackAnimal {
    public Horse(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Лошадь";
    }
}

class Camel extends PackAnimal {
    public Camel(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Верблюд";
    }
}

class Donkey extends PackAnimal {
    public Donkey(String name, Date birthDate) {
        super(name, birthDate);
    }

    @Override
    public String getType() {
        return "Осел";
    }
}