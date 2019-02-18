package cn.people.service.view.obj;

import cn.people.entity.SplitContent;

public class SplitContentComparator implements java.util.Comparator{
	/**
	 * @param args
	 */
	public int compare(Object obj1,Object obj2){
		int a = ((SplitContent)obj1).getSubindex();
		int b =  ((SplitContent)obj2).getSubindex();
		if(a>b){
			return -1;
		}else if(a==b){
			return 0;
		}else{
			return 1;
		}
	}
}
