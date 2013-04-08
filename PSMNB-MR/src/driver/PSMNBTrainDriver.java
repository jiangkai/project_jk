package driver;

import java.io.IOException;

import mapReduceTask.PSMNBMapper;
import mapReduceTask.PSMNBReducer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import utils.CommonParameters;
import utils.Utils;

import dataStructure.TupleWritable;
import dataStructure.WordsLabelDic;

public class PSMNBTrainDriver{

	public static void main(String[] args) {
	    try {
			double start = System.currentTimeMillis();
			Configuration conf = new Configuration();
			
			conf.setInt(CommonParameters.WORD_AMOUNT, WordsLabelDic.getWordCount());
			conf.setInt(CommonParameters.LABEL_AMOUNT, WordsLabelDic.getLabelCount());
			
			Job job = new Job(conf, "Parallel semi-supervised multinominal naive bayes");
		    job.setJarByClass(PSMNBTrainDriver.class);
		    
		    job.setMapperClass(PSMNBMapper.class);
		    job.setCombinerClass(PSMNBReducer.class);
		    job.setReducerClass(PSMNBReducer.class);
		    
		    job.setOutputKeyClass(IntWritable.class);
		    job.setOutputValueClass(TupleWritable.class);
			
		    FileInputFormat.addInputPath(job,new Path(CommonParameters.trainDocsDirPath));
		    FileOutputFormat.setOutputPath(job,new Path(CommonParameters.modelFilePath));
		    Utils.removeFile(CommonParameters.modelFilePath);
		    job.waitForCompletion(true);
			double timeCost = System.currentTimeMillis()-start;
			System.out.println("The map-reduce' cost time is "+timeCost);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
