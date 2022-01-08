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

    public double getNewSantaBudget() {
        return newSantaBudget;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public List<Child> getNewChildren() {
        return newChildren;
    }

    public List<Child> getChildrenUpdates() {
        return childrenUpdates;
    }
}
