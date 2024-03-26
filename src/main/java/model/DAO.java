package model;

import java.sql.Connection;
//import  Controller.Controller;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import util.Responses;


public class DAO {

	/** The driver. */
	private String driver = "com.mysql.cj.jdbc.Driver";

	/**
	 * The url. Insert in the ireport end party utf8
	 * &useTimezone=true&serverTimezone=UTC
	 */
	//private String url = "jdbc:mysql://5.161.219.78:3306/appprovidamed_compras?characterEncoding=utf8";
	private String url = "jdbc:mysql://127.0.0.1:3306/appprovidamed_compras?characterEncoding=utf8";
	
	/** The user. */
	//private String user = "app_compras";
	private String user = "root";
	
	/** The password. */
	//private String password = "hqeOPGLr6Ic1kK!l";
	private String password = "9138";

	public Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public String BrazilianData(String data) { //OK

		String ano = data.substring(0, 4);
		String mes = data.substring(5, 7);
		String dia = data.substring(8, 10);
		data = dia + "/" + mes + "/" + ano;
		return data;

	}
	
	public ArrayList<Itens> listarScItens(int compraServicosId) { //OK

		ArrayList<Itens> lista = new ArrayList<>();
		String read = "SELECT * from item WHERE compraServicos_id = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, compraServicosId);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Itens cs = new Itens();
				cs.setId(rs.getInt("id"));
				cs.setCompraServicos_id(rs.getInt("compraServicos_id"));
				cs.setQuantidade(rs.getString("quantidade"));
				cs.setItem(rs.getString("produto"));
				cs.setEspecificacao(rs.getString("especificacao"));
				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();
			return lista;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	public String validaEmail(String email) { //OK
		
		String sql = "select id, nome, email from colaborador where email=?";
		String emailValido = null;
		String token = new Responses().gerarToken("aleatorio");
		cadastrarToken("aleatorio", token);

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, email);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					String assunto = "Recuperação de conta";
					String mensagem = "Olá " + rs.getString("nome") + ", \n"
							+ "Acesse o link abaixo para a recuperação de sua conta.\n"
							+ "\nhttp://app.provida.med.br:8080/Compras/recuperarConta?id=" + rs.getInt("id") + "&token=" + token;
					Email.enviaEmail("nao-responda@provida.med.br", email, assunto, mensagem);
					emailValido = rs.getString("email");

				} else {

					System.out.println("Email não encontrado");
					return null;
				}
				stmt.close();
				con.close(); 
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				
			}

			return emailValido;
		}	
		
		
		
	public void alterarSenha(String senha, int id) { //OK

		System.out.println("aqui a senha: " + senha + " eo id: " + id);
		
		
		String sql = "update usuario set senha = md5(?) where colaborador_id = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, senha);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			con.close();

			System.out.println("Senha alterada com sucesso!");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	public void login(int id) { //OK

		String sql = "update usuario set logado = ? where id = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			con.close();

			System.out.println("Login");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	public void logout(int id) { //OK

		String sql = "update usuario set logado = ? where id = ?";
		

		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, 0);
			stmt.setInt(2, id);
			stmt.execute();
			stmt.close();
			con.close();

			System.out.println("Logout");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	
	public void enviarEmail(Usuario user, String status, String tipo, int solicitacaoId){ //OK
		
		
		
		/*System.out.println("###########Email#############");
		System.out.println("Cidade: " + user.getCidade());
		System.out.println("Tipo:" + tipo);*/
		
		if(user.getPerfil().equals("colaborador")){
			
			
			if(!user.getCidade().equals("Cachoeira do Sul") && tipo.equals("compraServicos")) {
				System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? and usuario.perfil = 'supervisor'";
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						stmt.setString(1, user.getCidade());
						
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) {

				            String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços foi gerada."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do solicitante:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

					
					
				}else if(!user.getCidade().equals("Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? and usuario.perfil = 'supervisor'";
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						stmt.setString(1, user.getCidade());
						
						ResultSet rs = stmt.executeQuery();
						if (rs.next()) {

						    String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços está "+status+"."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do solicitante:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("compraServicos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? "
								+ "and usuario.perfil = 'supervisor' and colaborador.estabelecimento = ? and colaborador.setor = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						
						stmt.setString(1, user.getCidade());
						stmt.setString(2, user.getEstabelecimento());
						stmt.setString(3, user.getSetor());
						
						ResultSet rs = stmt.executeQuery();
						
						if (rs.next()) {

						    String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços foi gerada."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do solicitante:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				} else if(user.getCidade().equals("Cachoeira do Sul") && user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? "
								+ "and usuario.perfil = 'supervisor' and colaborador.estabelecimento = ? and colaborador.setor = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						
						stmt.setString(1, user.getCidade());
						stmt.setString(2, user.getEstabelecimento());
						stmt.setString(3, user.getSetor());
						
						ResultSet rs = stmt.executeQuery();
						
						if (rs.next()) {

						    String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços está "+status+"."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do solicitante:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && !user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? "
								+ "and usuario.perfil = 'supervisor' and colaborador.estabelecimento = ? and colaborador.setor = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						
						stmt.setString(1, user.getCidade());
						stmt.setString(2, user.getEstabelecimento());
						stmt.setString(3, user.getSetor());
						
						ResultSet rs = stmt.executeQuery();
						
						if (rs.next()) {

						    String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços está "+status+"."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do solicitante:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && !user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("compraServicos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id and colaborador.cidade = ? "
								+ "and usuario.perfil = 'supervisor' and colaborador.estabelecimento = ? and colaborador.setor = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						
						stmt.setString(1, user.getCidade());
						stmt.setString(2, user.getEstabelecimento());
						stmt.setString(3, user.getSetor());
						
						ResultSet rs = stmt.executeQuery();
						
						if (rs.next()) {

							 String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova solicitação de compras e serviços foi gerada."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do solicitante:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} else {

							System.out.println("Erro ao enviar email");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				} 	 		 		 	
			
		} else if(user.getPerfil().equals("supervisor")){
			
			//pagamentosDiversos
			
			int idColaborador = 0;
			
			
			if(tipo.equals("compraServicos")) {
				
				try {
					String sql = "SELECT compraservicos.colaborador_id FROM compraservicos WHERE compraservicos.id = ?";
					
					Connection con = conectar();
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setInt(1, solicitacaoId);
					
					ResultSet rs = stmt.executeQuery();

				if (rs.next()) {

						  
					
					idColaborador = rs.getInt("colaborador_id");
					//System.out.println("ColaboradorID: "  + idColaborador);
							
					}
					stmt.close();
					con.close();
					rs.close();


				} catch (SQLException e) {

					// System.out.println(null, "" + e);
					System.out.println("Erro: " + e);

				}
				
			}else {
				
				
				
				try {
					String sql = "SELECT pagamentosdiversos.colaborador_id FROM pagamentosdiversos WHERE pagamentosdiversos.id = ?";
					
					Connection con = conectar();
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setInt(1, solicitacaoId);
					
					ResultSet rs = stmt.executeQuery();

				if (rs.next()) {

						  
					
					idColaborador = rs.getInt("colaborador_id");
					
							
					}
					stmt.close();
					con.close();
					rs.close();


				} catch (SQLException e) {

					// System.out.println(null, "" + e);
					System.out.println("Erro: " + e);

				}
				
				
			}
			
		
			
			
			if(!user.getCidade().equals("Cachoeira do Sul") && tipo.equals("compraServicos")) {
				System.out.println("Entrou aqui\n" + "id: " + idColaborador);
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id "
								+ "and usuario.perfil = 'gerente' union SELECT nome, email FROM colaborador WHERE id = ?";
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
					
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						while (rs.next()) {

				            String mensagem = "Olá " + rs.getString("nome") + 
				            		", \n Uma nova solicitação de compras e serviços está "+status+"."
				            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
				            		+ "\n Dados do gerente:"
				            		+"\n Nome: " +  user.getNome() + " "
				            		+ "\n Setor: " + user.getSetor() + " "
				            		+ "\n Email: " + user.getEmail() + " "
				            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
				            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

					
					
				}else if(!user.getCidade().equals("Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id "
								+ " and usuario.perfil = 'gerente' union SELECT nome, email FROM colaborador WHERE id = ?";
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
					
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova autorização de pagamentos está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} 
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("compraServicos")) {
				
			
					
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id "
								+ "and usuario.perfil = 'gerente'  "
								+ "union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
		
					while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova solicitação de compras e serviços está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							   
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				} else if(user.getCidade().equals("Cachoeira do Sul") && user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id  "
								+ "and usuario.perfil = 'gerente'  union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						/*
						 * 		String remetente = request.getParameter("remetente");
								String destinatario = request.getParameter("destinatario");
								String assunto = request.getParameter("assunto");
								String mensagem = request.getParameter("mensagem");
								Email.enviaEmail(remetente, destinatario, assunto, mensagem);
						 * 
						 * */
						
						
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova autorização de pagamentos está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} 
						
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && !user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id "
								+ "and usuario.perfil = 'gerente'  union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);		
		
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova autorização de pagamentos está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} 
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if(user.getCidade().equals("Cachoeira do Sul") && !user.getEstabelecimento().equals("Sede Ramiro - Cachoeira do Sul") && tipo.equals("compraServicos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "SELECT colaborador.nome, colaborador.email FROM colaborador JOIN usuario on colaborador.id = usuario.colaborador_id "
								+ "and usuario.perfil = 'gerente'  union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
						
						stmt.setInt(1, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova solicitação de compras e serviços está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				} 	 		 		 	
			
		}else if(user.getPerfil().equals("gerente")){
			
			
			
			int idColaborador = 0;
			
		if(tipo.equals("compraServicos")) {
				
				try {
					String sql = "SELECT compraservicos.colaborador_id FROM compraservicos WHERE compraservicos.id = ?";
					
					Connection con = conectar();
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setInt(1, solicitacaoId);
					
					ResultSet rs = stmt.executeQuery();

				if (rs.next()) {

						  
					
					idColaborador = rs.getInt("colaborador_id");
					
							
					}
					stmt.close();
					con.close();
					rs.close();


				} catch (SQLException e) {

					// System.out.println(null, "" + e);
					System.out.println("Erro: " + e);

				}
				
			}else {
				
				
				
				try {
					String sql = "select pagamentosdiversos.colaborador_id FROM pagamentosdiversos WHERE pagamentosdiversos.id = ?";
					
					Connection con = conectar();
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setInt(1, solicitacaoId);
					
					ResultSet rs = stmt.executeQuery();

				if (rs.next()) {

						  
					
					idColaborador = rs.getInt("colaborador_id");
					
							
					}
					stmt.close();
					con.close();
					rs.close();


				} catch (SQLException e) {

					// System.out.println(null, "" + e);
					System.out.println("Erro: " + e);

				}
				
				
			}
			
			
			
				
				if(tipo.equals("pagamentosDiversos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "select nome, email from colaborador where colaborador.id=(select supervisorid from pagamentosdiversos where id = ?) union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
					
						stmt.setInt(1, solicitacaoId);
						stmt.setInt(2, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova autorização de pagamentos está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Autorização de Pagamentos", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						} 
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				}else if( tipo.equals("compraServicos")) {
				
					System.out.println("Enviando Email");
					try {
						String sql = "select nome, email from colaborador where colaborador.id=(select supervisorid from compraservicos where id = ?) union SELECT nome, email FROM colaborador WHERE id = ?";
						
						Connection con = conectar();
						PreparedStatement stmt = con.prepareStatement(sql);
					
						
						stmt.setInt(1, solicitacaoId);
						stmt.setInt(2, idColaborador);
						
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {

							   String mensagem = "Olá " + rs.getString("nome") + 
					            		", \n Uma nova solicitação de compras e serviços está "+status+"."
					            		+ " Está na categoria correspondente no Dashboard do sistema.\n"
					            		+ "\n Dados do gerente:"
					            		+"\n Nome: " +  user.getNome() + " "
					            		+ "\n Setor: " + user.getSetor() + " "
					            		+ "\n Email: " + user.getEmail() + " "
					            		+ "\n Estabelecimento: " + user.getEstabelecimento() + "\n"
					            		+ "\n Acesse: http://app.provida.med.br:8080/Compras/";
						
							
							//Email.enviaEmail(remetente, destinatario, assunto, mensagem);
				            
							Email.enviaEmail("nao-responda@provida.med.br", rs.getString("email"), "Solicitação de Compra e Serviços", mensagem);
						
								System.out.println("Email enviado com sucesso!");
						}
						stmt.close();
						con.close();
						rs.close();


					} catch (SQLException e) {

						// System.out.println(null, "" + e);
						System.out.println("Erro: " + e);

					}

				
				} 	 		 		 	
			
		}
				 		

			
		}

	
	
	public String getData() { //OK

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String data = LocalDate.now().format(formatters).toString();

		return data;

	}

	public String formatarData(String oldData) { //OK

		String data = oldData;
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		String newData = ano + "-" + mes + "-" + dia;

		return newData;

	}

	public ArrayList<String> listaEmpresas(String cidade) { //OK

		ArrayList<String> lista = new ArrayList<>();

		String read = "SELECT nome from empresas where nome like ?";
		if (cidade.equalsIgnoreCase("Cachoeira do Sul")) {
			read = "SELECT nome from empresas where nome like ? or nome like '%lifecare%'";
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, "%" + cidade + "%");
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					lista.add(rs.getString("nome"));
				}
				pst.close();
				rs.close();
				con.close();
				return lista;
			} catch (Exception e) {
				System.out.println("Erro:" + e);
				return null;
			}

		} else {

			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, "%" + cidade + "%");
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					lista.add(rs.getString("nome"));
				}
				pst.close();
				rs.close();
				con.close();
				return lista;
			} catch (Exception e) {
				System.out.println("Erro:" + e);
				return null;
			}

		}

	}
	
	public ArrayList<String> listaFornecedores() { //OK

		ArrayList<String> lista = new ArrayList<>();

		String read = "SELECT nome from fornecedor order by nome asc";
	
		
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read);
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					lista.add(rs.getString("nome"));
				}
				pst.close();
				rs.close();
				con.close();
				return lista;
			} catch (Exception e) {
				System.out.println("Erro:" + e);
				return null;
			}

		

	}

	public ArrayList<String> listaCidades() { //OK

		ArrayList<String> lista = new ArrayList<>();

		String read = "SELECT nome from cidades";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lista.add(rs.getString("nome"));
			}
			pst.close();
			rs.close();
			con.close();
			
			return lista;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}

	public ArrayList<PagamentosDiversos> listarPagamentosDiversos(int colaboradorid, String status) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.cidade, "
				+ "  colaborador.setor, colaborador.estabelecimento, " + "  colaborador.email, "
				+ "  pagamentosdiversos.id AS 'ID Pag.', " + "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = ? and pagamentosdiversos.colaborador_id = ? " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.`status`= ? order by pagamentosdiversos.id desc";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, colaboradorid);
			pst.setInt(2, colaboradorid);
			pst.setString(3, status);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setCidade(rs.getString("cidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));
				lista.add(cs);
			}
			pst.close();
			rs.close();
			con.close();
			return lista;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}





	public int verificarListaPagD(int colaboradorid, String status) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.setor, " + "  colaborador.email, "
				+ "  pagamentosdiversos.id AS 'ID Pag.', " + "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = ? and pagamentosdiversos.colaborador_id = ? " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.colaborador_id = ? AND pagamentosdiversos.`status`= ? ";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, colaboradorid);
			pst.setInt(2, colaboradorid);
			pst.setInt(3, colaboradorid);
			pst.setString(4, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));

				lista.add(cs);
			}
			
			pst.close();
			rs.close();
			con.close();
			
			return lista.size();
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return 0;
		}

	}

	public int verificarListaPagD(String status) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.setor, " + "  colaborador.email, "
				+ "  pagamentosdiversos.id AS 'ID Pag.', " + "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = pagamentosdiversos.colaborador_id " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.`status`= ? ";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));

				lista.add(cs);
			}
			
			pst.close();
			rs.close();
			con.close();
			
			return lista.size();
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return 0;
		}

	}

	public int verificarListaPagD(String status, String cidade, int x) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.setor, " + "  colaborador.email, "
				+ "  pagamentosdiversos.id AS 'ID Pag.', " + "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = pagamentosdiversos.colaborador_id " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.`status`= ? and pagamentosdiversos.empresa like ? ";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			pst.setString(2, "%" + cidade + "%");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));

				lista.add(cs);
			}
			
			pst.close();
			rs.close();
			con.close();
			
			return lista.size();
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return 0;
		}

	}

	public int verificarEstabPagD(String status, String cidade, String estab, String setor, int count) { //OK

		//ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT     colaborador.nome,     colaborador.cidade,     colaborador.setor,     colaborador.estabelecimento, "
				+ "				   colaborador.email as 'email',     pagamentosdiversos.id AS 'ID Pag.', "
				+ "				   pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "				   pagamentosdiversos.descricao AS 'Descrição', "
				+ "				   pagamentosdiversos.vencimento AS 'Vencimento',     pagamentosdiversos.valor AS 'Valor', "
				+ "				   pagamentosdiversos.empresa AS 'Empresa',     pagamentosdiversos.supervisor, "
				+ "				   pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "				   pagamentosdiversos.supervisorhash, "
				+ "				   pagamentosdiversos.gerente,     pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "				   pagamentosdiversos.gerentehash,     pagamentosdiversos.`data` AS 'Data', "
				+ "				   pagamentosdiversos.`status` AS 'Status', "
				+ "				   formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "				   formapagamento.formaPagamento AS 'F.Pagamento',     formapagamento.banco AS 'Banco', "
				+ "				   formapagamento.agencia AS 'Agencia',     formapagamento.contacorrente AS 'C/C', "
				+ "				   formapagamento.cpf AS 'CPF',     formapagamento.cnpj AS 'CNPJ', "
				+ "				   formapagamento.email AS 'emailpag',     formapagamento.telefone AS 'Telefone' "
				+ "				 FROM pagamentosdiversos   JOIN colaborador "
				+ "				 ON colaborador.id = pagamentosdiversos.colaborador_id "
				+ "				 JOIN formapagamento     ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "				   AND pagamentosdiversos.`status`= ? and  "
				+ "				    (pagamentosdiversos.empresa like ?  OR pagamentosdiversos.empresa LIKE '%life%')  and colaborador.estabelecimento = ? and colaborador.setor = ? ";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			pst.setString(4, setor);
			pst.setString(2, "%" + cidade + "%");
			pst.setString(3, estab);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				count++;

			}
			
			pst.close();
			rs.close();
			con.close();

				try {

					con = conectar();
					pst = con.prepareStatement(read);
					pst.setString(2, status);
					pst.setString(1, setor);
					pst.setString(3, "%lifecare%");
					pst.setString(4, estab);
					rs = pst.executeQuery();

					while (rs.next()) {

						count++;

					}
					
					pst.close();
					rs.close();
					con.close();

					return count;

				} catch (Exception e) {
					System.out.println("Erro:" + e);
					return count;
				}

			

		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return count;
		}

	}

	public ArrayList<PagamentosDiversos> verificarListaPagDGlobal(String status, String cidade) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.cidade, " + "  colaborador.setor, "
				+ "  colaborador.estabelecimento, " + "  colaborador.email, " + "  pagamentosdiversos.id AS 'ID Pag.', "
				+ "  pagamentosdiversos.empresa, " + "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = pagamentosdiversos.colaborador_id " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.status = ? and pagamentosdiversos.empresa like ? order by pagamentosdiversos.id desc";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			pst.setString(2, "%" + cidade + "%");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			
			pst.close();
			rs.close();
			con.close();
			
			return lista;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}

	public ArrayList<PagamentosDiversos> verificarListaPagDGlobal(String status, String cidade, String estab,
			String setor) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT     colaborador.nome,     colaborador.cidade,     colaborador.setor,     colaborador.estabelecimento, "
				+ "				   colaborador.email as 'email',     pagamentosdiversos.id AS 'ID Pag.', "
				+ "				   pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "				   pagamentosdiversos.descricao AS 'Descrição', "
				+ "				   pagamentosdiversos.vencimento AS 'Vencimento',     pagamentosdiversos.valor AS 'Valor', "
				+ "				   pagamentosdiversos.empresa AS 'Empresa',     pagamentosdiversos.supervisor, "
				+ "				   pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "				   pagamentosdiversos.supervisorhash, "
				+ "				   pagamentosdiversos.gerente,     pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "				   pagamentosdiversos.gerentehash,     pagamentosdiversos.`data` AS 'Data', "
				+ "				   pagamentosdiversos.`status` AS 'Status', "
				+ "				   formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "				   formapagamento.formaPagamento AS 'F.Pagamento',     formapagamento.banco AS 'Banco', "
				+ "				   formapagamento.agencia AS 'Agencia',     formapagamento.contacorrente AS 'C/C', "
				+ "				   formapagamento.cpf AS 'CPF',     formapagamento.cnpj AS 'CNPJ', "
				+ "				   formapagamento.email AS 'emailpag',     formapagamento.telefone AS 'Telefone' "
				+ "				 FROM pagamentosdiversos   JOIN colaborador "
				+ "				 ON colaborador.id = pagamentosdiversos.colaborador_id "
				+ "				 JOIN formapagamento     ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "				   AND pagamentosdiversos.`status`= ? and  "
				+ "				    (pagamentosdiversos.empresa like ?  OR pagamentosdiversos.empresa LIKE '%life%')  and colaborador.estabelecimento = ? and colaborador.setor = ? order by pagamentosdiversos.id desc";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			pst.setString(2, "%" + cidade + "%");
			pst.setString(4, setor);
			pst.setString(3, estab);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			
			pst.close();
			rs.close();
			con.close();
			
				
				try {
			
				    con = conectar();
					pst = con.prepareStatement(read);
					pst.setString(1, status);
					pst.setString(2, "%lifecare%");
					pst.setString(3, setor);
					pst.setString(4, estab);
					rs = pst.executeQuery();

					while (rs.next()) {
						PagamentosDiversos cs = new PagamentosDiversos();
						cs.setId(rs.getInt("ID Pag."));
						cs.setFornecedor(rs.getString("Fornecedor"));
						cs.setDescricao(rs.getString("Descrição"));
						cs.setVencimento(rs.getString("Vencimento"));
						cs.setValor(rs.getString("Valor"));
						cs.setEmpresa(rs.getString("Empresa"));
						cs.setSupervisor(rs.getString("Supervisor"));
						cs.setSupervisorid(rs.getInt("SupervisorID"));
						cs.setSupervisorhash(rs.getString("SupervisorHash"));
						cs.setGerente(rs.getString("Gerente"));
						cs.setGerenteid(rs.getInt("GerenteID"));
						cs.setGerentehash(rs.getString("GerenteHash"));
						cs.setData(rs.getString("Data"));
						cs.setStatus(rs.getString("Status"));
						cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
						cs.setFormaPagamento(rs.getString("F.Pagamento"));
						cs.setBanco(rs.getString("Banco"));
						cs.setAgencia(rs.getString("Agencia"));
						cs.setContacorrente(rs.getString("C/C"));
						cs.setCpf(rs.getString("CPF"));
						cs.setCnpj(rs.getString("CNPJ"));
						cs.setEmail(rs.getString("Email"));
						cs.setTelefone(rs.getString("Telefone"));
						cs.setNome(rs.getString("nome"));
						cs.setSetor(rs.getString("setor"));
						cs.setEmail(rs.getString("email"));
						cs.setCidade(rs.getString("cidade"));
						cs.setEstabelecimento(rs.getString("estabelecimento"));

						lista.add(cs);
					}
					con.close(); 
					rs.close();
					pst.close();
					return lista;
				} catch (Exception e) {
					System.out.println("Erro:" + e);
					return null;
				}
				
	
			
			
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}

	public ArrayList<PagamentosDiversos> verificarListaPagDGlobal(String status) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();

		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.cidade, " + "  colaborador.setor, "
				+ "  colaborador.estabelecimento, " + "  colaborador.email, " + "  pagamentosdiversos.id AS 'ID Pag.', "
				+ "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id = pagamentosdiversos.colaborador_id " + "JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id "
				+ "  AND pagamentosdiversos.status = ? order by pagamentosdiversos.id desc";
		try {

			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();
			return lista;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}

	public ArrayList<PagamentosDiversos> listarPagamentosDiversos(int id) { //OK

		ArrayList<PagamentosDiversos> lista = new ArrayList<>();
		
		String read = "SELECT " + "  colaborador.nome, " + "  colaborador.cidade, " + "  colaborador.setor, "
				+ "  colaborador.estabelecimento, " + "  colaborador.email, " + "  pagamentosdiversos.id AS 'ID Pag.', "
				+ "  pagamentosdiversos.fornecedor AS 'Fornecedor', "
				+ "  pagamentosdiversos.descricao AS 'Descrição', "
				+ "  pagamentosdiversos.vencimento AS 'Vencimento', " + "  pagamentosdiversos.valor AS 'Valor', "
				+ "  pagamentosdiversos.empresa AS 'Empresa', " + "  pagamentosdiversos.supervisor AS 'Supervisor', "
				+ "  pagamentosdiversos.supervisorid AS 'SupervisorID', "
				+ "  pagamentosdiversos.supervisorhash AS 'SupervisorHash', "
				+ "  pagamentosdiversos.gerente AS 'Gerente', " + "  pagamentosdiversos.gerenteid AS 'GerenteID', "
				+ "  pagamentosdiversos.gerentehash AS 'GerenteHash', " + "  pagamentosdiversos.`data` AS 'Data', "
				+ "  pagamentosdiversos.`status` AS 'Status', "
				+ "  formapagamento.pagamentosDiversos_id AS 'ID Pag.', "
				+ "  formapagamento.formaPagamento AS 'F.Pagamento', " + "  formapagamento.banco AS 'Banco', "
				+ "  formapagamento.agencia AS 'Agencia', " + "  formapagamento.contacorrente AS 'C/C', "
				+ "  formapagamento.cpf AS 'CPF', " + "  formapagamento.cnpj AS 'CNPJ', "
				+ "  formapagamento.email AS 'Email', " + "  formapagamento.telefone AS 'Telefone' "
				+ "FROM pagamentosdiversos " + "JOIN colaborador "
				+ "ON colaborador.id =  pagamentosdiversos.colaborador_id JOIN formapagamento "
				+ "  ON  pagamentosdiversos.id = formapagamento.pagamentosDiversos_id WHERE pagamentosdiversos.id = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				PagamentosDiversos cs = new PagamentosDiversos();
				cs.setId(rs.getInt("ID Pag."));
				cs.setFornecedor(rs.getString("Fornecedor"));
				cs.setDescricao(rs.getString("Descrição"));
				cs.setVencimento(rs.getString("Vencimento"));
				cs.setValor(rs.getString("Valor"));
				cs.setEmpresa(rs.getString("Empresa"));
				cs.setSupervisor(rs.getString("Supervisor"));
				cs.setSupervisorid(rs.getInt("SupervisorID"));
				cs.setSupervisorhash(rs.getString("SupervisorHash"));
				cs.setGerente(rs.getString("Gerente"));
				cs.setGerenteid(rs.getInt("GerenteID"));
				cs.setGerentehash(rs.getString("GerenteHash"));
				cs.setData(rs.getString("Data"));
				cs.setStatus(rs.getString("Status"));
				cs.setPagamentosDiversos_id(rs.getInt("ID Pag."));
				cs.setFormaPagamento(rs.getString("F.Pagamento"));
				cs.setBanco(rs.getString("Banco"));
				cs.setAgencia(rs.getString("Agencia"));
				cs.setContacorrente(rs.getString("C/C"));
				cs.setCpf(rs.getString("CPF"));
				cs.setCnpj(rs.getString("CNPJ"));
				cs.setEmail(rs.getString("Email"));
				cs.setTelefone(rs.getString("Telefone"));
				cs.setNome(rs.getString("nome"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();
			return lista;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}


	public ArrayList<ComprasServicos> listarSc(int id) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();
		
		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.`status`, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.supervisorid, " + "compraservicos.gerentehash, " + "compraservicos.`data` "
				+ "FROM compraservicos " + "JOIN colaborador "
				+ "ON colaborador.id = compraservicos.colaborador_id WHERE compraservicos.id = ? ";
				

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, id);
		
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));
				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();

			return lista;

		} catch (Exception e) {
			System.out.println("Erro SC: " + e);
			return null;
		}

	}

	

	public ArrayList<ComprasServicos> listarSc(int colaboradorid, String status) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();
		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.`status`, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.supervisorid, " + "compraservicos.gerentehash, " + "compraservicos.`data` "
				+ "FROM compraservicos " + "JOIN colaborador "
				+ "ON colaborador.id = ? and compraservicos.colaborador_id = ? " + "AND STATUS = ? order by compraservicos.id desc";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, colaboradorid);
			pst.setInt(2, colaboradorid);
			pst.setString(3, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();

			return lista;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}


	

	public int verificarListaSC(int colaboradorid, String status) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();

		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.`status`, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.`data` " + "FROM compraservicos " + "JOIN colaborador "
				+ "ON colaborador.id = ? and compraservicos.colaborador_id = ? " + "AND STATUS = ? ";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, colaboradorid);
			pst.setInt(2, colaboradorid);
			pst.setString(3, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();

			if (lista.size() == 0) {

				return 0;

			} else {

				return lista.size();

			}

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}

	}

	public int verificarListaSC(String status) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();

		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.`status`, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.supervisorid, " + "compraservicos.gerentehash, " + "compraservicos.`data` "
				+ "FROM compraservicos " + "JOIN colaborador " + "ON  compraservicos.colaborador_id = colaborador.id "
				+ "AND STATUS = ?";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();

			if (lista.size() == 0) {

				return 0;

			} else {

				return lista.size();

			}

		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}

	}

	



	public int verificarSCEstab(String status, String cidade, String estabelecimento, String setor, int count) { //OK

		String read = "SELECT * FROM compraservicos JOIN  colaborador ON colaborador.id = compraservicos.colaborador_id AND colaborador.setor = ? "
				+ "AND compraservicos.status = ?  and colaborador.estabelecimento = ?"
				+ "and (compraservicos.unidade like ? or compraservicos.unidade like '%lifecare%') ";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(2, status);
			pst.setString(1, setor);
			pst.setString(3, estabelecimento);
			pst.setString(4, "%" + cidade + "%");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				count++;
			}

			if (count == 0) {

				try {
					pst = con.prepareStatement(read);
					pst.setString(2, status);
					pst.setString(1, setor);
					pst.setString(3, estabelecimento);
					pst.setString(4, "%lifecare%");
					rs = pst.executeQuery();

					while (rs.next()) {

						count++;
					}
					con.close(); 
					rs.close();
					pst.close();
				
					if (count == 0) {

						return 0;

					} else {

						return count;

					}

				} catch (Exception e) {
					System.out.println(e);
					return count;
				}

			} else {

				return count;

			}

		} catch (Exception e) {
			System.out.println(e);
			return count;
		}

	}

	public ArrayList<ComprasServicos> verificarListaSCGlobasl(String status) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();

		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.status, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.supervisorid, " + "compraservicos.gerentehash, " + "compraservicos.`data` "
				+ "FROM compraservicos " + "JOIN colaborador " + "ON  colaborador.id = compraservicos.colaborador_id "
				+ "AND compraservicos.status = ? order by compraservicos.id desc";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}
			con.close(); 
			rs.close();
			pst.close();
			return lista;

		} catch (Exception e) {
			System.out.println("Erro aqui: " + e);
			return null;
		}

	}

	
	public ArrayList<ComprasServicos> verificarListaSCGlobasl(String status, String cidade, String stab, String setor) { //OK

		ArrayList<ComprasServicos> lista = new ArrayList<>();

		String read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
				+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
				+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.status, "
				+ "compraservicos.supervisor, " + "compraservicos.supervisorid, " + "compraservicos.supervisorhash, "
				+ "compraservicos.gerente, " + "compraservicos.gerenteid, " + "compraservicos.gerentehash, "
				+ "compraservicos.supervisorid, " + "compraservicos.gerentehash, " + "compraservicos.`data` "
				+ "FROM compraservicos " + "JOIN colaborador " + "ON  colaborador.id = compraservicos.colaborador_id "
				+ "AND compraservicos.status = ? and (compraservicos.unidade like ? or compraservicos.unidade like '%lifecare%') "
				+ " and colaborador.setor = ? and colaborador.estabelecimento = ? order by compraservicos.id desc";

		try {
			System.out.println("Cachoeira sadasdsadsad");
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, status);
			pst.setString(2, "%" + cidade + "%");
			pst.setString(3, setor);
			pst.setString(4, stab);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ComprasServicos cs = new ComprasServicos();
				cs.setId(rs.getInt("id"));
				cs.setJustificativa(rs.getString("justificativa"));
				cs.setStatus(rs.getString("status"));
				cs.setSupervisor(rs.getString("supervisor"));
				cs.setSupervisorid(rs.getInt("supervisorid"));
				cs.setSupervisorhash(rs.getString("supervisorhash"));
				cs.setGerente(rs.getString("gerente"));
				cs.setGerenteid(rs.getInt("gerenteid"));
				cs.setGerentehash(rs.getString("gerentehash"));
				cs.setData(rs.getString("data"));
				cs.setNome(rs.getString("nome"));
				cs.setUnidade(rs.getString("unidade"));
				cs.setSetor(rs.getString("setor"));
				cs.setEmail(rs.getString("email"));
				cs.setCidade(rs.getString("cidade"));
				cs.setEstabelecimento(rs.getString("estabelecimento"));

				lista.add(cs);
			}

			if (lista.size() > 0) {
				con.close(); 
				rs.close();
				pst.close();
				return lista;

			} else {
				con.close();
				rs.close();
				pst.close();
				System.out.println("lifecare");
				read = "SELECT  " + "colaborador.nome, " + "colaborador.cidade, " + "colaborador.setor, "
						+ "colaborador.estabelecimento, " + "colaborador.email, " + "compraservicos.id, "
						+ "compraservicos.justificativa, " + "compraservicos.unidade, " + "compraservicos.status, "
						+ "compraservicos.supervisor, " + "compraservicos.supervisorid, "
						+ "compraservicos.supervisorhash, " + "compraservicos.gerente, " + "compraservicos.gerenteid, "
						+ "compraservicos.gerentehash, " + "compraservicos.supervisorid, "
						+ "compraservicos.gerentehash, " + "compraservicos.`data` " + "FROM compraservicos "
						+ "JOIN colaborador " + "ON  colaborador.id = compraservicos.colaborador_id "
						+ "AND compraservicos.status = ? and compraservicos.unidade like ? "
						+ " and colaborador.setor = ? and colaborador.estabelecimento = ?";

				try {
					con = conectar();
					pst = con.prepareStatement(read);
					pst.setString(1, status);
					pst.setString(2, "%lifecare%");
					pst.setString(3, setor);
					pst.setString(4, stab);
					rs = pst.executeQuery();

					while (rs.next()) {
						ComprasServicos cs = new ComprasServicos();
						cs.setId(rs.getInt("id"));
						cs.setJustificativa(rs.getString("justificativa"));
						cs.setStatus(rs.getString("status"));
						cs.setSupervisor(rs.getString("supervisor"));
						cs.setSupervisorid(rs.getInt("supervisorid"));
						cs.setSupervisorhash(rs.getString("supervisorhash"));
						cs.setGerente(rs.getString("gerente"));
						cs.setGerenteid(rs.getInt("gerenteid"));
						cs.setGerentehash(rs.getString("gerentehash"));
						cs.setData(rs.getString("data"));
						cs.setNome(rs.getString("nome"));
						cs.setUnidade(rs.getString("unidade"));
						cs.setSetor(rs.getString("setor"));
						cs.setEmail(rs.getString("email"));
						cs.setCidade(rs.getString("cidade"));
						cs.setEstabelecimento(rs.getString("estabelecimento"));

						lista.add(cs);
					}
					con.close();
					rs.close();
					pst.close();
					return lista;

				} catch (Exception e) {
					System.out.println("Erro aqui: " + e);
					return null;
				}

			}

		} catch (Exception e) {
			System.out.println("Erro aqui: " + e);
			return null;
		}

	}

	

	public void deltoken(String perfil, String token) { //OK

		String sql = null;

		if (perfil.equalsIgnoreCase("supervisor")) {
			sql = "delete from token where supervisor = ?";
		} else if (perfil.equalsIgnoreCase("gerente")) {
			sql = "delete from token where gerente = ?";

		} else if (perfil.equalsIgnoreCase("colaborador")) {
			sql = "delete from token where colaborador = ?";

		} else if (perfil.equalsIgnoreCase("aleatorio")) {
			sql = "delete from token where aleatorio = ?";

		}

		

		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, token);
			stmt.execute();
			stmt.close();
			con.close();

			System.out.println("Token deletado com sucesso!");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	public void delPag(int id) { //OK

		String sql = "delete from formapagamento where pagamentosDiversos_id = ?";
		

		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			con.close(); 

			System.out.println("Formapagamento deletada com sucesso!");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		try {

			sql = "delete from pagamentosdiversos where id=?";
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			con.close();

			System.out.println("Ordem deletada com sucesso!");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	
	public void aceiteGerenteComServ(String adminNome, int adminId, String adminHash, int idSolicitacao,
			String tipoAdmin) { //OK

		if (tipoAdmin.equalsIgnoreCase("gerente")) {

			String sql = "update compraservicos set gerente=?, gerenteid=?, gerentehash=?, status=? where id=?";
			Connection con = conectar();

			try {

				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "aprovado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close(); 

				System.out.println("Ordem aprovada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else {

			String sql = "update compraservicos set supervisor=?, supervisorid=?, supervisorhash=?, status=? where id=?";
			

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "preaprovado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Ordem pré-aprovada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}

	public void recusaGerenteComServ(String adminNome, int adminId, String adminHash, int idSolicitacao,
			String tipoAdmin) { //OK

		if (tipoAdmin.equalsIgnoreCase("gerente")) {

			String sql = "update compraservicos set gerente=?, gerenteid=?, gerentehash=?, status=? where id=?";
			

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "recusado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Ordem recusada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else {

			String sql = "update compraservicos set supervisor=?, supervisorid=?, supervisorhash=?, status=? where id=?";
		

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "recusado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Ordem recusada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}

	public void aceiteGerentePagD(String adminNome, int adminId, String adminHash, int idSolicitacao,
			String tipoAdmin) { //OK

		if (tipoAdmin.equalsIgnoreCase("gerente")) {

			String sql = "update pagamentosdiversos set gerente=?, gerenteid=?, gerentehash=?, status=? where id=?";
			

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "aprovado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close(); 

				System.out.println("Ordem aprovada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else {

			String sql = "update pagamentosdiversos set supervisor=?, supervisorid=?, supervisorhash=?, status=? where id=?";
			
			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "preaprovado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close(); 
				System.out.println("Ordem pré-aprovada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}

	public void recusarGerentePagD(String adminNome, int adminId, String adminHash, int idSolicitacao,
			String tipoAdmin) { //OK

		if (tipoAdmin.equalsIgnoreCase("gerente")) {

			String sql = "update pagamentosdiversos set gerente=?, gerenteid=?, gerentehash=?, status=? where id=?";
			

			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "recusado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close(); 

				System.out.println("Ordem recusada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else {

			String sql = "update pagamentosdiversos set supervisor=?, supervisorid=?, supervisorhash=?, status=? where id=?";
			
			try {
				Connection con = conectar();
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, adminNome);
				stmt.setInt(2, adminId);
				stmt.setString(3, adminHash);
				stmt.setString(4, "recusado");
				stmt.setInt(5, idSolicitacao);
				stmt.execute();
				stmt.close();
				con.close();
				System.out.println("Ordem recusada com sucesso!");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}
	
	
	public String consultarEmail(int id) { //OK


		String sql = "select email from colaborador where id=?";
        String email = null;
			try {
				Connection con = conectar();
				
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setInt(1, id);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					email = rs.getString("email");

				} else {

					System.out.println("CompraServicos_id não encontrado");
				
				}
				stmt.close();
				con.close(); 
				rs.close();

			} catch (SQLException e) {
				System.out.println("Erro: " + e);
			
			}

		return email;
	}

	public String consultarToken(String perfil, String newToken) { //OK

		
		String sql = null;
		String token = null;
		if (perfil.equalsIgnoreCase("supervisor")) {

			try {
				Connection con = conectar();
				sql = "select supervisor from token where supervisor=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = rs.getString("supervisor");

				} else {

					System.out.println("CompraServicos_id não encontrado");
					token = null;
				}
				stmt.close();
				con.close(); 
				rs.close();

			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = null;
			}

		} else if (perfil.equalsIgnoreCase("gerente")) {

			try {
				Connection con = conectar();
				sql = "select gerente from token where gerente=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = rs.getString("gerente");

				} else {

					System.out.println("Token não encontrado");
					token = null;
				}
				stmt.close();
				con.close(); 
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = null;
			}

		} else if (perfil.equalsIgnoreCase("colaborador")) {

			try {
				Connection con = conectar();
				sql = "select colaborador from token where colaborador=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = rs.getString("colaborador");

				} else {

					System.out.println("Token não encontrado");
					token = null;
				}
				stmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = null;
			}

		} else if (perfil.equalsIgnoreCase("aleatorio")) {

			try {
				Connection con = conectar();
				sql = "select aleatorio from token where aleatorio=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = rs.getString("aleatorio");

				} else {

					System.out.println("Token não encontrado");
					token = null;
				}
				stmt.close();
				con.close(); 
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = null;
			}

		}

		return token;

	}

	public int consultarTokenExiste(String perfil, String newToken) { //OK

		
		String sql = null;
		int token = 0;
		if (perfil.equalsIgnoreCase("supervisor")) {

			try {
				Connection con = conectar();
				sql = "select supervisor from token where supervisor=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = 1;

				} else {

					System.out.println("CompraServicos_id não encontrado");
					token = 0;
				}
				stmt.close();
				con.close(); 
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = 0;
			}

		} else if (perfil.equalsIgnoreCase("gerente")) {

			try {
				Connection con = conectar();
				sql = "select gerente from token where gerente=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = 1;

				} else {

					System.out.println("Token não encontrado");
					token = 0;
				}
				stmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = 0;
			}

		} else if (perfil.equalsIgnoreCase("colaborador")) {

			try {
				Connection con = conectar();
				sql = "select colaborador from token where colaborador=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = 1;

				} else {

					System.out.println("Token não encontrado");
					token = 0;
				}
				stmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = 0;
			}

		} else if (perfil.equalsIgnoreCase("aleatorio")) {

			try {
				Connection con = conectar();
				sql = "select admin from token where aleatorio=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, newToken);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {

					token = 1;

				} else {

					System.out.println("Token não encontrado");
					token = 0;
				}
				stmt.close();
				con.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("Erro: " + e);
				token = 0;
			}

		}

		return token;

	}

	public void cadastrarToken(String perfil, String token) { //OK

		
		String sql = null;

		if (perfil.equalsIgnoreCase("supervisor")) {

			try {
				Connection con = conectar();
				sql = "insert into token(supervisor) VALUES(?)";
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, token);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Token: Cadastro realizado com sucesso");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else if (perfil.equalsIgnoreCase("gerente")) {

			try {
				Connection con = conectar();
				sql = "insert into token(gerente) VALUES(?)";
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, token);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Token: Cadastro realizado com sucesso");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else if (perfil.equalsIgnoreCase("colaborador")) {

			try {
				Connection con = conectar();
				sql = "insert into token(colaborador) VALUES(?)";
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, token);
				stmt.execute();
				stmt.close();
				con.close(); 

				System.out.println("Token: Cadastro realizado com sucesso");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		} else if (perfil.equalsIgnoreCase("aleatorio")) {

			try {
				Connection con = conectar();
				sql = "insert into token(aleatorio) VALUES(?)";
				PreparedStatement stmt = con.prepareStatement(sql);
				// System.out.println(colaboradorId);
				stmt.setString(1, token);
				stmt.execute();
				stmt.close();
				con.close();

				System.out.println("Token: Cadastro realizado com sucesso");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}

	public void cadastrarUsuario(Usuario colaborador) { //OK

		String sql = "insert into colaborador(nome, cidade, estabelecimento, setor, email) VALUES(?,?,?,?,?)";
		int colaboradorid = 0;
		

		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			// System.out.println(colaboradorId);
			stmt.setString(1, colaborador.getNome());
			stmt.setString(2, colaborador.getCidade());
			stmt.setString(3, colaborador.getEstabelecimento());
			stmt.setString(4, colaborador.getSetor());
			stmt.setString(5, colaborador.getEmail());

			stmt.execute();
			stmt.close();
			con.close(); 
			

			System.out.println("Colaborador cadastrado com sucesso!");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// obtendo o id do ultimo registro
		try {
			Connection con = conectar();
			sql = "select id from colaborador where nome=? and email=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, colaborador.getNome());
			stmt.setString(2, colaborador.getEmail());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				colaboradorid = rs.getInt("id");

			} else {

				System.out.println("Colaborador não encontrado");
			}
			stmt.close();
			con.close(); 
			rs.close();
		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// Cadastrando o usuario
		try {
			Connection con = conectar();
			sql = "insert into usuario(colaborador_id, usuario, senha, perfil, hash, logado) values(?,?,md5(?),?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, colaboradorid);
			stmt.setString(2, colaborador.getUsuario());
			stmt.setString(3, colaborador.getSenha());
			stmt.setString(4, colaborador.getPerfil());
			stmt.setString(5, colaborador.getHash());
			stmt.setInt(6, 0);

			stmt.execute();

			stmt.close();
			con.close(); 
		
			System.out.println("Usuário cadastrado sucesso");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	public void pagamentosDiversos(PagamentosDiversos pagamento, FormaPagamento formaPag, int colaboradorId,
			List<String> caminhos) {  //OK

		String sql = "insert into pagamentosdiversos(colaborador_id, fornecedor, descricao, vencimento, valor, empresa, gerente, DATA, status) VALUES(?,?,?,?,?,?,?,?,?)";
		int pagamentosDiversosId = 0;

		String data = pagamento.getData();
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		String newData = ano + "-" + mes + "-" + dia;
		System.out.println("Data: " + newData);
		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			// System.out.println(colaboradorId);
			stmt.setInt(1, colaboradorId);
			stmt.setString(2, pagamento.getFornecedor());
			stmt.setString(3, pagamento.getDescricao());
			stmt.setString(4, pagamento.getVencimento());
			stmt.setString(5, pagamento.getValor());
			stmt.setString(6, pagamento.getEmpresa());
			stmt.setString(7, pagamento.getGerente());
			stmt.setString(8, newData);
			stmt.setString(9, pagamento.getStatus());
			stmt.execute();
			stmt.close();
			con.close(); 
			System.out.println("Pagamentos Diversos: Cadastro realizado com sucesso");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// obtendo o id do ultimo registro
		try {
			Connection con = conectar();
			sql = "select id from pagamentosdiversos where colaborador_id=? order by id desc limit 1";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, colaboradorId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				pagamentosDiversosId = rs.getInt("id");

			} else {

				System.out.println("pagamentosDiversosId não encontrado");
			}
			 stmt.close();
			 con.close();
			 rs.close();
		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// Inserindo na tabela item
		try {
			Connection con = conectar();
			sql = "insert into formapagamento(pagamentosDiversos_id, formaPagamento, banco, agencia, contacorrente, cpf, cnpj, email, telefone) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
		
			stmt.setInt(1, pagamentosDiversosId);
			stmt.setString(2, formaPag.getFormaPagamento());
			stmt.setString(3, formaPag.getBanco());
			stmt.setString(4, formaPag.getAgencia());
			stmt.setString(5, formaPag.getContacorrente());
			stmt.setString(6, formaPag.getCpf());
			stmt.setString(7, formaPag.getCnpj());
			stmt.setString(8, formaPag.getEmail());
			stmt.setString(9, formaPag.getTelefone());
			stmt.execute();

			stmt.close();
			con.close();

			System.out.println("Forma Pagamento: Cadastro realizado");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// Inserindo na tabela files

		for (int x = 0; x < caminhos.size(); x++) {

			try {
				Connection con = conectar();
				sql = "insert into files(pagamentosdiversos_id, caminho) values(?,?)";
				PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setInt(1, pagamentosDiversosId);
				stmt.setString(2, caminhos.get(x));

				stmt.execute();

				stmt.close();
				con.close(); 

				System.out.println("Caminho " + x + ": Cadastro realizado");

			} catch (SQLException e) {

				System.out.println("Erro: " + e);

			}

		}

	}

	public ArrayList<String> veirificaranexo(int id) { //OK
		ArrayList<String> caminhos = new ArrayList<>();
		String read = "select caminho from files where pagamentosdiversos_id = ?";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				caminhos.add(rs.getString("caminho"));
			}
			pst.close();
			con.close(); 
			rs.close();
			return caminhos;
		} catch (Exception e) {
			System.out.println("Erro:" + e);
			return null;
		}

	}

	public void comprasServicos(List<Itens> item, ComprasServicos colaborador) { //OK

		// Inserindo na tabela compras e servicos
		String sql = "insert into compraservicos(colaborador_id, justificativa, data, status, unidade) values(?,?,?,?,?)";
		int compraServicos_Id = 0;
		String data = colaborador.getData();
		String ano = data.substring(6, 10);
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		String newData = ano + "/" + mes + "/" + dia;

		try {
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, colaborador.getId());
			stmt.setString(2, colaborador.getJustificativa());
			stmt.setString(3, newData);
			stmt.setString(4, colaborador.getStatus());
			stmt.setString(5, colaborador.getUnidade());
			stmt.execute();
			stmt.close();
			con.close();
			// System.out.println(null, "Cadastro realizado");

		} catch (SQLException e) {

			// JOptionPane.showConfirmDialog(null, "" + e);
			System.out.println("Erro: " + e);

		}
		// obtendo o id do ultimo registro
		try {
			Connection con = conectar();
			sql = "select id from compraservicos where colaborador_id=? order by id desc limit 1";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, colaborador.getId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				compraServicos_Id = rs.getInt("id");

			} else {

				System.out.println("CompraServicos_id não encontrado");
			}
			stmt.close();
			con.close(); 
			rs.close();
		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		// Inserindo na tabela item
		try {
			Connection con = conectar();
			sql = "insert into item(compraServicos_id, quantidade, produto, especificacao) values(?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);

			for (Itens i : item) {
				stmt.setInt(1, compraServicos_Id);
				// System.out.println("info:" + Integer.toString(compraServicos_Id));
				stmt.setString(2, i.getQuantidade());
				// System.out.println("info:" + i.getQuantidade());
				stmt.setString(3, i.getItem());
				// System.out.println("info:" + i.getItem());
				stmt.setString(4, i.getEspecificacao());
				// System.out.println("info:" + i.getEspecificacao());
				stmt.execute();

			}
			stmt.close();
			con.close();

			System.out.println("Cadastro realizado");

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

	}

	

	public Usuario consultaUsuario(String usuario, String senha) { //OK

		Usuario user = new Usuario();
		try {
			String sql = "SELECT colaborador.id AS colaboradorid, colaborador.nome AS nomecolaborador,"
					+ "  colaborador.cidade, " + "  colaborador.setor, " + "  colaborador.email, "
					+ "  colaborador.estabelecimento, " + "  usuario.id AS usuarioid, "
					+ "  usuario.usuario AS usuariologin, " + "  usuario.logado, " + "  usuario.senha AS senha, "
					+ "  usuario.perfil AS perfil, " + "  usuario.hash AS hash " + " FROM usuario "
					+ " JOIN colaborador " + "  ON colaborador.id = usuario.colaborador_id" + "  AND usuario.usuario =?"
					+ "  AND usuario.senha = md5(?)";
			Connection con = conectar();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, usuario);
			stmt.setString(2, senha);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				user.setUsuarioId(rs.getInt("usuarioid"));
				user.setColaboradorId(rs.getInt("colaboradorid"));
				user.setUsuarioId(rs.getInt("usuarioid"));
				user.setUsuario(rs.getString("usuariologin"));
				user.setSenha(rs.getString("senha"));
				user.setPerfil(rs.getString("perfil"));
				user.setCidade(rs.getString("cidade"));
				user.setSetor(rs.getString("setor"));
				user.setEmail(rs.getString("email"));
				user.setNome(rs.getString("nomecolaborador"));
				user.setHash(rs.getString("hash"));
				user.setEstabelecimento(rs.getString("estabelecimento"));
				user.setLogado(rs.getInt("logado"));
				// System.out.println("Info: " + rs.getString("hash"));

				// nome = rs.getString("nome");

			} else {

				System.out.println("Não encontrado");
			}
			stmt.close();
			con.close(); 
			rs.close();
			return user;

		} catch (SQLException e) {

			System.out.println("Erro: " + e);

		}

		return null;

	}

	public void testeConexao() {

		try {
			Connection con = conectar();
			System.out.println("Conectado com sucesso");
			con.close(); 
		} catch (Exception e) {

			System.out.println(e);

		}

	}

}
