package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;


public class Controller {
    @FXML
    Button searchBtn;
    @FXML
    TextField searchField;
    @FXML
    ChoiceBox choices;
    String username = "global/cloud@apiexamples.com";
    String password = "VMlRo/eh+Xd8M~l";
    String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
    //String phone_no = "888888855599222";

    @FXML
    private Label info;

    private JSONObject responses;

    public static void main(String[] args) {
    }

    public JSONObject getClientsbyPhone(String phone_no) throws Exception {

        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("phone", phone_no).
                asJson();
        responses = response.getBody().getObject().getJSONObject("_embedded");
        //Using unirest to call API with query parameter phone
        return responses;
    }

    public JSONObject getClientsbyEmail(String email) throws Exception {
        String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("email", email).
                asJson();
            responses = response.getBody().getObject().getJSONObject("_embedded");

        return responses;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Controller client = new Controller();
        JSONObject response;

        if (event.getSource() == searchBtn) {
            String search = searchField.getText();
            String choice = choices.getSelectionModel().getSelectedItem().toString();
            if (choice.equals("Phone Number")) {
                response = client.getClientsbyPhone(search);
            } else {
                response = client.getClientsbyEmail(search);
            }
            JSONArray client1 = response.getJSONArray("clients");


            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            primaryStage = (Stage)searchBtn.getScene().getWindow();
            Parent root= loader.load(getClass().getResource("viewFXML.fxml").openStream());
            View_Controller controller = loader.getController();
            controller.setClients(client1);
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

    }
}
