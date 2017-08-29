import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Main {

    String username = "global/cloud@apiexamples.com";
    String password = "VMlRo/eh+Xd8M~l";
    String phone_no = "888888855599222";
    public static void main(String args[]) throws Exception{
        Main client = new Main();
        client.getClients();
    }

    public void getClients() throws Exception {
        String base_url= "https://api-gateway-dev.phorest.com/third-party-api-server/api/business/eTC3QY5W3p_HmGHezKfxJw/client";
        HttpResponse<JsonNode> response = Unirest.get(base_url).basicAuth(username, password).
                queryString("phone", phone_no).
                asJson();
        System.out.println(response.getBody().getObject().toString(2));
    }
}
