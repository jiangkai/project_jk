package dataStructure.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import dataStructure.WordsLabelDic;


public class MNBModel implements Model{
	
	//parameters to build the model
	int labelAmount;
	int uniqueWordsAmount;
	double laplace = 1.0;
	
	double[] labeledDocsPerClass = null;						//use labeled datasets only
	double[][] labeledWordAmountOfGivenClass = null;			//use labeled datasets only
	
	//parameters to compute the probability that a document belongs to a class
	double[] labeledClassProbability = null;					//use labeled datasets only
	double[] wordsAmountPerClass = null;
	double[][] wordProbabilityOfGivenClass = new double[labelAmount][uniqueWordsAmount];
	
	public MNBModel()
	{
		labelAmount = WordsLabelDic.getLabelCount();
		uniqueWordsAmount = WordsLabelDic.getWordCount();
		
		labeledDocsPerClass = new double[labelAmount];		
		labeledWordAmountOfGivenClass = new double[labelAmount][uniqueWordsAmount];	
		
		labeledClassProbability = new double[labelAmount];
		wordsAmountPerClass = new double[labelAmount];
		wordProbabilityOfGivenClass = new double[labelAmount][uniqueWordsAmount];
		
		for(int i=0;i<labelAmount;i++)
		{
			wordsAmountPerClass[i] += laplace*uniqueWordsAmount;
			for(int j=0;j<uniqueWordsAmount;j++)
				labeledWordAmountOfGivenClass[i][j] = laplace;
		}
	}
	
	//compute
	private void computeModel()
	{
		//compute the probability of every class P(c)
		int labeledDocsAmount = 0;
		for(int i=0;i < labelAmount; i++)
			labeledDocsAmount += labeledDocsPerClass[i];
		for(int i=0;i < labelAmount; i++)
			labeledClassProbability[i] = Math.log(labeledDocsPerClass[i]/labeledDocsAmount);
		
		//compute the probability that a word in c is wi P(wi|c)
 	
		//compute the probability that a word in class c that is w P(w|c)
		for(int w=0; w < uniqueWordsAmount; w++)
		{			 
			for(int c=0;c<labelAmount;c++)
			{
				 wordProbabilityOfGivenClass[c][w] = laplace+labeledWordAmountOfGivenClass[c][w];;
				 wordsAmountPerClass[c] += labeledWordAmountOfGivenClass[c][w];;
			}	 
		}	
		
		for(int c=0;c < labelAmount;c++)
			for(int w=0;w < uniqueWordsAmount;w++)
				wordProbabilityOfGivenClass[c][w] = Math.log(wordProbabilityOfGivenClass[c][w]/wordsAmountPerClass[c]);
	}
	
	
	//predict
	public double[] getClassesProbabilityGivenDocument(double[] document)
	{
		double[] pro = new double[labelAmount];
		for(int i=0;i<labelAmount;i++)
			pro[i] = getClassProabilityGivenDocument(document,i);
		return pro;
	}
	
	public double getClassProabilityGivenDocument(double[] document,int  label)
	{
		double pro = labeledClassProbability[label];
		for(int i=0;i<uniqueWordsAmount;i++)
			pro += document[i]*wordProbabilityOfGivenClass[label][i];
		return pro;
	}
	
	@Override
	public int getPredictedLabel(double[] document)
	{
		double[] probability = getClassesProbabilityGivenDocument(document);
		
		int label = 0;
		double maxPro = probability[0];
		for(int i=1;i<probability.length;i++)
			if(maxPro<probability[i])
			{
				label = i;
				maxPro = probability[i];
			}
		return label;
	}
	
	public static MNBModel getModel(String modelDirPath)
	{
		MNBModel model = new MNBModel();
		loadInModel(model,modelDirPath);
		model.computeModel();
		return model;
	}
	
	//loadin
	private static void loadInModel(MNBModel model,String modelDirPath) {
		File modelDirFile = new File(modelDirPath);
		File[] modelFiles = modelDirFile.listFiles();
		
		for(File file:modelFiles)
			loadIn(file,model.labeledDocsPerClass,model.labeledWordAmountOfGivenClass);
		
		model.computeModel();
	}

	private static void loadIn(File file, double[] labeledDocsPerClass,double[][] labeledWordAmountOfGivenClass) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line=reader.readLine())!=null)
			{
				StringTokenizer itr = new StringTokenizer(line);
				String label = itr.nextToken();
				switch(Integer.parseInt(label))
				{
				case -1: getAmount(itr,labeledDocsPerClass);break;
				case -2:break;
				default: configLwagc(labeledWordAmountOfGivenClass,Integer.parseInt(label),itr);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void configLwagc(double[][] labeledWordAmountOfGivenClass,int label, StringTokenizer itr) {
		while(itr.hasMoreElements())
			labeledWordAmountOfGivenClass[label][Integer.parseInt(itr.nextToken())] += Double.parseDouble(itr.nextToken());
	}

	private static void getAmount(StringTokenizer itr,double[] array) {
		int i = 0;
		while(itr.hasMoreElements())
			array[i++] += Double.parseDouble(itr.nextToken());
	}
}
