<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="model.Colaborador"%>
<%@ page import="model.DAO"%>

  <%@ page import="model.Usuario"%>
  
  <%!
  
  Usuario user = new Usuario(); 
  
  %>
  
 <%  
 String retorno = (String) request.getAttribute("retorno");
 
 user = (Usuario) session.getAttribute("usuario"); %> 

<%

	
//Minhas Solicitações de compras e serviços
	int scAprovada = 0;
	int scPreAprovada = 0;

	int scPendente = 0;
	int scRecusada = 0;

	//Minhas Solicitações de pagamentos diversos
	int spAprovada = 0;
	int spPreAprovada = 0;
	int spPendente = 0;
	int spRecusado = 0;

	int scAprovadaGlobal = 0;
	int scPreAprovadaGlobal = 0;
	int scPendenteGlobal = 0;
	int  scRecusadaGlobal = 0;

	//Minhas Solicitações de pagamentos diversos global
	int  spAprovadaGlobal = 0;
	int  spPreAprovadaGlobal = 0;
	int  spPendenteGlobal = 0;
	int  spRecusadoGlobal = 0;	 	



String[] nomeSobrenome = null;

%>


<%

if(user == null){
	%> 	
	<jsp:forward page="login.jsp" />
<% }else{
	
	nomeSobrenome = user.getNome().split(" ");

	 scAprovada = new DAO().verificarListaSC(user.getColaboradorId(), "aprovado");
	 scPreAprovada = new DAO().verificarListaSC(user.getColaboradorId(), "preaprovado");

	 scPendente = new DAO().verificarListaSC(user.getColaboradorId(), "pendente");
	 scRecusada = new DAO().verificarListaSC(user.getColaboradorId(), "recusado");

	//Minhas Solicitações de pagamentos diversos
  spAprovada = new DAO().verificarListaPagD(user.getColaboradorId(), "aprovado");
  spPreAprovada = new DAO().verificarListaPagD(user.getColaboradorId(), "preaprovado");
  spPendente = new DAO().verificarListaPagD(user.getColaboradorId(), "pendente");
  spRecusado = new DAO().verificarListaPagD(user.getColaboradorId(), "recusado");
  
  scAprovadaGlobal = new DAO().verificarSCEstab("aprovado",user.getCidade(),user.getEstabelecimento(),user.getSetor(), 0);
  scPreAprovadaGlobal = new DAO().verificarSCEstab("preaprovado",user.getCidade(),user.getEstabelecimento(),user.getSetor(), 0);
  scPendenteGlobal = new DAO().verificarSCEstab("pendente", user.getCidade(), user.getEstabelecimento(),user.getSetor(), 0);
  scRecusadaGlobal = new DAO().verificarSCEstab("recusado",user.getCidade(), user.getEstabelecimento(), user.getSetor(), 0);

	//Minhas Solicitações de pagamentos diversos global
  spAprovadaGlobal = new DAO().verificarEstabPagD("aprovado", user.getCidade(), user.getEstabelecimento(), user.getSetor(), 0);
  spPreAprovadaGlobal = new DAO().verificarEstabPagD("preaprovado", user.getCidade(), user.getEstabelecimento(), user.getSetor(), 0);
  spPendenteGlobal = new DAO().verificarEstabPagD("pendente", user.getCidade(), user.getEstabelecimento(), user.getSetor(), 0);
  spRecusadoGlobal = new DAO().verificarEstabPagD("recusado", user.getCidade(), user.getEstabelecimento(), user.getSetor(), 0);	 	
	
	
	if(user.getPerfil().equals("gerente")){
		
		 scAprovadaGlobal = new DAO().verificarListaSC("aprovado");
		 scPreAprovadaGlobal =  new DAO().verificarListaSC("preaprovado");
		   scPendenteGlobal = new DAO().verificarListaSC("pendente");
		   scRecusadaGlobal =  new DAO().verificarListaSC("recusado");

	//Minhas Solicitações de pagamentos diversos global
	  spAprovadaGlobal = new DAO().verificarListaPagD("aprovado");
	  spPreAprovadaGlobal = new DAO().verificarListaPagD("preaprovado");
	  spPendenteGlobal = new DAO().verificarListaPagD("pendente");
	  spRecusadoGlobal = new DAO().verificarListaPagD("recusado");
		  
		
	}
	
	
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
		
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  <link
        href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'
        rel='stylesheet'>
<link
        href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'
        rel='stylesheet'>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

         <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
  
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
          
          <li><a href="index.jsp"><span class="fa fa-home mr-3" ></span>Dashboard </a></li>
          <li><a href="compraservicos.jsp" ><span class="fa fa-sticky-note mr-3"></span>Compras/Serviços</a></li>
          <li><a href="pagamentosdiversos.jsp" ><span class="fa fa-sticky-note mr-3"></span>Pgtos/Diversos</a></li>
		  <% if(user.getSetor().equalsIgnoreCase("T.I")){ %>
				<li><a href="cadastro.jsp"><span class="fa fa-address-book mr-3" ></span>Cadastro</a></li>
				<% } %>
          <li><a href="logout.jsp"><span class="fa fa-sign-out mr-3" ></span>Logout</a></li>
          
          
        </ul>

    	</nav>

	<!--   
	<div id="content" class="p-4 p-md-5 pt-5">
       <h2 class='mb-4'>Bem vindo ao sistema de requisição de compras e serviços</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit officia deserunt mollit anim id est laborum.</p>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore </p>
      </div> -->

        <!-- Page Content  -->
         
      <div id="content" class="p-4 p-md-5 pt-5">
      
      
      
      
      <!-- Button trigger modal -->
<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModalLong">
  Info
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Bem-vindo!</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        
        
        
           <img alt="" src="./images/logo_oficial.jpg" with="250" height="130" style="margin-top: -15px!important">
       <h2 class='mb-4' style="margin-top: -35px!important">Sistema de requisição de compras e serviços</h2>
      
      <blockquote >

    <p  class="font-weight-light">Por aqui, você poderá solicitar requisição de compras e autorização de pagamentos. Veja como funciona o <b><i>workflow do sistema:</i></b></p> 
<p  class="font-weight-light">O <b>colaborador</b>, irá solicitar e o <b>supervisor</b> da unidade e/ou setor irá pré-aprovar/recusar antes do aceite ou recusa do gerente. <br>
  As solicitações efetuadas pelo próprio <b>gerente</b>, após o aceite do mesmo, irão para categoria "APROVADOS". Porém do <b>supervisor</b>, irá para "PRÉ-APROVADOS".<br>
  Toda e qualquer solicitação efetuada, ficará documentada no sistema.</p>
  
  <footer class="blockquote-footer">Texto elaborado pela <cite title="Source Title">Equipe de T.I Próvida </cite></footer>
</blockquote>
        
        
        
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
      <!-- <button type="button" class="btn btn-primary">Save changes</button> -->  
      </div>
    </div>
  </div>
</div>
      
      
      
      
      
      
      
      
   
      
     
  
       <% if(user.getPerfil().equals("colaborador")) {%>
        <h4 class='mb-4'>Minhas solicitações de compras e serviços</h4>
     <div class="form-row">
    <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/aproved-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSC?status=aprovado" class="btn btn-success">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scAprovada)); %></span></a>
  				</div>
			</div>
     	 </div>
      
         <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/waiting-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PRÉ-APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSC?status=preaprovado" class="btn btn-primary">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scPreAprovada)); %></span></a>
  				</div>
			</div>
     	 </div>
      
        <div class="form-group col-md-3">
			<div class="card" style="width: 96%;">
  				<img class="card-img-top" src="./images/pendent-folder-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PENDENTES</h5>
    			<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSC?status=pendente" class="btn btn-warning">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scPendente)); %></span></a>
  				</div>
			</div>
     	 </div>
    
    	<div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/danied-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">REPROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSC?status=recusado" class="btn btn-danger">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scRecusada)); %></span></a>
  				</div>
			</div>
     	</div> 
    
      </div>
      
      
          <h4 class='mb-4'>Minhas solicitações de pagamentos diversos</h4>
     <div class="form-row">
    <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  					<img class="card-img-top" src="./images/aproved-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSP?status=aprovado" class="btn btn-success">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spAprovada)); %></span></a>
  				</div>
			</div>
     	 </div>
      
         <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/waiting-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PRÉ-APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSP?status=preaprovado" class="btn btn-primary">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spPreAprovada)); %></span></a>
  				</div>
			</div>
     	 </div>
      
        <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/pendent-folder-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PENDENTES</h5>
    			<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSP?status=pendente" class="btn btn-warning">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spPendente)); %></span></a>
  				</div>
			</div>
     	 </div>
      
      	<div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/danied-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">REPROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="mSP?status=recusado" class="btn btn-danger">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spRecusado)); %></span></a>
  				</div>
			</div>
     	</div> 
      
      </div>
            
      
      <% } %>
      
      <% if(user.getPerfil().equals("gerente") || user.getPerfil().equals("supervisor")) { %>
      
         <h4 class='mb-4'>Solicitações de Compras e Serviços</h4>
     <div class="form-row">
    <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/aproved-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SCGlobal?status=aprovado" class="btn btn-success">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scAprovadaGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
         <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/waiting-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PRÉ-APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SCGlobal?status=preaprovado" class="btn btn-primary">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scPreAprovadaGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
        <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/pendent-folder-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PENDENTES</h5>
    			<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SCGlobal?status=pendente" class="btn btn-warning">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scPendenteGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
      	<div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/danied-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">REPROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SCGlobal?status=recusado" class="btn btn-danger">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(scRecusadaGlobal)); %></span></a>
  				</div>
			</div>
     	</div> 
      
      </div>
      
    
      
      
       <h4 class='mb-4'>Solicitações de Pagamentos Diversos</h4>
     <div class="form-row">
    <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/aproved-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SPGlobal?status=aprovado" class="btn btn-success">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spAprovadaGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
         <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/waiting-rafiki.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PRÉ-APROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SPGlobal?status=preaprovado" class="btn btn-primary">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spPreAprovadaGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
        <div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/pendent-folder-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">PENDENTES</h5>
    			<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SPGlobal?status=pendente" class="btn btn-warning">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spPendenteGlobal)); %></span></a>
  				</div>
			</div>
     	 </div>
      
      	<div class="form-group col-md-3">
			<div class="card" style="width: 95%;">
  				<img class="card-img-top" src="./images/danied-cuate.png" alt="Card image cap">
  				<div class="card-body">
   		    		<h5 class="card-title">REPROVADOS</h5>
    				<!-- <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>  -->
    				<a href="SPGlobal?status=recusado" class="btn btn-danger">Visualizar  <span class="badge badge-light"><% out.println(Integer.toString(spRecusadoGlobal)); %></span></a>
  				</div>
			</div>
     	</div> 
      
      </div>
      
      
      <% } %>
      
		</div>
		
		
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



<% }else if(retorno == "recusado" ){  %>

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
        <button type="button" class="btn btn-secondary" id="close" data-dismiss="modal">Close</button>
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
		
		
		
		

    <script src="js/jquery.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>