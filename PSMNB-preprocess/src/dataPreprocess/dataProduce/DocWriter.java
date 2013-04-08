package dataPreprocess.dataProduce;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocWriter {
	
	Map<String,BufferedWriter> labelsWriter;
	
	public DocWriter(List<String> labels)
	{
		try {
			labelsWriter = new HashMap<String,BufferedWriter>();
			for(String label:labels)
			{
				File file = new File(Parameters.tokenOutputPath+"/"+label);
				if(file.exists())	file.delete();
				file.createNewFile();
				labelsWriter.put(label, new BufferedWriter(new FileWriter(file)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String label,String docContent)
	{
		try {
			labelsWriter.get(label).write(docContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			for(BufferedWriter writer:labelsWriter.values())
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
