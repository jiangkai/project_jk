package mapReduceTask;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import utils.CommonParameters;

import dataStructure.RandomAccessSparseVector;
import dataStructure.TupleWritable;

public class PSMNBReducer extends Reducer<IntWritable, TupleWritable, IntWritable, TupleWritable>
{
	@Override
	protected void reduce(IntWritable key, Iterable<TupleWritable> values, Context context)
	    throws IOException, InterruptedException {
		
		Configuration conf = context.getConfiguration();
		
		switch(key.get())
		{
		case -2:context.write(key, 
				countWordAmount(values,Integer.parseInt(conf.get(CommonParameters.WORD_AMOUNT))));break;
		case -1:context.write(key, 
				countLabelAmount(values,Integer.parseInt(conf.get(CommonParameters.LABEL_AMOUNT))));break;
		default:context.write(key, countWordAmountPerLabel(values));
		}
	}
	
	private TupleWritable countWordAmountPerLabel(Iterable<TupleWritable> values) {
		RandomAccessSparseVector record = new  RandomAccessSparseVector();
		for(TupleWritable tuple:values)
			record.add(tuple.getVector());
		
		return new TupleWritable(record);
	}

	private TupleWritable countLabelAmount(Iterable<TupleWritable> values,int labelAmount) {
		double[] docsAmountPerLabel = new double[labelAmount];
		for(TupleWritable tuple:values)
		{
			double[] da = tuple.getRecord();
			for(int i=0;i<labelAmount;i++)
				docsAmountPerLabel[i] += da[i];
		}
		return new TupleWritable(docsAmountPerLabel);
	}

	private TupleWritable countWordAmount(Iterable<TupleWritable> values,int wordAmount)
	{
		double[] wordAmountPerWord = new double[wordAmount];
		for(TupleWritable tuple:values)
		{
			double[] wa = tuple.getRecord();
			for(int i=0;i<wordAmount;i++)
				wordAmountPerWord[i] += wa[i];
		}
		return new TupleWritable(wordAmountPerWord);
	}
}
