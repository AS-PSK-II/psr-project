package pl.arusoftware.psrproject.edge.cache;

import pl.arusoftware.psrproject.edge.model.Device;

import java.util.HashMap;
import java.util.Map;

public class DeviceCache {
    private static final DeviceCache INSTANCE = new DeviceCache();
    private final Map<String, Device> devices;

    private DeviceCache() {
        devices = new HashMap<>();
    }

    public static DeviceCache getInstance() {
        return DeviceCache.INSTANCE;
    }

    public void addDevice(String id, Device device) {
        devices.put(id, device);
    }

    public Device getDevice(String id) {
        return devices.get(id);
    }

    public Map<String, Device> getDevices() {
        return devices;
    }
}
