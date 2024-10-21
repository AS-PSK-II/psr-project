import { DeviceConfig } from "../types";
import { v4 as uuid } from 'uuid';
import * as dotenv from "dotenv";

dotenv.config();

export const deviceConfig: DeviceConfig = {
    id: uuid(),
    name: process.env.APP_DEVICE_NAME || "simulator",
    isConnected: false,
    isActive: true,
    sensors: [
        {
            name: "temperature",
            delay: 500,
            valueRange: {
                min: -20,
                max: 50
            }
        },
        {
            name: "humidity",
            delay: 1000,
            valueRange: {
                min: 0,
                max: 100
            }
        },
        {
            name: "pressure",
            delay: 60000,
            valueRange: {
                min: 900,
                max: 1100
            }
        }
    ]
}