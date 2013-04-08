package dataStructure;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class RandomAccessSparseVector implements Writable{
	
	/*
	 * The first component is the word's index in the vocabulary dictionary
	 * The second component is the word's frequency in this document  
	 */
	Map<Integer, Double> vector = null;
	
	public RandomAccessSparseVector()
	{
		vector = new HashMap<Integer,Double>();
	}
	
	public RandomAccessSparseVector(Map<Integer,Double> v)
	{
		vector = v;
	}
	
	public Map<Integer,Double> getVector()
	{
		return vector;
	}
	
	public RandomAccessSparseVector add(Map<Integer,Double> v)
	{
		Set<Integer> keySet = v.keySet();
		
		for(Integer key:keySet)
			if(vector.containsKey(key))
				vector.put(key, vector.get(key)+v.get(key));
			else
				vector.put(key, v.get(key));
		return this;
	}
	
	public RandomAccessSparseVector add(RandomAccessSparseVector v)
	{
		return add(v.getVector());
	}
	
	public void add(Integer verb,double weight)
	{
		Double w = vector.get(verb);
		if(w==null) w=0.0;
		vector.put(verb, weight+w);
	}
	
	public void put(Integer verb,double weight)
	{
		vector.put(verb, weight);
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		new IntWritable(vector.size()).write(out);
		for(Integer key:vector.keySet())
		{
			new IntWritable(key).write(out);
			new DoubleWritable(vector.get(key)).write(out);
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		int size = in.readInt();
	//	vector = new HashMap<Integer,Double>(size.get());
		
		IntWritable key = new IntWritable();
		DoubleWritable value = new DoubleWritable();
		
		for(int i=0;i<size;i++)
		{
			key.readFields(in);
			value.readFields(in);
			vector.put(key.get(), value.get());
		}
	}
	
	@Override
	public String toString()
	{
		String s = new String();
		for(int key:vector.keySet())
			s += " "+key+" "+vector.get(key);
		return s;
	}
	
	public void amendment(double amendAmount)
	{
		for(int key:vector.keySet())
			vector.put(key, vector.get(key)+amendAmount);
	}
	
}
