package Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.Colaborador;
import model.ComprasServicos;
import model.DAO;
import model.Email;
import model.FormaPagamento;
import model.Itens;
import model.PagamentosDiversos;
import model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import util.Hash;
import util.Responses;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insertAdmPagD", "/insertAdmCompServ", "/select", "/update",
		"/recusadoCompServ", "/recusadoPagD", "/report", "/verificarLogin", "/addItem", "/compraserv", "/pagDiversos",
		"/relatorioStatus", "/relatorioPagamentosDiversos", "/mSC", "/mSP", "/SCGlobal", "/SPGlobal",
		"/cadastrarToken", "/consultarToken", "/cadastrarUsuario", "/consultarEmail", "/recuperarConta", "/alterarSenha",
		"/download", "/upload"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Usuario user;
	public static Colaborador colabora;
	int count = 1;

	DAO dao = new DAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getServletPath();
	         if (action.equals("/consultarEmail")) {

				consultarEmail(request, response);
			}else if (action.equals("/alterarSenha")) {

				alterarSenha(request, response);
			}else if (action.equals("/cadastrarUsuario")) {

				cadastrarUsuario(request, response);
			}if (action.equals("/verificarLogin")) {

				verificarUsuario(request, response);

			} else if (action.equals("/compraserv")) {

				compraServicos(request, response);

			}else if (action.equals("/upload")) {

				upload(request, response);

			}
	}
	
	

	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		 //System.setProperty("java.awt.headless", "true");
		 
		String action = request.getServletPath();
		 if (action.equals("/main")) {

			login(request, response);

		} else if (action.equals("/relatorioStatus")) {

			statusRelatorio(request, response);
		} else if (action.equals("/relatorioPagamentosDiversos")) {

			pagamentosDiversosRelatorio(request, response);
		} else if (action.equals("/insertAdmPagD")) {

			inserirAdmsPagDiv(request, response);
		} else if (action.equals("/insertAdmCompServ")) {

			inserirAdmsComServ(request, response);
		} else if (action.equals("/recusadoCompServ")) {

			recusarCompServ(request, response);
		} else if (action.equals("/recusadoPagD")) {

			recusarPagD(request, response);
			// minhaSCAprovada
		} else if (action.equals("/mSC")) {

			minhaSC(request, response);

		}  else if (action.equals("/mSP")) {

			minhaSP(request, response);

		}  else if (action.equals("/SCGlobal")) {

			SCGlobal(request, response);
		} else if (action.equals("/SPGlobal")) {

			SPGlobal(request, response);
		} else if (action.equals("/cadastrarToken")) {

			tokenCadastro(request, response);
		} else if (action.equals("/consultarToken")) {

			consultarToken(request, response);
			
		}  else if (action.equals("/recuperarConta")) {

			recuperarConta(request, response);
		}else if (action.equals("/download")) {

			download(request, response);
		}
	}

	protected void download(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if(request.getParameter("caminho").contains(".jpg") || request.getParameter("caminho").contains(".jpeg") || request.getParameter("caminho").contains(".png")) {
			
			String gurufile = request.getParameter("caminho");	    
			String gurupath = "/opt/guru/";
				    response.setContentType("image/png");  
				    ServletOutputStream out;  
				    out = response.getOutputStream();  
				    FileInputStream fin = new FileInputStream(gurupath + gurufile);  
				      
				    BufferedInputStream bin = new BufferedInputStream(fin);  
				    BufferedOutputStream bout = new BufferedOutputStream(out);  
				    int ch =0; ;  
				    while((ch=bin.read())!=-1)  
				    {  
				    bout.write(ch);  
				    }  
				      
				    bin.close();  
				    fin.close();  
				    bout.close();  
				    out.close();  
				  
			
		}else if(request.getParameter("caminho").contains(".pdf")) {
			
			//response.setContentType("text/html");
			
			  String gurufile = request.getParameter("caminho");
			  
			  String gurupath = "/opt/guru/";
			  //response.setContentType("APPLICATION/OCTET-STREAM");
			  response.setContentType("application/pdf");
			  
			  //attachment faz download do arquivo application/pdf //inline abre o arquiv no navegador
			  
			  response.setHeader("Content-Disposition", "inline; filename=\"" + gurufile +
			  "\"");
			  
			  File file = new File(gurupath + gurufile);
			  response.setContentLength((int)file.length());
			  
			  
			  OutputStream out = response.getOutputStream();
			  
			  FileInputStream in = new FileInputStream(file);
			  
			  // Copy the contents of the file to the output stream 
			  byte[] buf = new byte[1024]; 
			  int count = 0; while ((count = in.read(buf)) >= 0) {
			  out.write(buf, 0, count); } 
			  out.close(); 
			  in.close();
			 
			
		}else {
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String gurufile = request.getParameter("caminho");
			String gurupath = "/opt/guru/";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ gurufile + "\"");
	 
			FileInputStream fileInputStream = new FileInputStream(gurupath
					+ gurufile);
	 
			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
			fileInputStream.close();
			out.close();
		}
			
			
			
		}
		
		

	public String getData() {

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu");
		
		String data = LocalDate.now().format(formatters).toString();

		return data;

	}
	
	
	
	public String gerarToken() {
		
		  UUID uuid = UUID.randomUUID();
		  String myRandom = uuid.toString();
		  String token = null;
		  
		
				  
				  token = myRandom.substring(0,10); 
				  
				  
			 	  
		  
		  return token;
		
		
		
	}
	

	protected void upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		
		List<PagamentosDiversos> listaPagamentos = new ArrayList<>();
		PagamentosDiversos pagamento = new PagamentosDiversos();
		String formaPagamento = null;
		String[] formasPag = { "boleto", "avista", "deposito", "pix" };
		String[] chavesPix = { "cpf", "cnpj", "email", "telefone" };
		String chavePix = null;
		List<String> caminhos = new ArrayList<>();
		FormaPagamento fmPag = new FormaPagamento();
		
		if(ServletFileUpload.isMultipartContent(request)){
			
		
            try {
            	
                List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                    	
                    	//System.out.println("Arquivo: " + item.getFieldName());	
                        String name = new File(item.getName()).getName();
                        String caminho =  getData() + "_" + gerarToken() + "_" + name;
                        item.write( new File("/opt/guru" + File.separator + caminho));
                        caminhos.add(caminho);
                     
                    }else {
                    	
                    	//#inicio

                    	if(item.getFieldName().equals("fornecedor")){
                    		
                       		pagamento.setFornecedor(item.getString("UTF-8"));
                    		System.out.println(item.getString("UTF-8"));
                    		
                    	}
                    	if(item.getFieldName().equals("descricao")){
                    		
                       		pagamento.setDescricao(item.getString("UTF-8"));
                    		System.out.println(item.getString("UTF-8"));
                    		
                    	}
                    	if(item.getFieldName().equals("vencimento")){
                    		
                       		pagamento.setVencimento(item.getString("UTF-8"));
                    		System.out.println(item.getString("UTF-8"));
                    		
                    	}
                    	if(item.getFieldName().equals("valor")){
                    		
                       		pagamento.setValor(item.getString("UTF-8"));
                    		System.out.println(item.getString("UTF-8"));
                    		
                    	}
                    	if(item.getFieldName().equals("empresa")){
                    		
                       		pagamento.setEmpresa(item.getString("UTF-8"));
                    		System.out.println(item.getString("UTF-8"));
                    		
                    	}
                    
                	
                		//pagamento.setUnidade(request.getParameter("unidade"));
                		//System.out.println(request.getParameter("unidade"));
                		pagamento.setGerente(null);
                		pagamento.setData(new DAO().getData());
                		System.out.println("Data: " + pagamento.getData());
                		pagamento.setStatus("pendente");
                		listaPagamentos.add(pagamento);

                		for (String i : formasPag) {

                			if (item.getFieldName().equals(i)) {

                				formaPagamento = i;

                			}
                		}

                		for (String p : chavesPix) {

                			if (item.getFieldName().equals(p)) {

                				chavePix = p;

                			}

                		}

                	

                		if (chavePix != null) {

                			formaPagamento = "pix";

                		}

                		// System.out.println("Info: " + formaPagamento);
                		if (formaPagamento == "pix") {
                			// System.out.println("Info: " + chavePix);
                			fmPag.setFormaPagamento(formaPagamento);
                			System.out.println(formaPagamento);

                			if (item.getFieldName().equals("cpf")) {

                				fmPag.setCpf(item.getString("UTF-8"));
                				System.out.println("CPF: " + item.getString("UTF-8"));

                			} else if (item.getFieldName().equals("cnpj")) {

                				fmPag.setCnpj(item.getString("UTF-8"));
                				System.out.println("CNPJ: " + item.getString("UTF-8"));

                			} else if (item.getFieldName().equals("email")) {

                				fmPag.setEmail(item.getString("UTF-8"));
                				System.out.println("Email: " + item.getString("UTF-8"));

                			} else if (item.getFieldName().equals("telefone")) {

                				fmPag.setTelefone(item.getString("UTF-8"));
                				System.out.println("Telefone: " + item.getString("UTF-8"));
                			}

                		} else if (formaPagamento == "deposito") {
                			fmPag.setFormaPagamento(formaPagamento);
                			System.out.println(formaPagamento);

                			if (item.getFieldName().equals("banco")) {

                				fmPag.setBanco(item.getString("UTF-8"));
                				System.out.println("Banco: " + item.getString("UTF-8"));

                			} else if (item.getFieldName().equals("agencia")) {

                				fmPag.setAgencia(item.getString("UTF-8"));
                				System.out.println("Agencia: " + item.getString("UTF-8"));

                			} else if (item.getFieldName().equals("contacorrente")) {

                				fmPag.setContacorrente(item.getString("UTF-8"));
                				System.out.println("contacorrente: " + item.getString("UTF-8"));

                			}
                			/*fmPag.setBanco(request.getParameter("banco"));
                			System.out.println(request.getParameter("banco"));
                			fmPag.setAgencia(request.getParameter("agencia"));
                			System.out.println(request.getParameter("agencia"));
                			fmPag.setContacorrente(request.getParameter("contacorrente"));
                			System.out.println(request.getParameter("contacorrente"));*/

                		} else {

                			fmPag.setFormaPagamento(formaPagamento);
                			System.out.println(formaPagamento);

                		}

                    	
                    	//#fim
                    	
                    }
                }
               //File uploaded successfully
               request.setAttribute("gurumessage", "File Uploaded Successfully");
            } catch (Exception ex) {
               request.setAttribute("gurumessage", "File Upload Failed due to " + ex);
            }         		
        }else{

            request.setAttribute("gurumessage","No File found");
}
        //request.getRequestDispatcher("/result.jsp").forward(request, response);
		Usuario col = (Usuario) request.getSession().getAttribute("usuario");

		new DAO().pagamentosDiversos(pagamento, fmPag, col.getColaboradorId(), caminhos);
		new DAO().enviarEmail(col, "pendente", "pagamentosDiversos",0);
		request.setAttribute("resultado", "ok");
		//response.sendRedirect("pagamentosdiversos.jsp");
		request.getRequestDispatcher("/pagamentosdiversos.jsp").forward(request, response);
		

	
	
	}
	
	
	
	protected void consultarEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = new DAO().validaEmail(request.getParameter("email"));
		
		if(email != null){
			//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
			
			request.setAttribute("email", "emailValido");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);	
			
		}else {
			
			request.setAttribute("email", "emailInvalido");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			
		}
		
	}
	
	protected void alterarSenha(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Token aqui: " +  request.getParameter("token"));
		String token = new DAO().consultarToken("aleatorio", request.getParameter("token"));
		
		if(token == null) {
			
			request.setAttribute("senhaAlterada", "invalido");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			
		}else {
		
		    new DAO().alterarSenha(request.getParameter("senha"), Integer.parseInt(request.getParameter("id")));
		     new DAO().deltoken("aleatorio", request.getParameter("token"));
		     	request.setAttribute("senhaAlterada", "senhaAlterada");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			
		}
		
		
	 
		
	}
	
	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		Usuario usuario = new Usuario();
       
        	
        	try {
    			usuario.setHash(new Hash().encriptPassword(request.getParameter("nome") + new DAO().getData()));
    		} catch (NoSuchAlgorithmException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

    		usuario.setPerfil(request.getParameter("perfil"));
    		usuario.setNome(request.getParameter("nome"));

    		usuario.setUsuario(request.getParameter("usuario"));
    		usuario.setSenha(request.getParameter("senha"));
    		usuario.setEmail(request.getParameter("usuario"));
    		usuario.setCidade(request.getParameter("cidade"));
    		usuario.setSetor(request.getParameter("setor"));

    		if (request.getParameter("estabelecimento") != null) {

    			usuario.setEstabelecimento(request.getParameter("estabelecimento") + " - " + request.getParameter("cidade"));
    			System.out.println(usuario.getEstabelecimento());

    		}else {
    			
    		usuario.setEstabelecimento(request.getParameter("cidade"));	
    			
    		}

    		System.out.println(usuario.getPerfil());
    		System.out.println(usuario.getNome());
    		System.out.println(usuario.getUsuario());
    		System.out.println(usuario.getSenha());
    		System.out.println(usuario.getEmail());
    		System.out.println(usuario.getCidade());
    		System.out.println("Setor: " + usuario.getSetor());

    		new DAO().cadastrarUsuario(usuario);
    	
    		request.setAttribute("usuarioCadastrado", "cadastrado");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
 
    
        	//response.sendRedirect("login.jsp");
        	
        
		

	}
	
	
	
	protected void recuperarConta(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String token = request.getParameter("token");
		System.out.println("ID recebido: " + id);
		System.out.println("Token recebido: " + token);
		
		request.setAttribute("recuperarSenha", id);
		request.setAttribute("recuperarSenhaToken", token);
		request.setAttribute("recuperarSenhaEmail", new DAO().consultarEmail(Integer.parseInt(id)));
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);	

		
	}
	
	

	protected void delPagD(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		new DAO().delPag(Integer.parseInt(request.getParameter("solicitacaoid")));

	}

	protected void tokenCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tipoUsuario = request.getParameter("tipoUsuario");
		System.out.println(tipoUsuario);
		String token = null;
		switch (tipoUsuario) {
		case "supervisor":
			token = new Responses().gerarToken(tipoUsuario);
			new DAO().cadastrarToken(tipoUsuario, token);
			break;
		case "gerente":
			token = new Responses().gerarToken(tipoUsuario);
			new DAO().cadastrarToken(tipoUsuario, token);
			break;
		case "colaborador":
			token = new Responses().gerarToken(tipoUsuario);
			new DAO().cadastrarToken(tipoUsuario, token);
			break;
		case "admin":
			token = new Responses().gerarToken(tipoUsuario);
			new DAO().cadastrarToken(tipoUsuario, token);
			break;

		}
	//pppp
		request.setAttribute("token", token);
		RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
		rd.forward(request, response);
		

	}

	protected void consultarToken(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int x;
		String newToken = request.getParameter("token");
		String perfil = "colaborador";
		String token = null;
		for (x = 5; x < 9; x++) {

			if (newToken.length() == 5) {

				perfil = "supervisor";
				token = new DAO().consultarToken(perfil, newToken);
			     break;

			} else if (newToken.length() == 6) {

				perfil = "gerente";
				token = new DAO().consultarToken(perfil, newToken);
				break;

			} else if (newToken.length() == 7) {

				perfil = "colaborador";
				token = new DAO().consultarToken(perfil, newToken);
				break;
			}

		}


		int eToken = new DAO().consultarTokenExiste(perfil, newToken);
		if (eToken == 0) {
			//Responses.setValidaToken("Token Inválido");
			//response.sendRedirect("login.jsp");
			request.setAttribute("token", "Token Inválido");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);

		} else {
			request.setAttribute("token", newToken);
			request.setAttribute("perfil", perfil);
			new DAO().deltoken(perfil, token);
			RequestDispatcher rd = request.getRequestDispatcher("cadastroUsuario.jsp");
			rd.forward(request, response);


		}

	}

	protected void recusarCompServ(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// new DAO().delComp(Integer.parseInt(request.getParameter("solicitacaoid")));
		if (user.getPerfil().equalsIgnoreCase("supervisor")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Supervisor:" + user.getNome());
			System.out.println("Supervisor ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Supervisor Hash" + user.getHash());
			new DAO().recusaGerenteComServ(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "supervisor");
			new DAO().enviarEmail(user, "recusado", "compraServicos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "recusado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		} else if (user.getPerfil().equalsIgnoreCase("gerente")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Gerente:" + user.getNome());
			System.out.println("Gerente ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Gerente Hash" + user.getHash());

			new DAO().recusaGerenteComServ(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "gerente");
			new DAO().enviarEmail(user, "recusado", "compraServicos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "recusado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		}

	}

	protected void recusarPagD(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// new DAO().delComp(Integer.parseInt(request.getParameter("solicitacaoid")));

		if (user.getPerfil().equalsIgnoreCase("supervisor")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Supervisor:" + user.getNome());
			System.out.println("Supervisor ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Supervisor Hash" + user.getHash());
			new DAO().recusarGerentePagD(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "supervisor");
			//Responses.setReponseNotFound("recusado");
			//response.sendRedirect("relatoriospd.jsp");
			new DAO().enviarEmail(user, "recusado", "pagamentosDiversos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "recusado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		} else if (user.getPerfil().equalsIgnoreCase("gerente")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Gerente:" + user.getNome());
			System.out.println("Gerente ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Gerente Hash" + user.getHash());

			new DAO().recusarGerentePagD(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "gerente");
			//Responses.setReponseNotFound("recusado");
			//response.sendRedirect("relatoriospd.jsp");
			new DAO().enviarEmail(user, "recusado", "pagamentosDiversos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "recusado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		}
	}

	protected void inserirAdmsComServ(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (user.getPerfil().equalsIgnoreCase("supervisor")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Supervisor:" + user.getNome());
			System.out.println("Supervisor ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Supervisor Hash" + user.getHash());
			new DAO().aceiteGerenteComServ(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "supervisor");
			//Responses.setReponseNotFound("preaprovado");
			//response.sendRedirect("relatorios.jsp");
			new DAO().enviarEmail(user, "preaprovado", "compraServicos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "preaprovado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		} else if (user.getPerfil().equalsIgnoreCase("gerente")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Gerente:" + user.getNome());
			System.out.println("Gerente ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Gerente Hash" + user.getHash());

			new DAO().aceiteGerenteComServ(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "gerente");
			//Responses.setReponseNotFound("aprovado");
			//response.sendRedirect("relatorios.jsp");
			new DAO().enviarEmail(user, "aprovado", "compraServicos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "aprovado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

	}

	protected void inserirAdmsPagDiv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (user.getPerfil().equalsIgnoreCase("supervisor")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Supervisor:" + user.getNome());
			System.out.println("Supervisor ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Supervisor Hash" + user.getHash());
			new DAO().aceiteGerentePagD(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "supervisor");
			//Responses.setReponseNotFound("preaprovado");
			//response.sendRedirect("relatoriospd.jsp");
			new DAO().enviarEmail(user, "preaprovado", "pagamentosDiversos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "preaprovado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
			
		} else if (user.getPerfil().equalsIgnoreCase("gerente")) {

			System.out.println("Insert: " + request.getParameter("solicitacaoid"));
			System.out.println("Gerente:" + user.getNome());
			System.out.println("Gerente ID: " + Integer.toString(user.getColaboradorId()));
			System.out.println("Gerente Hash" + user.getHash());
			new DAO().aceiteGerentePagD(user.getNome(), user.getColaboradorId(), user.getHash(),
					Integer.parseInt(request.getParameter("solicitacaoid")), "gerente");
			new DAO().enviarEmail(user, "aprovado", "pagamentosDiversos", Integer.parseInt(request.getParameter("solicitacaoid")));
			request.setAttribute("retorno", "aprovado");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);

		}

	}

	protected void pagamentosDiversosRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String statusSelect = request.getParameter("selecaoStatus");

		try {

						if (request.getParameter("pdf").equalsIgnoreCase("gerar")) {

							pdfColaborador(request, response, "pagamentosDiversos");

						} else {
							
	
							visuRelatorioColaborador(request, response,  "pagamentosDiversos");

						}



		}catch(Exception e) {
			
			System.out.println("Erro: " + e);
			
		}
	

	}

	protected void minhaSC(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = new DAO().verificarListaSC(user.getColaboradorId(), request.getParameter("status"));

		if (tamanhoLista > 0) {

			ArrayList<ComprasServicos> lista = dao.listarSc(user.getColaboradorId(), request.getParameter("status"));

			request.setAttribute("statusRelatorio", lista);
			RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
			rd.forward(request, response);

		} else {

			//Responses.setReponseNotFound("Não encontrado");
			//response.sendRedirect("relatorios.jsp");
			request.setAttribute("retorno", "Não encontrado");
			RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
			rd.forward(request, response);

		}

	}



	protected void minhaSP(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = new DAO().verificarListaPagD(user.getColaboradorId(), request.getParameter("status"));

		if (tamanhoLista > 0) {

			ArrayList<PagamentosDiversos> lista = dao.listarPagamentosDiversos(user.getColaboradorId(),  request.getParameter("status"));

			
			request.setAttribute("pagamentosDiversosRelatorio", lista);
			RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
			rd.forward(request, response);

		} else {

			request.setAttribute("retorno", "Não encontrado");
			RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
			rd.forward(request, response);

		}

	}

	

	protected void SCGlobal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int tamanhoLista = 0;

		System.out.println("Status: " + request.getParameter("status"));

		if (user.getPerfil().equals("gerente")) {

			tamanhoLista = new DAO().verificarListaSC(request.getParameter("status"));
			//Responses.setGlobal("global");
			if (tamanhoLista > 0) {

				ArrayList<ComprasServicos> lista = dao.verificarListaSCGlobasl(request.getParameter("status"));

			
				// verificarListaSCGlobasl
				request.setAttribute("statusRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
				rd.forward(request, response);

			} else {

				//Responses.setReponseNotFound("Não encontrado");
				//response.sendRedirect("relatorios.jsp");
				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
				rd.forward(request, response);

			}

		} else {

			System.out.println("Estabelecimento: " + user.getEstabelecimento());

			tamanhoLista = new DAO().verificarSCEstab(request.getParameter("status"), user.getCidade(),
					user.getEstabelecimento(), user.getSetor(), 0);
			//Responses.setGlobal("global");
			System.out.println("Tamanho lista: " + tamanhoLista);
			if (tamanhoLista > 0) {

				ArrayList<ComprasServicos> lista = dao.verificarListaSCGlobasl(request.getParameter("status"),
						user.getCidade(), user.getEstabelecimento(), user.getSetor());
				
				System.out.println("aqui porra! " + 	lista.get(0).getEstabelecimento());

				// verificarListaSCGlobasl
				request.setAttribute("statusRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatorios.jsp");
				rd.forward(request, response);

			}

		}

	}

	protected void SPGlobal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = 0;
		//Responses.setGlobal("global");
		ArrayList<PagamentosDiversos> lista;

		if (user.getPerfil().equals("gerente")) {
			tamanhoLista = new DAO().verificarListaPagD(request.getParameter("status"));
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal(request.getParameter("status"));

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				//Responses.setReponseNotFound("Não encontrado");
				//response.sendRedirect("relatoriospd.jsp");
				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		} else {

			tamanhoLista = new DAO().verificarEstabPagD(request.getParameter("status"), user.getCidade(),
					user.getEstabelecimento(), user.getSetor(), 0);

			System.out.println("TamanhoLista: " + tamanhoLista);
			System.out.println("Status: " + request.getParameter("status"));

			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal(request.getParameter("status"), user.getCidade(),
						user.getEstabelecimento(), user.getSetor());

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				//Responses.setReponseNotFound("Não encontrado");
				//response.sendRedirect("relatoriospd.jsp");
				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		}

	}

	protected void SPPreAprovadaGlobal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = 0;
		//Responses.setGlobal("global");
		ArrayList<PagamentosDiversos> lista;

		if (user.getPerfil().equals("gerente")) {
			tamanhoLista = new DAO().verificarListaPagD("preaprovado");
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("preaprovado");

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		} else {
			tamanhoLista = new DAO().verificarListaPagD("preaprovado", user.getCidade(), 0);
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("preaprovado", user.getCidade());

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		}

	}

	protected void SPPendenteGlobal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = 0;
		//Responses.setGlobal("global");
		ArrayList<PagamentosDiversos> lista;

		if (user.getPerfil().equals("gerente")) {
			tamanhoLista = new DAO().verificarListaPagD("pendente");
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("pendente");

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		} else {
			tamanhoLista = new DAO().verificarListaPagD("pendente", user.getCidade(), 0);
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("pendente", user.getCidade());

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		}

	}

	protected void SPRecusadaGlobal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int tamanhoLista = 0;
		//Responses.setGlobal("global");
		ArrayList<PagamentosDiversos> lista;

		if (user.getPerfil().equals("gerente")) {
			tamanhoLista = new DAO().verificarListaPagD("recusado");
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("recusado");

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		} else {
			tamanhoLista = new DAO().verificarListaPagD("recusado", user.getCidade(), 0);
			if (tamanhoLista > 0) {

				lista = dao.verificarListaPagDGlobal("recusado", user.getCidade());

				request.setAttribute("pagamentosDiversosRelatorio", lista);
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			} else {

				request.setAttribute("retorno", "Não encontrado");
				RequestDispatcher rd = request.getRequestDispatcher("relatoriospd.jsp");
				rd.forward(request, response);

			}

		}

	}


	protected void visuRelatorioColaborador(HttpServletRequest request, HttpServletResponse response,
		String tipoRelatorio) throws ServletException, IOException {

		if(tipoRelatorio.equals("pagamentosDiversos")) {
			System.out.println("Visualizando pagamentos diversos");
			ArrayList<PagamentosDiversos> lista = dao.listarPagamentosDiversos(
					Integer.parseInt(request.getParameter("idSolicitacao")));

			request.setAttribute("pagamentosDiversosRelatorio", lista);
			RequestDispatcher rd = request.getRequestDispatcher("viewrelatoriospd.jsp");
			rd.forward(request, response);
		
			
		}else {
			
			ArrayList<ComprasServicos> lista = dao.listarSc(Integer.parseInt(request.getParameter("idSolicitacao")));

			request.setAttribute("statusRelatorio", lista);
			RequestDispatcher rd = request.getRequestDispatcher("viewrelatorio.jsp");
			rd.forward(request, response);

			
			
		}

	
	}

	
	protected void pdfColaborador(HttpServletRequest request, HttpServletResponse response, String tipoRelatorio)
			throws ServletException, IOException {

		
		if(tipoRelatorio.equals("pagamentosDiversos")) {
			
			try {
				Connection con = new DAO().conectar();

				// Define o caminho do template.
				// String path = "/WEB-INF/reports/listagemDocumentos.jrxml";
				/*
				 * DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); java.util.Date
				 * dataInicial = null; java.util.Date dataFinal = null; try { dataInicial =
				 * (java.util.Date) formatter.parse(request.getParameter("datainicial"));
				 * dataFinal = (java.util.Date)
				 * formatter.parse(request.getParameter("datafinal")); } catch (Exception ex) {
				 * System.out.println("Erro: " + ex); }
				 * 
				 * java.sql.Date dataInicial_Sql = new java.sql.Date(dataInicial.getTime());
				 * java.sql.Date dataFinal_Sql = new java.sql.Date(dataFinal.getTime());
				 */
				HashMap filtro = new HashMap();
				filtro.put("IDcompraservicos", Integer.parseInt(request.getParameter("idSolicitacao")));
				filtro.put("picture",  getServletContext().getRealPath("/images/logo_oficial.jpg"));

				String jrxmlFile = getServletContext().getRealPath("/WEB-INF/reports/Colaborador/colaboradorpagd001.jrxml");

				System.out.println("Caminho: " + jrxmlFile);

				FileInputStream input = new FileInputStream(new File(jrxmlFile));

				// Generating the report

				JasperReport jasperReport = JasperCompileManager.compileReport(input);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, con);

				// Exporting the report as a PDF

				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

			} catch (NumberFormatException | JRException e) {
				System.out.println("Erro: " + e);
			}
		
		}else {
		
			try {

				Connection con = new DAO().conectar();

				// Define o caminho do template.
				// String path = "/WEB-INF/reports/listagemDocumentos.jrxml";
				
				
				/*
				 * System.out.println("Gerando relatorio colaborador"); DateFormat formatter =
				 * new SimpleDateFormat("dd/MM/yyyy"); java.util.Date dataInicial = null;
				 * java.util.Date dataFinal = null; try { dataInicial = (java.util.Date)
				 * formatter.parse(request.getParameter("datainicial")); dataFinal =
				 * (java.util.Date) formatter.parse(request.getParameter("datafinal")); } catch
				 * (Exception ex) { System.out.println("Erro: " + ex); }
				 * 
				 * java.sql.Date dataInicial_Sql = new java.sql.Date(dataInicial.getTime());
				 * java.sql.Date dataFinal_Sql = new java.sql.Date(dataFinal.getTime());
				 */
				HashMap filtro = new HashMap();
				filtro.put("IDcompraservicos", Integer.parseInt(request.getParameter("idSolicitacao")));
				filtro.put("picture",  getServletContext().getRealPath("/images/logo_oficial.jpg"));
				filtro.put("scitem",getServletContext().getRealPath("/WEB-INF/reports/Colaborador/reportscitem.jasper"));
				String jrxmlFile = getServletContext().getRealPath("/WEB-INF/reports/Colaborador/colaboradorsc001.jrxml");

				System.out.println("Caminho: " + jrxmlFile);

				FileInputStream input = new FileInputStream(new File(jrxmlFile));

				// Generating the report

				JasperReport jasperReport = JasperCompileManager.compileReport(input);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, con);

				// Exporting the report as a PDF

				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

			} catch (NumberFormatException | JRException e) {
				System.out.println("Erro: " + e);
			}
			
		
		}
		
		
		

		

	}

	





	protected void statusRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	

		try {

						if (request.getParameter("pdf").equalsIgnoreCase("gerar")) {

							pdfColaborador(request, response, "comprasServicos");

						} else {

							visuRelatorioColaborador(request, response, "comprasServicos");

						}

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	

	protected void compraServicos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		List<Itens> listaItem = new ArrayList<>();

		int x = 1;
		while (request.getParameter("quantidade" + x) != null) {
			Itens itens = new Itens();
			itens.setQuantidade(request.getParameter("quantidade" + x));
			System.out.println(request.getParameter("quantidade" + x));
			itens.setItem(request.getParameter("item" + x));
			System.out.println(request.getParameter("item" + x));
			itens.setEspecificacao(request.getParameter("especificacao" + x));
			System.out.println(request.getParameter("especificacao" + x));
			listaItem.add(itens);
			x++;
		}

		String justificativa = request.getParameter("justificativa");
		// System.out.println(request.getParameter("justificativa"));
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		ComprasServicos cs = new ComprasServicos();
		cs.setId(usuario.getColaboradorId());
		cs.setJustificativa(justificativa);
		cs.setData(new DAO().getData());
		cs.setStatus("pendente");
		cs.setUnidade(request.getParameter("unidade"));
		System.out.println("Unidade: " + request.getParameter("unidade"));
		new DAO().comprasServicos(listaItem, cs);
		new DAO().enviarEmail(usuario, "pendente", "compraServicos", 0);
		//Responses.setReponseNotFound("compraservicos");
		request.setAttribute("retorno", "compraservicos");
		RequestDispatcher rd = request.getRequestDispatcher("compraservicos.jsp");
		rd.forward(request, response);


		/*
		 * System.out.println("QTD: " + quantidade); System.out.println("Item: " +
		 * item); System.out.println("Justificativa: " + justificativa);
		 * System.out.println("Especificacao: " + especificacao);
		 */
		// RequestDispatcher rd = request.getRequestDispatcher("compraservicos.jsp");
		// rd.forward(request, response);

	}

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");

	}

	protected void verificarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("pass");
		user = new DAO().consultaUsuario(usuario, senha);
		// System.out.println(user.getNome());

		if (user.getUsuario() == null) {

			request.setAttribute("verificarUsuario", "usuario ou senha incorreta!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);

			// response.sendRedirect("login.jsp");

		} else {
			
			//Usuario logUser = (Usuario) request.getSession().getAttribute("usuario");

			if(user.getLogado() == 1){
				
				request.setAttribute("verificarUsuario", "usuario ou senha incorreta!");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				
			}else {
				
		        System.out.println("O ID do colaborador é este: " + user.getColaboradorId());		    	
		        
		        new DAO().login(user.getUsuarioId());
                 
				request.getSession().setAttribute("usuario", user);
				
				response.sendRedirect("index.jsp");
				
			}
			
		

		}

		
		// Usuario teste = (Usuario) request.getSession().getAttribute("usuario");
					// System.out.println("Aqui" + teste.getNome());
		/*
		 * contato.setNome(request.getParameter("nome"));
		 * contato.setFone(request.getParameter("fone"));
		 * contato.setEmail(request.getParameter("email")); dao.inserirContato(contato);
		 * response.sendRedirect("main");
		 */
	}

}
