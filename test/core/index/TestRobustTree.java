package core.index;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import core.index.key.CartilageIndexKey;
import core.index.robusttree.RobustTree;

public class TestRobustTree extends TestCase {

	private RobustTree t;
	private int bucketSize;

	private CartilageIndexKey key;
	//private CartilageBinaryRecord r;

	List<String> tuples;
	String datafile;

	@Override
	public void setUp(){
		datafile = Settings.tpchPath + "lineitem.tbl";

		tuples = new ArrayList<String>();
		tuples.add("1|1552|93|1|17|24710.35|0.04|0.02|N|O|1996-03-13|1996-02-12|1996-03-22|DELIVER IN PERSON|TRUCK|egular courts above the");
		tuples.add("1|674|75|2|36|56688.12|0.09|0.06|N|O|1996-04-12|1996-02-28|1996-04-20|TAKE BACK RETURN|MAIL|ly final dependencies: slyly bold ");
		tuples.add("1|637|38|3|8|12301.04|0.10|0.02|N|O|1996-01-29|1996-03-05|1996-01-31|TAKE BACK RETURN|REG AIR|riously. regular, express dep");
		tuples.add("1|22|48|4|28|25816.56|0.09|0.06|N|O|1996-04-21|1996-03-30|1996-05-16|NONE|AIR|lites. fluffily even de");
		tuples.add("1|241|23|5|24|27389.76|0.10|0.04|N|O|1996-03-30|1996-03-14|1996-04-01|NONE|FOB| pending foxes. slyly re");
		tuples.add("1|157|10|6|32|33828.80|0.07|0.02|N|O|1996-01-30|1996-02-07|1996-02-03|DELIVER IN PERSON|MAIL|arefully slyly ex");
		tuples.add("2|1062|33|1|38|36596.28|0.00|0.05|N|O|1997-01-28|1997-01-14|1997-02-02|TAKE BACK RETURN|RAIL|ven requests. deposits breach a");
		tuples.add("3|43|19|1|45|42436.80|0.06|0.00|R|F|1994-02-02|1994-01-04|1994-02-23|NONE|AIR|ongside of the furiously brave acco");
		tuples.add("3|191|70|2|49|53468.31|0.10|0.00|R|F|1993-11-09|1993-12-20|1993-11-24|TAKE BACK RETURN|RAIL| unusual accounts. eve");
		tuples.add("3|1285|60|3|27|32029.56|0.06|0.07|A|F|1994-01-16|1993-11-22|1994-01-23|DELIVER IN PERSON|SHIP|nal foxes wake. ");

		t = new RobustTree(16);
		key = new CartilageIndexKey('|', new int[]{0,1,2,3,4,5});
		bucketSize = 1024*1024*10;	// 10mb

		//r = new CartilageBinaryRecord('|');
	}

//	public void testInitBuild(){
//		t.initBuild(bucketSize);
//		assert(true);
//	}
//
//	public void testInsert(){
//		key.setBytes(tuples.get(0).getBytes());
//		t.insert(key);
//		assert(true);
//	}

//	public void testInitProbe(){
//		for (int i=0; i<tuples.size(); i++) {
//			key.setBytes(tuples.get(i).getBytes());
//			t.insert(key);
//		}
//
//		t.initProbe();
//		t.printTree();
//	}

	public void testInitProbeBulk(){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(datafile));
			String line;
			while ((line = br.readLine()) != null) {
				key.setBytes(line.getBytes());
				t.insert(key);
			}
			br.close();
			t.initProbe();
			t.printTree();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public void testGetBucketId(){
//	}

	@Override
	public void tearDown(){
	}
}