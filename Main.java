import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		Matrix matrix=new Matrix(new float[][]{
			{1,2,3},
			{2,2,1},
			{3,4,3}
		});
		//TODO 分母为0的问题待解决。
		System.out.println(matrix);
		System.out.println(matrix.inverse());
	}
}
