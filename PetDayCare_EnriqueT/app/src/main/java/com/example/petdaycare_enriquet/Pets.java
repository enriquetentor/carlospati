package com.example.petdaycare_enriquet;

public class Pets {
    private String Name;
    private String Breed;
    private String Gender;
    private double Weight;

    public Pets(String name, String breed, String gender, double weight) {
        Name = name;
        Breed = breed;
        Gender = gender;
        Weight = weight;
    }

     Pets(String name, String breed) {
        Name = name;
        Breed = breed;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }
}
