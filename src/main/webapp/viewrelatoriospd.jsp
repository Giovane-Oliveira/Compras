<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="model.Colaborador"%>
<%@ page import="model.DAO"%>
 <%@ page import="java.util.ArrayList" %>
  <%@ page import="model.Usuario"%>
   <%@ page import="model.PagamentosDiversos"%>
            <%@ page import="java.util.List"%>

<%! Usuario user = new Usuario(); %>


<%
String[] nomeSobrenome = null;
String retorno = (String) request.getAttribute("retorno");
ArrayList<PagamentosDiversos> lista = null;
ArrayList<String> caminhos = null;

if(user == null){
	%> 	
	<jsp:forward page="login.jsp" />
<% }else{
	
	 user = (Usuario) session.getAttribute("usuario");
	//out.println(user.getNome());
    lista = (ArrayList<PagamentosDiversos>) request.getAttribute("pagamentosDiversosRelatorio");
   nomeSobrenome = user.getNome().split(" ");
	
	
} %>	
  
<!doctype html>
<html lang="pt-br">
  <head>
  	<title>Compras</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/x-icon"
	href="https://grupoprovida.com.br/wp-content/uploads/2023/12/fav-icon2-150x150.png"
	sizes="32x32" />
    <link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900" rel="stylesheet">
		 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  

  
  
  </head>
  <body>		
		<div class="wrapper d-flex align-items-stretch">
			<nav id="sidebar">
				<div class="custom-menu">
					<button type="button" id="sidebarCollapse" class="btn btn-primary">
	          <i class="fa fa-bars"></i>
	          <span class="sr-only">Toggle Menu</span>
	        </button>
        </div>
	  		<h1><a href="index.jsp" class="logo">Compras</a></h1>
        <ul class="list-unstyled components mb-5">
        <li class="active">
          <% if(nomeSobrenome.length == 1) {%>
            <a href="#"><span class="fa fa-user mr-3"></span><% out.println(nomeSobrenome[0]);%></a>
            <% }else{ 
            
            	if(nomeSobrenome[1].equalsIgnoreCase("da") || nomeSobrenome[1].equalsIgnoreCase("de") || nomeSobrenome[1].equalsIgnoreCase("do") || nomeSobrenome[1].equalsIgnoreCase("dos")){
            		
            		nomeSobrenome[1] = nomeSobrenome[2];
            		
            	}
            
            %>
             <a href="#"><span class="fa fa-user mr-3"></span><% out.println(nomeSobrenome[0] + " " + nomeSobrenome[1]);%></a>
             <% } %>
          </li> 
          <li>
              <a href="index.jsp"><span class="fa fa-home mr-3" ></span>Dashboard </a>
          </li>
          <li>
            <a href="compraservicos.jsp" ><span class="fa fa-sticky-note mr-3"></span>Compras/Serviços</a>
          </li>
          <li>
            <a href="pagamentosdiversos.jsp" ><span class="fa fa-sticky-note mr-3"></span>Pgtos/Diversos</a>
          </li>
        
          <% if(user.getSetor().equalsIgnoreCase("T.I")){ %>
				<li><a href="cadastro.jsp"><span class="fa fa-address-book mr-3" ></span>Cadastro</a></li>
				<% } %>
          <li>    
            <a href="logout.jsp"><span class="fa fa-sign-out mr-3" ></span>Logout</a>
          </li>
          
        </ul>

    	</nav>

        <!-- Page Content  -->
      <div id="content" class="p-4 p-md-5 pt-5">
 <h4 class='mb-6'>Relatório de Solicitações de Pagamentos Diversos</h4>
 


			<style>
				tr, td {
						border: none;
						}
			</style>

<% if(lista != null){ %>
<br>
            <table id="tabela">
	

			<%
				for (int i = 0; i < lista.size(); i++) { 
					
					System.out.println(lista.get(i).getEstabelecimento() );
			%>
					<% if(user.getPerfil().equals("supervisor") && lista.get(i).getCidade().equals("Cachoeira do Sul") && user.getSetor().equals(lista.get(i).getSetor())){ %>
				
				
		<table class="table" id="tabela" ">
			<tr>
				<td colspan="6" class="table-warning" style="text-align: left"><label><b>ID:</b> <%= lista.get(i).getId() %> &nbsp;<b>Solicitante:</b> <%= lista.get(i).getNome() %>
				&nbsp;<b>Setor: </b><%= lista.get(i).getSetor() %> &nbsp; <b>Unidade: </b><%=  lista.get(i).getEstabelecimento() %> &nbsp; <br><b>Empresa: </b><%= lista.get(i).getEmpresa() %></label></td>
			</tr>
				<tr>
						   <td class="table-success"><label><b>Status:</b> <%= lista.get(i).getStatus() %> </label> &nbsp; <label><b>Data:</b> <%= new DAO().BrazilianData(lista.get(i).getData()) %></label></td>
		   </tr>
		 <tr>
		   		<td class="table-success"><label><b>Descrição:</b> <%= lista.get(i).getDescricao() %></label></td>
		  	 </tr>
		   <tr>
		   <td class="table-success"><label><b>Fornecedor:</b> <%= lista.get(i).getFornecedor() %> </label></label></td>

		   </tr>
	
		   	 
		   	 
			
			<tr>	
				<td class="table-success"><label><b>Vencimento:</b> <%= lista.get(i).getVencimento() %></label>  &nbsp; <label><b>Valor:</b> <%= lista.get(i).getValor() %></label></td>	
		
			</tr>

				 <% if(lista.get(i).getGerente() != null) { %>
				<tr>
				<td class="table-success"><label><b>Gerente:</b> <%= lista.get(i).getGerente() %></label></td>
				</tr>
				<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getGerentehash() %></label></td>
				</tr>
				<% } %>
				
	
			
			
				
				
				 <% if(lista.get(i).getSupervisor() != null) { %>
				  <tr>
				<td class="table-success"><label><b>Supervisor:</b> <%= lista.get(i).getSupervisor() %></label></td>
					</tr>
					<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getSupervisorhash() %> </label></td>
					</tr>
				<% } %>
				
		
			

		   <tr>
					<td class="table-success"><label><b>Forma Pagamento:</b> <%= lista.get(i).getFormaPagamento() %> </label></td>
		   </tr>
			
					<% if(lista.get(i).getFormaPagamento().equalsIgnoreCase("deposito")){ %>  
							
													
			<tr>
				
				<td class="table-success"><label><b>Banco:</b> <%=lista.get(i).getBanco() %> </label> &nbsp; <label><b>Agência:</b> <%= lista.get(i).getAgencia() %> </label> &nbsp; <label><b>C/C:</b> <%= lista.get(i).getContacorrente() %> </label> </td>
	       
			</tr>
							
				<% } else if(lista.get(i).getFormaPagamento().equalsIgnoreCase("pix")){ %>
							
			<tr>
				<% if(lista.get(i).getCpf() != null){ %>			
				 <td class="table-success"><label><b>CPF:</b> <%=lista.get(i).getCpf()%> </label></td>
				<% }else if(lista.get(i).getCnpj() != null){ %>
				 <td class="table-success"><label><b>CNPJ:</b> <%=lista.get(i).getCnpj() %> </label></td>		
				 <% }else if(lista.get(i).getEmail() != null){ %>
				 <td class="table-success"><label><b>Email:</b> <%=lista.get(i).getEmail()%> </label></td>	
				 <% }else if(lista.get(i).getTelefone() != null){ %>
				  <td class="table-success"><label><b>Telefone:</b> <%=lista.get(i).getTelefone()%> </label></td>	
				 <% } %>	
			</tr>	
							
							
						<% } %>
			
			  	<% // out.println("Unidade: " + user.getNome()); %>
	  	<% // out.println("Unidade: " + lista.get(i).getNome()); %>
				<% 
			  	caminhos  = new DAO().veirificaranexo(lista.get(i).getId());
			  	if(caminhos.size() > 0){ System.out.println("Tamanho lista:" + caminhos.size()); %>
			  	<tr>
			  	<td class="table-success">
			  	<% for(int x = 0; x < caminhos.size(); x++){ %>
			  	<% if(x == 0) {%>
			  <a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo </a>  &nbsp; 
			  	<% }else{ %>
				<a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo <%= x %></a>  &nbsp;	
				
				<% }}} %>
				</td>
				</tr>
			   <% if(!lista.get(i).getStatus().equals("aprovado") && !lista.get(i).getStatus().equals("recusado")){ %>	
				
	<tr>
				
					<% if(lista.get(i).getCidade().equals("Cachoeira do Sul")){ %>
					
							<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getSetor().equals(lista.get(i).getSetor())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
					
					<% } } else{ %>
	  
			<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getCidade().equals(lista.get(i).getCidade())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
				
					
				
			<% } } %>
		
		</tr>
	<% } %>


		</table>

			<% }else if(user.getPerfil().equals("colaborador") && user.getNome().equals(lista.get(i).getNome())){ %>
		
			<table class="table" id="tabela" ">
			<tr>
				<td colspan="6" class="table-warning" style="text-align: left"><label><b>ID:</b> <%= lista.get(i).getId() %> &nbsp;<b>Solicitante:</b> <%= lista.get(i).getNome() %>
				&nbsp;<b>Setor: </b><%= lista.get(i).getSetor() %> &nbsp; <b>Unidade: </b><%=  lista.get(i).getEstabelecimento() %> &nbsp; <br><b>Empresa: </b><%= lista.get(i).getEmpresa() %></label></td>
			</tr>
				<tr>
						   <td class="table-success"><label><b>Status:</b> <%= lista.get(i).getStatus() %> </label> &nbsp; <label><b>Data:</b> <%= new DAO().BrazilianData(lista.get(i).getData()) %></label></td>
		   </tr>
		 <tr>
		   		<td class="table-success"><label><b>Descrição:</b> <%= lista.get(i).getDescricao() %></label></td>
		  	 </tr>
		   <tr>
		   <td class="table-success"><label><b>Fornecedor:</b> <%= lista.get(i).getFornecedor() %> </label></label></td>

		   </tr>
	
		   	 
		   	 
			
			<tr>	
				<td class="table-success"><label><b>Vencimento:</b> <%= lista.get(i).getVencimento() %></label>  &nbsp; <label><b>Valor:</b> <%= lista.get(i).getValor() %></label></td>	
		
			</tr>

				 <% if(lista.get(i).getGerente() != null) { %>
				<tr>
				<td class="table-success"><label><b>Gerente:</b> <%= lista.get(i).getGerente() %></label></td>
				</tr>
				<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getGerentehash() %></label></td>
				</tr>
				<% } %>
				
	
			
			
				
				
				 <% if(lista.get(i).getSupervisor() != null) { %>
				  <tr>
				<td class="table-success"><label><b>Supervisor:</b> <%= lista.get(i).getSupervisor() %></label></td>
					</tr>
					<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getSupervisorhash() %> </label></td>
					</tr>
				<% } %>
				
		
			

		   <tr>
					<td class="table-success"><label><b>Forma Pagamento:</b> <%= lista.get(i).getFormaPagamento() %> </label></td>
		   </tr>
			
					<% if(lista.get(i).getFormaPagamento().equalsIgnoreCase("deposito")){ %>  
							
													
			<tr>
				
				<td class="table-success"><label><b>Banco:</b> <%=lista.get(i).getBanco() %> </label> &nbsp; <label><b>Agência:</b> <%= lista.get(i).getAgencia() %> </label> &nbsp; <label><b>C/C:</b> <%= lista.get(i).getContacorrente() %> </label> </td>
	       
			</tr>
							
				<% } else if(lista.get(i).getFormaPagamento().equalsIgnoreCase("pix")){ %>
							
			<tr>
				<% if(lista.get(i).getCpf() != null){ %>			
				 <td class="table-success"><label><b>CPF:</b> <%=lista.get(i).getCpf()%> </label></td>
				<% }else if(lista.get(i).getCnpj() != null){ %>
				 <td class="table-success"><label><b>CNPJ:</b> <%=lista.get(i).getCnpj() %> </label></td>		
				 <% }else if(lista.get(i).getEmail() != null){ %>
				 <td class="table-success"><label><b>Email:</b> <%=lista.get(i).getEmail()%> </label></td>	
				 <% }else if(lista.get(i).getTelefone() != null){ %>
				  <td class="table-success"><label><b>Telefone:</b> <%=lista.get(i).getTelefone()%> </label></td>	
				 <% } %>	
			</tr>	
							
							
						<% } %>
			
			  	<% // out.println("Unidade: " + user.getNome()); %>
	  	<% // out.println("Unidade: " + lista.get(i).getNome()); %>
				<% 
			  	caminhos  = new DAO().veirificaranexo(lista.get(i).getId());
			  	if(caminhos.size() > 0){ System.out.println("Tamanho lista:" + caminhos.size()); %>
			   	<tr>
			  	<td class="table-success">
			  	<% for(int x = 0; x < caminhos.size(); x++){ %>
			  	<% if(x == 0) {%>
			  <a href="download?caminho=<%= caminhos.get(x) %>" class="badge badge-dark" target="_blank">Visualizar anexo </a>  &nbsp; 
			  	<% }else{ %>
				<a href="download?caminho=<%= caminhos.get(x) %>" class="badge badge-dark" target="_blank">Visualizar anexo <%= x %></a>  &nbsp;	
				
				<% }}} %>
				</td>
				</tr>
			   <% if(!lista.get(i).getStatus().equals("aprovado") && !lista.get(i).getStatus().equals("recusado")){ %>	
				
				<tr>
				
					<% if(lista.get(i).getCidade().equals("Cachoeira do Sul")){ %>
					
							<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getSetor().equals(lista.get(i).getSetor())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
					
					<% } } else{ %>
	  
			<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getCidade().equals(lista.get(i).getCidade())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
				
					
				
			<% } } %>
		
		</tr>
	<% } %>


		</table>

		
			
				<% }else if(user.getPerfil().equals("supervisor") && user.getCidade().equals(lista.get(i).getCidade())){ %>
				
			
		
		<table class="table" id="tabela" ">
			<tr>
				<td colspan="6" class="table-warning" style="text-align: left"><label><b>ID:</b> <%= lista.get(i).getId() %> &nbsp;<b>Solicitante:</b> <%= lista.get(i).getNome() %>
				&nbsp;<b>Setor: </b><%= lista.get(i).getSetor() %> &nbsp; <b>Unidade: </b><%=  lista.get(i).getEstabelecimento() %> &nbsp; <br><b>Empresa: </b><%= lista.get(i).getEmpresa() %></label></td>
			</tr>
				<tr>
						   <td class="table-success"><label><b>Status:</b> <%= lista.get(i).getStatus() %> </label> &nbsp; <label><b>Data:</b> <%= new DAO().BrazilianData(lista.get(i).getData()) %></label></td>
		   </tr>
		 <tr>
		   		<td class="table-success"><label><b>Descrição:</b> <%= lista.get(i).getDescricao() %></label></td>
		  	 </tr>
		   <tr>
		   <td class="table-success"><label><b>Fornecedor:</b> <%= lista.get(i).getFornecedor() %> </label></label></td>

		   </tr>
	
		   	 
		   	 
			
			<tr>	
				<td class="table-success"><label><b>Vencimento:</b> <%= lista.get(i).getVencimento() %></label>  &nbsp; <label><b>Valor:</b> <%= lista.get(i).getValor() %></label></td>	
		
			</tr>

				 <% if(lista.get(i).getGerente() != null) { %>
				<tr>
				<td class="table-success"><label><b>Gerente:</b> <%= lista.get(i).getGerente() %></label></td>
				</tr>
				<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getGerentehash() %></label></td>
				</tr>
				<% } %>
				
	
			
			
				
				
				 <% if(lista.get(i).getSupervisor() != null) { %>
				  <tr>
				<td class="table-success"><label><b>Supervisor:</b> <%= lista.get(i).getSupervisor() %></label></td>
					</tr>
					<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getSupervisorhash() %> </label></td>
					</tr>
				<% } %>
				
		
			

		   <tr>
					<td class="table-success"><label><b>Forma Pagamento:</b> <%= lista.get(i).getFormaPagamento() %> </label></td>
		   </tr>
			
					<% if(lista.get(i).getFormaPagamento().equalsIgnoreCase("deposito")){ %>  
							
													
			<tr>
				
				<td class="table-success"><label><b>Banco:</b> <%=lista.get(i).getBanco() %> </label> &nbsp; <label><b>Agência:</b> <%= lista.get(i).getAgencia() %> </label> &nbsp; <label><b>C/C:</b> <%= lista.get(i).getContacorrente() %> </label> </td>
	       
			</tr>
							
				<% } else if(lista.get(i).getFormaPagamento().equalsIgnoreCase("pix")){ %>
							
			<tr>
				<% if(lista.get(i).getCpf() != null){ %>			
				 <td class="table-success"><label><b>CPF:</b> <%=lista.get(i).getCpf()%> </label></td>
				<% }else if(lista.get(i).getCnpj() != null){ %>
				 <td class="table-success"><label><b>CNPJ:</b> <%=lista.get(i).getCnpj() %> </label></td>		
				 <% }else if(lista.get(i).getEmail() != null){ %>
				 <td class="table-success"><label><b>Email:</b> <%=lista.get(i).getEmail()%> </label></td>	
				 <% }else if(lista.get(i).getTelefone() != null){ %>
				  <td class="table-success"><label><b>Telefone:</b> <%=lista.get(i).getTelefone()%> </label></td>	
				 <% } %>	
			</tr>	
							
							
						<% } %>
			
			  	<% // out.println("Unidade: " + user.getNome()); %>
	  	<% // out.println("Unidade: " + lista.get(i).getNome()); %>
				<% 
			  	caminhos  = new DAO().veirificaranexo(lista.get(i).getId());
			  	if(caminhos.size() > 0){ System.out.println("Tamanho lista:" + caminhos.size()); %>
			   	<tr>
			  	<td class="table-success">
			  	<% for(int x = 0; x < caminhos.size(); x++){ %>
			  	<% if(x == 0) {%>
			  <a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo </a>  &nbsp; 
			  	<% }else{ %>
				<a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo <%= x %></a>  &nbsp;	
				
				<% }}} %>
				</td>
				</tr>
			   <% if(!lista.get(i).getStatus().equals("aprovado") && !lista.get(i).getStatus().equals("recusado")){ %>	
				
				<tr>
				
					<% if(lista.get(i).getCidade().equals("Cachoeira do Sul")){ %>
					
							<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getSetor().equals(lista.get(i).getSetor())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
					
					<% } } else{ %>
	  
			<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getCidade().equals(lista.get(i).getCidade())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
				
					
				
			<% } } %>
		
		</tr>
	<% } %>


		</table>

		
			
		<% } else{ %>
		
		
		
	<table class="table" id="tabela" ">
			<tr>
				<td colspan="6" class="table-warning" style="text-align: left"><label><b>ID:</b> <%= lista.get(i).getId() %> &nbsp;<b>Solicitante:</b> <%= lista.get(i).getNome() %>
				&nbsp;<b>Setor: </b><%= lista.get(i).getSetor() %> &nbsp; <b>Unidade: </b><%=  lista.get(i).getEstabelecimento() %> &nbsp; <br><b>Empresa: </b><%= lista.get(i).getEmpresa() %></label></td>
			</tr>
				<tr>
						   <td class="table-success"><label><b>Status:</b> <%= lista.get(i).getStatus() %> </label> &nbsp; <label><b>Data:</b> <%= new DAO().BrazilianData(lista.get(i).getData()) %></label></td>
		   </tr>
		 <tr>
		   		<td class="table-success"><label><b>Descrição:</b> <%= lista.get(i).getDescricao() %></label></td>
		  	 </tr>
		   <tr>
		   <td class="table-success"><label><b>Fornecedor:</b> <%= lista.get(i).getFornecedor() %> </label></label></td>

		   </tr>
	
		   	 
		   	 
			
			<tr>	
				<td class="table-success"><label><b>Vencimento:</b> <%= lista.get(i).getVencimento() %></label>  &nbsp; <label><b>Valor:</b> <%= lista.get(i).getValor() %></label></td>	
		
			</tr>

				 <% if(lista.get(i).getGerente() != null) { %>
				<tr>
				<td class="table-success"><label><b>Gerente:</b> <%= lista.get(i).getGerente() %></label></td>
				</tr>
				<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getGerentehash() %></label></td>
				</tr>
				<% } %>
				
	
			
			
				
				
				 <% if(lista.get(i).getSupervisor() != null) { %>
				  <tr>
				<td class="table-success"><label><b>Supervisor:</b> <%= lista.get(i).getSupervisor() %></label></td>
					</tr>
					<tr>
				<td class="table-success"><label><b>Hash:</b> <%= lista.get(i).getSupervisorhash() %> </label></td>
					</tr>
				<% } %>
				
		
			

		   <tr>
					<td class="table-success"><label><b>Forma Pagamento:</b> <%= lista.get(i).getFormaPagamento() %> </label></td>
		   </tr>
			
					<% if(lista.get(i).getFormaPagamento().equalsIgnoreCase("deposito")){ %>  
							
													
			<tr>
				
				<td class="table-success"><label><b>Banco:</b> <%=lista.get(i).getBanco() %> </label> &nbsp; <label><b>Agência:</b> <%= lista.get(i).getAgencia() %> </label> &nbsp; <label><b>C/C:</b> <%= lista.get(i).getContacorrente() %> </label> </td>
	       
			</tr>
							
				<% } else if(lista.get(i).getFormaPagamento().equalsIgnoreCase("pix")){ %>
							
			<tr>
				<% if(lista.get(i).getCpf() != null){ %>			
				 <td class="table-success"><label><b>CPF:</b> <%=lista.get(i).getCpf()%> </label></td>
				<% }else if(lista.get(i).getCnpj() != null){ %>
				 <td class="table-success"><label><b>CNPJ:</b> <%=lista.get(i).getCnpj() %> </label></td>		
				 <% }else if(lista.get(i).getEmail() != null){ %>
				 <td class="table-success"><label><b>Email:</b> <%=lista.get(i).getEmail()%> </label></td>	
				 <% }else if(lista.get(i).getTelefone() != null){ %>
				  <td class="table-success"><label><b>Telefone:</b> <%=lista.get(i).getTelefone()%> </label></td>	
				 <% } %>	
			</tr>	
							
							
						<% } %>
			
			  	<% // out.println("Unidade: " + user.getNome()); %>
	  	<% // out.println("Unidade: " + lista.get(i).getNome()); %>
				<% 
			  	caminhos  = new DAO().veirificaranexo(lista.get(i).getId());
			  	if(caminhos.size() > 0){ System.out.println("Tamanho lista:" + caminhos.size()); %>
			   	<tr>
			  	<td class="table-success">
			  	<% for(int x = 0; x < caminhos.size(); x++){ %>
			  	<% if(x == 0) {%>
			  <a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo </a>  &nbsp; 
			  	<% }else{ %>
				<a href="download?caminho=<%= caminhos.get(x) %>"  class="badge badge-dark"   target="_blank">Visualizar anexo <%= x %></a>  &nbsp;	
				
				<% }}} %>
				</td>
				</tr>
			   <% if(!lista.get(i).getStatus().equals("aprovado") && !lista.get(i).getStatus().equals("recusado")){ %>	
				
				<tr>
				
					<% if(lista.get(i).getCidade().equals("Cachoeira do Sul")){ %>
					
							<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getSetor().equals(lista.get(i).getSetor())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
					
					<% } } else{ %>
	  
			<%  if(user.getPerfil().equalsIgnoreCase("gerente") && lista.get(i).getSupervisor() != null && lista.get(i).getGerente() == null || user.getPerfil().equalsIgnoreCase("gerente") && user.getNome().equals(lista.get(i).getNome())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
		    <a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
	
				
				
			<% }  else if(user.getPerfil().equalsIgnoreCase("supervisor") && lista.get(i).getSupervisor() == null && lista.get(i).getGerente() == null && user.getCidade().equals(lista.get(i).getCidade())){ %>
			
			<td class="table-success"><a href="insertAdmPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-success btn-sm">Aceitar</button> </a> &nbsp;
			<a href="recusadoPagD?solicitacaoid=<%=lista.get(i).getId()%>"> <button  class="btn btn-danger btn-sm">Recusar</button> </a> </td>
				
					
				
			<% } } %>
		
		</tr>
	<% } %>


		</table>

	
<% } %>
 <% } }  %>


 <%if(user.getPerfil().equals("colaborador")){ %>
 
 <% if(lista.get(0).getStatus().equals("preaprovado")){ %> 
 
 <a href="mSP?status=preaprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if(user.getPerfil().equals("colaborador") && lista.get(0).getStatus().equals("aprovado")){ %>

<a href="mSP?status=aprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if(user.getPerfil().equals("colaborador") && lista.get(0).getStatus().equals("pendente")){ %>

<a href="mSP?status=pendente"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if(user.getPerfil().equals("colaborador") && lista.get(0).getStatus().equals("recusado")){ %>

<a href="mSP?status=recusado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% } %>
 
 <%}else if(user.getPerfil().equals("supervisor")){ %> 
 
 <% if(lista.get(0).getStatus().equals("preaprovado")){ %> 
 
 <a href="SPGlobal?status=preaprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("aprovado")){ %>

<a href="SPGlobal?status=aprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("pendente")){ %>

<a href="SPGlobal?status=pendente"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("recusado")){ %>

<a href="SPGlobal?status=recusado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% } %>
 
  <%}else if(user.getPerfil().equals("gerente")){ %>
  
  
   <% if(lista.get(0).getStatus().equals("preaprovado")){ %> 
 
 <a href="SPGlobal?status=preaprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("aprovado")){ %>

<a href="SPGlobal?status=aprovado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("pendente")){ %>

<a href="SPGlobal?status=pendente"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% }else if( lista.get(0).getStatus().equals("recusado")){ %>

<a href="SPGlobal?status=recusado"><button type="button"  class="btn btn-info" name="voltar">Voltar</button></a>

<% } %> 
  
   <% } %> 


      </div>
		</div>
		
		<script>
		
	    $("#datainicial, #datafinal").mask("00/00/0000");
		 
		 
	 	 $("#naogerarpdf").on("click", function() {
				
				// alert('Não gerar pdf');
		
						document.getElementById("imprimirpdf").innerHTML = "";
						document.getElementById("imprimirpdf").innerHTML = "" +
						"<input hidden name='pdf' type='text' class='form-control' id='inputEmail4' value='Naogerar'>\n";
						document.forms["formrelatorio"].setAttribute('target', '_self');
				  
			 });
			 
			 
			 $("#gerarpdf").on("click", function() {
					
				 //alert('Gerar Pdf');
		
						document.getElementById("imprimirpdf").innerHTML = "";
						document.getElementById("imprimirpdf").innerHTML = "" +
						"<input hidden name='pdf' type='text' class='form-control' id='inputEmail4' value='gerar'>\n";
						
						//
						//$('input').val("");  $('select').val("");
						setTimeout(function() {  window.location.reload(true);   }, 2000);
						
							//document.forms["formrelatorio"].submit()
						
						
						
				  
			 });
			
		 
		 
		 
		
		</script>

    <script src="js/jquery.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    
 
      <%  if(retorno == "Não encontrado" ){ %>
	
    		<script>
    
    			$(document).ready(function() {
       			 $('#notfound').modal('show');
    			})
    
    		</script>

		
			
<% }else if(retorno == "preaprovado" ){  %>
    
    <script>
    
    			$(document).ready(function() {
       			 $('#preaprovado').modal('show');
    			})
    
    		</script>

    
<% }else if(retorno == "aprovado" ){   %>

<script>
    
    			$(document).ready(function() {
       			 $('#aprovado').modal('show');
    			})
    
    		</script>



<% }  else if(retorno == "recusado" ){%>

<script>
    
    			$(document).ready(function() {
       			 $('#recusado').modal('show');
    			})
    
    		</script>


<% } %>
   
   <script type="text/javascript">
 
    		 $(":button").on("click", function() {
    			 var nome = $(this).attr('name');
    			 
    			 if(nome != "voltar"){
    				 
    					$(document).ready(function() {
    						 $('#carregando').modal('show');
    						 $('#divConsultarEmail').removeAttr('hidden');
    					})
    				 
    			 }
	
	});


</script>
				    <!-- Modal -->
<div class="modal fade" id="carregando" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <div hidden id="divConsultarEmail" class="form-group">
           <span class="badge badge-info">Aguarde...</span>
         </div> 
      </div>
      <!--  <div class="modal-footer">
       <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>      
      </div> -->
    </div>
  </div>
</div>	
  
    
    <!-- Modal -->
<div class="modal fade" id="notfound" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Dados não encontrados
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>

    <!-- Modal -->
<div class="modal fade" id="recusado" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
         Solicitação recusada com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>

    <!-- Modal -->
<div class="modal fade" id="preaprovado" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       Solicitação pré-aprovada com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>
 
    <!-- Modal -->
<div class="modal fade" id="aprovado" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       Solicitação aprovada com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>
    
    
    
    
    
    
  </body>
</html>