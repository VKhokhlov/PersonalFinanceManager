import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final FinanceManager financeManager;
    private final Gson gson;

    public Server(int port, FinanceManager financeManager) {
        this.port = port;
        this.financeManager = financeManager;

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Purchase.class, new PurchaseGsonDeserializer())
                .create();
    }

    public void start() throws RuntimeException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    String request = MessageReader.read(in);
                    System.out.println("New connection accepted, port " + clientSocket.getPort() + ".");
                    System.out.println("Request:\n" + request);
                    System.out.println("Response:");

                    String response = null;

                    try {
                        Purchase purchase = gson.fromJson(request, Purchase.class);
                        financeManager.addPurchase(purchase);
                    } catch (JsonSyntaxException e) {
                        response = "Json syntax error: " + e.getMessage();
                    } catch (JsonParseException e) {
                        response = "Json parse error: " + e.getMessage();
                    }

                    if (response == null) {
                        Statistics statistics = financeManager.getStatistics();
                        response = gson.toJson(statistics);
                    }

                    out.println(response);

                    System.out.println(response);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
