package org.geocrowd.maxcover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.geocrowd.datasets.params.GeocrowdConstants;

/**
 * Change the stopping condition of max cover problem.
 * 
 * The algorithm stop when the gain (i.e., the number of newly covered tasks) of
 * adding a worker is less than a threshold.
 * 
 * @author ubriela
 *
 */
public class MaxCoverAdaptS extends MaxCoverSpatial {

	public double lambda; // algorithm stops when gain is less than lambda
	public int deltaBudget;	// > 0 means over-utilization; < 0 otherwise
	public double eps;

	public MaxCoverAdaptS(ArrayList container, Integer currentTI) {
		super(container, currentTI);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Greedy algorithm.
	 * 
	 * @return the assigned workers
	 */
	@Override
	public HashSet<Integer> maxCover() {
		HashMap<Integer, HashMap<Integer, Integer>> S = (HashMap<Integer, HashMap<Integer, Integer>>) mapSets
				.clone();

		/**
		 * Q is the universe of tasks
		 */
		HashSet<Integer> Q = (HashSet<Integer>) universe.clone();
//		assignedTaskSet = new HashSet<Integer>();

		/**
		 * Run until either the gain of adding one worker is less than a
		 * threshold or no more tasks to cover
		 */
		while (assignWorkers.size() < budget && !Q.isEmpty()) {
			int bestWorkerIndex = 0;
			double smallestAvgWeight = 10000000;
			double maxUncoveredUtility = 0.0;
			
			/**
			 * Iterate all workers, find the one which covers maximum number of
			 * uncovered tasks
			 */
			for (int k : S.keySet()) {
				HashMap<Integer, Integer> s = S.get(k);
				WeightGain wg = weight(k, s, currentTimeInstance,
						assignedTaskSet);
				if (wg.weight < smallestAvgWeight) {
					smallestAvgWeight = wg.weight;
					bestWorkerIndex = k;
					maxUncoveredUtility = wg.gain;
				}
			}

			// Check gain threshold
			double deltaGain = maxNoUncoveredTasks - lambda;
			if (currentTimeInstance != GeocrowdConstants.TIME_INSTANCE - 1) {
				if (deltaGain <= 0 && deltaBudget <= 0) {
					break;	// stop allocating budget
				} else if (deltaGain <= 0 && deltaBudget > 0) {
					Random r = new Random();
					r.setSeed(System.nanoTime());
					
					if (r.nextFloat() < eps)
						break;
				} else if (deltaGain > 0 && deltaBudget <= 0) {
					Random r = new Random();	
					r.setSeed(System.nanoTime());
						
					if (r.nextFloat() < eps)
						break;
				}
				
				// otherwise (deltaGain > 0 && deltaBudget < 0) --> increase budget by 1
			}

			deltaBudget -= 1;
			gain = maxNoUncoveredTasks;
			
			assignWorkers.add(bestWorkerIndex);
			assignedUtility += maxUncoveredUtility;
			HashMap<Integer, Integer> taskSet = S.get(bestWorkerIndex);
			S.remove(bestWorkerIndex);
			Q.removeAll(taskSet.keySet());

			/**
			 * compute average time to assign tasks in taskSet
			 */
			for (Integer taskidx : taskSet.keySet())
				if (!assignedTaskSet.contains(taskidx)) {

					averageDelayTime += currentTimeInstance
							- (taskSet.get(taskidx) - GeocrowdConstants.MAX_TASK_DURATION)
							+ 1;
					assignedTaskSet.add(taskidx);
				}
		}
		assignedTasks = assignedTaskSet.size();
//		System.out.println("#Task assigned: " + assignedTasks);

		return assignWorkers;
	}
}
