package sample;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.json.JSONObject;
import org.json.JSONArray;
import javafx.scene.control.Label;


public class Controller {
    @FXML
    Button searchBtn;
    @FXML
    TextField searchField;
    @FXML
    ChoiceBox choices;
    String username = "global/cloud@apiexamples.com";
    String password = "VMlRo/eh+Xd8M~l";
    //String phone_no = "888888855599222";

    @FXML private Label info;


    public static void main(String[] args){
    }

    public JSONObject getClientsbyPhone(String phone_no) throws Exception {
        String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("phone", phone_no).
                asJson();
        JSONObject responses = response.getBody().getObject().getJSONObject("_embedded");
        return responses;
    }

    public JSONObject getClientsbyEmail(String email) throws Exception {
        String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("email", email).
                asJson();
        JSONObject responses = response.getBody().getObject().getJSONObject("_embedded");
        return responses;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        Controller client = new Controller();
        JSONObject response = new JSONObject();

        if(event.getSource() == searchBtn) {
            String search = searchField.getText();
            String choice = choices.getSelectionModel().getSelectedItem().toString();
            if(choice.equals("Phone Number")){
                System.out.println(search);
                response = client.getClientsbyPhone(search);
            }
            else {
                client.getClientsbyEmail(search);
                response = client.getClientsbyPhone(search);
            }
            JSONArray clients = response.getJSONArray("clients");

            String name, sname, phone, email, add;

            JSONObject client1 = clients.getJSONObject(0);
            name = client1.getString("firstName");
            sname = client1.getString("lastName");
            phone = client1.getString("mobile");
            email = client1.getString("email");
            add = client1.getString("clientSince");

            info.setText("Name: "+name+" "+sname+"\n\nPhone: "+phone+"\n\nEmail: "+email+"\n\nClient Since: "+add);
        }

    }
}
