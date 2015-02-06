package core.crtlg;

import lib.udf.parse.CartilageBinaryParser;
import lib.udf.partition.CartilageHDFSSizePartitioner;
import core.conf.CartilageConf;
import core.index.MDIndex;
import core.index.key.MDIndexKey;
import core.pipeline.Dataflow;
import core.udf.CartilageUDF;
import core.utils.ConfUtils;

public class CartilageDataflow {
	
	public static char delimiter = '|';
	private String propertiesFile;

	public CartilageDataflow(){
		this.propertiesFile = "cartilage.conf";
	}
	
	public CartilageDataflow(String propertiesFile){
		this.propertiesFile = propertiesFile;
	}
	
	public CartilageDataflow(char delimiter, String propertiesFile){
		this.propertiesFile = propertiesFile;
		CartilageDataflow.delimiter = delimiter;
	}
	
	/**
	 * 
	 * The first pass of the indexing which builds the internal 
	 * nodes of the index tree. 
	 * 
	 *
	 */
	public static class FirstPass extends Dataflow{
		private static final long serialVersionUID = 1L;
		public FirstPass(CartilageConf conf, MDIndex mdIndex, MDIndexKey mdIndexKey) {
			super(conf);
			
			CartilageUDF parser = new CartilageBinaryParser(delimiter);
			CartilageUDF indexBuilder = new CartilageIndexBuilder(mdIndex, mdIndexKey);
			
			createStage("parse", null, parser);
			chainStage("parse", "indexBuild", null, indexBuilder);
			createBlock("indexBuilding", "parse");			
		}
		
		protected String getBlockHead(){
			return "indexBuilding";
		}
	}
	
	
	/**
	 * 
	 * The second pass of the indexing which maps each tuple 
	 * to a partition in the index. 
	 * 
	 *
	 */
	public static class SecondPass extends Dataflow{
		private static final long serialVersionUID = 1L;
		public SecondPass(CartilageConf conf, MDIndex mdIndex, MDIndexKey mdIndexKey) {
			super(conf);
			
			long MAX_FILE_SIZE = 63*1024*1024*1024*10;	// 630 GB
			
			CartilageUDF parser = new CartilageBinaryParser(delimiter);
			CartilageUDF partitionMapper = new CartilagePartitionMapper(mdIndex, mdIndexKey);
			CartilageUDF physicalPartitioner = new CartilageHDFSSizePartitioner(partitionMapper, MAX_FILE_SIZE, (short)3);
			
			createStage("parse", null, parser);
			chainStage("parse", "partitionMap", null, partitionMapper);
			chainStage("partitionMap", "physicalP", null, physicalPartitioner);
			createBlock("physicalPartitioning", "parse");
		}
		
		protected String getBlockHead(){
			return "physicalPartitioning";
		}
	}
	
	/**
	 * Run the two pass-indexing strategy.
	 * 
	 * @param mdIndex	-- the index to be used
	 * @param inputPath	-- the input dataset
	 * @param hdfsPath	-- the target location on hdfs
	 */
	public void run(MDIndex mdIndex, MDIndexKey mdIndexKey, String inputPath, String hdfsPath, int bucketSize){		
		CartilageConf conf = ConfUtils.create(propertiesFile, hdfsPath);
		mdIndex.initBuild(bucketSize);
		new FirstPass(conf, mdIndex, mdIndexKey).run(inputPath, 0);
		mdIndex.initProbe();
		//new SecondPass(conf, mdIndex, mdIndexKey).run(inputPath, 0);
	}
	

}
