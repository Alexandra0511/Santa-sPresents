package implementations;

import enums.Category;

import java.util.*;

public class Input extends Observable {
    private int numberOfYears;
    private double santaBudget;
    private List<Child> children;
    private List<Gift> gifts;
    private List<AnnualChange> annualChanges;

    public Input(final int numberOfYears,
                 final double santaBudget,
                 final List<Child> children,
                 final List<Gift> gifts,
                 final List<AnnualChange> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.children = children;
        this.gifts = gifts;
        this.annualChanges = annualChanges;
    }

    /**
     * Getter pentru bugetul mosului
     * @return buget
     */
    public double getSantaBudget() {
        return santaBudget;
    }

    /**
     * Setter pentru bugetul mosului
     * @param santaBudget noul buget
     */
    public void setSantaBudget(final double santaBudget) {
        this.santaBudget = santaBudget;
    }

    /**
     * Adauga in lista de copii un nou copil
     * @param child noul copil ce trebuie adaugat
     */
    public void addNewChildren(final Child child) {
        children.add(child);
    }

    /**
     * Getter pentru numarul de ani pe care se implementeaza simularea
     * @return numarul de ani
     */
    public int getNumberOfYears() {
        return numberOfYears;
    }

    /**
     * Getter pentru lista de copii
     * @return lista de copii
     */
    public List<Child> getChildren() {
        return children;
    }

    /**
     * Getter pentru lista de cadouri
     * @return lista de cadouri
     */
    public List<Gift> getGifts() {
        return gifts;
    }

    /**
     * Getter pentru lista de schimbari anuale
     * @return lista de schimbari
     */
    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    /**
     * Calculeaza bugetul asignat fiecarui copil
     * Pentru fiecare copil din lista de copii, se initializeaza mapa cu bugetul initial 0 si,
     * in acelasi timp sa calculeaza suma totala a scorurilor average de la toti copiii. Bugetul
     * se imparte in unitati egale cum este cerut in enunt. Apoi, se parcurge din nou lista de
     * copii si se repartizeaza fiecaruia un buget egal cu unitatea anterioara inmultita cu
     * scorul mediu de cumintenie al copilului. Valoarea este introdusa in mapa.
     * @return mapa cu cheia copilul si valoarea bugetul asignat
     */
    public Map<Child, Double> budget() {
        Map<Child, Double> budget = new HashMap<>();
        double sum = 0.0;
        double assignedBudget;
        for (Child child : children) {
            budget.put(child, 0.0);
            sum = sum + child.averageScore();
        }
        double budgetUnit = (Double) this.santaBudget / sum;
        for (Child child : children) {
            assignedBudget = (Double) budgetUnit * child.averageScore();
            budget.put(child, assignedBudget);
        }
        return budget;
    }

    /**
     * Repartizeaza fiecarui copil cadourile primite
     * Initial, se sorteaza crescator lista de cadouri, in functie de pret, pentru a alege
     * mereu primul cadou dintr-o anumita categorie, care are cel mai mic pret. Apoi, pentru
     * fiecare copil, se creaza o noua lista de cadouri primite si se parcurge lista de preferinte.
     * Daca in lista de cadouri se gaseste unul cu categoria egala cu cea actuala si daca pretul
     * acestuia nu depaseste bugetul, se adauga in lista de cadouri primite de copil, dupa care
     * se trece la urmatoarea categorie. In final, se adauga in mapa copilul impreuna cu lista
     * sa de cadouri primite.
     * @return mapa cu cheia copilul si valoarea lista de cadouri primite
     */
    public Map<Child, List<Gift>> receivedGifts() {
        Map<Child, List<Gift>> receivedGifts =  new HashMap<>();
        //sortez crescator in functie de pret lista de cadouri
        Collections.sort(gifts, (o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
        for (Child child : children) {
            List<Gift> giftsGiven = new LinkedList<>();
            double sum = 0.0;
            for (Category category : child.getGiftsPreferences()) {
                for (Gift gift : gifts) {
                    if (gift.getCategory().equals(category)) {
                        if ((sum + gift.getPrice()) > this.budget().get(child)) {
                            break;
                        }
                        sum = sum + gift.getPrice();
                        giftsGiven.add(gift);
                        break;
                    }
                }
            }
            receivedGifts.put(child, giftsGiven);
        }
        return receivedGifts;
    }
}
