package core.index;
import java.util.List;

import core.access.Predicate;
import core.index.key.MDIndexKey;


/**
 *
 * An API for implementing multi-dimensional index, e.g. R-Tree, K-d Tree, etc.
 *
 */

public interface MDIndex {

	/*
	 * Placeholder class for the index leaves.
	 *
	 */

	public final static class Bucket{
		int bucketId;
		int numTuples;

		public static int maxBucketId = 0;

		public Bucket() {
			bucketId = maxBucketId;
			numTuples = -1;
			maxBucketId += 1;
		}

		public void setNumTuples(int t) {
			numTuples = t;
		}

		public int getNumTuples() {
			return numTuples;
		}

		public int getBucketId() {
			return bucketId;
		}

		// Needed for unmarshall
		public void setBucketId(int id) {
			this.bucketId = id;
		}
	}


	public MDIndex clone() throws CloneNotSupportedException;


	/*
	 *
	 * The Build phase of the index
	 *
	 */


	/**
	 * Initialize the index with the maximum number of buckets.
	 *
	 * @param numBuckets;
	 */
	public void initBuild(int numBuckets);


	/**
	 * Insert an entry into the index structure (internal nodes).
	 * This method does not load the actual data into the index.
	 *
	 * @param key
	 */
	public void insert(MDIndexKey key);


	/**
	 * Bulk load the index structure, without loading the actual data.
	 *
	 * TODO: this method does not really fit in our project because it
	 * assumes data to be in memory.
	 *
	 * @param keys
	 */
	public void bulkLoad(MDIndexKey[] keys);



	/*
	 *
	 * The Probe phase of the index
	 *
	 */


	public void initProbe();

	/**
	 * Get the bucket id, for a given key, from an existing index.
	 *
	 * @param key
	 * @return
	 */
	public Object getBucketId(MDIndexKey key);


//	/**
//	 * Point query.
//	 *
//	 * @param key
//	 * @return the bucket containing the key.
//	 */
//	public Bucket search(MDIndexKey key);
//
//
//	/**
//	 * Range query.
//	 *
//	 * @param low
//	 * @param high
//	 * @return the set of buckets containing the given range.
//	 */
//	public List<Bucket> range(MDIndexKey low, MDIndexKey high);

	public List<Bucket> search(Predicate[] predicates);

	/*
	 *
	 * Other Utility methods.
	 *
	 */

	/**
	 * Serialize the index into a byte array.
	 *
	 * @return serialized index.
	 */
	public byte[] marshall();


	/**
	 * Deserialize the index from a byte array.
	 *
	 * @param bytes
	 */
	public void unmarshall(byte[] bytes);

}
