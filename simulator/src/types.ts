export type DeviceConfig = {
    id: string;
    name: string;
    isConnected: boolean;
    isActive: boolean;
    sensors: SensorConfig[];
};

export type SensorConfig = {
    name: string;
    delay: number;
    valueRange: {
        min: number;
        max: number;
    };
}

export type SensorData = {
    id: string;
    name: string;
    value: number;
    timestamp: string;
}

export type EdgeConnection = {
    host: string;
    port: number;
};

export type TCPServer = {
    port: number;
};
