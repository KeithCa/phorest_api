package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;



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
                asJson(); //would've liked to store this as an object but didn't think I would've had time.
        responses = response.getBody().getObject().getJSONObject("_embedded"); //store object "_embedded" in JSONObject
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

        if (event.getSource() == searchBtn) { //get field from dropdown for search
            String search = searchField.getText();
            String choice = choices.getSelectionModel().getSelectedItem().toString();
            if (choice.equals("Phone Number")) {
                response = client.getClientsbyPhone(search);
            } else {
                response = client.getClientsbyEmail(search);
            }
            JSONArray client1 = response.getJSONArray("clients"); //get array of all clients


            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            primaryStage = (Stage)searchBtn.getScene().getWindow();
            Parent root= loader.load(getClass().getResource("viewFXML.fxml").openStream());
            View_Controller controller = loader.getController();
            controller.setClients(client1); //setting other scene to have access to the array of clients
            Scene scene= new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

    }
}
