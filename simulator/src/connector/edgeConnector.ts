import * as dgram from "node:dgram";
import { Buffer } from "node:buffer";
import { edgeConnectionConfig, tcpServerConfig } from "../config/edgeConnectionConfig";
import * as net from "node:net";

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
                console.log(data);
            });
        });

        console.log(`Starting TCP server on port ${tcpServerConfig.port}`)
        tcpServer.listen(tcpServerConfig.port, "localhost");
     }
}

export default edgeConnector;