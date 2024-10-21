import { Device } from "../types";
import { v4 as uuid } from 'uuid';
import * as dotenv from "dotenv";

dotenv.config();

export const deviceConfiguration: Device = {
    id: uuid(),
    name: process.env.APP_DEVICE_NAME || "simulator",
    isConnected: false,
    isActive: true,
    sensors: [
        {
            name: "temperature",
            value: 0,
            timestamp: new Date()
        },
        {
            name: "humidity",
            value: 0,
            timestamp: new Date()
        },
        {
            name: "pressure",
            value: 0,
            timestamp: new Date()
        }
    ]
}