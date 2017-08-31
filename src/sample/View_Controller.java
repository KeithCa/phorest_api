package sample;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import org.json.JSONArray;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class View_Controller implements Initializable {
    String username = "global/cloud@apiexamples.com";
    String password = "VMlRo/eh+Xd8M~l";
    String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/voucher"; //shouldnt really be using base URL like this but eh
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

        if (clients.length() > 1) { //If statement to check if there is more than one client and if there is give a pop up with the names of the clients so you can choose one
            for (i = 0; i < clients.length(); i++) {
                client = clients.getJSONObject(i);
                choices.add(i + "." + client.getString("firstName") + client.getString("lastName")); //adds the object element and first/last name to a string for choicebox
                ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
                dialog.setTitle("Choice Dialog");
                dialog.setContentText("Choose client:");

                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String string = result.get();
                    String[] parts = string.split("."); //splits the string at the . to get the element it should be
                    int part1 = Integer.parseInt(parts[0]);
                    client = clients.getJSONObject(part1);
                    setClient(client);

                    name = client.getString("firstName");
                    sname = client.getString("lastName");
                    phone = client.getString("mobile");
                    email = client.getString("email");
                    add = client.getString("clientSince");

                    info.setText("Name: " + name + " " + sname + "\n\nPhone: " + phone + "\n\nEmail: " + email + "\n\nClient Since: " + add);
                } //Not exactly sure if this works as I didn't know an email or phone number with multiples but I'm not really sure why there would be a use case with that anyway.

            }
        } else {
            client = clients.getJSONObject(i); //gets first client object.
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

            String var = voucher_client.getString("clientId");
            System.out.println(var);
            HttpResponse<JsonNode> response = Unirest.post(base_url).basicAuth(username, password).header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body("{\"clientId\": \""+var+"\", \"creatingBranchId\": \"SE-J0emUgQnya14mOGdQSw\",\"expiryDate\": \"2017-08-29T21:05:32.461Z\",\"issueDate\": \"2017-08-29T21:05:32.461Z\",\"links\": [ {\"href\": \"string\",\"rel\": \"string\",\"templated\": true }],\"originalBalance\": 123.12,\"serialNumber\": null,\"voucherId\": \"ab-465\" }")
                    .asJson(); //again would've liked to post this as an object
            JSONObject body = response.getBody().getObject();
            System.out.println(body);
        } //this works now as long as you change the voucherId. doesnt display a success message or anything though.

    }
}
