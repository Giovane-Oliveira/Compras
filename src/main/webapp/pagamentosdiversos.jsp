<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="model.Colaborador"%>
<%@ page import="model.DAO"%>
  <%@ page import="model.Usuario"%>
<%@ page import="util.Responses"%>    
  <%@ page import= "java.util.ArrayList"%>
<%! Usuario user = new Usuario(); %>

<% String msg = (String)request.getAttribute("gurumessage");
String resultado = (String)request.getAttribute("resultado");
  // out.println(msg);
%>

<%  user = (Usuario) session.getAttribute("usuario");

String[] nomeSobrenome = null;
ArrayList<String> listaUnidades = null;
ArrayList<String> listaFornecedores = null;

%>

<%

if(user == null){
	%> 	
	<jsp:forward page="login.jsp" />
<% }else{
	
 nomeSobrenome = user.getNome().split(" ");
listaUnidades = new DAO().listaEmpresas(user.getCidade());	
listaFornecedores = new DAO().listaFornecedores();		
	
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-maskmoney/3.0.2/jquery.maskMoney.min.js" type="text/javascript"></script>
    
		
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
       <h2 class='mb-4'>Autorização de Pagamentos Diversos</h2>
      <form id="guru"  action="upload" method="post"
                        enctype="multipart/form-data" accept-charset="utf-8">
  <div class="form-row">
       <div class="form-group col-md-6">
      <label for="inputText">Fornecedor</label>
      <select name="fornecedor" class="form-control" required>
                <option value="" disabled="disabled" selected="selected" required>Selecione o Fornecedor</option>
                      <%for(int x = 0; x < listaFornecedores.size(); x++) {%>
                         <option value='<%= listaFornecedores.get(x) %>'><%=  listaFornecedores.get(x) %></option>
                       <% } %>     </select>
    </div>
    <div class="form-group col-md-6">
      <label for="inputText">Empresa</label>
      <select name="empresa" class="form-control" required>
                <option value="" disabled="disabled" selected="selected" required>Selecione a Empresa</option>
                      <%for(int x = 0; x < listaUnidades.size(); x++) {%>
                         <option value='<%= listaUnidades.get(x) %>'><%=  listaUnidades.get(x) %></option>
                       <% } %>     </select>
    </div>
  </div>

  
<div class="form-group">
    <label for="exampleFormControlTextarea1">Descrição</label>
    <textarea name="descricao" class="form-control" id="exampleFormControlTextarea1" rows="6" required placeholder="Descreva sobre o pagamento"></textarea>
  </div>
 <div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputEmail4">Vencimento</label>
      <input name="vencimento" type="text" class="form-control" id="vencimento" required placeholder="Informe o vencimento">
      <script>$("#vencimento").mask("00/00/0000");</script>
    </div>
    <div class="form-group col-md-6">
      <label for="inputText">Valor</label>
      <input name="valor" type="text" class="form-control" id="valor" required placeholder="Informe o valor">
            <script>
            $('#valor').maskMoney({ prefix: 'R$ ', allowNegative: true, thousands: '.', decimal: ',', affixesStay: true });
        	
        
            </script>
    </div>
  </div>
  <div class="form-group">
    <label for="exampleFormControlSelect1">Formas de Pagamento</label>
    <select id="sl" class="form-control" id="exampleFormControlSelect1" required>
       <option value="">Selecione</option>
      <option value="Boleto">Boleto</option>
      <option value="Á vista">Á vista</option>
      <option value="Depósito">Depósito</option>
      <option value="Pix">Pix</option>
    </select>
  </div>
  <div id="formadePagamento">
  
  </div>
  
   <div id="chavesPix">

  </div>

 <script type="text/javascript">
 $.jMaskGlobals.watchDataMask= true;
 
 $("#sl").on("change", function() {
	  var valor = $(this).val();   // aqui vc pega cada valor selecionado com o this
		if(valor == "Depósito"){
			document.getElementById("chavesPix").innerHTML = "";
			document.getElementById("formadePagamento").innerHTML = "" +
			"<input hidden name='deposito' type='text' class='form-control' id='inputEmail4' value='deposito'>\n" +
			"<div class='form-row'>\n" +
		    "<div class='form-group col-md-3'>\n" +
		      "<label for='inputEmail4'>Banco</label>\n" +
		      "<input name='banco' type='text' class='form-control' id='inputEmail4' required placeholder='Informe o Banco'>\n" +
		    "</div>\n" +
		    "<div class='form-group col-md-3'>\n" +
		      "<label for='inputText'>Agência</label>\n" +
		      "<input name='agencia' type='text' class='form-control' id='inputPassword4' data-mask='0000'  required placeholder='Informe a agência'>\n" +
		    "</div>\n" +
		    "<div class='form-group col-md-3'>\n" +
		      "<label for='inputText'>Conta Corrente</label>\n" +
		      "<input name='contacorrente' type='text' class='form-control' id='inputPassword4' data-mask='00000000-0' required placeholder='Informe a conta corrente'>\n" +
		    "</div>\n" +
		  "</div>\n";
			
			
		} else if(valor == "Pix"){
			document.getElementById("formadePagamento").innerHTML = "";
			document.getElementById("chavesPix").innerHTML = "" +
			"<input hidden name='pix' type='text' class='form-control' id='inputEmail4' value='pix'>\n" +
			  "<div class='form-group'>\n" +
		    "<label for='exampleFormControlSelect1'>Chaves Pix</label>\n" +
		    "<select id='selectPix' class='form-control' id='exampleFormControlSelect1' required>\n" +
		       "<option value=''>Selecione</option>\n" +
		      "<option value='CPF'>CPF</option>\n" +
		      "<option value='CNPJ'>CNPJ</option>\n" +
		      "<option value='EMAIL'>EMAIL</option>\n" +
		      "<option value='TELEFONE'>TELEFONE</option>\n" +
		    "</select>\n" +
		  "</div>\n";
			
		   $("#selectPix").on("change", function() {
			  	  var value = $(this).val();
			  	  if(value == "CPF"){
			
			  		  document.getElementById("chavesPix").innerHTML = "" +
					   "<div class='form-group col-md-3'>\n" +
					      "<label for='inputEmail4'>CPF</label>\n" +
					      "<input name='cpf' type='text' class='form-control' id='cpf'  data-mask='000.000.000-00' required placeholder='Informe o CPF'>\n" +
					    "</div>"; 
					    			  	
			  		
				   }else if(value == "CNPJ"){
						
				  		  document.getElementById("chavesPix").innerHTML = "" +
				  		 "<div class='form-group col-md-3'>\n" +
					      "<label for='inputText'>CNPJ</label>\n" +
					      "<input name='cnpj' type='text' class='form-control' id='inputPassword4' data-mask='00.000.000/0000-00'  required placeholder='Informe o CNPJ'>\n" +
					    "</div>\n";
						   
					   }else if(value == "EMAIL"){
							
					  		  document.getElementById("chavesPix").innerHTML = "" +
					  		  "<div class='form-group col-md-3'>\n" +
						      "<label for='inputText'>Email</label>\n" +
						      "<input name='email' type='text' class='form-control' id='inputPassword4' required placeholder='Informe o EMAIL'>\n" +
						    "</div>\n";
							   
						   }else if(value == "TELEFONE"){
								
						  		  document.getElementById("chavesPix").innerHTML = "" +
						  		 "<div class='form-group col-md-3'>\n" +
							      "<label for='inputText'>Telefone</label>\n" +
							      "<input name='telefone' type='text' class='form-control' id='inputPassword4' data-mask='(00) 0 0000-0000'  required placeholder='Informe o TELEFONE'>\n" +
							    "</div>\n";
							   }else{
							
							document.getElementById("chavesPix").innerHTML = "";
						}
					  
			  	 
			    })
		
					    
			
			
		}else if(valor == "Boleto"){
			
			document.getElementById("chavesPix").innerHTML = "";
			document.getElementById("formadePagamento").innerHTML = "" +
			
		      "<input hidden name='boleto' type='text' class='form-control' id='inputEmail4' value='boleto'>\n";
			
		}else if(valor == "Á vista"){
			document.getElementById("chavesPix").innerHTML = "";
			document.getElementById("formadePagamento").innerHTML = "" +
			"<input hidden name='avista' type='text' class='form-control' id='inputEmail4' value='avista'>\n";
			
		}else{
			document.getElementById("chavesPix").innerHTML = "";
			document.getElementById("formadePagamento").innerHTML = "";
		}	 
	  
	
 })

 </script> 



			<table id="principal" border="0px" width="70%">
        <tr>
            <td>Anexo</td>
           	<td>&nbsp;</td> 
        </tr>
        <tr>

            <td class='col-md-2'><input type="file" name="anexo1" size="50" accept="image/jpeg,image/png,image/jpg,application/pdf" required/></td>

           <!-- <td>
                <button type="button" class="btn btn-danger btn-sm">Excluir</button>
            </td> --> 
        </tr>
    </table>
    <br>
    <button type="button" class="btn btn-secondary" onclick="adicionarParcela()">+ Anexo</button></td>
	
	<script>				
				 let count = 2;
			        function adicionarParcela() {
			        	
			        	
			            $('#principal').append("<tr><td class='col-md-2'><input type='file' name='anexo"+count+"' size='50' accept='image/jpeg,image/png,image/jpg,application/pdf' required /></td>" +
			            "<td class='col-md-2'><button type='button' class='btn btn-danger btn-sm'>Excluir</button></td>");
			            count++;
			        }

			        $('#principal').on('click', 'button', function () {
			            var td = $(this).parent();
			            var tr = $(td).parent();
			            count--;
			            tr.remove();
			        });

				</script>


  

  <button type="submit" class="btn btn-info">Enviar</button>
</form>
      </div>
		</div>

    <script src="js/jquery.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    
    
          <%  if(resultado != null){ %>
	
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
        Solicitação de Autorização de Pagamento realizada com sucesso!
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



































