import java.util.*;

class Oops{
    public static void main(String[] args) {
        //polimorphism
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Dog("Lucky"));
        animals.add(new Bird("Sky"));
        animals.add(new Bird("ping"));

        for(Animal animal : animals){
            System.out.println(animal.getName());
            animal.move();
            animal.makeSound();
        }


    }
}



//abstraction// Encapsulation
abstract class Animal {
    private String name;
    public Animal(String name) {
        this.name = name;
    }
    // Encapsulation
    public String getName() {
        return name;
    }


    public abstract void makeSound();
    public abstract void move();
}


class Dog extends Animal{
  public Dog(String name) {
        super(name);
    }
    public void makeSound() {
        System.out.println("Barks");
    }
    public void move(){
        System.out.println("Moves whith four legs");
    }
}

class Bird extends Animal{
  public Bird(String name) {
        super(name);
    }
    public void makeSound() {
        System.out.println("Chirps");
    }
    public void move(){
        System.out.println("flies with wings");
    }
}

// interface

interface Human{
  public static final int populationInBillion = 8;
  public void speak();
}

class Person implements Human{
  public void speak() {
        System.out.println("Hello, I am a person.");
    }
}
