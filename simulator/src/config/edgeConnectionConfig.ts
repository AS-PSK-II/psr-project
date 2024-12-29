import { EdgeConnection, TCPServer } from "../types";
import * as process from "node:process";
import * as dotenv from "dotenv";

dotenv.config();

export const edgeConnectionConfig: EdgeConnection = {
    host: process.env.APP_EDGE_UDP_HOST || 'localhost',
    port: parseInt(process.env.APP_EDGE_UDP_PORT, 10) || 12345,
};

export const tcpServerConfig: TCPServer = {
    port: parseInt(process.env.APP_EDGE_TCP_PORT, 10) || 12346,
    address: process.env.APP_EDGE_TCP_SERVER_HOST || "127.0.0.1"
}