import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Bankers_Example {

	public static void main(String[] args) {
		//create scanner
		Scanner scan = new Scanner(System.in);
		
		//ask for number of processes
		System.out.println("input number of processes:");
		int p = scan.nextInt();
		
		//ask for number of resources 
		System.out.println("input number of resources:");
		int r = scan.nextInt();
		
		//generate the empty matrix for allocation
		MatrixRow[] allocationMatrix = new MatrixRow[p];
		
		for (int i = 0; i < allocationMatrix.length; i++) {
		    allocationMatrix[i] = new MatrixRow(r);
		}
		
		//printArrayMatrix(allocationMatrix);
		
		//generate empty matrix for max
		MatrixRow[] maxMatrix = new MatrixRow[p];
		
		for (int i = 0; i < maxMatrix.length; i++) {
		    maxMatrix[i] = new MatrixRow(r);
		}
		
		//printArrayMatrix(maxMatrix);
		
		
		
		//printArrayList(availableMatrix);
		
		
		//generate empty matrix for need
		MatrixRow[] needMatrix = new MatrixRow[p];
		
		for (int i = 0; i < needMatrix.length; i++) {
		    needMatrix[i] = new MatrixRow(r);
		}
		
		//printArrayMatrix(needMatrix);
		
		//fill allocation matrix
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				System.out.printf("enter a number for allocation matrix at element " +"%d %d:" + "\n", i, j);
				int userin = scan.nextInt();
				allocationMatrix[i].setElement(j, userin);
			}
		}
		//printArrayMatrix(allocationMatrix);
		
		//fill max matrix
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				System.out.printf("enter a number for max matrix at element " +"%d %d:" + "\n", i, j);
				int userin = scan.nextInt();
				maxMatrix[i].setElement(j, userin);
			}
		}
		
		//generate array list for available using ArrayListand set first row
				ArrayList<MatrixRow> availableMatrix = new ArrayList<MatrixRow>();
				availableMatrix.add(new MatrixRow(r));
				int curentavailableindex = 0;
				for(int i = 0; i < r; i++) {
					System.out.printf("enter a number for the first row of available matrix at element " +"%d:" + "\n", i);
					int userin = scan.nextInt();
					availableMatrix.get(0).setElement(i, userin);
				}
		
		//calculate and fill need. need = max - allocation 
		for(int i = 0; i < p; i++) {
			for(int j = 0; j < r; j++) {
				int element;
				element = maxMatrix[i].getElement(j) - allocationMatrix[i].getElement(j);
				needMatrix[i].setElement(j, element);
				
			}
		}
		//create process array and initialize each to -1
		int[] safeSequence = new int[p];
		int safeindex = 0;
		//safety int 0 = not safe 1= initalized safe until proven otherwise.
		int safety = 1;
		
		/*false check1 and 2 outside of loop initialize to 0 and -1(do while false check 2 != 0)if false check 1 = false check 2 break safety int = 0 else: false check 1 = false check 2 Initialize false check2 to zero.check if bool is set to 1 if yes skip to next row else:
		 *for each row of need check if available >= need if true: add row index to process table and calculate new available if false: update new available as current available set bool to 0 false_count2++ skip to next row at the end of while */
		int fcheck1 = 0;
		int fcheck2 = -1;
		do  {
			if(fcheck1 == fcheck2) {
				fcheck2 = -1;
				break;
			}
			else {
				fcheck1 = fcheck2;
				fcheck2 = 0;
				outerloop:
				for(int i = 0; i < p; i++) {
					if(needMatrix[i].getBoolnum() == 1) {
						continue;
					}
					for(int j = 0; j < r; j++) {
						if (availableMatrix.get(curentavailableindex).getElement(j) >= needMatrix[i].getElement(j)) {
							needMatrix[i].setBoolnum(1);
							if(j != r - 1) {
								continue;
							}
							
							needMatrix[i].setBoolnum(1);
							//System.out.printf("bool num for need matrix at index %d = %d", i, needMatrix[i].getBoolnum());
							safeSequence[safeindex] = i;
							safeindex++;
							//set new available matrix row
							availableMatrix.add(new MatrixRow(r));
							for(int k = 0; k < r; k++) {
								int temp = availableMatrix.get(curentavailableindex).getElement(k) + allocationMatrix[i].getElement(k);
								availableMatrix.get(curentavailableindex+1).setElement(k, temp);
							}
							curentavailableindex++;
						}
						else {
							needMatrix[i].setBoolnum(0);
							//System.out.printf("bool num for need matrix at index %d = %d", i, needMatrix[i].getBoolnum());
							fcheck2++;
							availableMatrix.add(new MatrixRow(r));
							for(int l = 0; l < r; l++) {
								availableMatrix.get(curentavailableindex+1).setElement(l, availableMatrix.get(curentavailableindex).getElement(l));
							}
							curentavailableindex++;
							break;							
							
						}
					}
				}
			}
		}while(fcheck2 != 0);
		
		
		//if fcheck2 != 0 not safe print need and available
		if(fcheck2 == 0) {
			System.out.println("Allocation Matrix:");
			printArrayMatrix(allocationMatrix);
			System.out.println("Max Matrix:");
			printArrayMatrix(maxMatrix);
			System.out.println("Available Array:");
			printArrayList(availableMatrix);
			System.out.println("Need Matrix:");
			printArrayMatrix(needMatrix);
			System.out.println("The system is in a safe state and the request is granted. the safe sequence is:");
			for(int s = 0; s < p; s++) {
				System.out.printf("p%d",safeSequence[s]);
				if(s != p - 1) {
					System.out.printf(", ");
				}
			}
			
			
		}
		
		else {
			System.out.println("Allocation Matrix:");
			printArrayMatrix(allocationMatrix);
			System.out.println("Max Matrix:");
			printArrayMatrix(maxMatrix);
			System.out.println("Available Array:");
			printArrayList(availableMatrix);
			System.out.println("Need Matrix:");
			printArrayMatrix(needMatrix);
			System.out.println("The system is not in a safe state and the request is denied.");
		}
		//else print safety message and print need and available
	}
	
	public static void printArrayMatrix(MatrixRow[] matrix) {
	    for (int i = 0; i < matrix.length; i++) {
	        System.out.println(Arrays.toString(matrix[i].getArray()));
	    }
	}
	
	public static void printArrayList(ArrayList<MatrixRow> matrix) {
	    for (int i = 0; i < matrix.size(); i++) {
	        System.out.println(Arrays.toString(matrix.get(i).getArray()));
	    }
	}
	
	//create function to print a matrix
	/*public static void printArrayMatrix(MatrixRow[] matrix) {
	    for (MatrixRow obj : matrix) {
	        System.out.println(Arrays.toString(obj.myArray));
	    }
	}*/
	//create object that stores an array and has an int for boolean purposes
	
	//create set function for object
	//create get function for object


}