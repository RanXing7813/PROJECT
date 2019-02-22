package cn.com.taiji.util.tools;

import com.google.gson.Gson;

import cn.com.taiji.util.tools.code.XEncoding;
import cn.com.taiji.util.tools.date.DateUtil;
import cn.com.taiji.util.tools.file.ZipUtil;
import cn.com.taiji.util.tools.lang.StringTool;
import cn.com.taiji.util.tools.lang.XRegex;
import cn.com.taiji.util.tools.lang.XString;
import cn.com.taiji.util.tools.properties.LoadProperties;

public class RTools {

	public RTools() {
	}

	static {
		encoding = new XEncoding();
		string = new XString();
		regex = new XRegex();
		json = new Gson();
		dateTimeUtil = new DateUtil();
		properties = new LoadProperties();
		zip = new ZipUtil();
		stringTool = new StringTool();
	}
	public static final XEncoding encoding;
	public static final XString string;
	public static final XRegex regex;
	public static final Gson json;
	public static final DateUtil dateTimeUtil;
	public static final LoadProperties properties;
	public static final ZipUtil zip;
	public static final StringTool stringTool;

}
