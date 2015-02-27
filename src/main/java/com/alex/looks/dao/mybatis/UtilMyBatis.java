package com.alex.looks.dao.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.EntrancePrice;
import com.alex.looks.models.Provider;
import com.alex.looks.models.ReturnOrWriteOff;
import com.alex.looks.models.SoldProduct;

public class UtilMyBatis {

	public boolean comparison(EntrancePrice a, DirectoryProduct b){
		if(a.getArticle() == b.getArticle()){
			return true;
		}
		else{
			return false;
		}
	}
	
	MyBatisDAO dao = new MyBatisDAO(
			MyBatisConnectionFactory.getSqlSessionFactory());
	
	public void reserve(File f) throws ParserConfigurationException, SAXException, IOException {
	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		Element looks = document.createElement("looks");
		document.appendChild(looks);
		
		List<EntrancePrice> entrs = dao.selectAllEntrancePrice();
		int id = 1;
		int idel = 1;
		
		Element entranceprice = document.createElement("entranceprice");
		Attr attrDepartment = document.createAttribute("id");
		attrDepartment.setValue(id+"");
		entranceprice.setAttributeNode(attrDepartment);
		looks.appendChild(entranceprice);
		id++;
		for(EntrancePrice entr : entrs){
			
			Element productEl = document.createElement("productEntrance");
			Attr attrProduct = document.createAttribute("id");
			attrProduct.setValue(idel+"");
			productEl.setAttributeNode(attrProduct);
			entranceprice.appendChild(productEl);
			idel++;
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(entr.getName()));
			productEl.appendChild(name);
			
			Element firm = document.createElement("firm");
			firm.appendChild(document.createTextNode(entr.getFirm()));
			productEl.appendChild(firm);
			
			Element provider = document.createElement("provider");
			provider.appendChild(document.createTextNode(entr.getProvider()));
			productEl.appendChild(provider);
			
			Element article = document.createElement("article");
			article.appendChild(document.createTextNode(entr.getArticle()));
			productEl.appendChild(article);
			
			Element number = document.createElement("number");
			number.appendChild(document.createTextNode(entr.getNumber()+""));
			productEl.appendChild(number);
			
			Element costEntrance = document.createElement("costEntrance");
			costEntrance.appendChild(document.createTextNode(entr.getCostEntrance()+""));
			productEl.appendChild(costEntrance);
			
			Element costOutPut = document.createElement("costOutPut");
			costOutPut.appendChild(document.createTextNode(entr.getCostOutPut()+""));
			productEl.appendChild(costOutPut);
			
			Element time = document.createElement("time");
			time.appendChild(document.createTextNode(entr.getTime()+""));
			productEl.appendChild(time);
			
			Element nameAdmin = document.createElement("nameAdmin");
			nameAdmin.appendChild(document.createTextNode(entr.getNameAdmin()));
			productEl.appendChild(nameAdmin);
		}
		
		List<SoldProduct> sps = dao.selectAllSoldProduct();
		
		Element soldproduct = document.createElement("soldproduct");
		attrDepartment = document.createAttribute("id");
		attrDepartment.setValue(id+"");
		soldproduct.setAttributeNode(attrDepartment);
		looks.appendChild(soldproduct);
		id++;
		idel = 1;
		for(SoldProduct sp : sps){
			
			Element productEl = document.createElement("productSold");
			Attr attrProduct = document.createAttribute("id");
			attrProduct.setValue(idel+"");
			productEl.setAttributeNode(attrProduct);
			soldproduct.appendChild(productEl);
			idel++;
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(sp.getName()));
			productEl.appendChild(name);
			
			Element firm = document.createElement("firm");
			firm.appendChild(document.createTextNode(sp.getFirm()));
			productEl.appendChild(firm);
			
			Element article = document.createElement("article");
			article.appendChild(document.createTextNode(sp.getArticle()));
			productEl.appendChild(article);
			
			Element number = document.createElement("number");
			number.appendChild(document.createTextNode(sp.getNumber()+""));
			productEl.appendChild(number);
			
			Element costOutPut = document.createElement("costOutPut");
			costOutPut.appendChild(document.createTextNode(sp.getCostOutPut()+""));
			productEl.appendChild(costOutPut);
			
			Element discount = document.createElement("discount");
			discount.appendChild(document.createTextNode(sp.getDiscount()+""));
			productEl.appendChild(discount);
			
			Element sum = document.createElement("sum");
			sum.appendChild(document.createTextNode(sp.getSum()+""));
			productEl.appendChild(sum);
			
			Element time = document.createElement("time");
			time.appendChild(document.createTextNode(sp.getTime()+""));
			productEl.appendChild(time);
			
			Element nameUserAdmin = document.createElement("nameUserAdmin");
			nameUserAdmin.appendChild(document.createTextNode(sp.getNameUserAdmin()));
			productEl.appendChild(nameUserAdmin);
		}
		
		List<DirectoryProduct> dps = dao.selectAllDirectoryProduct();
		
		Element directoryproduct = document.createElement("directoryproduct");
		attrDepartment = document.createAttribute("id");
		attrDepartment.setValue(id+"");
		directoryproduct.setAttributeNode(attrDepartment);
		looks.appendChild(directoryproduct);
		id++;
		idel = 1;
		for(DirectoryProduct dp : dps){
			
			Element productEl = document.createElement("productDirectory");
			Attr attrProduct = document.createAttribute("id");
			attrProduct.setValue(idel+"");
			productEl.setAttributeNode(attrProduct);
			directoryproduct.appendChild(productEl);
			idel++;
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(dp.getName()));
			productEl.appendChild(name);
			
			Element firm = document.createElement("firm");
			firm.appendChild(document.createTextNode(dp.getFirm()));
			productEl.appendChild(firm);
			
			Element provider = document.createElement("provider");
			provider.appendChild(document.createTextNode(dp.getProvider()));
			productEl.appendChild(provider);
			
			Element article = document.createElement("article");
			article.appendChild(document.createTextNode(dp.getArticle()));
			productEl.appendChild(article);
			
			Element number = document.createElement("number");
			number.appendChild(document.createTextNode(dp.getNumber()+""));
			productEl.appendChild(number);
			
			Element costOutPut = document.createElement("costOutPut");
			costOutPut.appendChild(document.createTextNode(dp.getCostOutPut()+""));
			productEl.appendChild(costOutPut);
			
			Element time = document.createElement("timeUpdate");
			time.appendChild(document.createTextNode(dp.getTimeUpdate()+""));
			productEl.appendChild(time);
			
			Element story = document.createElement("story");
			story.appendChild(document.createTextNode(dp.getStory()));
			productEl.appendChild(story);
			
			Element nameUserAdmin = document.createElement("nameAdmin");
			nameUserAdmin.appendChild(document.createTextNode(dp.getNameAdmin()));
			productEl.appendChild(nameUserAdmin);
		}
		
		List<Provider> provs = dao.selectAllProvides();
		
		Element providers = document.createElement("Providers");
		attrDepartment = document.createAttribute("id");
		attrDepartment.setValue(id+"");
		providers.setAttributeNode(attrDepartment);
		looks.appendChild(providers);
		id++;
		idel = 1;
		for(Provider prov : provs){
			
			Element productEl = document.createElement("Provider");
			Attr attrProduct = document.createAttribute("id");
			attrProduct.setValue(idel+"");
			productEl.setAttributeNode(attrProduct);
			providers.appendChild(productEl);
			idel++;
			Element idl = document.createElement("idl");
			idl.appendChild(document.createTextNode(prov.getId()+""));
			productEl.appendChild(idl);
			
			Element fullname = document.createElement("fullname");
			fullname.appendChild(document.createTextNode(prov.getFullName()));
			productEl.appendChild(fullname);
			
			Element adress = document.createElement("adress");
			adress.appendChild(document.createTextNode(prov.getAdress()));
			productEl.appendChild(adress);
			
			Element tel = document.createElement("tel");
			tel.appendChild(document.createTextNode(prov.getTel()));
			productEl.appendChild(tel);
			
			Element site = document.createElement("site");
			site.appendChild(document.createTextNode(prov.getSite()));
			productEl.appendChild(site);
			
			Element email = document.createElement("email");
			email.appendChild(document.createTextNode(prov.getEmail()));
			productEl.appendChild(email);
			
			Element description = document.createElement("description");
			description.appendChild(document.createTextNode(prov.getDescription()));
			productEl.appendChild(description);
		}
		
		List<ReturnOrWriteOff> rets = dao.selectAllReturnOrWriteOff();
		
		Element returns = document.createElement("ReturnOrWriteOffs");
		attrDepartment = document.createAttribute("id");
		attrDepartment.setValue(id+"");
		returns.setAttributeNode(attrDepartment);
		looks.appendChild(returns);
		id++;
		idel = 1;
		for(ReturnOrWriteOff dp : rets){
			
			Element productEl = document.createElement("ReturnOrWriteOff");
			Attr attrProduct = document.createAttribute("id");
			attrProduct.setValue(idel+"");
			productEl.setAttributeNode(attrProduct);
			returns.appendChild(productEl);
			idel++;
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(dp.getName()));
			productEl.appendChild(name);
			
			Element firm = document.createElement("firm");
			firm.appendChild(document.createTextNode(dp.getFirm()));
			productEl.appendChild(firm);
			
			Element provider = document.createElement("provider");
			provider.appendChild(document.createTextNode(dp.getProvider()));
			productEl.appendChild(provider);
			
			Element article = document.createElement("article");
			article.appendChild(document.createTextNode(dp.getArticle()));
			productEl.appendChild(article);
			
			Element number = document.createElement("number");
			number.appendChild(document.createTextNode(dp.getNumber()+""));
			productEl.appendChild(number);
			
			Element costOutPut = document.createElement("costOutPut");
			costOutPut.appendChild(document.createTextNode(dp.getCostOutPut()+""));
			productEl.appendChild(costOutPut);
			
			Element time = document.createElement("timeUpdate");
			time.appendChild(document.createTextNode(dp.getTime()+""));
			productEl.appendChild(time);
			
			Element nameUserAdmin = document.createElement("nameUserAdmin");
			nameUserAdmin.appendChild(document.createTextNode(dp.getNameUserAdmin()));
			productEl.appendChild(nameUserAdmin);
			
			Element description = document.createElement("description");
			description.appendChild(document.createTextNode(dp.getDescription()));
			productEl.appendChild(description);
			
			Element descriptionForWhom = document.createElement("descriptionForWhom");
			descriptionForWhom.appendChild(document.createTextNode(dp.getDescriptionForWhom()));
			productEl.appendChild(descriptionForWhom);
		}
		
		TransformerFactory factoryTr = TransformerFactory.newInstance();
		
		try{
			Transformer transformer = factoryTr.newTransformer();
			DOMSource domSource = new DOMSource(document);
			
			StreamResult streamFile = new StreamResult(f);
			
			
			transformer.transform(domSource, streamFile);
			System.out.println("Документ Сохранен !!!");
		}catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void fileCopy(InputStream is,  OutputStream os) throws IOException
			{
			  int nLength;
			  byte[] buf = new byte[8000];
			  while(true)
			  {
			    nLength = is.read(buf);
			      if(nLength < 0) 
			        break;
			    os.write(buf, 0, nLength);
			  }
			       
			  is.close();
			  os.close();
			}
	
	
}
