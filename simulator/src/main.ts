import generateData from "./simulator/generateData";
import edgeConnector from "./connector/edgeConnector";
import sendConnectivityInfo from "./simulator/sendConnectivityInfo";


((): void => {
    console.log('Running simulator...');
    edgeConnector.startTCPServer();
    sendConnectivityInfo();
    generateData();
})();

