package pl.arusoftware.psrproject.edge.udp;

import pl.arusoftware.psrproject.edge.udp.observer.Observer;
import pl.arusoftware.psrproject.edge.udp.observer.Subject;

public class UDPReader implements Observer {

    public UDPReader(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
