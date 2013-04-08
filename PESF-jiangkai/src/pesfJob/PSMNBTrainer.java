package pesfJob;

import sequence.SequenceWork;
import utils.CommonParameters;
import utils.JobParameters;
import dataPreprocess.Preprocessor;
import dataPreprocess.dataProduce.Process;
import dataStructure.model.MNBModel;
import dataStructure.model.Model;
import dataStructure.model.PSMNBModel;

public class PSMNBTrainer {
	
	JobParameters parameters = null;
	Preprocessor preprocessor = null;
	Model model = null;
	
	public PSMNBTrainer(String[] args)
	{

	}
	
	public void startJob()
	{
		//new Process().startProcess();
		//new Preprocessor().preprocess();
		//new PSMNBTrainDriver().train();
		//new SequenceWork().train();
		model = PSMNBModel.getModel(CommonParameters.modelFilePath);
		//model = MNBModel.getModel(CommonParameters.modelFilePath);
		new PSMNBTestDriver().test(model);
	}
	
	public static void main(String[] args)
	{
		new PSMNBTrainer(args).startJob();
	}
}
