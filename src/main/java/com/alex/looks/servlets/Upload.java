package com.alex.looks.servlets;


import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
				boolean isMultipart = ServletFileUpload.isMultipartContent(request);
				if (!isMultipart) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
		 
				// ������ ����� ������� 
				DiskFileItemFactory factory = new DiskFileItemFactory();
		 
				// ������������ ������ ������ � ������,
				// ��� ��� ���������� ������ ������ ������������ �� ���� �� ��������� ����������
				// ������������� ���� ��������
				factory.setSizeThreshold(1024*1024);
				
				// ������������� ��������� ����������
				File tempDir = (File)getServletContext().getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(tempDir);
		 
				//������ ��� ���������
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				//������������ ������ ������ ������� ��������� ��������� � ������
				//�� ��������� -1, ��� �����������. ������������� 10 ��������. 
				upload.setSizeMax(1024 * 1024 * 10);
		 
				try {
					List items = upload.parseRequest(request);
					Iterator iter = items.iterator();
					
					while (iter.hasNext()) {
					    FileItem item = (FileItem) iter.next();
		 
					    if (item.isFormField()) {
					    	//���� ����������� ����� ������ �������� ����� �����			    	
					        
					    } else {
					    	//� ��������� ������ ������������� ��� ����
					        processUploadedFile(item);
					    }
					}			
				} catch (Exception e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return;
				}		
				response.sendRedirect("adminservice.jsp"); 
	}

	private void processUploadedFile(FileItem item) throws Exception {
		File uploadetFile = null;
		//�������� ����� ��� ���� �� ����� ���������
		do{
			String path = getServletContext().getRealPath("/upload/admin_load_reserve_file/alex_kras_new_copy_file_for_reserve_copy_dtfgmgorifjgurnfhdyrjfudk.xml");	
			uploadetFile = new File(path);		
		}while(uploadetFile.exists());
		
		//������ ����
		uploadetFile.createNewFile();
		//���������� � ���� ������
		item.write(uploadetFile);
	}
 
	/**
	 * ������� �� ������� ��� ��������� � ��������
	 * @param item
	 */
	
}