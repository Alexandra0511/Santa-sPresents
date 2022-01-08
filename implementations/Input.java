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

    public void setSantaBudget(double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public void addNewChildren(Child child) {
        children.add(child);
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public Map<Child, Double> budget() {
        Map<Child, Double> budget = new HashMap<>();
        double sum = 0.0;
        double assignedBudget = 0.0;
        for (Child child : children) {
            budget.put(child, 0.0);
            sum = sum + child.averageScore();
        }
        double budgetUnit = (Double) this.santaBudget / sum;
        for (Child child : children) {
            assignedBudget = (Double) budgetUnit * child.averageScore();
            budget.put(child, assignedBudget);
            assignedBudget = 0.0;
        }
        return budget;
    }

    public Map<Child, List<Gift>> receivedGifts() {
        Map<Child, List<Gift>> receivedGifts =  new HashMap<>();
        //sortez crescator in functie de pret lista de cadouri
        Collections.sort(gifts, (o1, o2) -> (int) (o1.getPrice() - o2.getPrice()));
        for (Child child : children) {
            List<Gift> giftsGiven = new LinkedList<>();
            double sum = 0.0;
            for (Category category : child.getGiftsPreferences()) {
                for (Gift x : gifts) {
                    if (x.getCategory().equals(category)) {
                        if ((sum + x.getPrice()) > this.budget().get(child)) {
                            break;
                        }
                        sum = sum + x.getPrice();
                        giftsGiven.add(x);
                        break;
                    }
                }
            }
            receivedGifts.put(child, giftsGiven);
        }
        return receivedGifts;
    }
}
