package dataPreprocess;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocWriter {
	
	Map<Integer,BufferedWriter> labelsWriter;
	
	public DocWriter(List<Integer> labelIds,String outputPath)
	{
		try {
			labelsWriter = new HashMap<Integer,BufferedWriter>();
			for(int labelId:labelIds)
			{
				File file = new File(outputPath+"/"+labelId);
				if(file.exists())	file.delete();
				file.createNewFile();
				labelsWriter.put(labelId, new BufferedWriter(new FileWriter(file)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(int label,String docContent)
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

