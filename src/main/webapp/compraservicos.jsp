<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="model.Colaborador"%>
<%@ page import="model.DAO"%>
  <%@ page import= "java.util.ArrayList"%>
<%@ page import="model.Usuario"%>
<%@ page import="util.Responses"%>
<%!Usuario user = new Usuario();%>

<%
user = (Usuario) session.getAttribute("usuario");
String[] nomeSobrenome = null;
ArrayList<String> listaUnidades = null;
String retorno = (String) request.getAttribute("retorno");


//out.println(user.getCidade());
%>

<%
if (user == null) {
%>
<jsp:forward page="login.jsp" />
<%
}else{
	 nomeSobrenome = user.getNome().split(" ");
	 listaUnidades = new DAO().listaEmpresas(user.getCidade());
	
}
%>

<!doctype html>
<html lang="pt-br">
<head>
<title>Compras</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="icon" type="image/x-icon"
	href="https://grupoprovida.com.br/wp-content/uploads/2023/12/fav-icon2-150x150.png"
	sizes="32x32" />
<link
	href="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700,800,900"
	rel="stylesheet">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body>

	<div class="wrapper d-flex align-items-stretch">
		<nav id="sidebar">
			<div class="custom-menu">
				<button type="button" id="sidebarCollapse" class="btn btn-primary">
					<i class="fa fa-bars"></i> <span class="sr-only">Toggle Menu</span>
				</button>
			</div>
			<h1>
				<a href="index.jsp" class="logo">Compras</a>
			</h1>
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
          </li>  </li>
				<li><a href="index.jsp"><span class="fa fa-home mr-3"></span>Dashboard</a></li>
				<li><a href="compraservicos.jsp"><span class="fa fa-sticky-note mr-3"></span>Compras/Serviços</a></li>
				<li><a href="pagamentosdiversos.jsp"><span class="fa fa-sticky-note mr-3"></span>Pgtos/Diversos</a></li>
				<% if(user.getSetor().equalsIgnoreCase("T.I")){ %>
				<li><a href="cadastro.jsp"><span class="fa fa-address-book mr-3" ></span>Cadastro</a></li>
				<% } %>
				<li><a href="logout.jsp"><span class="fa fa-sign-out mr-3"></span>Logout</a></li>
			
				

			</ul>

		</nav>

		<!-- Page Content  -->
		<div id="content" class="p-4 p-md-5 pt-5">
			<h2 class='mb-4'>Solicitação de Compras e Serviços</h2>
			<h6>Solicitante: <% out.println(user.getNome()); %><!-- &nbsp; Gerente: --> </h6>
			<h6> Cidade: <% out.println(user.getCidade()); %> </h6>
			<h6> Setor: <% out.println(user.getSetor()); %> &nbsp; Data:
				<% out.println(new DAO().getData()); %> </h6>

			<form id="guru" name="compraservicos" action="compraserv" method="post" accept-charset="utf-8">
				<style>
					tr, td {
							border: none;
							}
			   </style>

				<table id="principal" border="0px" width="100%">
	<tr>
		<td colspan="6" style="text-align: center"><label><b>Informe os dados dos produtos a serem solicitados</b></label></td>
	</tr>
        <tr>
            <td>Quantidade</td>
            <td>Item</td>
            <td>Especificação</td>
            <td>Unidade</td>
           	<td>&nbsp;</td> 
        </tr>

        <tr>
            <td class="col-md-1"><input type="number" min=1 name="quantidade1" class="form-control" id="inputEmail4" required
                    placeholder="0" /></td>
            
            <td class="col-md-3"><input type="text" name="item1" class="form-control" id="inputEmail4" required placeholder="Item" />
            </td>
           
            <td class="col-md-4"><input type="text" name="especificacao1" class="form-control" id="inputEmail4" required
                    placeholder="Especificação" /></td>
           
             <td class="col-md-4"> 
                <select name="unidade" class="form-control" required>
                <option value="" disabled="disabled" selected="selected" required>Selecione a Unidade</option>
                      <%for(int x = 0; x < listaUnidades.size(); x++) {%>
                         <option value='<%= listaUnidades.get(x) %>'><%=  listaUnidades.get(x) %></option>
                       <% } %>     </select>
         </td>
            
       
           <!-- <td>
                <button type="button" class="btn btn-danger btn-sm">Excluir</button>
            </td> --> 
        </tr>
    </table>
				<div class="form-group col-md-8">
					<label for="exampleFormControlTextarea1">Justificativa</label>
					<textarea class="form-control" name="justificativa" id="exampleFormControlTextarea1" required placeholder="Informe a justificativa" rows="6"></textarea>
				</div>
				<button type="button" class="btn btn-secondary" onclick="adicionarParcela()">+ Item</button>
				<button type="submit" class="btn btn-info">Enviar</button>
				<script>				
				 let count = 2;
			        function adicionarParcela() {

			        	$('#principal').append("<tr><td class='col-md-1'><input type='number' min=1 name='quantidade" + count + "' class='form-control' id='inputEmail4' required placeholder='0'/></td>" +
			        		    "<td class='col-md-3'><input type='text' name='item" + count + "' class='form-control' id='inputEmail4' required placeholder='Item' /></td>" +
			        		    "<td class='col-md-4'><input type='text' name='especificacao" + count + "' class='form-control' id='inputEmail4' required placeholder='Especificação'  /></td>" +
			        		    "<td class='col-md-2'><button type='button' class='btn btn-danger btn-sm'>Excluir</button></td></tr>");
			        		count++;
			        }

			        $('#principal').on('click', 'button', function () {
			            var td = $(this).parent();
			            var tr = $(td).parent();
			            count--;
			            tr.remove();
			    
			        });

				</script>
			</form>
</div>
			<script src="js/jquery.min.js"></script>
			<script src="js/popper.js"></script>
			<script src="js/bootstrap.min.js"></script>
			<script src="js/main.js"></script>
			<script src="js/js.js"></script>
			<script src="scripts/validador.js"></script>
			
	
      <%  if(retorno == "compraservicos" ){ %>
	
    		<script>
    
    			$(document).ready(function() {
       			 $('#notfound').modal('show');
    			})
    
    		</script>

			
<% } %>		
			


	
		<script type="text/javascript">

$("#guru").submit(function(){
			
	$(document).ready(function() {
			 $('#carregando').modal('show');
			 $('#divConsultarEmail').removeAttr('hidden');
		})	
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
        Solicitação de Compras realizada com sucesso!
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