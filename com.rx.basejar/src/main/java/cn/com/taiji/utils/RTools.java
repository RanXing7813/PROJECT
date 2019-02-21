package cn.com.taiji.utils;

import com.google.gson.Gson;

import cn.com.taiji.utils.code.XEncoding;
import cn.com.taiji.utils.date.DateBetweenUtil;
import cn.com.taiji.utils.lang.XRegex;
import cn.com.taiji.utils.lang.XString;

public class RTools {

	public RTools() {
	}

	static {
		encoding = new XEncoding();
		string = new XString();
		regex = new XRegex();
		json = new Gson();
		dateTimeUtil = new DateBetweenUtil();
	}
	public static final XEncoding encoding;
	public static final XString string;
	public static final XRegex regex;
	public static final Gson json;
	public static final DateBetweenUtil dateTimeUtil;

}
