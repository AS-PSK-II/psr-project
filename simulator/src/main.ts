import generateData from "./simulator/generateData";
import edgeConnector from "./connector/edgeConnector";
import { deviceConfig } from "./config/deviceConfiguration";
import { networkInterfaces } from "node:os";
import { tcpServerConfig } from "./config/edgeConnectionConfig";

const getLocalAddress = () => {
    const nets = networkInterfaces();
    const result = {};
    for (const net in nets) {
        const ipv4Addresses= nets[net]
            .filter((item) => item.family === "IPv4")
            .map((item) => item.address);

        if (ipv4Addresses.length > 0) {
            result[net] = ipv4Addresses;
        }
    }

    console.log(result);
}

((): void => {
    console.log('Running simulator...');
    const {sensors, ...rest} = deviceConfig;
    edgeConnector.startTCPServer();
    edgeConnector.sendData(JSON.stringify({
        ...rest,
        tcpServer: [tcpServerConfig],
        timestamp: new Date().toISOString()
    }));
    getLocalAddress();
    generateData();
})();

