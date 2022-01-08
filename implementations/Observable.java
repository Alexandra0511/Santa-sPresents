package implementations;

import java.util.LinkedList;
import java.util.List;

public class Observable {
    private List<Observer> observers;

    public Observable() {
        this.observers = new LinkedList<>();
    }

    public void announce() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }
}
