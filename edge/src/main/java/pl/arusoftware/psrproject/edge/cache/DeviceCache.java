package pl.arusoftware.psrproject.edge.cache;

import pl.arusoftware.psrproject.edge.model.Device;

import java.util.HashMap;
import java.util.Map;

public class DeviceCache {
    private static final Object mutex = new Object();
    private static DeviceCache instance;
    private final Map<String, Device> devices;

    private DeviceCache() {
        devices = new HashMap<>();
    }

    public static DeviceCache getInstance() {
        DeviceCache result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new DeviceCache();
                }
            }
        }
        return result;
    }

    public void addDevice(String id, Device device) {
        devices.put(id, device);
        System.out.println(device.toJSON());
    }

    public Device getDevice(String id) {
        return devices.get(id);
    }
}
