import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		try {
			BufferedReader catalogFile = new BufferedReader(new FileReader(
					"LivingRoomCatalog.txt"));
			
			File genDir = new File("generatedDatFiles/");
			if (!genDir.exists()) genDir.mkdirs();
			BufferedWriter countWriter = new BufferedWriter(new FileWriter("generatedDatFiles/" + "furnitureCount.txt"));
			
			List<String> furnCata = new ArrayList<>();
			String line = null;
			while ((line = catalogFile.readLine()) != null) {
				if(line.contains("#"))
					furnCata.add(line);
			}
			System.out.println(furnCata.size());
			for (int i=0;i<furnCata.size();++i){
				for (int j=0;j<furnCata.size();++j){
					GenerateDat gd = new GenerateDat();
					int ct = gd.parseFile("M14_merged.xml", furnCata.get(i),
							furnCata.get(j), 10);
					countWriter.write(furnCata.get(i) + "-" + furnCata.get(j) + "\n");
					countWriter.write(ct + "\n");
				}
			}
			countWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
