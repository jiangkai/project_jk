package com.yuqiao.yqhbase;

import java.io.InputStream;

public interface HBaseOperator {
	public void write(String key,InputStream is);
	public byte[] read(String key);
}
