package mapReduceTask;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.CommonParameters;

import dataStructure.RandomAccessSparseVector;
import dataStructure.TupleWritable;

public class PSMNBMapper extends Mapper<Object,Text,IntWritable,TupleWritable>{
	 
//	  private final OpenObjectIntHashMap<String> labelMap = new OpenObjectIntHashMap<String>();
//	  private final OpenObjectIntHashMap<String> wordMap = new OpenObjectIntHashMap<String>();
	  
	  double[] labelAmount = null;
	  double[] wordAmount = null;
	  
	  @Override
	  protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		  
	      StringTokenizer itr = new StringTokenizer(value.toString());
	      int labelId = Integer.parseInt(itr.nextToken());
	      
	      if(labelId == CommonParameters.UNLABELED_ID)
	    	  while(itr.hasMoreElements())
	    		  wordAmount[Integer.parseInt(itr.nextToken())]++;
	      else
	      {
    		  RandomAccessSparseVector document = new RandomAccessSparseVector();
    		  //System.out.println(labelId);
	    	  labelAmount[labelId]++;
	    	  while(itr.hasMoreElements())
	    	  {
	    		  int wordId = Integer.parseInt(itr.nextToken());
	    		  document.add(wordId, 1.0);
	    		  wordAmount[wordId]++;
	    	  }
	    	  context.write(new IntWritable(labelId), new TupleWritable(document));
	      }
	  }
	  
	  @Override
	  protected void setup(Context context) throws IOException, InterruptedException {
	    super.setup(context);
	    Configuration conf = context.getConfiguration();
	    
	    labelAmount = new double[Integer.parseInt(conf.get(CommonParameters.LABEL_AMOUNT))];
	   // System.out.println(labelAmount.length);
	    wordAmount = new double[Integer.parseInt(conf.get(CommonParameters.WORD_AMOUNT))];
	    
/*	    URI[] localFiles = DistributedCache.getCacheFiles(conf);
	    if (localFiles == null || localFiles.length < 2) {
	      throw new IllegalArgumentException("missing paths from the DistributedCache");
	    }
	    Path labelMapFile = new Path(localFiles[0].getPath());
	    // key is word value is id
	    for (Pair<Writable,IntWritable> record
	         : new SequenceFileIterable<Writable,IntWritable>(labelMapFile, true, conf)) {
	      labelMap.put(record.getFirst().toString(), record.getSecond().get());
	    }
	    
	    Path wordMapFile = new Path(localFiles[1].getPath());
	    for (Pair<Writable,IntWritable> record
		         : new SequenceFileIterable<Writable,IntWritable>(wordMapFile, true, conf)) {
	    	wordMap.put(record.getFirst().toString(), record.getSecond().get());
		  }
		  */
	  }
	  
	  @Override
	  protected void cleanup(Context context) throws IOException, InterruptedException {
		  context.write(new IntWritable(-1), new TupleWritable(labelAmount));
		  context.write(new IntWritable(-2), new TupleWritable(wordAmount));
	  }

}
