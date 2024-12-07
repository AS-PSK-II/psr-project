package pl.arusoftware.psrproject.edge.repository;

import pl.arusoftware.psrproject.edge.model.DeviceTCPServer;

import java.util.*;

public class DeviceTCPServerRepository {
    private final Map<UUID, List<DeviceTCPServer>> deviceServers;

    public DeviceTCPServerRepository() {
        this.deviceServers = new HashMap<>();
    }

    public void addDeviceServer(UUID deviceId, DeviceTCPServer deviceTCPServer) {
        if (!deviceServers.containsKey(deviceId)) {
            deviceServers.put(deviceId, new ArrayList<>());
        }

        deviceServers.get(deviceId).add(deviceTCPServer);
    }

    public List<DeviceTCPServer> getDeviceServers(UUID deviceId) {
        return deviceServers.get(deviceId);
    }
}
