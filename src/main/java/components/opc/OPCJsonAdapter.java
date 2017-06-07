package components.opc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OPCJsonAdapter {

	public Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public Map<String, List<String>> jsonMap = new HashMap<String, List<String>>();

	public Map<String, List<String>> parseForProducer(Exchange exchange) throws IOException {

		JsonParser parser = new JsonParser();
		Object obj = parser.parse(exchange.getIn().getBody(String.class));
		JsonObject jsonObject = (JsonObject) obj;

		jsonMap = gson.fromJson(jsonObject, HashMap.class);
		System.out.println("MMMMMMMMMMMMMMMMMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPPPPPPPPPPPPPPPPPPP \n" + jsonMap);
		return jsonMap;
	}

	public Map<String, List<String>> parseForConsumer() throws IOException {

		File file = new File(
				"E://Документи/Eclipse for Java EE Projects/Camel/componentsData/opcData/read/Values.json");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str1, str2 = new String();
		while ((str1 = br.readLine()) != null) {
			str2 += str1;
		}
		fr.close();
		br.close();
		System.out.println(str2);

		JsonParser parser = new JsonParser();
		Object obj = parser.parse(str2);
		JsonObject jsonObject = (JsonObject) obj;

		jsonMap = gson.fromJson(jsonObject, HashMap.class);
		System.out.println("MMMMMMMMMMMMMMMMMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPPPPPPPPPPPPPPPPPPP \n" + jsonMap);

		return jsonMap;
	}
}
