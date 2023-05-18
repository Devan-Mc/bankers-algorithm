import java.util.Arrays;

public class MatrixRow 
{
	private int[] myArray;
	int boolnum = 2;       //if need is true 1 if need is false 0 initalized 2
	public MatrixRow(int size) 
	{
        myArray = new int[size];
        for (int i = 0; i < size; i++) 
        {
            myArray[i] = -1;
        }
	}
	
	public void setElement(int index, int value) {
        myArray[index] = value;
    }
	
	public void setBoolnum(int n) {
		boolnum = n;
	}
	
	public int[] getArray() {
		return myArray;
	}
	
	public int getElement(int index) {
        return myArray[index];
    }
	public int getBoolnum() {
		return boolnum;
	}
	
}
