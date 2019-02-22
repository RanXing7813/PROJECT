package cn.com.taiji.platform.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.taiji.platform.entity.UploadFile;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.UploadFileService;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/file")
public class UploadController {

	@Inject
	UploadFileService uploadFileService;

	@Inject
	LogService logService;

	@RequestMapping(value = "upload_module")
	public String uploadModule(Model model, HttpServletRequest request) {

		LogBean begin = logService.begin(request.getParameterMap());

		String fkid = request.getParameter("fkid");

		String suffix = RTools.properties.getValue("suffix");
		String acceptMime = RTools.properties.getValue("acceptMime");
		String file_number = RTools.properties.getValue("file_number");
		Long maxUploadSize = Long.valueOf(RTools.properties.getValue("maxUploadSize"));// 插件中为文件大小限制单位为：KB
		model.addAttribute("exts", suffix.replaceAll(",", "|"));
		model.addAttribute("suffix", suffix);
		model.addAttribute("acceptMime", acceptMime);
		model.addAttribute("file_number", file_number);
		model.addAttribute("maxUploadSize", maxUploadSize);
		model.addAttribute("size", maxUploadSize / (1024 * 1024));
		model.addAttribute("fkid", fkid);
		logService.success(begin, "附件上传组件！", request);
		return "platform/module/upload_file";
	}

	@RequestMapping(value = "upload_test")
	public String upload(Model model, HttpServletRequest request) {
		String suffix = RTools.properties.getValue("suffix");
		String acceptMime = RTools.properties.getValue("acceptMime");
		String file_number = RTools.properties.getValue("file_number");
		Long maxUploadSize = Long.valueOf(RTools.properties.getValue("maxUploadSize"));// 插件中为文件大小限制单位为：KB
		model.addAttribute("exts", suffix.replaceAll(",", "|"));
		model.addAttribute("suffix", suffix);
		model.addAttribute("acceptMime", acceptMime);
		model.addAttribute("file_number", file_number);
		model.addAttribute("maxUploadSize", maxUploadSize);
		return "platform/test/upload_test";
	}

	@RequestMapping(value = "delfilebyfid")
	@ResponseBody
	public String delFileByFid(Model model, HttpServletRequest request, @RequestParam("fid") String fid) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String[] ids = fid.split(";");
			uploadFileService.delFile(ids);
			map.put("code", "0");
			map.put("msg", "删除成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// e.printStackTrace();
			map.put("code", "1");
			map.put("msg", "删除失败！");
			map.put("info", e.getMessage());
			logService.fail(begin, map, request);
		} finally {
			return RTools.json.toJson(map);
		}
	}

	@RequestMapping(value = "mult_upload")
	@ResponseBody
	public String uploadFiles(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam("fkid") String fkid) {

		// System.out.println(RTools.json.toJson(file));
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();

		try {

			// 最大文件限制
			Long maxUploadSize = Long.valueOf(RTools.properties.getValue("maxUploadSize"));

			boolean isUpload = true;

			// 如果文件不为空，写入上传路径
			if (!file.isEmpty()) {

				// 上传文件路径
				String path = RTools.properties.getValue("file_save_path");
				// System.out.println("文件上传路径：" + path);
				// 上传文件名
				String filename = file.getOriginalFilename();
				// System.out.println("上传文件名：" + filename);
				// 上传文件后缀
				String suffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
				// System.out.println("上传文件后缀：" + suffix);

				if (!checkFile(filename)) {
					map.put("code", "1");
					map.put("msg", "上传失败！");
					map.put("info", "文件类型不在可允许上传的文件类型之内！");
					isUpload = false;
				}

				// 上传文件大小
				Long fileSize = file.getSize();
				// System.out.println("上传文件大小：" + fileSize);
				if (isUpload) {
					if (fileSize <= maxUploadSize) {

						// 文件保存名称 用32为UUID 避免文件覆盖 同时文件的ID也使用该值
						String filesavename = RTools.encoding.getUUID32();
						// 文件保存文件夹路径
						String filesavepath = path + File.separator + RTools.dateTimeUtil.getNowTime(3)
								+ File.separator;
						// 文件路径
						String targetpath = filesavepath + filesavename + "." + suffix;

						File filepath = new File(targetpath);
						// 判断路径是否存在，如果不存在就创建一个
						if (!filepath.getParentFile().exists()) {
							filepath.getParentFile().mkdirs();
						}
						// 将上传文件保存到目标文件当中
						file.transferTo(new File(targetpath));

						// 入库保存附件信息
						UploadFile uf = new UploadFile();
						uf.setFileid(filesavename);
						uf.setCreattime(RTools.dateTimeUtil.getNowTime(1));
						uf.setDeleteflog("1");
						uf.setFilesize(fileSize);
						uf.setFiletype(suffix);
						uf.setFilename(filename);
						uf.setFilesavepath(filesavepath);
						uf.setFilesavename(filesavename);
						uf.setFkid(fkid);

						uploadFileService.saveUploadFile(uf);

						map.put("code", "0");
						map.put("fid", uf.getFileid());
						map.put("fname", filename);
						map.put("msg", "上传成功！");
						map.put("info", "");
					} else {
						map.put("code", "1");
						map.put("msg", "上传失败！");
						map.put("info", "文件超过所允许上传的文件大小！");
					}

				}
			} else {
				map.put("code", "1");
				map.put("msg", "上传失败！");
				map.put("info", "文件不能为空！");
			}
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			map.put("code", "1");
			map.put("msg", "上传失败！");
			map.put("info", e.getMessage());
			logService.fail(begin, map, request);
		} finally {
			return RTools.json.toJson(map);
		}

	}

	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("fid") String fid) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			// 通过文件名找出文件的所在目录
			UploadFile uf = uploadFileService.getUploadFileByFid(fid);
			String path = uf.getFilesavepath();
			String fileName = uf.getFilename();
			String serverSaveFilename = uf.getFilesavename() + "." + uf.getFiletype();// request.getParameter("server_save_filename");
			long file_size = uf.getFilesize(); // request.getParameter("fil_size");
			int length = 0;
			if (!"".equals(file_size)) {
				length = (int) file_size;
			}

			String userAgent = request.getHeader("User-Agent");
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// //非IE浏览器的处理：
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			File file = new File(path + serverSaveFilename);
			if (file.exists()) {
				// 设置响应头，控制浏览器下载该文件
				response.reset();
				response.setContentLength(length);
				response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
				response.setContentType("multipart/form-data");
				response.setCharacterEncoding("UTF-8");
				// 读取要下载的文件，保存到文件输入流
				FileInputStream ins = new FileInputStream(file);
				// 创建输出流
				OutputStream out = response.getOutputStream();
				// 创建缓冲区
				byte buffer[] = new byte[1024 * 1024];
				int len = 0;
				// 循环将输入流中的内容读取到缓冲区当中
				while ((len = ins.read(buffer)) > 0) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					out.write(buffer, 0, len);
				}
				// 关闭文件输入流
				ins.close();
				// 关闭输出流
				out.flush();
				out.close();
			} else {
				response.setHeader("Warning", "199 Miscellaneous warning");
				response.setHeader("content-disposition",
						"attachment;filename=" + URLEncoder.encode("文件不存在或者失效.html", "UTF-8"));
			}
			logService.success(begin, "下载成功！", request);
		} catch (IOException e) {
			Map map = new HashMap<String, Object>();
			map.put("state", "1");
			map.put("msg", "下载失败！ " + e.getMessage());
			logService.success(begin, map, request);
		}

	}

	/**
	 * 判断是否为允许的上传文件类型,true表示允许
	 */
	private boolean checkFile(String fileName) {
		// 设置允许上传文件类型
		String suffixList = RTools.properties.getValue("suffix");
		if ("*".equals(suffixList)) {
			return true;
		}
		// 获取文件后缀
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if (suffixList.contains(suffix.trim().toLowerCase())) {
			return true;
		}
		return false;
	}

}
