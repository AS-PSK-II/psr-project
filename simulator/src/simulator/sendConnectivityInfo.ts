import edgeConnector from "../connector/edgeConnector";
import { tcpServerConfig } from "../config/edgeConnectionConfig";
import { deviceConfig } from "../config/deviceConfiguration";

const sendConnectivityInfo = (): void => {
    setInterval(() => {
        const {sensors, ...rest} = deviceConfig;

        edgeConnector.sendData(JSON.stringify({
            ...rest,
            tcpServer: [tcpServerConfig],
            timestamp: new Date().getTime()
        }));
        console.log("SendConnectivityInfo");
    }, 60000)
}

export default sendConnectivityInfo;