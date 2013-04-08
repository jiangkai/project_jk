package utils;

public class CommonParameters {
	
	//parameters for preprocess
	public final static String labelIdTablePath = "/home/jiangkai/work/datasets/psmnb/preprocess/source/rcv1-v2.topics.qrels";
	public final static String tokenSourcePath = "/home/jiangkai/work/datasets/psmnb/preprocess/source/tokens";
	public final static String tokenOutputPath = "/home/jiangkai/work/datasets/psmnb/preprocess/preprocessed";
	
	
	
	public final static String tokensInputDirPath = "/home/jiangkai/work/datasets/psmnb/preprocess/preprocessed";
	public final static String labelsSoucrceFilePath = "/home/jiangkai/work/datasets/psmnb/preprocess/source/labelToChoose";
	public final static String labelsInputFilePath = "/home/jiangkai/work/datasets/psmnb/input/labels";

	
	public final static String tokensSourceDirPath = "/home/jiangkai/work/datasets/psmnb/input";
	public final static String tokensForTrainOutputDirPath = tokensSourceDirPath+"/trainDocs";
	public final static String toensForTestOutputDirPath = tokensSourceDirPath+"/testDocs";
	
	
	public final static String LABEL_DIC_FILE_PATH = "labelDicFilePath";
	public final static String WORD_DIC_FILE_PATH = "wordDicFilePath";
	
	//local
	public final static String dicDirPath = "/home/jiangkai/work/datasets/psmnb/output/dic";	
	public final static String labelDicFilePath = dicDirPath+"/labelDic";
	public final static String wordDicFilePath = dicDirPath+"/wordDic";
	
	//hadfs
	//public final static String dicDirPath = "/home/jiangkai/work/datasets/psmnb/output/dic";	
	//public final static String labelDicFilePath = dicDirPath+"/labelDic";
	//public final static String wordDicFilePath = dicDirPath+"/wordDic";
	
	
	public final static double train_test_facotor = 0.8;
	public final static double labeled_unlabeled_factor = 0.005;
	
	
	
	//parameters for psmnb
	public final static double alpha = 1.0;
	public final static String trainDocsDirPath = tokensForTrainOutputDirPath;
	public final static String testDocsDirPath = toensForTestOutputDirPath;
	
	public final static String TRAIN_DOCS_DIR_PATH = "trainDocsDirPath";
	public final static String TEST_DOCS_DIR_PATH = "testDocsDirPath";
	

	
	public final static String MODEL_FILE_PATH = "modelFilePath";
	public final static String RESULT_DIR_PATH = "resultDirPath";
	
	//Local
	public final static String modelFilePath = "/home/jiangkai/work/datasets/psmnb/output/model";
	public final static String resultDirPath = "/home/jiangkai/work/datasets/psmnb/output/result";

	//hdfs
	//public final static String modelFilePath = "/home/jiangkai/work/datasets/psmnb/output/model/model";
	//public final static String resultDirPath = "/home/jiangkai/work/datasets/psmnb/output/result";
	
	
	
	
	//some parameters
	public final static String HELP = "help";
	public final static String COMMAND_LINE = "commandLine";
	public final static String TRAIN_DATASETS = "trainDatasets";
	public final static String TEST_DATASETS = "testDatasets";
	public final static String TRAINED_MODEL = "trainedModel";
	public final static String LAPLACE_FACTORIAL = "laplaceFactorial";
	public final static String LABELED = "labeled";
	public final static String UNLABELED = "unlabeled";
	public final static int UNLABELED_ID = -1;
	public final static String LABEL_MAP = "labelMap";
	public final static String LABEL_AMOUNT = "labelAmount";
	public final static String WORD_AMOUNT = "wordAmount";
	
	
}
