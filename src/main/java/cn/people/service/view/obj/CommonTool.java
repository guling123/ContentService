package cn.people.service.view.obj;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class CommonTool {
  public static int getInt(Object str) {
	return getInt(str, 0);
  }

  public static int getInt(Object str, int pre) {
	try {
	  String temp = StringTool.getString(str);
	  return Integer.parseInt(pre > 0 ? temp.substring(pre) : temp);
	} catch (Exception e) {
	  // TODO: handle exception
	}
	return 0;
  }

  public static long getLong(Object str) {
	return getLong(str, 0);
  }

  // pre 前缀字符数
  public static long getLong(Object str, int pre) {
	try {
	  String temp = StringTool.getString(str);
	  return Long.parseLong(pre > 0 ? temp.substring(pre) : temp);
	} catch (Exception e) {
	  // TODO: handle exception
	}
	return 0;
  }

  // 判断是否为boolean
  public static boolean getBoolean(Object obj) {
	if (obj != null) {
		if (obj instanceof Boolean) {
			return (Boolean)obj;
		}else	  if (obj instanceof String) {
		if (((String) obj).equalsIgnoreCase("Y") || ((String) obj).equalsIgnoreCase("TRUE") || ((String) obj).equalsIgnoreCase("YES")) {
		  return true;
		}
	  } else if (obj instanceof Integer) {
		if (((Integer) obj) > 0)
		  return true;
	  } else if (obj instanceof Short) {
		if (((Short) obj) > 0)
		  return true;
	  }
	}
	return false;
  }

  public static List getList(List list) {
	if (null == list || 0 >= list.size()) {
	  return null;
	}
	return list;
  }
  
  public static<T> T getList0(List<T> list) {
  	if (null == list || 0 >= list.size()) {
  		return null;
  	}
  	return list.get(0);
  }
  
	/**
	 * 一对多map
	 * @param ids
	 * @param contentIds
	 * @return
	 */
	public static Map<Integer, List<Integer>> getO2M(int[] keys, int[] values) {
		Map<Integer, List<Integer>> idMap = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < keys.length; i++) {
			Integer id = keys[i];
			List<Integer> idsList = idMap.containsKey(id) ?  idMap.get(id) : new ArrayList<Integer>();
			idsList.add(values[i]);
			if (idsList.size()==1) {
				idMap.put(id, idsList);
			}
		}
		return idMap;
	}
	
	/**
	 * 判断是否 完全匹配分隔符中的key
	 * @param text 内容
	 * @param key 匹配值
	 * @param split 分隔符
	 * @return
	 */
	public static Boolean contains(String text, String key, String split) {
		if (null == text || "".equals(text.trim()) || null == key || "".equals(key.trim())) {
			return false;
		}
		String regex = "(^|"+split+")\\s*"+key+"\\s*("+split+"|$)";
		Pattern pattern = Pattern.compile(regex);  
    Matcher matcher = pattern.matcher(text); 
		return matcher.find();
	}
	
	// 根据nodeID判断节点是否在ParentString
	public static boolean isSubNode(String parentString, int nodeID) {
		boolean result = false;
		if (parentString != null && nodeID > 0) {
			result = parentString.startsWith(nodeID + ",") || parentString.indexOf("," + nodeID + ",") > 0;
		}
		return result;
	}
	
	
	/**
	 * 按JSONObject内指定的字段排序
	 * @param list
	 * @param name JSONObject中用来排序的属性名
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void sortArrayListASC(ArrayList<JSONObject> list,final String name){
		Collections.sort(list, new Comparator() {
			Comparator cmp = Collator.getInstance(java.util.Locale.CHINA); 
			public int compare(Object o1, Object o2) {
				String obj1=null;
				String obj2=null;
				try {
					obj1 = ((JSONObject) o1).getString(name);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					obj2 = ((JSONObject) o2).getString(name);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return cmp.compare(obj1==null?"":obj1,obj2==null?"":obj2);
			}
		});
	}
	
	/**
	 * 服务端：密码校验
	 * @param password
	 * @return
	 */
	public static  boolean checkpwd(String password){
		if(password.matches("\\w+")){
			//密码强度验证,此处要重写
			String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
			return  password.length()>=8 && password.length() <=16  && password.matches(regex);
		}else{
			return false;
		}
	}
	
	public static void main(String[] agrs){
		//System.out.println(isSubNode("67404,95955,94899,",95955));
	}
}
