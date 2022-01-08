package implementations;

import java.util.LinkedList;
import java.util.List;

public class Observable {
    private List<Observer> observers;

    public Observable() {
        this.observers = new LinkedList<>();
    }

    /**
     * Notifica observatorii pentru a se updata
     */
    public void announce() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Adauga un nou observator in lista de observatori
     * @param observer noul obiect observator
     */
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }
}
