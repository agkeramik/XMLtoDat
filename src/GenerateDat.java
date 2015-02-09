import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GenerateDat {
	double sigma_cm=7;
	double sigma_rad=0.075;
	
	Map<Furniture, Map<Furniture, Integer>> countMatrix = new HashMap<Furniture, Map<Furniture, Integer>>();
	
	public Integer getFromMatrix(Furniture f1, Furniture f2){
		if (!countMatrix.containsKey(f1))
			countMatrix.put(f1, new HashMap<Furniture, Integer>());
		if (!countMatrix.get(f1).containsKey(f2))
			countMatrix.get(f1).put(f2, 0);
		return countMatrix.get(f1).get(f2);
	}
	
	public void addToMatrix(Furniture f1, Furniture f2){
		Integer v = getFromMatrix(f1, f2);
		countMatrix.get(f1).put(f2, v+1);
	}
	
	public void addToMatrixBi(Furniture f1, Furniture f2){
		addToMatrix(f1, f2); if (!f1.equals(f2)) addToMatrix(f2, f1);
	}
	
	int ct = 0;
	
	int parseFile(String fileName, String furnId1, String furnId2,int perturbationFactor) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			File genDir = new File("generatedDatFiles/");
			if (!genDir.exists()) genDir.mkdirs();
			FileWriter outputFile=new FileWriter("generatedDatFiles/" + furnId2+"-"+furnId1+"");
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fileName));
			NodeList nList = document.getElementsByTagName("Room");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Element room = (Element) (nList.item(temp));
				NodeList nList1 = room.getElementsByTagName("Furniture");
				List<Furniture> furnitures = new ArrayList<>();
				for (int i = 0; i < nList1.getLength(); ++i) {
					Element furnEl = (Element) (nList1.item(i));
					Furniture f = new Furniture();
					f.width = Double.parseDouble(furnEl.getAttribute("width"));
					f.height = Double
							.parseDouble(furnEl.getAttribute("height"));
					f.depth = Double.parseDouble(furnEl.getAttribute("depth"));
					f.catalogId = ((Element) (furnEl
							.getElementsByTagName("CatalogId").item(0)))
							.getTextContent();
					f.position.setRot( Double.parseDouble(furnEl
							.getAttribute("rotation")));
					Element posEl = (Element) (furnEl
							.getElementsByTagName("Position").item(0));
					f.position.posX = Double.parseDouble(posEl
							.getAttribute("posX"));
					f.position.posY = Double.parseDouble(posEl
							.getAttribute("posY"));
					furnitures.add(f);
				}
				for (Furniture fur1:furnitures){
					if (fur1.catalogId.equals(furnId1)){
						for (Furniture fur2:furnitures){
							if (fur2.catalogId.equals(furnId2) && fur1!=fur2){
								Position relative=(fur2.getRelative(fur1));
								outputFile.write(relative.toString());
								for(int i=0;i<perturbationFactor;++i){
									outputFile.write((relative.disturb(sigma_cm,sigma_rad)).toString());
									//addToMatrixBi(fur1, fur2);
								}
								++ct;
							}
						}
					}
				}
			}
			outputFile.close();
			System.out.println(ct);
			return ct;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
}
