package cn.com.taiji.utils.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	/**
	 * isMobiPhoneNum:(判断手机号码是否合法). <br/>
	 * @author zhangf
	 * @param numArray
	 * @return
	 * @since JDK 1.6
	 */
	private static boolean isMobiPhoneNum(String[] numArray){
		boolean flag = false;
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";  
        Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);   //CASE_INSENSITIVE  忽略大小写
        if(numArray != null && numArray.length >0){
        	for(String str:numArray){
        		if(str != null && !"".equals(str)){
        			Matcher m = p.matcher(str);
            		flag = m.matches();
            		if(flag == false){
            			break;
            		}
        		}
        	}
        }
        return flag;  
    }  
	
//	public static void main(String[] args) {
//		System.out.println(isMobiPhoneNum(new String[]{"151712686046","15171268604"} ));
//	}
	
	  private  ValidateUtils()
	  {
	         }
	        
	         private static class HolderClass
	         {
	                private final static ValidateUtils  instance = new ValidateUtils();
	         }
	        
	         public static ValidateUtils getInstance()
	         {
	                return HolderClass.instance;
	         }
	        
	         public  static void main(String args[])
	         {
	                ValidateUtils  s1, s2;
	                s1 = ValidateUtils.getInstance();
	                s2  = ValidateUtils.getInstance();
	                System.out.println(s1==s2);
	         }
	
}
