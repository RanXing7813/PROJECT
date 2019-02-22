package cn.com.taiji.util.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	static int k = 1; // 定义递归次数变量

	public static void main(String[] args) {
		// compression("D:\\1111",
		// "D:\\usr\\oracle\\weblogic\\user_projects\\domains\\spsj\\files\\spdbFolder\\T0\\HB11512900H000120161130001220112.zip");
		// decompression("D:\\test\\c510660696b1453f9a6ff01cb8c736b5608fc973c3674fdabac88b7014cbe461.dat.zip",
		// "GBK", "D:\\test");
	}

	/**
	 * 
	 * @Description (压缩)
	 * @throws Exception
	 */
	public void compression(String inputFilePath, String outPutZip) {
		try {
			String zipFileName = outPutZip;
			File inputFile = new File(inputFilePath);
			System.out.println("压缩中...");
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			BufferedOutputStream bo = new BufferedOutputStream(out);
			zip(out, inputFile, inputFile.getName(), bo);

			bo.close();
			out.close(); // 输出流关闭
			System.out.println("压缩完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void zip(ZipOutputStream out, File inputFile, String base, BufferedOutputStream bo) throws Exception { // 方法重载
		if (inputFile.isDirectory()) {
			File[] fl = inputFile.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
			System.out.println("第" + k + "次递归");
			k++;
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			System.out.println(base);
			FileInputStream in = new FileInputStream(inputFile);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bi.close();
			in.close(); // 输入流关闭
		}
	}

	/**
	* 
	* @Description (解压)
	* @param zipPath zip路径
	* @param charset 编码
	* @param outPutPath 输出路径
	*/
	public void decompression(String zipPath, String charset, String outPutPath) {
		long startTime = System.currentTimeMillis();
		try {
			ZipInputStream Zin = new ZipInputStream(new FileInputStream(zipPath), Charset.forName(charset));// 输入源zip路径
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			String Parent = outPutPath; // 输出路径（文件夹目录）
			File Fout = null;
			ZipEntry entry;
			try {
				while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
					Fout = new File(Parent, entry.getName());
					if (!Fout.exists()) {
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out = new FileOutputStream(Fout);
					BufferedOutputStream Bout = new BufferedOutputStream(out);
					int b;
					while ((b = Bin.read()) != -1) {
						Bout.write(b);
					}
					Bout.close();
					out.close();
					System.out.println(Fout + "解压成功");
				}
				Bin.close();
				Zin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间： " + (endTime - startTime) + " ms");
	}

}