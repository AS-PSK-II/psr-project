package pl.kielce.tu.svcservice.cache;

import pl.kielce.tu.svcservice.model.DeviceConfig;

import java.util.HashMap;
import java.util.Map;

public class DeviceConfigCache {
    private static final DeviceConfigCache instance = new DeviceConfigCache();

    private final Map<String, DeviceConfig> deviceConfigs;

    private DeviceConfigCache() {
        this.deviceConfigs = new HashMap<>();
    }

    public static DeviceConfigCache getInstance() {
        return instance;
    }

    public void addDeviceConfig(DeviceConfig deviceConfig) {
        deviceConfigs.put(deviceConfig.getId().toString(), deviceConfig);
    }

    public DeviceConfig getDeviceConfig(String id) {
        return deviceConfigs.get(id);
    }

    public boolean containsDeviceConfig(String id) {
        return deviceConfigs.containsKey(id);
    }
}
