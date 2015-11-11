package perf.benchmark;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import core.access.Predicate;
import core.access.Predicate.PREDTYPE;
import core.access.Query;
import core.access.Query.FilterQuery;
import core.access.spark.SparkQuery;
import core.globals.Globals;
import core.utils.ConfUtils;
import core.utils.HDFSUtils;
import core.utils.TypeUtils.SimpleDate;
import core.utils.TypeUtils.TYPE;

public class TPCHWorkload {
	public ConfUtils cfg;

	public String schemaString;

	int numFields;

	int method;

	int numQueries;

	Random rand;

	public void setUp() {
		cfg = new ConfUtils(BenchmarkSettings.conf);
		rand = new Random();

		// Making things more deterministic.
		rand.setSeed(0);

		Globals.load(cfg.getHDFS_WORKING_DIR() + "/info",
				HDFSUtils.getFSByHadoopHome(cfg.getHADOOP_HOME()));
		assert Globals.schema != null;

		// delete query history
		// Cleanup queries file - to remove past query workload
		HDFSUtils.deleteFile(HDFSUtils.getFSByHadoopHome(cfg.getHADOOP_HOME()),
				cfg.getHDFS_WORKING_DIR() + "/queries", false);
	}

	// Given the TPC-H query number returns the query.
	// Note that only 8 queries are encoded at the moment.
	// Query source: http://www.tpc.org/tpch/spec/tpch2.7.0.pdf p28.
	public FilterQuery getQuery(int queryNo) {
		String[] mktSegmentVals = new
			String[]{"AUTOMOBILE","BUILDING","FURNITURE","HOUSEHOLD","MACHINERY"};
		String[] regionNameVals = new
			String[]{"AFRICA", "AMERICA", "ASIA", "EUROPE", "MIDDLE EAST"};
		String[] partTypeVals = new
			String[]{"BRASS", "COPPER", "NICKEL", "STEEL", "TIN"};
		String[] shipModeVals = new
			String[]{"AIR", "FOB", "MAIL", "RAIL", "REG AIR", "SHIP", "TRUCK"};
		switch (queryNo) {
		case 3:
			int rand_3 = rand.nextInt(mktSegmentVals.length);
			String c_mktsegment = mktSegmentVals[rand_3];
			Calendar c = new GregorianCalendar();
			int dateOffset = (int) (rand.nextFloat() * (31 + 28 + 31));
			c.set(1995, Calendar.MARCH, 01);
			c.add(Calendar.DAY_OF_MONTH, dateOffset);
			SimpleDate d3 = new SimpleDate(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			Predicate p1_3 = new Predicate("c_mktsegment", TYPE.STRING, c_mktsegment, PREDTYPE.LEQ);
			Predicate p2_3 = new Predicate("o_orderdate", TYPE.DATE, d3, PREDTYPE.LT);
			Predicate p3_3 = new Predicate("l_shipdate", TYPE.DATE, d3, PREDTYPE.GT);
			if (rand_3 > 0) {
				String c_mktsegment_prev = mktSegmentVals[rand_3 - 1];
				Predicate p4_3 = new Predicate("c_mktsegment", TYPE.STRING, c_mktsegment_prev, PREDTYPE.GT);
				return new Query.FilterQuery(new Predicate[]{p1_3,p2_3,p3_3,p4_3});
			} else {
				return new Query.FilterQuery(new Predicate[]{p1_3,p2_3,p3_3});
			}
		case 5:
			int rand_5 = rand.nextInt(regionNameVals.length);
			String r_name_5 = regionNameVals[rand_5];
			int year_5 = 1993 + rand.nextInt(5);
			SimpleDate d5_1 = new SimpleDate(year_5, 1, 1);
			SimpleDate d5_2 = new SimpleDate(year_5 + 1, 1, 1);
			Predicate p1_5 = new Predicate("c_region", TYPE.STRING, r_name_5, PREDTYPE.LEQ);
			Predicate p2_5 = new Predicate("s_region", TYPE.STRING, r_name_5, PREDTYPE.LEQ);
			Predicate p3_5 = new Predicate("o_orderdate", TYPE.DATE, d5_1, PREDTYPE.GEQ);
			Predicate p4_5 = new Predicate("o_orderdate", TYPE.DATE, d5_2, PREDTYPE.LT);
			if (rand_5 > 0) {
				String r_name_prev_5 = regionNameVals[rand_5 - 1];
				Predicate p5_5 = new Predicate("c_region", TYPE.STRING, r_name_prev_5, PREDTYPE.GT);
				Predicate p6_5 = new Predicate("s_region", TYPE.STRING, r_name_prev_5, PREDTYPE.GT);
				return new Query.FilterQuery(new Predicate[]{p1_5, p2_5, p3_5, p4_5, p5_5, p6_5});
			} else {
				return new Query.FilterQuery(new Predicate[]{p1_5, p2_5, p3_5, p4_5});
			}
		case 6:
			int year_6 = 1993 + rand.nextInt(5);
			SimpleDate d6_1 = new SimpleDate(year_6, 1, 1);
			SimpleDate d6_2 = new SimpleDate(year_6 + 1, 1, 1);
			double discount = rand.nextDouble() * 0.07 + 0.02;
			double quantity = rand.nextInt(2) + 24.0;
			Predicate p1_6 = new Predicate("l_shipdate", TYPE.DATE, d6_1, PREDTYPE.GEQ);
			Predicate p2_6 = new Predicate("l_shipdate", TYPE.DATE, d6_2, PREDTYPE.LT);
			Predicate p3_6 = new Predicate("l_discount", TYPE.DOUBLE, discount - 0.01, PREDTYPE.GT);
			Predicate p4_6 = new Predicate("l_discount", TYPE.DOUBLE, discount + 0.01, PREDTYPE.LEQ);
			Predicate p5_6 = new Predicate("l_quantity", TYPE.DOUBLE, quantity, PREDTYPE.LEQ);
			return new Query.FilterQuery(new Predicate[]{p1_6, p2_6, p3_6, p4_6, p5_6});
		case 8:
			// Show that c_region gets introduced before s_region.
			int rand_8_1 = rand.nextInt(regionNameVals.length);
			String r_name_8 = regionNameVals[rand_8_1];
			SimpleDate d8_1 = new SimpleDate(1995, 1, 1);
			SimpleDate d8_2 = new SimpleDate(1996, 12, 31);
			String p_type_8 = partTypeVals[rand.nextInt(partTypeVals.length)];
			Predicate p1_8 = new Predicate("c_region", TYPE.STRING, r_name_8, PREDTYPE.LEQ);
			Predicate p2_8 = new Predicate("o_orderdate", TYPE.DATE, d8_1, PREDTYPE.GEQ);
			Predicate p3_8 = new Predicate("o_orderdate", TYPE.DATE, d8_2, PREDTYPE.LEQ);
			Predicate p4_8 = new Predicate("p_type", TYPE.STRING, p_type_8, PREDTYPE.EQ);
			if (rand_8_1 > 0) {
				String r_name_prev_8 = regionNameVals[rand_8_1 - 1];
				Predicate p5_8 = new Predicate("c_region", TYPE.STRING, r_name_prev_8, PREDTYPE.GT);
				return new Query.FilterQuery(new Predicate[]{p1_8, p2_8, p3_8, p4_8, p5_8});
			} else {
				return new Query.FilterQuery(new Predicate[]{p1_8, p2_8, p3_8, p4_8});
			}
		case 10:
			String l_returnflag_10 = "R";
			String l_returnflag_prev_10 = "N";
			int year_10 = 1993;
			int monthOffset = rand.nextInt(24);
			SimpleDate d10_1 = new SimpleDate(year_10 + monthOffset/12, monthOffset%12 + 1, 1);
			monthOffset = monthOffset + 3;
			SimpleDate d10_2 = new SimpleDate(year_10 + monthOffset/12, monthOffset%12 + 1, 1);
			Predicate p1_10 = new Predicate("l_returnflag", TYPE.STRING, l_returnflag_10, PREDTYPE.LEQ);
			Predicate p4_10 = new Predicate("l_returnflag", TYPE.STRING, l_returnflag_prev_10, PREDTYPE.GT);
			Predicate p2_10 = new Predicate("o_orderdate", TYPE.DATE, d10_1, PREDTYPE.GEQ);
			Predicate p3_10 = new Predicate("o_orderdate", TYPE.DATE, d10_2, PREDTYPE.LT);
			return new Query.FilterQuery(new Predicate[]{p1_10, p2_10, p3_10, p4_10});
		case 12:
			// TODO: We don't handle attrA < attrB style predicate.
			// TODO: We also don't handle IN queries directly.
			int rand_12 = rand.nextInt(shipModeVals.length);
			String shipmode_12 = shipModeVals[rand_12];
			int year_12 = 1993 + rand.nextInt(5);
			SimpleDate d12_1 = new SimpleDate(year_12, 1, 1);
			SimpleDate d12_2 = new SimpleDate(year_12 + 1, 1, 1);
			Predicate p1_12 = new Predicate("l_shipmode", TYPE.STRING, shipmode_12, PREDTYPE.LEQ);
			Predicate p2_12 = new Predicate("l_receiptdate", TYPE.DATE, d12_1, PREDTYPE.GEQ);
			Predicate p3_12 = new Predicate("l_receiptdate", TYPE.DATE, d12_2, PREDTYPE.LT);
			if (rand_12 > 0) {
				String shipmode_prev_12 = shipModeVals[rand_12 - 1];
				Predicate p4_12 = new Predicate("l_shipmode", TYPE.STRING, shipmode_prev_12, PREDTYPE.GT);
				return new Query.FilterQuery(new Predicate[]{p1_12, p2_12, p3_12, p4_12});
			} else {
				return new Query.FilterQuery(new Predicate[]{p1_12, p2_12, p3_12});
			}
		case 14:
			int year_14 = 1993;
			int monthOffset_14 = rand.nextInt(60);
			SimpleDate d14_1 = new SimpleDate(year_14 + monthOffset_14/12, monthOffset_14%12 + 1, 1);
			monthOffset_14 += 1;
			SimpleDate d14_2 = new SimpleDate(year_14 + monthOffset_14/12, monthOffset_14%12 + 1, 1);
			Predicate p1_14 = new Predicate("o_orderdate", TYPE.DATE, d14_1, PREDTYPE.GEQ);
			Predicate p2_14 = new Predicate("o_orderdate", TYPE.DATE, d14_2, PREDTYPE.LT);
			return new Query.FilterQuery(new Predicate[]{p1_14, p2_14});
		case 19:
			// TODO: Add to paper how to handle OR. We can treat it as separate set of filters.
			// TODO: Consider adding choices for p_container and l_shipmode.
			String brand_19 = "Brand#" + (rand.nextInt(5) + 1) + "" + (rand.nextInt(5) + 1);
			String shipInstruct_19 = "DELIVER IN PERSON";
			double quantity_19 = rand.nextInt(10) + 1;
			Predicate p1_19 = new Predicate("l_shipinstruct", TYPE.STRING, shipInstruct_19, PREDTYPE.EQ);
			Predicate p2_19 = new Predicate("p_brand", TYPE.STRING, brand_19, PREDTYPE.EQ);
			Predicate p3_19 = new Predicate("p_container", TYPE.STRING, "SM CASE",PREDTYPE.EQ);
			Predicate p4_19 = new Predicate("l_quantity", TYPE.DOUBLE, quantity_19, PREDTYPE.GT);
			quantity_19 += 10;
			Predicate p5_19 = new Predicate("l_quantity", TYPE.DOUBLE, quantity_19, PREDTYPE.LEQ);
			Predicate p6_19 = new Predicate("p_size", TYPE.INT, 1, PREDTYPE.GEQ);
			Predicate p7_19 = new Predicate("p_size", TYPE.INT, 5, PREDTYPE.LEQ);
			Predicate p8_19 = new Predicate("l_shipmode", TYPE.STRING, "AIR", PREDTYPE.LEQ);
			return new Query.FilterQuery(new Predicate[]{p1_19, p2_19, p3_19, p4_19, p5_19, p6_19, p7_19, p8_19});
		default:
			return null;
		}
	}

	public List<FilterQuery> generateWorkload(int numQueries) {
		ArrayList<FilterQuery> queries = new ArrayList<FilterQuery>();
		int[] queryNums = new int[] { 3, 5, 6, 8, 10, 12, 14, 19 };

		for (int i = 0; i < numQueries; i++) {
			int qNo = queryNums[rand.nextInt(queryNums.length)];
			FilterQuery q = getQuery(qNo);
			queries.add(q);
		}

		return queries;
	}

	public void runWorkload(int numQueries) {
		long start, end;
		SparkQuery sq = new SparkQuery(cfg);
		List<FilterQuery> queries = generateWorkload(numQueries);
		System.out.println("INFO: Workload " + numQueries);
		for (FilterQuery q: queries) {
			System.out.println("INFO: Query:" + q.toString());
		}

		for (FilterQuery q : queries) {
			start = System.currentTimeMillis();
			long result = sq.createAdaptRDD(cfg.getHDFS_WORKING_DIR(),
					q.getPredicates()).count();
			end = System.currentTimeMillis();
			System.out.println("RES: Time Taken: " + (end - start) + 
					"; Result: " + result);
		}
	}

	public void loadSettings(String[] args) {
		int counter = 0;
		while (counter < args.length) {
			switch (args[counter]) {
			case "--schema":
				schemaString = args[counter + 1];
				counter += 2;
				break;
			case "--numFields":
				numFields = Integer.parseInt(args[counter + 1]);
				counter += 2;
				break;
			case "--method":
				method = Integer.parseInt(args[counter + 1]);
				counter += 2;
				break;
			case "--numQueries":
				numQueries = Integer.parseInt(args[counter+1]);
				counter += 2;
				break;
			default:
				// Something we don't use
				counter += 2;
				break;
			}
		}
	}

	public static void main(String[] args) {
		BenchmarkSettings.loadSettings(args);
		BenchmarkSettings.printSettings();

		TPCHWorkload t = new TPCHWorkload();
		t.loadSettings(args);
		t.setUp();

		switch (t.method) {
		case 1:
			System.out.println("Num Queries: " + t.numQueries);
			t.runWorkload(t.numQueries);
			break;
		default:
			break;
		}
	}
}
