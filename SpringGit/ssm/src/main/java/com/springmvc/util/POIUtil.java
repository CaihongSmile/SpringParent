package com.springmvc.util;
import com.springmvc.util.CellUtil;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.util.CellRangeAddress;


public class POIUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(POIUtil.class);

	/**
	 * 导出Excel xlsx格式
	 * 
	 * @param pTitle
	 * @param pHeaders
	 * @param pMapkey
	 * @param pDataset
	 * @param pOut
	 */
	public static Workbook exportExcelXssf(String pTitle, Object[] pHeaders, Object[] pMapkey,
			List<Map<String, Object>> pDataset){
		return exportExcelXssf(pTitle, pHeaders,pMapkey, pDataset, "yyyy-MM-dd HH:mm:ss");
	}


	/**
	 * 导出Excel xlsx格式
	 * 
	 * @param pTitle
	 * @param pHeaders
	 * @param pMapkey
	 * @param pDataset
	 * @param pOut
	 * @param pPattern
	 */
	public static Workbook exportExcelXssf(String pTitle, Object[] pHeaders, Object[] pMapkey,
			List<Map<String, Object>> pDataset, String pPattern) {
		Workbook workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet(pTitle);
		sheet.setDefaultColumnWidth((short) 20);
		
		CellStyle styleHeader = workbook.createCellStyle();
		styleHeader.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader.setBorderBottom(BorderStyle.THIN);
		styleHeader.setBorderLeft(BorderStyle.THIN);
		styleHeader.setBorderRight(BorderStyle.THIN);
		styleHeader.setBorderTop(BorderStyle.THIN);
		styleHeader.setAlignment(HorizontalAlignment.CENTER);

		Font font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		styleHeader.setFont(font);
		
		CellStyle styleDataSet = workbook.createCellStyle();
		styleDataSet.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleDataSet.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleDataSet.setBorderBottom(BorderStyle.THIN);
		styleDataSet.setBorderLeft(BorderStyle.THIN);
		styleDataSet.setBorderRight(BorderStyle.THIN);
		styleDataSet.setBorderTop(BorderStyle.THIN);
		styleDataSet.setAlignment(HorizontalAlignment.CENTER);
		styleDataSet.setVerticalAlignment(VerticalAlignment.CENTER);

		Font font2 = workbook.createFont();
		font2.setBold(false);
	
		styleDataSet.setFont(font2);

		Row row = sheet.createRow(0);
		for (short i = 0; i < pHeaders.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(styleHeader);
			RichTextString text = new XSSFRichTextString((String)pHeaders[i]);
			cell.setCellValue(text);
		}

		for(int i=0;i<pDataset.size();i++){
			row = sheet.createRow(i + 1);
			for (short j = 0; j < pMapkey.length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(styleDataSet);
				Object value = pDataset.get(i).get(pMapkey[j]);
				if(value == null){
					value = "";
				}
				String textValue = null;
				if (value instanceof Date) {
					Date date = (Date) value;
					SimpleDateFormat sdf = new SimpleDateFormat(pPattern);
					textValue = sdf.format(date);
				} else {
					textValue = value.toString();
				}
				cell.setCellValue(textValue);
			}
		}		

		return workbook;

	}
	
	
	
	public static Workbook exportExcelTemplete(String pTitle, String[] pHeaders,String[] colWidths) {
		Workbook workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet(pTitle);
		sheet.setDefaultColumnWidth((short) 40);
		
		CellStyle styleHeader = workbook.createCellStyle();
		styleHeader.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
		styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader.setBorderBottom(BorderStyle.THIN);
		styleHeader.setBorderLeft(BorderStyle.THIN);
		styleHeader.setBorderRight(BorderStyle.THIN);
		styleHeader.setBorderTop(BorderStyle.THIN);
		styleHeader.setAlignment(HorizontalAlignment.CENTER);

		Font font = workbook.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		styleHeader.setFont(font);
		
		CellStyle styleDataSet = workbook.createCellStyle();
		styleDataSet.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleDataSet.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleDataSet.setBorderBottom(BorderStyle.THIN);
		styleDataSet.setBorderLeft(BorderStyle.THIN);
		styleDataSet.setBorderRight(BorderStyle.THIN);
		styleDataSet.setBorderTop(BorderStyle.THIN);
		styleDataSet.setAlignment(HorizontalAlignment.CENTER);
		styleDataSet.setVerticalAlignment(VerticalAlignment.CENTER);

		Font font2 = workbook.createFont();
		font2.setBold(false);
	
		styleDataSet.setFont(font2);

		Row row = sheet.createRow(0);
		for (short i = 0; i < pHeaders.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(styleHeader);
			RichTextString text = new XSSFRichTextString(pHeaders[i]);
			cell.setCellValue(text);
			sheet.setColumnWidth(i, (short) Integer.parseInt(colWidths[i]
					.toString()));
		}
		return workbook;

	}
	
	/**
	 * 
	 * 读取EXCEL文档，并将返回List<Object>
	 * 
	 */

	public static List<Object[]> getDataByExcelFile(String extName, InputStream inputStream) throws Exception {
		List<Object[]> list = null;
		if (extName != null) {
			extName = extName.toLowerCase();
			int rowsCount = 0;
			int cellCount = 0;
			FormulaEvaluator formulaEvaluator = null;
			try {
				if ("xls".equals(extName)) {
					logger.info("ExcelType---->>>>Excel2003");
					if (inputStream != null) {
						HSSFWorkbook wb = new HSSFWorkbook(inputStream);
						formulaEvaluator = new HSSFFormulaEvaluator(wb);
						HSSFSheet sheet = null;
						HSSFRow row = null;
						HSSFCell cell = null;
						if (wb != null) {
							sheet = wb.getSheetAt(0);
						}
						if (sheet != null) {
							rowsCount = sheet.getLastRowNum() + 1;
						}
						logger.info("rowsCount---->>>>" + rowsCount);
						list = new ArrayList<Object[]>();
						Object[] rowArr = null;
						int stopRow = rowsCount;
						HSSFRow firstRow =  sheet.getRow(0);
						cellCount = firstRow.getLastCellNum();
						for (int iter = 1; iter < stopRow; iter++) {
							row = sheet.getRow(iter);
							if (row != null) {
								//cellCount = row.getLastCellNum();
								rowArr = new Object[cellCount];
								for (short jj = 0; jj < cellCount; jj++) {
									cell = row.getCell(jj);
									if (cell != null) {
										rowArr[jj] = getHssCellData(cell);
									}
									cell = null;
								}
								list.add(rowArr);
								rowArr = null;
							}
							row = null;
						}
					sheet = null;
					wb = null;
					}
				}
				if ("xlsx".equals(extName)) {
					logger.info("ExcelType---->>>>Excel2007");
					XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
					formulaEvaluator = new XSSFFormulaEvaluator(xwb);
					XSSFSheet sheet = null;
					XSSFRow row = null;
					XSSFCell cell = null;
					if (xwb != null) {
						sheet = xwb.getSheetAt(0);
					}
					if (sheet != null) {
						rowsCount = sheet.getLastRowNum() + 1;
					}
					logger.info("rowsCount---->>>>" + rowsCount);
					list = new ArrayList<Object[]>();
					Object[] rowArr = null;
					int stopRow = rowsCount;
					
					XSSFRow firstRow =  sheet.getRow(0);
					cellCount = firstRow.getLastCellNum();
					
					for (int iter = 1; iter < stopRow; iter++) {
						 System.out.println(" "+ iter);
						row = sheet.getRow(iter);
						if (row != null) {
							//cellCount = row.getLastCellNum();
							rowArr = new Object[cellCount];
							for (int jj = 0; jj < cellCount; jj++) {
								cell = row.getCell(jj);
								if (cell != null) {
									rowArr[jj] = getXssCellData(formulaEvaluator,cell);
								}
								cell = null;
							}
							list.add(rowArr);
							rowArr = null;
						}
						row = null;
					}
				sheet = null;
				xwb = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
				inputStream = null;
			}
		}
		extName = null;
		return list;
	}
	
	
	public static List<Object[]> getDataByExcelFile1(String extName, InputStream inputStream) throws Exception {
		List<Object[]> list = null;
		if (extName != null) {
			extName = extName.toLowerCase();
			int rowsCount = 0;
			int cellCount = 0;
			FormulaEvaluator formulaEvaluator = null;
			try {
				if ("xlsx".equals(extName)) {
					XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
					formulaEvaluator = new XSSFFormulaEvaluator(xwb);
					XSSFSheet sheet = null;
					XSSFRow row = null;
					XSSFCell cell = null;
					if (xwb != null) {
						sheet = xwb.getSheetAt(0);
					}
					if (sheet != null) {
						rowsCount = sheet.getLastRowNum() + 1;
					}
					logger.info("rowsCount---->>>>" + rowsCount);
					list = new ArrayList<Object[]>();
					Object[] rowArr = null;
					int stopRow = rowsCount;
					
					XSSFRow firstRow =  sheet.getRow(1);
					cellCount = firstRow.getLastCellNum();
					
					for (int iter = 1; iter < stopRow; iter++) {
						 System.out.println(" "+ iter);
						row = sheet.getRow(iter);
						if (row != null) {
							rowArr = new Object[cellCount];
							for (int jj = 0; jj < cellCount; jj++) {
								cell = row.getCell(jj);
								if (cell != null) {
									rowArr[jj] = getXssCellData(formulaEvaluator,cell);
								}
								cell = null;
							}
							list.add(rowArr);
							rowArr = null;
						}
						row = null;
					}
				sheet = null;
				xwb = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
				inputStream = null;
			}
		}
		extName = null;
		return list;
	}
	
	
	/**
	 *
	 * 读取EXCEL文档，并将返回List<Object>
	 *
	 */
	public static List<Object[]> getDataByExcelFile(String filePathName, InputStream inputStream,
			String sheetName, int beginRow, int endRow, int cellnum) throws Exception {
		List<Object[]> list = null;
		if (filePathName != null && !"".equals(filePathName)
				&& filePathName.length() > 0) {
			logger.info("filePathName---->>>>" + filePathName);
			String extName = filePathName.substring(filePathName.lastIndexOf(".")+1);;
			if (extName != null) {
				extName = extName.toLowerCase();
				int rowsCount = 0;
				int cellCount = 0;
				FormulaEvaluator formulaEvaluator = null;
				try {
					if ("xls".equals(extName)) {
						logger.info("ExcelType---->>>>Excel2003");
						if (inputStream != null) {
							HSSFWorkbook wb = new HSSFWorkbook(inputStream);
							formulaEvaluator = new HSSFFormulaEvaluator(wb);
							HSSFSheet sheet = null;
							HSSFRow row = null;
							HSSFCell cell = null;
							if (wb != null) {
								if(sheetName != null){
									sheet = wb.getSheet(sheetName);
								}else{
									sheet = wb.getSheetAt(0);
								}
							} 
							if (sheet != null) {
								rowsCount = sheet.getLastRowNum() + 1;
							}
							logger.info("rowsCount---->>>>" + rowsCount);
							if (rowsCount > 0 && rowsCount >= beginRow) {
								list = new ArrayList<Object[]>();
								Object[] rowArr = null;
								int stopRow = rowsCount;
								if (endRow > 0 && rowsCount > endRow) {
									stopRow = endRow;
								}
								for (int iter = beginRow - 1; iter < stopRow; iter++) {
									row = sheet.getRow(iter);
									if (row != null) {
										cellCount = cellnum;
										rowArr = new Object[cellCount];
										for (short jj = 0; jj < cellCount; jj++) {
											cell = row.getCell(jj);
											if (cell != null) {
												rowArr[jj] = getHssCellData(cell);
											}
											cell = null;
										}
										list.add(rowArr);
										rowArr = null;
									}
									row = null;
								}
							}
							sheet = null;
							wb = null;
						}
					}
					if ("xlsx".equals(extName)) {
						logger.info("ExcelType---->>>>Excel2007");
						XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
						formulaEvaluator = new XSSFFormulaEvaluator(xwb);
						XSSFSheet sheet = null;
						XSSFRow row = null;
						XSSFCell cell = null;
						if (xwb != null) {
							if(sheetName != null){
								sheet = xwb.getSheet(sheetName);
							}else{
								sheet = xwb.getSheetAt(0);
							}
						}
						if (sheet != null) {
							rowsCount = sheet.getLastRowNum() + 1;
						}
						logger.info("rowsCount---->>>>" + rowsCount);
						if (rowsCount > 0 && rowsCount >= beginRow) {
							list = new ArrayList<Object[]>();
							Object[] rowArr = null;
							int stopRow = rowsCount;
							if (endRow > 0 && rowsCount > endRow) {
								stopRow = endRow;
							}
							for (int iter = beginRow - 1; iter < stopRow; iter++) {
								// System.out.println(" "+ iter);
								row = sheet.getRow(iter);
								if (row != null) {
									cellCount = cellnum;
									rowArr = new Object[cellCount];
									for (int jj = 0; jj < cellCount; jj++) {
										cell = row.getCell(jj);
										if (cell != null) {
											rowArr[jj] = getXssCellData(formulaEvaluator,cell);
										}
										cell = null;
									}
									list.add(rowArr);
									rowArr = null;
								}
								row = null;
							}
						}
						sheet = null;
						xwb = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

					inputStream.close();
					inputStream = null;
				}
			}
			extName = null;
		}
		return list;

	}
	
	
	
	/**
	 * 取Excel2003中单元格数据
	 */

	@SuppressWarnings("deprecation")
	public static Object getHssCellData(Object cell) {
		Object cellData = null;
		if (cell != null) {
			HSSFCell hssCell = (HSSFCell) cell;
			if (hssCell != null) {
				switch (hssCell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					cellData = hssCell.getStringCellValue();
					if (cellData != null) {
						cellData = String.valueOf(cellData).trim();
					}
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(hssCell)) {
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd");
						cellData = df.format(hssCell.getDateCellValue());
						df = null;
					} else {
						DecimalFormat df = new DecimalFormat("0.00");
						cellData = df.format(hssCell.getNumericCellValue());
						df = null;
					}
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					cellData = "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					cellData = String.valueOf(hssCell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					break;
				}
			}
			hssCell = null;

		}
		return cellData;
	}

	/**
	 * 取Excel2007中单元格数据
	 */

	public static Object getXssCellData(FormulaEvaluator formulaEvaluator,Object cell) {
		Object cellData = null;
		if (cell != null) {
			XSSFCell xssCell = (XSSFCell) cell;
			if (xssCell != null) {
				switch (xssCell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					cellData = xssCell.getStringCellValue();
					if (cellData != null) {
						cellData = String.valueOf(cellData).trim();
					}
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(xssCell)) {
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd");
						cellData = df.format(xssCell.getDateCellValue());
						df = null;
					} else {
						double  num = xssCell.getNumericCellValue();
						DecimalFormat df = new DecimalFormat("0.00");
						cellData = df.format(num);
						df = null;
						String tempData = String.valueOf(cellData);
						if (tempData.substring(0, tempData.indexOf(".")).equals("0")) {
							cellData = num;
						}
					}
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					cellData = "";
					break;
				case XSSFCell.CELL_TYPE_FORMULA:
					cellData = String.valueOf(formulaEvaluator.evaluate(xssCell).getNumberValue());
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					cellData = String.valueOf(xssCell.getBooleanCellValue());
					break;
				case XSSFCell.CELL_TYPE_ERROR:
					break;
				default:
					break;
				}
			}
			xssCell = null;

		}
		return cellData;
	}
	
	/**
     * 多行表头
     * dataList：导出的数据；sheetName：表头名称； head0：表头第一行列名；headnum0：第一行合并单元格的参数
     * head1：表头第二行列名；headnum1：第二行合并单元格的参数；detail：导出的表体字段
     *
     */
    public static void reportMergeXlsx(HttpServletResponse response, List<Map<String, Object>> dataList, String sheetName, 
    		String[] head0, String[] headnum0, String[] head1, String[] headnum1, String[] detail) throws Exception {
	        
    	Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);// 创建一个表
        // 设置列宽  （第几列，宽度）
        sheet.setDefaultColumnWidth((short)20);
        sheet.setDefaultRowHeight((short)200);//设置行高
        
        // 表头标题样式
        Font headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setBold(true);
        headfont.setFontHeightInPoints((short)18);// 字体大小
        CellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setBorderBottom(BorderStyle.MEDIUM);
        headstyle.setBorderLeft(BorderStyle.MEDIUM);
        headstyle.setBorderRight(BorderStyle.MEDIUM);
        headstyle.setBorderTop(BorderStyle.MEDIUM);
        headstyle.setLocked(true);    
               
        // 列名样式
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);// 字体大小
        
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        style.setLocked(true);
        
        CellStyle style0 = workbook.createCellStyle();
        style0.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style0.setBorderBottom(BorderStyle.MEDIUM);
        style0.setBorderLeft(BorderStyle.MEDIUM);
        style0.setBorderRight(BorderStyle.MEDIUM);
        style0.setBorderTop(BorderStyle.MEDIUM);
        style0.setAlignment(HorizontalAlignment.CENTER);
        style0.setFont(font);
        style0.setLocked(true);
        
        // 普通单元格样式（中文）
        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setFont(font2);
        style2.setWrapText(true); // 换行
        
        // 第一行表头标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, head0.length-1));
        Row row = sheet.createRow(0);
        row.setHeight((short) 0x349);
        Cell cell = row.createCell(0);
        cell.setCellStyle(headstyle);
        CellUtil.setCellValue(cell, sheetName);        
        
        // 第二行表头列名
        row = sheet.createRow(1);
        for (int i = 0; i < head0.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(head0[i]);
            cell.setCellStyle(style0);
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }
        // 设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        // 因为下标从0开始，所以这里表示的是excel中的第三行表头
	    row = sheet.createRow(2);  
	    for (int i = 0; i < head1.length; i++) { 
	    	cell = row.createCell(i);
            cell.setCellValue(head1[i]);
            cell.setCellStyle(style);
	    }
	    
	    // 设置列值-内容
	    for (int i = 0; i < dataList.size(); i++) {
	    	row = sheet.createRow(i + 3);//标题、时间、表头字段共占了3行，所以在填充数据的时候要加3，也就是数据要从第4行开始填充
	    	for (int j = 0; j < detail.length; j++) {
	            Object data = dataList.get(i).get(detail[j]);                
                cell = row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                CellUtil.setCellValue(cell, data);
	    	}
	    }
        String fileName = URLEncoder.encode(sheetName, StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
    	response.setContentType("applicaiton/vnd.ms-excel;charset=UTF-8");
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Cache-Control", "no-cache");
    	response.setDateHeader("Expires", 0);
    	
    	OutputStream os = response.getOutputStream();
    	BufferedOutputStream bos = new BufferedOutputStream(os);
    	workbook.write(bos);
    	bos.flush();
		bos.close();
        workbook.close();
	}

}
