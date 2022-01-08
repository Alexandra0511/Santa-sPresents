package implementations;

import common.Constants;
import enums.Category;
import enums.Cities;

import java.util.ArrayList;
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
    private List<List<Category>> nextYears;

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

    /**
     * Setter pentru noul scor de cumintenie al copilului
     * @param newNiceScore noul scor
     */
    public void setNewNiceScore(final Double newNiceScore) {
        this.newNiceScore = newNiceScore;
    }

    /**
     * Getter pentru bugetul asignat copilului
     * @return bugetul copilului
     */
    public double getAssignedBudget() {
        return assignedBudget;
    }

    /**
     * Setter pentru bugetul asignat copilului
     * @param assignedBudget noul buget
     */
    public void setAssignedBudget(final double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    /**
     * Getter pentru lista de liste de noi preferinte ale copilului din toti
     * anii urmatori
     * @return lista de noi preferinte
     */
    public List<List<Category>> getNextYears() {
        return nextYears;
    }

    /**
     * Getter pentru id-ul copilului
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter pentru numele de familie al copilului
     * @return numele de familie
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter pentru prenumele copilului
     * @return prenumele
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter pentru varsta copilului
     * @return varsta
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter pentru varsta copilului
     * @param age noua varsta
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * Getter pentru orasul copilului
     * @return orasul
     */
    public Cities getCity() {
        return city;
    }

    /**
     * Getter pentru lista de scoruri de cumintenie a copilului
     * @return lista de scoruri
     */
    public List<Double> getNiceScores() {
        return niceScores;
    }

    /**
     * Getter pentru lista de dorinte a copilului
     * @return lista de preferinte
     */
    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    /**
     * Setter pentru lista de preferinte a copilului
     * @param giftsPreferences noua lista de preferinte
     */
    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    /**
     * Adauga in lista de liste de preferinte pentru toti anii urmatori o noua lista
     * @param giftsPreferences noua lista de preferinte dintr-un an viitor
     */
    public void addNextYears(final List<Category> giftsPreferences) {
        if (giftsPreferences != null) {
            nextYears.add(giftsPreferences);
        }
    }

    /**
     * Adauga in lista de scoruri de cumintenie noul scor
     * @param score noul scor adaugat
     */
    public void addNiceScore(final double score) {
        niceScores.add(score);
    }

    /**
     * Calculeaza scorul mediu de cumintenie al copilului, in functie de varsta acestuia,
     * tinand cont de cerintele din enunt
     * @return scorul mediu de cumintenie
     */
    public double averageScore() {
        if (this.getAge() < Constants.BABY_MAX_AGE) {
            return Constants.MAX_SCORE;
        } else if (Constants.BABY_MAX_AGE <= this.getAge()
                && this.getAge() < Constants.KID_MAX_AGE) {
            double sum = 0;
            for (Double score : this.niceScores) {
                sum = sum + score;
            }
            return sum / niceScores.size();
        } else if (this.getAge() > Constants.TEEN_MAX_AGE) {
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

    /**
     * Observatorul se updateaza.
     * Se extrage din lista de liste de preferinte pentru anii urmatori prima lista
     * (corespunzatoare anului imediat urmator). Aceasta se contopeste cu lista initiala
     * de preferinte a copilului, care este pusa la finalul primei liste si din care se elimina
     * duplicatele. Se actualizeaza noua lista de prefrinte a copilui.
     * In acelasi timp, varsta creste cu o unitate, iar noul niceScore se aduga in lista de
     * scoruri de cumintenie.
     */
    @Override
    public void update() {
        if (!nextYears.isEmpty()) {
            List<Category> newPreferences = nextYears.get(0);
            nextYears.remove(0);
            List<Category> mergedList = Stream
                    .concat(newPreferences.stream(), giftsPreferences.stream())
                    .distinct()
                    .collect(Collectors.toList());
            this.setGiftsPreferences(mergedList);
        }
        this.setAge(this.getAge() + 1);
        if (newNiceScore != null) {
            this.addNiceScore(newNiceScore);
            this.newNiceScore = null;
        }
    }
}
