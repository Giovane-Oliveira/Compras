<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="model.Colaborador"%>
<%@ page import="model.DAO"%>
  <%@ page import="util.Responses"%>
  <%@ page import="model.Usuario"%>
  
  <%!
  
  Usuario user = new Usuario(); 
  
  %>
  
 <%  user = (Usuario) session.getAttribute("usuario"); %> 

<%
String token = (String) request.getAttribute("token");
//out.println(token);
String[] nomeSobrenome = user.getNome().split(" ");

%>


<%

if(user == null){
	%> 	
	<jsp:forward page="login.jsp" />
<% } %>	

  
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

              <li><a href="cadastro.jsp"><span class="fa fa-address-book mr-3" ></span>Cadastro</a></li>	   
          
          <li>    
            <a href="logout.jsp"><span class="fa fa-sign-out mr-3" ></span>Logout</a>
          </li>
      
          
        </ul>

    	</nav>

  
  
  <style>

#tokenDiv {
padding-top: 20px

}

</style>   

 
         
      <div id="content" class="p-4 p-md-5 pt-5">
       <h2 class='mb-4'>Cadastro de Usuários</h2>
      
      <form action="cadastrarToken">
  <div class="form-row">
        <div class="form-group col-md-4">
      <label for="inputState">Tipo usuário:</label>
      <select name="tipoUsuario" id="inputState" class="form-control" required>
        <option value="" selected>Selecione</option>
        <option value="colaborador">colaborador</option>
        <option value="supervisor">supervisor</option>
        <option value="gerente">gerente</option>
      </select>
    </div>      
  </div>
  <button type="submit" class="btn btn-info">Gerar Token</button>
</form>
  
    <% if(token != null){ %>    
<div id="tokenDiv" class="form-row">
		<input id="token" class="alert alert-success" height="100" role="alert" value=<%= token %> readonly>  </input> &nbsp; 
	<div>
	
  		<button id="txtButton" class="btn btn-secondary" onclick="copiar()">Copiar</button>
	</div>
</div>
<% } %>
</div>
  <script type="text/javascript">
  
  function copiar(){



	    // Seleciona o elemento de input text
	    var inputText = document.getElementById("token");

	    // Seleciona o texto dentro do elemento de input text
	    inputText.select();

	    // Copia o texto selecionado
	    document.execCommand("copy");

	    const btn = document.querySelectorAll('#token')

        btn.forEach(x => {
            x.addEventListener('click', () => x.innerHTML = "Leitura Confirmada")
        })
	    
	    // Alerta o usuário de que o texto foi copiado (opcional)
	    //alert("Texto copiado: " + inputText.value);

	  // Alert the copied text

	 // alert("Token copiado: " + copyText.value);  
	  
	  
  }
   </script>
   
   
   <% if(token != null){ %>
   
   		<script>
    
    			$(document).ready(function() {
       			 $('#notfound').modal('show');
    			})
    
    		</script>
   
   
   
   
   <% }  %>
   
   
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
          Token Gerado com sucesso!
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
         <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
        </div>
      </div>
    </div>
  </div>
    
    <script src="js/jquery.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    

  
  </body>
</html>