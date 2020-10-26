package methods;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Methods {
	
	/*Needleman/Wunsch算法求最长公共子序列长度，
	 *并从矩阵中回溯得到该最长公共子序列，
	 *将其反映到原字符串的下标中（以ArrayList<Integer>返回）*/
	public static Map<String, ArrayList<Integer>> compare(String str1, String str2) {
		
		int len1 = str1.length();
		int len2 = str2.length();
		int [][]ptr = new int[len1+1][len2+1];

		for(int i=0; i<len1+1; i++)
		    ptr[i][0] = 0;
	    for(int i=0; i<len2+1; i++)
	        ptr[0][i] = 0;
	    for(int i=0; i<len1; i++){
	        for(int j=0; j<len2; j++){
	        	if(str1.charAt(i)==str2.charAt(j))
	        		ptr[i+1][j+1] = ptr[i][j]+1;
	            else
	            	ptr[i+1][j+1] = Math.max(ptr[i+1][j],ptr[i][j+1]);
	        }
	    }
	    
	    ArrayList<Integer> res1 = new ArrayList<>();
	    ArrayList<Integer> res2 = new ArrayList<>();
	    
	    int i = len1, j = len2;
	    while(i>0 && j>0) {
    		if(str1.charAt(i-1)==str2.charAt(j-1)) {
    			res1.add(--i);
    			res2.add(--j);
    		}
    		else {
    			int maxNum = max(ptr[i-1][j-1], ptr[i-1][j], ptr[i][j-1]);
    			if(ptr[i-1][j-1]==maxNum){	//左上方单元格值最大
	    			i--;
	    			j--;
    			}
    			else if(ptr[i-1][j]==maxNum) 	//上方单元格值最大
    				i--;
    			else	//左方单元格值最大
    				j--;
    		}
	    }
	    

//	    for(int p=0,m=res1.size()-1; m>=0; m--,p++) {
//	    	if(p!=res1.get(m)) {
//	    		System.out.println("文本1中第一个不同之处出现在下标"+p+"的位置");
//	    		break;
//	    	}
//	    }
//	    for(int q=0,n=res2.size()-1; n>=0; n--,q++) {
//	    	if(q!=res2.get(n)) {
//	    		System.out.println("文本2中第一个不同之处出现在下标"+q+"的位置");
//	    		break;
//	    	}
//	    }
	    
	    Map<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
	    map.put("res1", res1);
	    map.put("res2", res2);
	    return map;
	}
	
	/*求三个数的最大值*/
	private static int max(int num1, int num2, int num3) {
		int max;
		max = Math.max(num1, num2);
		max = Math.max(max, num3);
		return max;
	}
	
}
