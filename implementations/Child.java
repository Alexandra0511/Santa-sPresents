package implementations;

import enums.Category;
import enums.Cities;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Child extends Observer {
    private final int id;
    private final String lastName;
    private final String firstName;
    private int age;
    private final Cities city;
    private final List<Double> niceScores;
    private List<Category> giftsPreferences;
    private double assignedBudget;
    private Double newNiceScore;

    public double getNewNiceScore() {
        return newNiceScore;
    }

    public void setNewNiceScore(final Double newNiceScore) {
        this.newNiceScore = newNiceScore;
    }

    public double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(final double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    private List<List<Category>> nextYears;

    public List<List<Category>> getNextYears() {
        return nextYears;
    }


    public Child(final int id, final String lastName,
                 final String firstName, final int age,
                 final Cities city, final Double niceScore,
                 final List<Category> giftsPreferences) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        this.niceScores = new ArrayList<>();
        niceScores.add(niceScore);
        this.giftsPreferences = giftsPreferences;
        this.nextYears = new ArrayList<>();
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public Cities getCity() {
        return city;
    }

    public List<Double> getNiceScores() {
        return niceScores;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public void addNextYears(final List<Category> giftsPreferences) {
        if (giftsPreferences != null) {
            nextYears.add(giftsPreferences);
        }
    }

    public void addNiceScore(final double score) {
        niceScores.add(score);
    }

    public double averageScore() {
        if (this.getAge() < 5) {
            return 10.0;
        } else if (5 <= this.getAge() && this.getAge() < 12) {
            double sum = 0;
            for (Double score : this.niceScores) {
                sum = sum + score;
            }
            return sum / niceScores.size();
        } else if (this.getAge() > 18) {
            return 0;
        } else {
            double sum = 0;
            int i = 1;
            for (Double score : this.niceScores) {
                sum = sum + score * i;
                i++;
            }
            int div = this.niceScores.size() * (this.niceScores.size() + 1) / 2;
            return sum / div;
        }
    }

    @Override
    public void update() {
        if (!nextYears.isEmpty()) {
            List<Category> newPreferences = nextYears.get(0);
            nextYears.remove(0);
            List<Category> mergedList = new LinkedList<>();
            mergedList = Stream.concat(newPreferences.stream(), giftsPreferences.stream())
                    .distinct()
                    .collect(Collectors.toList());
            //System.out.println(mergedList);
            //giftsPreferences = mergedList;
            this.setGiftsPreferences(mergedList);

        }
        this.setAge(this.getAge() + 1);
        if (newNiceScore != null) {
            this.addNiceScore(newNiceScore);
            this.newNiceScore = null;
        }
    }
}
