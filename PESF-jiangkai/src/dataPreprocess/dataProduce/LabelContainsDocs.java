package dataPreprocess.dataProduce;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabelContainsDocs {
	
	int docCount;
	String label = null;
	ArrayList<Integer> docIds = null;
	
	
	public LabelContainsDocs(String s)
	{
		label = s;
		docCount = 0;
		docIds = new ArrayList<Integer>();
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public List<Integer> get()
	{
		return docIds;
	}
	
	public void add(Integer docId)
	{
		docIds.add(docId);
	}
	
	public String contains(int docId)
	{
		if(docIds.contains(docId))
		{
			docCount++;
			return label;
		}
		return null;
	}
	
	public void writerToFile(BufferedWriter writer) throws IOException
	{
		writer.write(label+" "+docCount+"\n");
	}
	
	@Override
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		for(int i:docIds)
			s.append(""+i+"\n");
		return s.toString();
	}
	
}
