package dataStructure;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class TupleWritable implements Writable{
	
	//while type is true then the record is being used, otherwise the vector is 
	boolean type;
	
	double[] record = null;
	RandomAccessSparseVector vector = null;

	public TupleWritable()
	{
		super();
	}
	
	public TupleWritable(RandomAccessSparseVector v)
	{
		super();
		vector = v;
		type = false;
	}
	
	public double[] getRecord()
	{
		return record;
	}
	
	public RandomAccessSparseVector getVector()
	{
		return vector;
	}
	
	public TupleWritable(double[] d)
	{
		super();
		record = d;
		type = true;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		
		new BooleanWritable(type).write(out);
		
		if(type)
		{
			new IntWritable(record.length).write(out);
			for(double amount:record)
				new DoubleWritable(amount).write(out);
		}
		else
			vector.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		type = in.readBoolean();
		if(type)
		{
			record = new double[in.readInt()];
			for(int i=0;i<record.length;i++)
				record[i] = in.readDouble();
		}
		else
		{
			vector = new RandomAccessSparseVector();
			vector.readFields(in);
		}
	}
		
	@Override
	public String toString()
	{
		String s = new String();
		if(type)
			for(double value:record)
				s += " "+value;
		else
				s = vector.toString();
		return s;
	}
}
