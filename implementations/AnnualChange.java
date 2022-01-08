package implementations;

import java.util.List;

public class AnnualChange {
    private double newSantaBudget;
    private List<Gift> newGifts;
    private List<Child> newChildren;
    private List<Child> childrenUpdates;

    public AnnualChange(final double newSantaBudget,
                        final List<Gift> newGifts,
                        final List<Child> newChildren,
                        final List<Child> childrenUpdates) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }

    /**
     * Getter pentru noul buget al mosului
     * @return noul buget
     */
    public double getNewSantaBudget() {
        return newSantaBudget;
    }

    /**
     * Getter pentru lista de cadouri noi adaugate
     * @return noile cadouri
     */
    public List<Gift> getNewGifts() {
        return newGifts;
    }

    /**
     * Getter pentru lista de copii noi adaugati
     * @return noii copii
     */
    public List<Child> getNewChildren() {
        return newChildren;
    }

    /**
     * Getter pentru lista de copii ce contine update-urile acestora
     * @return update-urile pentru copii
     */
    public List<Child> getChildrenUpdates() {
        return childrenUpdates;
    }
}
