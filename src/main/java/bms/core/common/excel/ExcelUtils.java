package bms.core.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import bms.core.model.MsgLog;

/**
 * @author xu.jian
 * 
 */
public class ExcelUtils {

	/** */
	/**
	 * 导出数据为XLS格式
	 * 
	 * @param fileName
	 *            文件的名称，可以设为绝对路径，也可以设为相对路径
	 * @param content
	 *            数据的内容
	 */
	public static void exportExcel(String path, String fileName,
			List<MsgLog> content) {
		File fileDir = new File(path);
		if (!fileDir.exists())
			fileDir.mkdirs();
		fileName = fileDir + "\\" + fileName;

		WritableWorkbook wwb;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			WritableSheet ws = wwb.createSheet("消息日志", 1); // 创建一个工作表

			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			for (int i = 0; i < content.size(); i++) {
				MsgLog log = (MsgLog) content.get(i);
				ws.addCell(new Label(1, i + 1, log.getID() + "", wcf));
				ws.addCell(new Label(2, i + 1, log.getUserID(), wcf));
				ws.addCell(new Label(3, i + 1, log.getUserGroup(), wcf));
				ws.addCell(new Label(4, i + 1, log.getArea(), wcf));
				ws.addCell(new Label(5, i + 1, log.getOsVersion(), wcf));
				ws.addCell(new Label(6, i + 1, log.getApkVersion(), wcf));
				ws.addCell(new Label(7, i + 1, log.getAppCode(), wcf));
				ws.addCell(new Label(7, i + 1, log.getMsgType(), wcf));
				ws.addCell(new Label(7, i + 1, log.getLabel(), wcf));
				ws.addCell(new Label(7, i + 1, log.getTempCode(), wcf));
				ws.addCell(new Label(7, i + 1, log.getModuleUrl(), wcf));
				ws.addCell(new Label(7, i + 1, log.getPayAgentUrl(), wcf));
				ws.addCell(new Label(7, i + 1, log.getMessage(), wcf));
				ws.addCell(new Label(7, i + 1, log.getData(), wcf));
				ws.addCell(new Label(7, i + 1, log.getExt(), wcf));
				ws.addCell(new Label(7, i + 1, log.getIsValid() + "", wcf));
				ws.addCell(new Label(7, i + 1, log.getCreateTime().toString(),
						wcf));

				if (i == 0)
					wcf = new WritableCellFormat();
			}

			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}