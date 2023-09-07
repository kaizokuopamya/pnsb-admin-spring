package com.itl.pns.bean;

import java.util.List;
import java.util.Map;

public class DataSet {
	String setname;
	List<Map<String, String>> records;
	public DataSet() {}

	public DataSet(String setname) {
		this.setname = setname;
	}

	public String getParam() {
		return setname;
	}

	public void setParam(String setname) {
		this.setname = setname;
	}

	public List<Map<String, String>> getRecords() {
		return records;
	}

	public void setRecords(List<Map<String, String>> rslt) {
		this.records = rslt;
	}

	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	@Override
	public String toString() {
		return "DataSet [setname=" + setname + ", records=" + records + "]";
	}

	/*public static class DataSetInstanceCreator implements
			InstanceCreator<DataSet> {
		public DataSet createInstance(Type type) {
			return new DataSet();
		}
	}*/
}
