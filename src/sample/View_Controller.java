package sample;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.json.JSONArray;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class View_Controller implements Initializable {
    String username = "global/cloud@apiexamples.com";
    String password = "VMlRo/eh+Xd8M~l";
    String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/voucher";
    @FXML
    private Label info;
    @FXML
    Button createVoucher;
    JSONArray clients = new JSONArray();
    JSONObject voucher_client = new JSONObject();

    public void setClient(JSONObject voucher_client){
        this.voucher_client = voucher_client;
    }

    public void setClients(JSONArray clients) {
        this.clients = clients;
        List<String> choices = new ArrayList<>();
        int i = 0;
        String name, sname, phone, email, add;
        JSONObject client;
        System.out.println(clients);
        System.out.print(clients.length());

        if (clients.length() > 1) {
            for (i = 0; i < clients.length(); i++) {
                client = clients.getJSONObject(i);
                choices.add(i + "." + client.getString("firstName") + client.getString("lastName"));
                ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
                dialog.setTitle("Choice Dialog");
                dialog.setContentText("Choose client:");

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String string = result.get();
                    String[] parts = string.split(".");
                    int part1 = Integer.parseInt(parts[0]);
                    client = clients.getJSONObject(part1);
                    setClient(client);

                    name = client.getString("firstName");
                    sname = client.getString("lastName");
                    phone = client.getString("mobile");
                    email = client.getString("email");
                    add = client.getString("clientSince");

                    info.setText("Name: " + name + " " + sname + "\n\nPhone: " + phone + "\n\nEmail: " + email + "\n\nClient Since: " + add);
                }

            }
        } else {
            client = clients.getJSONObject(i);
            setClient(client);
            name = client.getString("firstName");
            sname = client.getString("lastName");
            phone = client.getString("mobile");
            email = client.getString("email");
            add = client.getString("clientSince");

            info.setText("Name: " + name + " " + sname + "\n\nPhone: " + phone + "\n\nEmail: " + email + "\n\nClient Since: " + add);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == createVoucher) {
            System.out.println(voucher_client.getString("clientId"));

            HttpResponse<JsonNode> response = Unirest.post(base_url).basicAuth(username, password).header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body("{\"clientId\":\"value\", \"creatingBranchId\":\"bar\", \"expiryDate\" : \"something\" " +
                            ", \"issueDate\":\"bar\", [\"links\"{\"href\":\"string\"" +
                            ", \"rel\":\"string\", \"creatingBranchId\":\"bar\"}], \"originalBalance\":\"bar\"," +
                            "\"serialNumber\":\"value\", \"voucherID\":\"bar\"}")
                    .asJson();
            String status = response.getBody().getObject().getString("detail");
            System.out.println(status);
        }

    }
}
