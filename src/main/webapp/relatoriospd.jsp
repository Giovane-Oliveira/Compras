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
  
  <!-- Datatable -->
 <!--  <script src="https://code.jquery.com/jquery-3.7.1.js"></script> -->
<script src="https://cdn.datatables.net/2.0.2/js/dataTables.js"></script>
<script src="https://cdn.datatables.net/2.0.2/css/dataTables.dataTables.css"></script>
  
  
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
        <h4 class='mb-6'> Relatório de Solicitações de Pagamentos Diversos </h4>
 

<% if(lista != null){ %>
<br>
<table id="example" class="table table-striped table-bordered" style="width:100%">


 <thead><tr> 
			   <th>Id</th>  
	           <th>Colaborador</th>  
	           <th>Setor</th>  
	           <th>Estabelecimento</th>  
	           <th>Unidade</th>  
	           <th>Status</th>  
	           <th>Data</th>  
	           <th>Visualizar</th> 
	           <th>Imprimir</th> 
	           </tr></thead><tbody>
	
			<%
				for (int i = 0; i < lista.size(); i++) {
			%>
						<% if(user.getPerfil().equals("supervisor") && lista.get(i).getCidade().equals("Cachoeira do Sul") && user.getSetor().equals(lista.get(i).getSetor())){ %>
				
        <tr>
       	   <td> <%=lista.get(i).getId() %> </td>  
           <td>  <%=lista.get(i).getNome() %>    </td>  
           <td>  <%=lista.get(i).getSetor() %>   </td>  
           <td>  <%=lista.get(i).getEstabelecimento() %>  </td>  
           <td>  <%=lista.get(i).getEmpresa() %>  </td>  
           <td>   <%=lista.get(i).getStatus() %>  </td>  
           <td>   <%= new DAO().BrazilianData(lista.get(i).getData()) %>   </td> 
					
<td><a href="relatorioPagamentosDiversos?pdf=naogerar&idSolicitacao=<%=lista.get(i).getId()%>"> <button ><i class="fa fa-eye"></i></button> </a></td>
<td><a href="relatorioPagamentosDiversos?pdf=gerar&idSolicitacao=<%=lista.get(i).getId()%>"  target="_blank"> <button ><i class="fa fa-print"></i></button> </a></td>      
			
			<% }else if(user.getPerfil().equals("colaborador") && user.getNome().equals(lista.get(i).getNome())){ %>
		

        <tr>
          <td> <%=lista.get(i).getId() %> </td>  
          <td>  <%=lista.get(i).getNome() %>    </td>  
          <td>  <%=lista.get(i).getSetor() %>   </td>  
          <td>  <%=lista.get(i).getEstabelecimento() %>  </td>  
          <td>  <%=lista.get(i).getEmpresa() %>  </td>  
          <td>   <%=lista.get(i).getStatus() %>  </td>  
          <td>   <%= new DAO().BrazilianData(lista.get(i).getData()) %>   </td> 
					
<td><a href="relatorioPagamentosDiversos?pdf=naogerar&idSolicitacao=<%=lista.get(i).getId()%>"> <button ><i class="fa fa-eye"></i></button> </a></td>
<td><a href="relatorioPagamentosDiversos?pdf=gerar&idSolicitacao=<%=lista.get(i).getId()%>"  target="_blank"> <button ><i class="fa fa-print"></i></button> </a></td>                     
   
 
					
			<% }else if(user.getPerfil().equals("supervisor") && user.getCidade().equals(lista.get(i).getCidade())){ %>
			
        <tr>
          <td> <%=lista.get(i).getId() %> </td>  
          <td>  <%=lista.get(i).getNome() %>    </td>  
          <td>  <%=lista.get(i).getSetor() %>   </td>  
          <td>  <%=lista.get(i).getEstabelecimento() %>  </td>  
          <td>  <%=lista.get(i).getEmpresa() %>  </td>  
          <td>   <%=lista.get(i).getStatus() %>  </td>  
          <td>   <%= new DAO().BrazilianData(lista.get(i).getData()) %>   </td> 
					
<td><a href="relatorioPagamentosDiversos?pdf=naogerar&idSolicitacao=<%=lista.get(i).getId()%>"> <button ><i class="fa fa-eye"></i></button> </a></td>
<td><a href="relatorioPagamentosDiversos?pdf=gerar&idSolicitacao=<%=lista.get(i).getId()%>"  target="_blank"> <button ><i class="fa fa-print"></i></button> </a></td>      


			
			<% }else { %>
			
        <tr>
          <td> <%=lista.get(i).getId() %> </td>  
          <td>  <%=lista.get(i).getNome() %>    </td>  
          <td>  <%=lista.get(i).getSetor() %>   </td>  
          <td>  <%=lista.get(i).getEstabelecimento() %>  </td>  
          <td>  <%=lista.get(i).getEmpresa() %>  </td>  
          <td>   <%=lista.get(i).getStatus() %>  </td>  
          <td>   <%= new DAO().BrazilianData(lista.get(i).getData()) %>   </td> 
					
<td><a href="relatorioPagamentosDiversos?pdf=naogerar&idSolicitacao=<%=lista.get(i).getId()%>"> <button ><i class="fa fa-eye"></i></button> </a></td>
<td><a href="relatorioPagamentosDiversos?pdf=gerar&idSolicitacao=<%=lista.get(i).getId()%>"  target="_blank"> <button ><i class="fa fa-print"></i></button> </a></td>      	
			
			<% } %>
			
			
		
 <% } %></tr></tbody></table> <% } %>

 <!-- <nav aria-label="Page navigation example">
  <ul class="pagination">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
  </ul>
</nav> -->

      </div>
      
		</div>
		
		<script>
		//let table = new DataTable('#example');
		
		var table = new DataTable('#example', {
    language: {
        url: '//cdn.datatables.net/plug-ins/2.0.3/i18n/pt-BR.json',
    },
    
    order: 'desc'
});
		
		
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