package pl.arusoftware.psrproject.edge.udp;

import pl.arusoftware.psrproject.edge.udp.observer.Observer;
import pl.arusoftware.psrproject.edge.udp.observer.Subject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UDPServer implements Subject {
    private final DatagramSocket socket;
    private final List<Observer> observers;

    public UDPServer() throws SocketException {
        this(12345);
    }

    public UDPServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.observers = new ArrayList<>();
        System.out.println("UDP Server started at " + Instant.now().toString());
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        observers.forEach(observer -> observer.update(message));
    }

    public void readMessagesFromSocket() throws IOException {
        while (!socket.isClosed()) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            notifyObservers(new String(packet.getData(), StandardCharsets.UTF_8));
        }
    }

    public void shutdown() {
        socket.close();
    }
}
