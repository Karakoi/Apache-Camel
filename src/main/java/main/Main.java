package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws Exception {
		ApplicationContext springContext = new ClassPathXmlApplicationContext("./META-INF/spring/camel-context.xml");
		CamelContext camelContext = (CamelContext) springContext.getBean("camelContext");
//		CamelContext camelContext2 = (CamelContext) springContext.getBean("camelContext2");

		try {
			camelContext.start();
			Thread.sleep(20000);
//			camelContext2.start();
			camelContext.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
