package dataPreprocess.dataProduce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Process {
	
	ArrayList<String> labels = null;
	ArrayList<LabelContainsDocs> labelToDocId = null;
	int docCount;
	
	public void startProcess()
	{
		loadInLabels();
		findDocIds();
		findDocContents();
		outputLabels();
	}
	
	private void outputLabels() {
		try {
			File labelFile = new File(Parameters.labelSourcePath);
			if(labelFile.exists()) labelFile.delete();
			labelFile.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(labelFile));
			
			for(LabelContainsDocs element:labelToDocId)
				element.writerToFile(writer);
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findDocContents() {

 		try {
 			DocWriter docWriter = new DocWriter(labels);
 			
 		 	File dir = new File(Parameters.tokenSourcePath);
 		 	File[] files = dir.listFiles();
 		 	for(File file:files)
 		 	{
				BufferedReader in = new BufferedReader(new FileReader(file));
				String line;
				while((line=in.readLine())!=null)
				{
					StringTokenizer itr = new StringTokenizer(line);
					itr.nextToken();
					String docId = itr.nextToken();
					String label = null;
					String newLine = null;
					in.readLine();
					if((label=containsDocId(Integer.parseInt(docId)))!=null)
					{
						String docContent = label+"-"+docId;
						while((newLine=in.readLine())!=null && !newLine.equals(""))
							docContent += " "+newLine;
						docWriter.write(label, docContent+"\n");
					}
					else
						while((newLine=in.readLine())!=null && !newLine.equals(""));
					if(newLine==null) break;
				}
 		 	}
 		 	docWriter.close();
 		}
 		catch (FileNotFoundException e) {
		e.printStackTrace();
	 	} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Process()
	{
		labels = new ArrayList<String>();
		labelToDocId = new ArrayList<LabelContainsDocs>();
		docCount = 0;
	}
	
	private void findDocIds() {
		try {
			for(String label:labels)
			{
				LabelContainsDocs labelContainsDocs = new LabelContainsDocs(label);
				labelToDocId.add(labelContainsDocs);
			}
			
			
			BufferedReader in = new BufferedReader(new FileReader(Parameters.labelIdTablePath));
			String line = null;
			while((line=in.readLine())!=null)
			{
				StringTokenizer itr = new StringTokenizer(line);
				String label = (String)itr.nextElement();
				
				for(LabelContainsDocs lcd:labelToDocId)
					if(label.equals(lcd.getLabel()))
					{
						docCount++;
						lcd.add(Integer.parseInt((String)itr.nextElement()));
						break;
					}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void print(List list) {
		for(Object element:list)
			System.out.println(element);
		System.out.println(docCount);
	}

	public void loadInLabels()
	{
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(Parameters.labelSourcePath));
			String label = null;
			while((label=in.readLine())!=null)
				labels.add(label);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String containsDocId(int docId)
	{
		String label;
		for(LabelContainsDocs element:labelToDocId)
			if((label=element.contains(docId))!=null)
				return label;
		return null;

	}
	
	public static void main(String[] args)
	{
		new Process().startProcess();
	}
}
