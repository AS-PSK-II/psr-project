import { appConfig } from "./config/appConfig";
import { deviceConfiguration } from "./config/deviceConfiguration";

(function runSimulator()  {
    console.log('Running simulator...');
    console.log({appConfig, deviceConfiguration: JSON.stringify(deviceConfiguration)});
})()
