package com.tgb.lk.demo.appserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

public class UploadFileServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ����request���룬��Ҫ��Ϊ�˴�����ͨ������е���������
		request.setCharacterEncoding("gbk");
		// �����request���з�װ��RequestContext�ṩ�˶�request������ʷ���
		org.apache.commons.fileupload.RequestContext requestContext = new ServletRequestContext(
				request);
		// �жϱ��Ƿ���Multipart���͵ġ��������ֱ�Ӷ�request�����жϣ������Ѿ���ǰ���÷���
		if (FileUpload.isMultipartContent(requestContext)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// �����ļ��Ļ���·��
			factory.setRepository(new File("d:/tmp/"));
			File dir = new File("d:\\download\\");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// System.out.print("�Ѿ�������ʱ�ļ�");

			ServletFileUpload upload = new ServletFileUpload(factory);
			// �����ϴ��ļ���С�����ޣ�-1��ʾ������
			upload.setSizeMax(100000 * 1024 * 1024);
			List items = new ArrayList();
			try {
				// �ϴ��ļ��������������еı��ֶΣ�������ͨ�ֶκ��ļ��ֶ�
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				System.out.println("�ļ��ϴ���������" + e1.getMessage());
			}
			// �����ÿ���ֶν��д�������ͨ�ֶκ��ļ��ֶ�
			Iterator it = items.iterator();
			while (it.hasNext()) {
				DiskFileItem fileItem = (DiskFileItem) it.next();
				// �������ͨ�ֶ�
				if (fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName()
							+ "   "
							+ fileItem.getName()
							+ "   "
							+ new String(fileItem.getString().getBytes(
									"iso8859-1"), "gbk"));
				} else {
					System.out.println(fileItem.getFieldName() + "   "
							+ fileItem.getName() + "   "
							+ fileItem.isInMemory() + "    "
							+ fileItem.getContentType() + "   "
							+ fileItem.getSize());
					// �����ļ�����ʵ���ǰѻ����������д��Ŀ��·����
					if (fileItem.getName() != null && fileItem.getSize() != 0) {
						File fullFile = new File(fileItem.getName());
						File newFile = new File("d:\\download\\"
								+ fullFile.getName());
						try {
							fileItem.write(newFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("�ļ�û��ѡ�� �� �ļ�����Ϊ��");
					}
				}

			}
		}

	}

}
