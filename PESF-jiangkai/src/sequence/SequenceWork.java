package sequence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import dataStructure.WordsLabelDic;
import utils.CommonParameters;
import utils.Utils;

public class SequenceWork {
	
	double timeCost;
	
	String trainDocsDirPath = null;
	String modelFilePath = null;
	
	int[] wordAmountPerWord = null;
	int[] docAmountPerLabel = null;
	int[][] labeledWordAmountGivenLabel = null;
	
	int wordAmount;
	int labelAmount;
	
	public SequenceWork()
	{
		timeCost = -1;
		
		trainDocsDirPath = CommonParameters.trainDocsDirPath;
		modelFilePath = CommonParameters.modelFilePath;
		
		wordAmount = WordsLabelDic.getWordCount();
		labelAmount = WordsLabelDic.getLabelCount();
		
		wordAmountPerWord = new int[wordAmount];
		docAmountPerLabel = new int[labelAmount];
		labeledWordAmountGivenLabel = new int[labelAmount][wordAmount];
	}
	
	public void train()
	{
		double start = System.currentTimeMillis();
		loadIn();
		writeOut();
		timeCost = System.currentTimeMillis()-start;
		System.out.println("The sequencework's cost time is "+timeCost);
	}

	public double getCostTime()
	{
		return timeCost;
	}
	
	private void writeOut() {
		try {
			Utils.removeFile(modelFilePath);
			new File(modelFilePath).mkdir();
			BufferedWriter writer = new BufferedWriter(new FileWriter(Utils.createNewFile(modelFilePath+"/model")));
			String line = new String("-2");
			for(int i=0;i<wordAmount;i++)
				line += " "+wordAmountPerWord[i];
			writer.write(line+"\n");
			
			line = new String("-1");
			for(int i=0;i<labelAmount;i++)
				line += " "+docAmountPerLabel[i];
			writer.write(line+"\n");
			
			for(int i=0;i<labelAmount;i++)
			{
				line = new String(i+"");
				for(int j=0;j<wordAmount;j++)
					if(labeledWordAmountGivenLabel[i][j]!=0)
						line += " "+j+" "+labeledWordAmountGivenLabel[i][j];
				writer.write(line+"\n");
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadIn() {
		try{
			File[] trainDocsFile = new File(trainDocsDirPath).listFiles();
			for(File file:trainDocsFile)
			{
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String  line = null;
				while((line=reader.readLine())!=null)
				{
					StringTokenizer itr = new StringTokenizer(line);
					int labelIndex = Integer.parseInt(itr.nextToken());
					if(labelIndex==-1)
						while(itr.hasMoreElements())
							wordAmountPerWord[Integer.parseInt(itr.nextToken())]+=1.0;
					else
					{
						docAmountPerLabel[labelIndex] += 1.0;
						while(itr.hasMoreElements())
						{
							int wordIndex = Integer.parseInt(itr.nextToken());
							wordAmountPerWord[wordIndex]+=1.0;
							labeledWordAmountGivenLabel[labelIndex][wordIndex]+=1.0;
						}
					}
				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
