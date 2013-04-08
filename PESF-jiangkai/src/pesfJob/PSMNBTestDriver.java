package pesfJob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import utils.CommonParameters;
import utils.JobParameters;
import dataStructure.WordsLabelDic;
import dataStructure.model.Model;

public class PSMNBTestDriver {

	int correctAnswer;
	int wrongAnswer;
	
	
	public void test(Model model) {
		correctAnswer = 0;
		wrongAnswer = 0;
		
		String testDocsDirPath = CommonParameters.testDocsDirPath;
		try {
			File[] testDocFiles = new File(testDocsDirPath).listFiles();
			for(File testFile:testDocFiles)
			{
					BufferedReader reader = new BufferedReader(new FileReader(testFile));
					String document = null;
					while((document = reader.readLine())!=null)
					{
						StringTokenizer itr = new StringTokenizer(document);
						String label = itr.nextToken();
						double[] d = new double[WordsLabelDic.getWordCount()];
						while(itr.hasMoreElements())
							d[Integer.parseInt(itr.nextToken())] += 1.0;
						int predictLabel = model.getPredictedLabel(d);
						if(label.equals(""+predictLabel))
							correctAnswer++;
						else
							wrongAnswer++;
					}
			} 
			System.out.println("The correct answer count is "+correctAnswer);
			System.out.println("The wrong answer count is "+wrongAnswer);
			System.out.println("The result is "+(100.0*correctAnswer)/(correctAnswer+wrongAnswer)+"%");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
