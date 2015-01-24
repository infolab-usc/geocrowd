package test.geocrowd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.geocrowd.DatasetEnum;
import org.geocrowd.Geocrowd;
import org.geocrowd.OfflineMTC;
import org.geocrowd.common.Constants;
import org.geocrowd.common.utils.Utils;

public class OfflineMTCTest {

	public static void main(String[] args) {
		Geocrowd.DATA_SET = DatasetEnum.SKEWED;
		
		int[] budgets = { 24, 48, 96, 192, 384, 768, 1536, 3072 };	// foursquare
//		int[] budgets = { 20, 40, 80, 160, 320, 640, 1280, 2560 };	// 
		
		varying_budget(budgets, 5); 
		
		
//		double[] radii = {0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5 };
//		double[] radii = {0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5};
		double[] radii = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		varying_radius(radii, 48);
	}
	
	public static void varying_radius(double[] radii, int budget) {
		
		boolean[] isFixes = { true , false};
		
		Integer[][] coveredTasks = new Integer[radii.length][isFixes.length];
		Integer[][] assignedWorkers = new Integer[radii.length][isFixes.length];

		for (int r = 0; r < radii.length; r++)
			for (int fix = 0 ; fix < isFixes.length; fix++) {
				
				OfflineMTC offlineMTC = new OfflineMTC();
				offlineMTC.isFixed = isFixes[fix];
				offlineMTC.budget = budget;
				Constants.radius = radii[r];
				
				offlineMTC.reset();
			
				for (int i = 0; i < Constants.TIME_INSTANCE; i++) {
					offlineMTC
					.readTasks(Utils.datasetToTaskPath(Geocrowd.DATA_SET)
							+ i + ".txt");
					offlineMTC.readWorkers(Utils.datasetToWorkerPath(Geocrowd.DATA_SET) + i
							+ ".txt", i);
				}

				System.out.print("\nbudget = " + radii[r] + ", isFixed = " + isFixes[fix]);
				System.out.printf("\n%-10s \t %-10s \t %-10s \t %-10s \t %-10s", "TotalTask",
						"CoveredTask", "TotalWorker", "SelectedWorker", "W/T");
				
				offlineMTC.matchingTasksWorkers();

				offlineMTC.maxTaskCoverage();
				
				assignedWorkers[r][fix] = OfflineMTC.TotalAssignedWorkers;
				coveredTasks[r][fix] = OfflineMTC.TotalAssignedTasks;

				System.out
				.printf("\n%-10d \t %-10d \t %-10d \t %-10d \t %-10d\n",
						offlineMTC.TaskCount, OfflineMTC.TotalAssignedTasks,
						offlineMTC.budget, OfflineMTC.TotalAssignedWorkers,
						OfflineMTC.TotalAssignedTasks
								/ Math.max(1, OfflineMTC.TotalAssignedWorkers));
			}

		
		System.out.printf("\n%-20s \t", "");
		for (int j2 = 0; j2 < isFixes.length; j2++)
			System.out.printf("%-20s \t", isFixes[j2]);
		for (int b = 0; b < radii.length; b++) {
			System.out.printf("\n%-20f \t", radii[b]);
			for (int j2 = 0; j2 < isFixes.length; j2++)
				System.out.printf("%-20d \t", coveredTasks[b][j2]);
		}
	}

	public static void varying_budget(int[] budgets, double radius) {

		Constants.radius = radius;
		boolean[] isFixes = { true , false };
		
		
		Integer[][] coveredTasks = new Integer[budgets.length][isFixes.length];
		Integer[][] assignedWorkers = new Integer[budgets.length][isFixes.length];
		
		for (int b = 0; b < budgets.length; b++)
			for (int fix = 0 ; fix < isFixes.length; fix++) {
				
				OfflineMTC offlineMTC = new OfflineMTC();
				offlineMTC.isFixed = isFixes[fix];
				offlineMTC.budget = budgets[b];
				offlineMTC.reset();
			
				for (int i = 0; i < Constants.TIME_INSTANCE; i++) {
					offlineMTC
					.readTasks(Utils.datasetToTaskPath(Geocrowd.DATA_SET)
							+ i + ".txt");
					offlineMTC.readWorkers(Utils.datasetToWorkerPath(Geocrowd.DATA_SET) + i
							+ ".txt", i);
				}

				System.out.print("\nbudget = " + budgets[b] + ", isFixed = " + isFixes[fix]);
				System.out.printf("\n%-10s \t %-10s \t %-10s \t %-10s \t %-10s", "TotalTask",
						"CoveredTask", "TotalWorker", "SelectedWorker", "W/T");
				
				offlineMTC.matchingTasksWorkers();
				offlineMTC.maxTaskCoverage();
				
				assignedWorkers[b][fix] = OfflineMTC.TotalAssignedWorkers;
				coveredTasks[b][fix] = OfflineMTC.TotalAssignedTasks;

				System.out
				.printf("\n%-10d \t %-10d \t %-10d \t %-10d \t %-10d\n",
						offlineMTC.TaskCount, OfflineMTC.TotalAssignedTasks,
						offlineMTC.budget, OfflineMTC.TotalAssignedWorkers,
						OfflineMTC.TotalAssignedTasks
								/ Math.max(1, OfflineMTC.TotalAssignedWorkers));
			}

		
		System.out.printf("\n%-20s \t", "");
		for (int j2 = 0; j2 < isFixes.length; j2++)
			System.out.printf("%-20s \t", isFixes[j2]);
		for (int b = 0; b < budgets.length; b++) {
			System.out.printf("\n%-20d \t", budgets[b]);
			for (int j2 = 0; j2 < isFixes.length; j2++)
				System.out.printf("%-20d \t", coveredTasks[b][j2]);
		}
	}
}
