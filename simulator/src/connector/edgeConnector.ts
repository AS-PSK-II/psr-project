type EdgeConnector = {
    sendData: (data: string) => void;
}

const edgeConnector: EdgeConnector = {
    sendData: (data: string) => {
        console.log(data);
    }
}

export default edgeConnector;