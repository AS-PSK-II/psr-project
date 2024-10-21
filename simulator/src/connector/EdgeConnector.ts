import { EdgeConnection } from "../types";

type EdgeConnector = {
    connect: (connection: EdgeConnection) => void;
}

const edgeConnector: EdgeConnector = {
    connect: (connectionProperty: EdgeConnection) => {

    }
}

export default edgeConnector;