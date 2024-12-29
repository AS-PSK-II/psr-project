import * as dgram from "node:dgram";
import { Buffer } from "node:buffer";
import { edgeConnectionConfig, tcpServerConfig } from "../config/edgeConnectionConfig";
import * as net from "node:net";
import { deviceConfig } from "../config/deviceConfiguration";

type EdgeConnector = {
    sendData: (data: string) => void;
    startTCPServer: () => void;
}

const edgeConnector: EdgeConnector = {
    sendData: (data: string) => {
        const { host, port } = edgeConnectionConfig;
        const client = dgram.createSocket("udp4");
        const message = Buffer.from(data, "utf8");
        client.send(message, port, host, (error) => {
            if (error) console.error(error);
            client.close();
        })
    },
     startTCPServer: () => {
        const tcpServer = net.createServer((socket) => {
            socket.on("data", (data:Buffer) => {
                const updatedConfig = JSON.parse(data.toString());
                const deviceSensors = [...deviceConfig.sensors];
                deviceSensors.find(sensor => sensor.name === updatedConfig.property).delay = updatedConfig.value;
                deviceConfig.sensors = deviceSensors;
            });
        });

        console.log(`Starting TCP server on port ${tcpServerConfig.port} and address ${tcpServerConfig.address}`)
        tcpServer.listen(tcpServerConfig.port, tcpServerConfig.address);
     }
}

export default edgeConnector;