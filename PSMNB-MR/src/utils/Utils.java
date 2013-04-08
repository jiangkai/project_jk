package utils;

import java.io.File;
import java.io.IOException;

public class Utils {
	
	public static void normalize(double[] array)
	{
		double sum = 0;
		for(double item:array)
			sum += item;
		for(int i=0;i<array.length;i++)
			array[i] /= sum;
	}
	
	public static File createNewFile(File file)
	{
		try {
			if(file.exists()) removeFile(file);
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static File createNewFile(String path)
	{
		return createNewFile(new File(path));
	}
	
	public static void removeFile(File file)
	{
		if(file.isFile()) file.delete();
		else{
			File[] files = file.listFiles();
			for(File f:files)
				removeFile(f);
			file.delete();
		}
	}
	
	public static void removeFile(String path)
	{
		removeFile(new File(path));
	}
	
	public static void main(String[] args)
	{
		removeFile(new File("/home/jiangkai/work/datasets/temp/removeFile"));
	}
}
