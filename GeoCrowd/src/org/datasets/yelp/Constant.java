/*******************************************************************************
* @ Year 2013
* This is the source code of the following papers. 
* 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
* 
* 
* Please contact the author Hien To, ubriela@gmail.com if you have any question.
*
* Contributors:
* Hien To - initial implementation
*******************************************************************************/
package org.datasets.yelp;

// TODO: Auto-generated Javadoc
/**
 * The Class constant.
 * 
 * @author dkh
 */
public class Constant {
	
	/** The tasks_loc. */
	public static String tasks_loc = "dataset/real/yelp/yelp_task.dat";
	
	/** The workers_loc. */
	public static String workers_loc = "dataset/real/yelp/yelp.dat";
	
	/** The business. */
	public static String business = "dataset/real/yelp/business.json";
	
	/** The review. */
	public static String review = "dataset/real/yelp/review.json";
	
	/** The user. */
	public static String user = "dataset/real/yelp/user.json";
	
	/** The entropy. */
	public static String entropy = "dataset/real/yelp/yelp_entropy.txt";
	
	/** The boundary. */
	public static String boundary = "dataset/real/yelp/yelp_boundary.txt";
	
	/** The curtail_review. */
	public static String curtail_review = "dataset/real/yelp/test.json";
	
	/** The Save statistic. */
	public static String SaveStatistic = "dataset/real/yelp/yelp_statistic.txt";

	/** The Split worker by time. */
	public static String SplitWorkerByTime = "dataset/real/yelp/worker/yelp_workers";

	/** The Save worker. */
	public static String SaveWorker = "dataset/real/yelp/worker/yelp_workers";
	
	/** The suffix. */
	public static String suffix = ".txt";
	
	/** The Save task. */
	public static String SaveTask = "dataset/real/yelp/task/yelp_tasks";

	/** The Max review. */
	public static int MaxReview = 20;
	
	/** The real resolution. */
	public static double realResolution = 0.001;
	
	/** The Worker per file. */
	public static int WorkerPerFile = 10000;
	
	/** The Task per file. */
	public static int TaskPerFile = 500;

}
