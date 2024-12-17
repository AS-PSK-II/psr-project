import { deviceConfig } from "../config/deviceConfiguration";
import edgeConnector from "../connector/edgeConnector";
import { SensorData, SensorConfig } from "../types";

const generateData = (): void => {
    const {id, sensors} = deviceConfig;

    sensors.forEach((sensorConfig: SensorConfig) => {
        generateSensorData(id, sensorConfig);
    });
};

const generateSensorData = (id: string, sensorConfig: SensorConfig): void => {
    const delay = deviceConfig.sensors.find(sensor => sensor.name === sensorConfig.name).delay;
    const sensorData: SensorData = {
        id,
        name: sensorConfig.name,
        value: generateSensorValue(sensorConfig),
        timestamp: new Date().toISOString()
    };
    edgeConnector.sendData(mapSensorDataToSend(sensorData));
    console.log({sensorData, delay})
    setTimeout(() => generateSensorData(id, sensorConfig), delay);
}

const generateSensorValue = (sensorConfig: SensorConfig): number => {
    const { min, max } = sensorConfig.valueRange;

    return Math.round((Math.random() * (max - min) + min) * 100) / 100;
}

const mapSensorDataToSend = (data: SensorData): string => `${data.id};${data.name};${data.value};${data.timestamp}`;

export default generateData;