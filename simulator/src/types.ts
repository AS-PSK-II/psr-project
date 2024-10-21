export type Device = {
    id: string;
    name: string;
    isConnected: boolean;
    isActive: boolean;
    sensors: Sensor[];
};

export type Sensor = {
    device?: string;
    name: string;
    value: number;
    timestamp: Date;
}

export type EdgeConnection = {
    host: string;
    port: number;
};

export type TCPServer = {
    port: number;
};
