package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button connectButton;

    @FXML
    private Button disConnectButton;

    public static ApplicationContext springContext;
    public static CamelContext camelContext;
    public static CamelContext camelContext2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        springContext = new ClassPathXmlApplicationContext("./META-INF/spring/camel-context.xml");
        camelContext = (CamelContext) springContext.getBean("camelContext");
        camelContext2 = (CamelContext) springContext.getBean("camelContext2");

        try {
            camelContext.stop();
            camelContext2.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void connect(ActionEvent actionEvent) throws Exception {
        camelContext.start();
//		camelContext2.start();
    }

    @FXML
    public void disconnect(ActionEvent actionEvent) throws Exception {
        camelContext.stop();
//		camelContext2.stop();
    }

}
