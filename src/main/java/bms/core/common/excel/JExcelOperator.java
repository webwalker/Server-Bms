package bms.core.common.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Excel工具类
 * 
 * <pre>
 * 基于jxl.jar类库
 * </pre>
 * 
 * @author 陈峰
 */
public class JExcelOperator {

	private File excelFile;

	private Workbook workBook;

	private WritableWorkbook wWorkBook;

	public JExcelOperator(File file) throws BiffException, IOException {
		this.excelFile = file;
		WorkbookSettings setting = new WorkbookSettings();
		setting.setSuppressWarnings(true);
		this.workBook = Workbook.getWorkbook(excelFile, setting);
		this.wWorkBook = Workbook.createWorkbook(excelFile, this.workBook);
	}

	/**
	 * 写入一组值
	 * 
	 * @param sheetNum
	 *            写入的sheet的编号
	 * @param fillRow
	 *            是写入行还是写入列
	 * @param startRowNum
	 *            开始行号
	 * @param startColumnNum
	 *            开始列号
	 * @param contents
	 *            写入的内容数组
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeArrayToExcel(int sheetNum, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	/**
	 * 写入一组值
	 * 
	 * @param sheetName
	 *            写入的sheet的名称
	 * @param fillRow
	 *            是写入行还是写入列
	 * @param startRowNum
	 *            开始行号
	 * @param startColumnNum
	 *            开始列号
	 * @param contents
	 *            写入的内容数组
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeArrayToExcel(String sheetName, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	/**
	 * 写入一组值
	 * 
	 * @param sheetNum
	 *            写入的sheet的编号
	 * @param fillRow
	 *            是写入行还是写入列
	 * @param startColumnRowNum
	 *            开始单元格的位置
	 * @param contents
	 *            写入的内容数组
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeArrayToExcel(int sheetNum, boolean fillRow,
			String startColumnRowNum, Object[] contents) throws BiffException,
			IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		WritableCell startCell = sheet.getWritableCell(startColumnRowNum);
		int startRowNum = startCell.getRow();
		int startColumnNum = startCell.getColumn();
		this.writeArrayToExcel(sheetNum, fillRow, startRowNum, startColumnNum,
				contents);
	}

	private void writeArrayToExcel(WritableSheet sheet, boolean fillRow,
			int startRowNum, int startColumnNum, Object[] contents)
			throws WriteException, RowsExceededException {
		for (int i = 0, length = contents.length; i < length; i++) {
			int rowNum;
			int columnNum;
			// 以行为单位写入
			if (fillRow) {
				rowNum = startRowNum;
				columnNum = startColumnNum + i;
			}
			// 　以列为单位写入
			else {
				rowNum = startRowNum + i;
				columnNum = startColumnNum;
			}
			this.writeToCell(sheet, rowNum, columnNum, contents[i]);
		}
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum
	 *            sheet的编号
	 * @param rowNum
	 *            行号
	 * @param columnNum
	 *            列号
	 * @param value
	 *            写入的值
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(int sheetNum, int rowNum, int columnNum,
			Object value) throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, rowNum, columnNum, value);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum
	 *            sheet的编号
	 * @param columnRowNum
	 *            单元格的位置
	 * @param value
	 *            写入的值
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(int sheetNum, String columnRowNum, Object value)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, columnRowNum, value);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetName
	 *            sheet的名称
	 * @param columnRowNum
	 *            单元格的位置
	 * @param value
	 *            写入的值
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(String sheetName, String columnRowNum, Object value)
			throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		this.writeToCell(sheet, columnRowNum, value);
	}

	private void writeToCell(WritableSheet sheet, int rowNum, int columnNum,
			Object value) throws WriteException, RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnNum, rowNum);
		writeToCell(sheet, cell, value);
	}

	private void writeToCell(WritableSheet sheet, String columnRowNum,
			Object value) throws WriteException, RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnRowNum);
		writeToCell(sheet, cell, value);
	}

	private void writeToCell(WritableSheet sheet, WritableCell cell,
			Object value) throws WriteException, RowsExceededException {
		CellFormat cellFormat = cell.getCellFormat();
		Label label;
		if (cellFormat == null) {
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value));
		} else {
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value), cellFormat);
		}
		sheet.addCell(label);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param columnNum
	 * @param value
	 * @param lineWrap
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(int sheetNum, int rowNum, int columnNum,
			Object value, boolean lineWrap) throws BiffException, IOException,
			WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, rowNum, columnNum, value, lineWrap);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum
	 * @param columnRowNum
	 * @param value
	 * @param lineWrap
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(int sheetNum, String columnRowNum, Object value,
			boolean lineWrap) throws BiffException, IOException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		this.writeToCell(sheet, columnRowNum, value, lineWrap);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetName
	 *            sheet的名称
	 * @param columnRowNum
	 *            写入单元格的位置
	 * @param value
	 *            写入单元格的值
	 * @param lineWrap
	 *            是否换行
	 * @throws BiffException
	 * @throws IOException
	 * @throws WriteException
	 */
	public void writeToExcel(String sheetName, String columnRowNum,
			Object value, boolean lineWrap) throws BiffException, IOException,
			WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		this.writeToCell(sheet, columnRowNum, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, int rowNum, int columnNum,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnNum, rowNum);
		writeToCell(sheet, cell, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, String columnRowNum,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		WritableCell cell = sheet.getWritableCell(columnRowNum);
		writeToCell(sheet, cell, value, lineWrap);
	}

	private void writeToCell(WritableSheet sheet, WritableCell cell,
			Object value, boolean lineWrap) throws WriteException,
			RowsExceededException {
		CellFormat cellFormat = cell.getCellFormat();
		Label label;
		if (cellFormat == null) {
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value));
		} else {
			WritableCellFormat wCellFormat = new WritableCellFormat(cellFormat);
			wCellFormat.setWrap(lineWrap);
			label = new Label(cell.getColumn(), cell.getRow(),
					convertString(value), wCellFormat);
		}
		sheet.addCell(label);
	}

	/**
	 * 插入一行
	 * 
	 * @param sheetNum
	 *            插入行的sheet的编号
	 * @param rowNum
	 *            插入行的位置
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void insertRow(int sheetNum, int rowNum)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		sheet.insertRow(rowNum);
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheetNum
	 *            插入行的sheet的编号
	 * @param rowNum
	 *            插入行的位置
	 * @param columnSize
	 *            需要参照上一行格式的单元格数量
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void insertRowWithFormat(int sheetNum, int rowNum, int columnSize)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		insertRowWithFormat(sheet, rowNum, columnSize);
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheetName
	 *            插入行的sheet的名称
	 * @param rowNum
	 *            插入行的位置
	 * @param columnSize
	 *            需要参照上一行格式的单元格数量
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void insertRowWithFormat(String sheetName, int rowNum, int columnSize)
			throws RowsExceededException, WriteException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetName);
		insertRowWithFormat(sheet, rowNum, columnSize);
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheet
	 *            插入行的sheet
	 * @param rowNum
	 *            插入行的位置
	 * @param columnSize
	 *            需要参照上一行格式的单元格数量
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private void insertRowWithFormat(WritableSheet sheet, int rowNum,
			int columnSize) throws RowsExceededException, WriteException {
		sheet.insertRow(rowNum);
		sheet.setRowView(rowNum, sheet.getRowView(rowNum - 1));
		for (int i = 0; i < columnSize; i++) {
			CellFormat cellFormat = sheet.getCell(i, rowNum - 1)
					.getCellFormat();
			if (cellFormat != null) {
				Label label = new Label(i, rowNum, "", cellFormat);
				sheet.addCell(label);
			}
		}
	}

	/**
	 * 重命名一个sheet
	 * 
	 * @param sheetNum
	 *            sheet的编号
	 * @param newName
	 *            新的名称
	 * @throws IOException
	 */
	public void renameSheet(int sheetNum, String newName) throws IOException {
		WritableSheet sheet = this.wWorkBook.getSheet(sheetNum);
		sheet.setName(newName);
	}

	/**
	 * 重命名一个sheet
	 * 
	 * @param oldName
	 *            旧的名称
	 * @param newName
	 *            新的名称
	 * @throws IOException
	 */
	public void renameSheet(String oldName, String newName) throws IOException {
		WritableSheet sheet = this.wWorkBook.getSheet(oldName);
		sheet.setName(newName);
	}

	/**
	 * 拷贝一个sheet
	 * 
	 * @param oldSheetNum
	 *            旧的sheet的编号
	 * @param newSheetNum
	 *            新的sheet的编号
	 * @param newSheetName
	 *            新的sheet的名称
	 */
	public void copySheet(int oldSheetNum, int newSheetNum, String newSheetName) {
		this.wWorkBook.copySheet(oldSheetNum, newSheetName, newSheetNum);
	}

	/**
	 * 写入Excel文件并关闭
	 */
	public void writeAndClose() {
		if (this.wWorkBook != null) {
			try {
				this.wWorkBook.write();
			} catch (Exception e) {
			}
		}
		if (this.wWorkBook != null) {
			try {
				this.wWorkBook.close();
			} catch (Exception e) {
			}
		}
		if (this.workBook != null) {
			try {
				this.workBook.close();
			} catch (Exception e) {
			}
		}
	}

	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

}