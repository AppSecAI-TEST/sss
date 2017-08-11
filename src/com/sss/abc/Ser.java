package com.sss.abc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Ser extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Ser() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.sendRedirect("http://192.168.2.168:8056/WXpay/wechat/aa");
		//request.getRequestDispatcher("http://192.168.2.168:8056/WXpay/wechat/aa").forward(request, response);
		
		try {

			// ��ѹBook1.xlsx
			ZipFile xlsxFile = new ZipFile(new File("D:\\1_work_record\\yidaigou\\1.xlsx"));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			// �ȶ�ȡsharedStrings.xml����ļ�����
			ZipEntry sharedStringXML = xlsxFile
					.getEntry("xl/sharedStrings.xml");
			InputStream sharedStringXMLIS = xlsxFile
					.getInputStream(sharedStringXML);
			Document sharedString = (Document) dbf.newDocumentBuilder().parse(
					sharedStringXMLIS);
			NodeList str = ((org.w3c.dom.Document) sharedString)
					.getElementsByTagName("t");
			String sharedStrings[] = new String[str.getLength()];
			for (int n = 0; n < str.getLength(); n++) {
				Element element = (Element) str.item(n);
				// System.out.println(element.getTextContent());
				sharedStrings[n] = element.getTextContent();
			}
			// �ҵ���ѹ�ļ������workbook.xml,���ļ��а��������Ź��������м���sheet
			ZipEntry workbookXML = xlsxFile.getEntry("xl/workbook.xml");
			InputStream workbookXMLIS = xlsxFile.getInputStream(workbookXML);
			Document doc = dbf.newDocumentBuilder().parse(workbookXMLIS);
			// ��ȡһ���м���sheet
			NodeList nl = doc.getElementsByTagName("sheet");

			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);// ��nodeת��Ϊelement�������õ�ÿ���ڵ������
				//System.out.println(element.getAttribute("name"));// ���sheet�ڵ��name���Ե�ֵ
				// ���ž�Ҫ����ѹ�ļ������ҵ���Ӧ��nameֵ��xml�ļ���������workbook.xml����<sheet
				// name="Sheet1" sheetId="1" r:id="rId1" /> �ڵ�
				// ��ô�Ϳ����ڽ�ѹ�ļ������xl/worksheets���ҵ�sheet1.xml,���xml�ļ�������ǰ����ı�������
				ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet"
						+ element.getAttribute("sheetId").toLowerCase()
						+ ".xml");
				InputStream sheetXMLIS = xlsxFile.getInputStream(sheetXML);
				Document sheetdoc = dbf.newDocumentBuilder().parse(sheetXMLIS);
				NodeList rowdata = sheetdoc.getElementsByTagName("row");
				for (int j = 0; j < rowdata.getLength(); j++) {
					// �õ�ÿ����
					// �еĸ�ʽ��
					Element row = (Element) rowdata.item(j);
					// �����еõ�ÿ�����е���
					NodeList columndata = row.getElementsByTagName("c");
					for (int k = 0; k < columndata.getLength(); k++) {
						Element column = (Element) columndata.item(k);
						NodeList values = column.getElementsByTagName("v");
						Element value = (Element) values.item(0);
						if (column.getAttribute("t") != null
								& column.getAttribute("t").equals("s")) {
							// ����ǹ����ַ�������sharedstring.xml����Ҹ��е�ֵ
							System.out.print(sharedStrings[Integer
									.parseInt(value.getTextContent())]);
							
							String all = sharedStrings[Integer
														.parseInt(value.getTextContent())];
							
							String alls [] = all.split("http:");
							
							Tools.shenchen(alls[0], "http:"+alls[1]);
						} else {
							System.out.print(value.getTextContent() + "");
						}
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		  

		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
