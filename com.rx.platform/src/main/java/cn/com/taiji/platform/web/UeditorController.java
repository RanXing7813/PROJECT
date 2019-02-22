package cn.com.taiji.platform.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.util.tools.RTools;

@Controller
@RequestMapping(value = "/ueditor")
public class UeditorController {

	@RequestMapping(value = "ueditor", method = RequestMethod.GET)
	public String ueditor(Model model, HttpServletRequest request) {
		return "platform/test/ueditor";
	}

	@RequestMapping(value = "initParams")
	@ResponseBody
	public String initParams(Model model, HttpServletRequest request) {
		// System.out.println(jsonPath);
		String rootPath = request.getRealPath("/");
		// String configContent = new ActionEnter(request, rootPath).exec();
		// System.out.println("-----------");
		// System.out.println(configContent);
		String configContent = "";
		return RTools.json.toJson(configContent);
	}

	@RequestMapping(value = "attachment", method = RequestMethod.GET)
	public String attachment(Model model, HttpServletRequest request) {
		return "platform/test/attachment";
	}

	private String readFile(String path) throws IOException {
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
			BufferedReader bfReader = new BufferedReader(reader);

			String tmpContent = null;

			while ((tmpContent = bfReader.readLine()) != null) {
				builder.append(tmpContent);
			}

			bfReader.close();
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}

		return filter(builder.toString());
	}

	private String filter(String input) {
		return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
	}

	private String getFilePath() {
		return String.valueOf(this.getClass().getClassLoader().getResource("json/uedirot_config.json"))
				.replace("file:/", "");
	}

	public static void main(String[] args) {
		// 04 08 13 25 30 31
		// int[] y = new int[] { 4, 8, 13, 25, 30, 31 };
		// 09 10 11 12 18 23 07
		int[] y = new int[] { 9, 10, 11, 12, 18, 23 };

		int sum = y[0] + y[1] + y[2] + y[3] + y[4] + y[5];
		System.out.println("sum:" + sum);
		for (int i = 0; i < y.length; i++) {
			System.out.println("取整：" + (sum - y[i]) / y[i] + "  取余 ：" + (sum - y[i]) % y[i]);
		}
	}
}
