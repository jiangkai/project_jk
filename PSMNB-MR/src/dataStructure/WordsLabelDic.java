package dataStructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import utils.CommonParameters;

public class WordsLabelDic {
	public static Map<String,Integer> word_id_dic = null;
	public static Map<String,Integer> label_id_dic = null;
	
	public static Map<String,Integer> getWordIdDic()
	{
		if(word_id_dic==null)
			word_id_dic=loadinWordIdDic();
		return word_id_dic;
	}
	
	public static Map<String,Integer> getLabelIdDic()
	{
		if(label_id_dic==null)
			label_id_dic=loadinLabelIdDic();
		return label_id_dic;
	}
	
	public static int getWordCount()
	{
		if(word_id_dic==null)
			word_id_dic=loadinWordIdDic();
		return word_id_dic.size();
	}
	
	public static int getLabelCount()
	{
		if(label_id_dic==null)
			label_id_dic=loadinLabelIdDic();
		return label_id_dic.size();
	}
	
	public static void setWordIdDic(Map<String,Integer> w)
	{
		word_id_dic = w;
	}
	
	public static void setLabelIdDic(Map<String,Integer> l)
	{
		label_id_dic = l;
	}
	
	public static Map<String,Integer> loadinWordIdDic()
	{
		try {
			Map<String,Integer> word_id_dic = new HashMap<String,Integer>();
			BufferedReader reader = new BufferedReader(new FileReader(CommonParameters.wordDicFilePath));
			String line = null;
			while((line = reader.readLine())!=null)
			{
				StringTokenizer itr = new StringTokenizer(line);
					word_id_dic.put(itr.nextToken(), Integer.parseInt(itr.nextToken()));
			}
			return word_id_dic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,Integer> loadinLabelIdDic()
	{
		try {
			Map<String,Integer> label_id_dic = new HashMap<String,Integer>();
			BufferedReader reader = new BufferedReader(new FileReader(CommonParameters.labelDicFilePath));
			String line = null;
			while((line = reader.readLine())!=null)
			{
				StringTokenizer itr = new StringTokenizer(line);
				label_id_dic.put(itr.nextToken(), Integer.parseInt(itr.nextToken()));
			}
			return label_id_dic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
