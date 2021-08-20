var PROTO_PATH = __dirname + '../../../proto/matcher.proto';
var grpc = require('@grpc/grpc-js');
var protoLoader = require('@grpc/proto-loader');

async function main() {
    // Suggested options for similarity to existing grpc.load behavior
    var packageDefinition = protoLoader.loadSync(
        PROTO_PATH,
        {keepCase: true,
            longs: String,
            enums: String,
            defaults: true,
            oneofs: true
        });
    var protoDescriptor = grpc.loadPackageDefinition(packageDefinition);
    var matcherRpc = protoDescriptor.matcherRPC;

    var matcherStub = new matcherRpc.Matcher('localhost:9090', grpc.credentials.createInsecure());


    var orders = await getOrders(matcherStub);
    console.log("orders:");
    console.log(orders);

    var trades = await placeRandomOrder(matcherStub);
    console.log("trades:");
    console.log(trades);

    var orders = await getOrders(matcherStub);
    console.log("orders:");
    console.log(orders);
}

main();

function getOrders(matcherStub) {
    return new Promise((resolve, reject) => {
        var orders = [];
        var ordersListener = matcherStub.getOrders({});
        ordersListener.on("data", order => {
            orders.push(order);
        });

        ordersListener.on("end", async () => {
            resolve(orders)
        });
    });
}

function placeRandomOrder(matcherStub) {
    return new Promise((resolve, reject) => {
        var buySell = ["BUY", "SELL"];
        var trades = [];

        var order = {
            "account": "GRPC test account",
            "price": Math.random()*10,
            "quantity": Math.floor(Math.random()*10),
            "action": buySell[Math.floor(Math.random()*buySell.length)],
        }

        console.log("Placing order: ")
        console.log(order);
        var tradesListener = matcherStub.placeOrder(order);

        tradesListener.on("data", order => {
            trades.push(order);
        });

        tradesListener.on("end", async () => {
            resolve(trades)
        });
    });
}