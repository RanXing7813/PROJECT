package cn.com.taiji.util.tools.file;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


/**
 * 
 * @Description:  dpf文件增水印
 * @author: zhongdd     
 * @date:   2018年6月12日 下午4:53:01   
 * @version V1.0 
 *
 */

public class PdfFileExportUtil {

	public static void main(String[] args) {
		String inputFile = "D:\\test\\qqwqw.pdf";
		String outputFile = "D:\\test\\qqwqw"+System.currentTimeMillis()+".pdf";
		String waterMarkName = "海丰县政务信息资源共享应用";
		waterMark(inputFile, outputFile, waterMarkName);
	}

	/**
	 * @param inputFile PDF文件地址
	 * @param outputFile 添加水印后生成PDF存放的地址
	 * @param waterMarkName 你的水印
	 * @return
	 */
	public static boolean waterMark(String inputFile, String outputFile, String waterMarkName) {
		try {
			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
			// 这里的字体设置比较关键，这个设置是支持中文的写法
			BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
			int total = reader.getNumberOfPages() + 1;

			PdfContentByte under;
			Rectangle pageRect = null;
			for (int i = 1; i < total; i++) {
				pageRect = stamper.getReader().getPageSizeWithRotation(i);
				// 计算水印X,Y坐标
				float x = pageRect.getWidth() / 2;
				float y = pageRect.getHeight() / 2 - 10;
				// 获得PDF最顶层
				under = stamper.getOverContent(i);
				// 获得PDF最底层
				//under = stamper.getUnderContent(i);
				under.saveState();
				// set Transparency
				PdfGState gs = new PdfGState();
				// 设置透明度为0.2
				gs.setFillOpacity(0.5f);
				under.setGState(gs);
				under.restoreState();
				under.beginText();
				under.setFontAndSize(base, 40);
				under.setColorFill(BaseColor.LIGHT_GRAY);

				// 水印文字成45度角倾斜
				under.showTextAligned(Element.ALIGN_CENTER, waterMarkName, x, y, 55);
				// 添加水印文字
				under.endText();
				under.setLineWidth(1f);
				under.stroke();
			}
			stamper.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
