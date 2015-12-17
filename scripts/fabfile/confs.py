confs = {
        "local_tpch" : {
            'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
            'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
            'INPUTSDIR' : '/Users/ylu/Documents/workspace/mdindex/data/tpch',
            'TABLENAME' : 'tpch',
            'HDFSDIR' : '/user/ylu/tpch',
            'HOMEDIR' : '/Users/ylu',
            'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
            'SAMPLINGRATE' : '0.1',
            'DELIMITER': '|',
            'SCHEMALINEITEM': 'l_orderkey int, l_partkey int, l_suppkey int, l_linenumber int, l_quantity double, l_extendedprice double, l_discount double, l_tax double, l_returnflag string,  l_linestatus string, l_shipdate date, l_commitdate date, l_receiptdate date, l_shipinstruct string, l_shipmode string, l_comment string',
            'SCHEMAORDERS': 'o_orderkey int, o_custkey int, o_orderstatus string, o_totalprice double, o_orderdate date, o_orderpriority string, o_clerk string, o_shippriority int, o_comment string',
            'USER': 'ylu',
            'HOSTS': ['localhost'],
            'ROLEDEFS': { 'master': ['localhost'] }
            },
        "tpch": {
            'JAR' : '/home/mdindex/yilu/mdindex/build/libs/mdindex-all.jar',
            'CONF' : '/home/mdindex/yilu/mdindex/conf/tpch.properties',
            'TABLENAME' : 'tpch',
            'INPUTSDIR' : '/user/mdindex/yilu/tpch/',
            'HDFSDIR' : '/user/yilu/tpch/',
            'HOMEDIR' : '/home/mdindex/',
            'HADOOPBIN' : '/home/mdindex/hadoop-2.6.0/bin/',
            'SAMPLINGRATE' : '0.1',
            'DELIMITER': '|',
            'SCHEMALINEITEM': 'l_orderkey int, l_partkey int, l_suppkey int, l_linenumber int, l_quantity double, l_extendedprice double, l_discount double, l_tax double, l_returnflag string,  l_linestatus string, l_shipdate date, l_commitdate date, l_receiptdate date, l_shipinstruct string, l_shipmode string, l_comment string',
            'SCHEMAORDERS': 'o_orderkey int, o_custkey int, o_orderstatus string, o_totalprice double, o_orderdate date, o_orderpriority string, o_clerk string, o_shippriority int, o_comment string',
            'SCHEMACUSTOMER': 'c_custkey int, c_name string, c_address string, c_nationkey int, c_phone string, c_acctbal double, c_mktsegment string , c_comment string',
            'SCHEMASUPPLIER': 's_suppkey int, s_name string, s_address string, s_nationkey int, s_phone string, s_acctbal double, s_comment string',
            'USER' : 'mdindex',
            'HOSTS' : ['istc2.csail.mit.edu', 'istc5.csail.mit.edu', 'istc6.csail.mit.edu', 'istc7.csail.mit.edu', 'istc8.csail.mit.edu', 'istc9.csail.mit.edu', 'istc10.csail.mit.edu', 'istc11.csail.mit.edu', 'istc12.csail.mit.edu', 'istc13.csail.mit.edu'],
            'ROLEDEFS' : { 'master': ['istc2.csail.mit.edu'] },
            },
            "local_lineitem" : {
                'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
                'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
                'INPUTSDIR' : '/Users/ylu/Documents/workspace/tpchdata/lineitem',
                'TABLENAME' : 'lineitem',
                'HDFSDIR' : '/user/ylu/lineitem',
                'HOMEDIR' : '/Users/ylu',
                'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
                'SAMPLINGRATE' : '0.1',
                'DELIMITER': '|',
                'NUMBUCKETS' : '16',
                'NUMTUPLES': '6001215',
                'NUMFIELDS' : '15',
                'SCHEMA': 'l_orderkey int, l_partkey int, l_suppkey int, l_linenumber int, l_quantity double, l_extendedprice double, l_discount double, l_tax double, l_returnflag string,  l_linestatus string, l_shipdate date, l_commitdate date, l_receiptdate date, l_shipinstruct string, l_shipmode string',
                'USER': 'ylu',
                'HOSTS': ['localhost'],
                'ROLEDEFS': { 'master': ['localhost'] }
                },
            "local_orders" : {
                'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
                'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
                'INPUTSDIR' : '/Users/ylu/Documents/workspace/tpchdata/orders',
                'TABLENAME' : 'orders',
                'HDFSDIR' : '/user/ylu/orders',
                'HOMEDIR' : '/Users/ylu',
                'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
                'SAMPLINGRATE' : '0.1',
                'DELIMITER': '|',
                'NUMBUCKETS' : '16',
                'NUMTUPLES': '1500000',
                'NUMFIELDS' : '8',
                'SCHEMA': 'o_orderkey int, o_custkey int, o_orderstatus string, o_totalprice double, o_orderdate date, o_orderpriority string, o_clerk string, o_shippriority int',
                'USER': 'ylu',
                'HOSTS': ['localhost'],
                'ROLEDEFS': { 'master': ['localhost'] }
                },
            "local_part" : {
                'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
                'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
                'INPUTSDIR' : '/Users/ylu/Documents/workspace/tpchdata/part',
                'TABLENAME' : 'part',
                'HDFSDIR' : '/user/ylu/part',
                'HOMEDIR' : '/Users/ylu',
                'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
                'SAMPLINGRATE' : '0.1',
                'DELIMITER': '|',
                'NUMBUCKETS' : '16',
                'NUMTUPLES': '200000',
                'NUMFIELDS' : '8',
                'SCHEMA': 'p_partkey int, p_name string, p_mfgr string, p_brand string, p_type string, p_size int, p_container string, p_retailprice double',
                'USER': 'ylu',
                'HOSTS': ['localhost'],
                'ROLEDEFS': { 'master': ['localhost'] }
                },
             "local_supplier" : {
                'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
                'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
                'INPUTSDIR' : '/Users/ylu/Documents/workspace/tpchdata/supplier',
                'TABLENAME' : 'supplier',
                'HDFSDIR' : '/user/ylu/supplier',
                'HOMEDIR' : '/Users/ylu',
                'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
                'SAMPLINGRATE' : '0.1',
                'DELIMITER': '|',
                'NUMBUCKETS' : '16',
                'NUMTUPLES': '10000',
                'NUMFIELDS' : '7',
                'SCHEMA': 's_suppkey int, s_name string, s_address string, s_phone string, s_acctbal double, s_nation string, s_region string',
                'USER': 'ylu',
                'HOSTS': ['localhost'],
                'ROLEDEFS': { 'master': ['localhost'] }
                },
             "local_customer" : {
                'JAR' : '/Users/ylu/Documents/workspace/mdindex/build/libs/mdindex-all.jar',
                'CONF' : '/Users/ylu/Documents/workspace/mdindex/conf/ylu.properties',
                'INPUTSDIR' : '/Users/ylu/Documents/workspace/tpchdata/customer',
                'TABLENAME' : 'customer',
                'HDFSDIR' : '/user/ylu/customer',
                'HOMEDIR' : '/Users/ylu',
                'HADOOPBIN' : '/Users/ylu/Documents/workspace/hadoop-2.6.0/bin',
                'SAMPLINGRATE' : '0.1',
                'DELIMITER': '|',
                'NUMBUCKETS' : '16',
                'NUMTUPLES': '150000',
                'NUMFIELDS' : '8',
                'SCHEMA': 'c_custkey int, c_name string, c_address string, c_phone string, c_acctbal double, c_mktsegment string, c_nation string, c_region string',
                'USER': 'ylu',
                'HOSTS': ['localhost'],
                'ROLEDEFS': { 'master': ['localhost'] }
                }
        }
