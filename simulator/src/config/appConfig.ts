import { EdgeConnection } from "../types";
import * as process from "node:process";
import * as dotenv from "dotenv";

dotenv.config();

export const appConfig: EdgeConnection = {
    host: process.env.APP_EDGE_HOST || '127.0.0.1',
    port: parseInt(process.env.APP_EDGE_PORT, 10) || 10000,
};