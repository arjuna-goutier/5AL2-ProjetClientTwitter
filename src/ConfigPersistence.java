import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigPersistence {
	private String filePath;

	public ConfigPersistence(String filePath) {
		// TODO Auto-generated constructor stub
		this.filePath = filePath;
	}

	public ConfigInfo loadConfig() {
		File file = new File(filePath);
		if(!file.exists())
			return null;
		String publicKey = null;
		String privateKey = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			Node root = document.getFirstChild();
			NodeList list = root.getChildNodes();
			for (int i = 0; i < list.getLength(); ++i) {
				switch (list.item(i).getNodeName()) {
				case "public":
					publicKey = list.item(i).getTextContent();
					break;
				case "private":
					privateKey = list.item(i).getTextContent();
					break;
				}
			}
			if(publicKey != null && privateKey != null)
				return new ConfigInfo(publicKey, privateKey);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveConfig(ConfigInfo config) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			// Element root = document.createElement("configuration");
			Element root = document.createElement("keys");
			Element publicKey = document.createElement("public");
			publicKey.setTextContent(config.getPublicKey());
			Element privateKey = document.createElement("private");
			privateKey.setTextContent(config.getPrivateKey());
			root.appendChild(publicKey);
			root.appendChild(privateKey);
			document.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
}
