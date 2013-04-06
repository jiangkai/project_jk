package com.yuqiao.yqhbase;

import java.io.InputStream;

public interface HBaseOperator {
	public void write(String key,InputStream is);
	public void write(String key,byte[] value);
	public byte[] read(String key);
}
