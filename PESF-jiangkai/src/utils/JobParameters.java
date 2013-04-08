package utils;

import java.util.HashMap;

public class JobParameters {
	
	protected HashMap<String,String> parametersMap = null;
	
	public String getParameter(String key)
	{
		return parametersMap.get(key);
	}
	
	public JobParameters(String[] args)
	{
		parametersMap = new HashMap<String,String>();
		setParameters();
	}
	
	private void setParameters() {
		parametersMap.put(CommonParameters.LABEL_DIC_FILE_PATH, CommonParameters.labelDicFilePath);
		parametersMap.put(CommonParameters.WORD_DIC_FILE_PATH, CommonParameters.wordDicFilePath);
		parametersMap.put(CommonParameters.TRAIN_DOCS_DIR_PATH, CommonParameters.trainDocsDirPath);
		parametersMap.put(CommonParameters.TEST_DOCS_DIR_PATH, CommonParameters.testDocsDirPath);
		parametersMap.put(CommonParameters.MODEL_FILE_PATH, CommonParameters.modelFilePath);
		parametersMap.put(CommonParameters.RESULT_DIR_PATH,CommonParameters.resultDirPath);
	}

	public static void main(String[] args)
	{
		JobParameters parameters = new JobParameters(args);
		parameters.toString();
	}
}
