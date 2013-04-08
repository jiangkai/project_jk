package dataPreprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import utils.CommonParameters;
import utils.Utils;


public class Preprocessor {
	
	Map<String,Integer> label_labelId_Dic = null;
	Map<Integer,Integer> labelId_docCount_Dic = null;
	
	Map<String,Integer> wordDic = null;
	
	int wordCount;
	
	public Preprocessor()
	{
		label_labelId_Dic = new HashMap<String,Integer>();
		labelId_docCount_Dic = new HashMap<Integer,Integer>();
		
		wordDic =  new HashMap<String,Integer>();
		wordCount = 0;
	}
	
	public void preprocess()
	{
		loadInLabels(CommonParameters.labelsInputFilePath);
		handleDocInputs(CommonParameters.tokensForTrainOutputDirPath,CommonParameters.toensForTestOutputDirPath,
				CommonParameters.tokensInputDirPath,CommonParameters.train_test_facotor*CommonParameters.labeled_unlabeled_factor,
				CommonParameters.train_test_facotor*(1-CommonParameters.labeled_unlabeled_factor),(1-CommonParameters.train_test_facotor));
		writeLabelDic(CommonParameters.labelDicFilePath);
		writeWordDic(CommonParameters.wordDicFilePath);
	}

	private void writeLabelDic(String labelDicfilepath) {
		try {
		File labelDicFile = Utils.createNewFile(labelDicfilepath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(labelDicFile));
		for(String label:label_labelId_Dic.keySet())
				writer.write(label+" "+label_labelId_Dic.get(label)+"\n");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeWordDic(String wordDicFilePath) {
		try {
			Utils.createNewFile(wordDicFilePath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(wordDicFilePath));
			for(String word:wordDic.keySet())
				writer.write(word+" "+wordDic.get(word)+"\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleDocInputs(String tokensfortrainOutputpath,
			String toensfortestOutputpath, String tokensSourcepath, double labeledFactor,
			double unlabeledFactor, double testFactor) {
		try {
			File tokensSourceDir = new File(tokensSourcepath);
			File[] tokensFiles = tokensSourceDir.listFiles();
			for(File tokensFile:tokensFiles)
			{
				String label = tokensFile.getName().toString();
				
				int labelId = label_labelId_Dic.get(label);
				int docCount = labelId_docCount_Dic.get(labelId);
				
				int labeledDocCount = (int)(docCount*labeledFactor);
				int unlabeledDocCount = (int)(docCount*unlabeledFactor);
				int testDocCount = (int)(docCount*testFactor);
						
				BufferedReader reader = new BufferedReader(new FileReader(tokensFile));
				
				String trainedFileWithLabelFilePath = tokensfortrainOutputpath+"/"+labelId;
				String testFileWithLabelFilePath = toensfortestOutputpath+"/"+labelId;
				
				File trainedFileWithLabelFile = Utils.createNewFile(trainedFileWithLabelFilePath);
				BufferedWriter writer = new BufferedWriter(new FileWriter(trainedFileWithLabelFile));
				writeDocs(writer,labelId,reader,labeledDocCount);
				writeDocs(writer,-1,reader,unlabeledDocCount);
				writer.close();
				
				File testFileWithLabelFile = Utils.createNewFile(testFileWithLabelFilePath);
				writer = new BufferedWriter(new FileWriter(testFileWithLabelFile));

				writeDocs(writer,labelId,reader,testDocCount);
				writer.close();
				
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeDocs(BufferedWriter writer, int labelId,
			BufferedReader reader, int docCount) {
		try {
			int lineCount = 0;
			String line = null;
			while((line=reader.readLine())!=null && lineCount<docCount  )
			{
				StringTokenizer itr = new StringTokenizer(line);
				itr.nextToken();
				
				String newLine = ""+labelId;
				String verb;
				while(itr.hasMoreElements())
				{
					verb=itr.nextToken();
					if(!wordDic.containsKey(verb))
						wordDic.put(verb, wordCount++);
					newLine += " "+wordDic.get(verb);
				}
				lineCount++;
				writer.write(newLine+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadInLabels(String labelsinputpath) {
		  try {
			int labelCount = 0;
			BufferedReader reader = new BufferedReader(new FileReader(labelsinputpath));
			
			String labelLine;
			String label;
			StringTokenizer itr = null;
			while((labelLine=reader.readLine())!=null)
			{
				itr = new StringTokenizer(labelLine);
				label = itr.nextToken();
				label_labelId_Dic.put(label, labelCount);
				labelId_docCount_Dic.put(labelCount, Integer.parseInt(itr.nextToken()));
				labelCount++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new Preprocessor().preprocess();
	}
	
	/**
	   * Write the list of labels into a map file
	   */
	  public static Path createMapFile(Iterable<String> tokens,
	                                        Configuration conf,
	                                        Path labelMapPathBase) throws IOException {
	    FileSystem fs = FileSystem.get(labelMapPathBase.toUri(), conf);
	    Path mapPath = new Path(labelMapPathBase,CommonParameters.LABEL_MAP);
	    
	    SequenceFile.Writer dictWriter = new SequenceFile.Writer(fs, conf, mapPath, Text.class, IntWritable.class);
	    int i = 0;
	    for (String token : tokens) {
	      Writable key = new Text(token);
	      dictWriter.append(key, new IntWritable(i++));
	    }
	    dictWriter.close();
	    return mapPath;
	  }
}
