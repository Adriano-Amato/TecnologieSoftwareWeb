package com.classes.controller;

import com.classes.model.bean.products.ImageBean;
import com.classes.model.bean.products.ProductBean;
import com.classes.model.dao.ImageModel;
import com.classes.model.dao.ProductModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class ProductModifyController extends HttpServlet {

	static String SAVE_DIR = "";

	public void init() {
		// Get the file location where it would be stored
		SAVE_DIR = getServletConfig().getInitParameter("file-upload");
	}
	
	private static final String insertError = "Errori nei campi di input";

    public ProductModifyController(){
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*UPLOAD CONTROLLER*/

		String appPath = req.getServletContext().getRealPath("");
		String savePath = appPath + File.separator + SAVE_DIR;
		String filePath = null; //qui e' salvata l'immagine uploadata, poi bisogna inserirla nel DB
		File f = null;
		byte[] bytes = null;

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		for (Part part : req.getParts()) {
			String fileName = extractFileName(part);
			if (fileName != null && !fileName.equals("")) {
				filePath = savePath + File.separator + fileName;
				part.write(filePath);
				f = new File(filePath);
				bytes = Files.readAllBytes(f.toPath());
			}
		}

		/**/

		String oldProductId = req.getParameter("oldProd");

		ProductModel pm = new ProductModel();
		ImageModel im = new ImageModel();

		ProductBean oldProduct = null;
		ImageBean oldImage = null;

		if (oldProductId != null ) {
			try {
				oldProduct = pm.doRetrieveByKey(oldProductId);
				oldImage = im.doRetrieveByKey(Integer.toString(oldProduct.getCode()));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		String nome = req.getParameter("nome");
		String descrizione = req.getParameter("desc");
		String prezzoStr =   req.getParameter("prezzo");
		String quantitaStr = req.getParameter("quantita");
		
		int prezzo = Integer.parseInt(prezzoStr);
		int quantita = Integer.parseInt(quantitaStr);
		
		//Controllo null e RegEx
		if ( nome != null || descrizione != null || prezzoStr != null || quantitaStr != null) {
			if ( ( ! ( nome.matches("[A-Za-z]+$") && descrizione.matches("[A-Za-z ]+$") && prezzoStr.matches("^[0-9]+$")  && quantitaStr.matches("^[0-9]+$")) ) || ( prezzo < 0 || quantita < 0 ) ) {
				req.setAttribute("errorAoC", insertError);
				req.getRequestDispatcher(req.getContextPath() + "/aOcProduct.jsp").forward(req, resp);
				return;
			}
		}
		 
		else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errori nei parametri della richiesta!");
        	return;
		}

		if ( oldProduct != null )  { //MODIFICA PRODOTTO

			if(filePath!=null){ //vuol dire che è stata uploadata una immagine
				oldImage.setImage(bytes);
			}

			oldProduct.setDescription(descrizione);
			oldProduct.setName(nome);
			oldProduct.setPrice(prezzo);
			oldProduct.setQuantity(quantita);

			try {
				pm.doUpdate(oldProduct);
				im.doUpdate(oldImage);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		}
		else { //AGGIUNGI PRODOTTO

			ProductBean newProduct = new ProductBean();
			Collection<ProductBean> prodotti = null;

			try {
				prodotti = pm.doRetriveAll("codId");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			/* Trovo il max codId fin'ora */
			Iterator<?> iterator = prodotti.iterator();
			int codMax = -1;
			ProductBean appProd;

			while (iterator.hasNext() ) {
				appProd = (ProductBean) iterator.next();
				if (appProd.getCode() > codMax )
					codMax = appProd.getCode();
			}
			/*-----------------------------*/

			ImageBean newImage = new ImageBean();

			int newCodId = codMax + 1;
			
			newImage.setCodId(newCodId);
			newImage.setImage(bytes);

			newProduct.setDescription(descrizione);
			newProduct.setName(nome);
			newProduct.setPrice(prezzo);
			newProduct.setQuantity(quantita);
			newProduct.setCode(newCodId);

			try {
				pm.doSave(newProduct);
				im.doSave(newImage);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		}

		resp.sendRedirect("/ProductController");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();

	}

	private String extractFileName(Part part) {
		// content-disposition: form-data; name="file"; filename="file.txt"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

}
