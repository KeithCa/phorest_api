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

    public static void main(String[] args) throws Exception {
        Controller client = new Controller();
        client.getClientsbyPhone("0868694227");
    }

    public void getClientsbyPhone(String phone_no) throws Exception {
        String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("phone", phone_no).
                asJson();
        System.out.println(response.getBody().getObject().toString(2));
    }

    public void getClientsbyEmail(String email) throws Exception {
        String base_url = "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("email", email).
                asJson();
        System.out.println(response.getBody().getObject().toString(2));
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        Controller client = new Controller();
        String search = searchField.getText();

        if(event.getSource() == searchBtn) {

            String choice = choices.getSelectionModel().getSelectedItem().toString();
            if(choice == "Phone Number"){
                client.getClientsbyPhone(search);
            }
            else {
                client.getClientsbyEmail(search);
            }

        }

    }
}
