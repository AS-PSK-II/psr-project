import generateData from "./simulator/generateData";
import edgeConnector from "./connector/edgeConnector";
import { deviceConfig } from "./config/deviceConfiguration";

(function runSimulator(): void {
    console.log('Running simulator...');
    const {sensors, ...rest} = deviceConfig;
    edgeConnector.startTCPServer();
    edgeConnector.sendData(JSON.stringify({...rest, timestamp: new Date().toISOString()}));
    generateData();
})()
