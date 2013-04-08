package com.yuqiao.yqhbase.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.yuqiao.yqhbase.HBaseOperator;

public class YQHBaseOperator implements HBaseOperator {

	private String hTableName;
	private String hColFamName;
	private String hColName;

	private HBaseHelper helper;
	private Configuration conf;

	public YQHBaseOperator(HBaseHelper helper, Configuration conf) {
		InputStream is = ClassLoader.getSystemResourceAsStream("qyhbase.properties");

		Properties prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		hTableName = prop.getProperty("com.yuqiao.yqhbase.tablename");
		hColFamName = prop.getProperty("com.yuqiao.yqhbase.hColFamName");
		hColName = prop.getProperty("com.yuqiao.yqhbase.hColName");

		this.helper = helper;
		this.conf = conf;
	}

	public void write(String key, InputStream in) {
		try {			
			int count = 0;
			while (count == 0) {
				count = in.available();
			}
			byte[] b = new byte[count];
			int readCount = 0; // 已经成功读取的字节的个数
			while (readCount < count) {
				readCount += in.read(b, readCount, count - readCount);
			}
			write(key,b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void write(String key, byte[] value) {
		try {
			HTable table = new HTable(conf, hTableName);
			Put put = new Put(Bytes.toBytes(key));
		    put.add(Bytes.toBytes(hColFamName), Bytes.toBytes(hColName),value);
		    table.put(put);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public byte[] read(String key) {
		try {
			HTable table = new HTable(conf, hTableName);
			Get get = new Get(Bytes.toBytes(key));
			get.addColumn(Bytes.toBytes(hColFamName), Bytes.toBytes(hColName));
			Result result = table.get(get);
			byte[] val = result.getValue(Bytes.toBytes(hColFamName),
					Bytes.toBytes(hColName));
			return val;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String gethTableName() {
		return hTableName;
	}

	public void sethTableName(String hTableName) {
		this.hTableName = hTableName;
	}

	public HBaseHelper getHelper() {
		return helper;
	}

	public void setHelper(HBaseHelper helper) {
		this.helper = helper;
	}
}
