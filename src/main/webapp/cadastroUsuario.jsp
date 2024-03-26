<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@ page import= "java.util.ArrayList"%>
<%@ page import="model.DAO"%>  

  <% 
  
  ArrayList<String> listaCidades = new ArrayList<String>();
  listaCidades = new DAO().listaCidades();
  String token = (String) request.getAttribute("token");
  String perfil = (String) request.getAttribute("perfil");
  %>  

<%

if(token == null){
	%> 	
	<jsp:forward page="login.jsp" />
<% } %>



<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cadastro</title>
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
     <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
    <div class="registration-form">
        <form action="cadastrarUsuario" method="post" accept-charset="utf-8">
            <div class="form-icon">
                <span><i class="icon icon-user"></i></span>
            </div>
               <div hidden class="form-group">
                <input type="text" class="form-control item" name="perfil" value=<%= perfil %>>
            </div>
            <div class="form-group">
                <input type="text" class="form-control item" name="nome" required placeholder="Informe o nome completo">
            </div>
            <div class="form-group">
                <input type="email" id="email" class="form-control item" name="usuario" required placeholder="Informe o email">
                <script type="text/javascript">
                $('#email').mask("A", {
                    translation: {
                        "A": { pattern: /[\w@\-.+]/, recursive: true }
                    }
                });
                </script>
            </div>
            <div class="form-group">
                <input type="password" class="form-control item" id="senha" name="senha" required placeholder="Senha">
            </div>
              <div class="form-group">
                <input type="password" class="form-control item" id="newsenha" name="newsenha" required placeholder="Senha">
            </div>
            <!-- <div class="form-group">
                <input type="text" class="form-control item" name="email" required placeholder="Email">
            </div> -->
          <div class="form-group">
                <select id="cidade" name="cidade" class="form-control" required>
                <option value="" disabled="disabled" selected="selected" required>Selecione a Cidade</option>
                      <%for(int x = 0; x < listaCidades.size(); x++) {%>
                         <option value='<%= listaCidades.get(x) %>'><%=  listaCidades.get(x) %></option>
                       <% } %>     </select>
               
                </select>
            </div>
            <div id="selestab">
                   
                 </div>
			<div id="selsetor">
                   
                 </div>
          <!--  <div class="form-group">
                <select id="inputState" class="form-control" required>
                    <option value="" disabled selected>Selecione o setor</option>
                    <option value="teste">...</option>
                </select> 
            </div> -->
            <div class="form-group">
                <button type="submit" class="btn btn-block create-account" onclick="return validarSenha()">Cadastrar</button>
            </div>
        </form>
		
		 <script type="text/javascript">

    $("#cidade").on("change", function() {
		  var valor = $(this).val();   // aqui vc pega cada valor selecionado com o this
			if(valor == "Cachoeira do Sul"){
				
				document.getElementById("selestab").innerHTML = "";
				document.getElementById("selestab").innerHTML = "" +
                "<div class='form-group'> \n" +
                    "<select id='estab' name='estabelecimento' class='form-control' required> \n" +
                        "<option value='' disabled='disabled' selected='selected'>Selecione o estabelecimento</option> \n" +
                        "<option value='Sede Ramiro'>Sede Ramiro</option> \n" +
                        "<option value='Medicina do Trabalho'>Medicina do Trabalho</option> \n" +
                        "<option value='Clinica'>Clínica</option> \n" +
           
                    "</select> \n" +
                    "<div class='select-dropdown'></div> \n" +
                "</div> \n" +
            "</div> \n";
            $("#estab").on("change", function() {
      		  var valor = $(this).val();   // aqui vc pega cada valor selecionado com o this
      			if(valor == "Sede Ramiro"){
      				
      				document.getElementById("selsetor").innerHTML = "";
    				document.getElementById("selsetor").innerHTML = "" +
                    "<div class='form-group'> \n" +
                        "<select name='setor' class='form-control' required> \n" +
                            "<option value='' disabled='disabled' selected='selected'>Selecione o setor</option> \n" +
                            "<option value='Financeiro'>Financeiro</option> \n" +
                            "<option value='RH'>RH</option> \n" +
                            "<option value='Compras'>Compras</option> \n" +
                            "<option value='T.I'>T.I</option> \n" +
                            "<option value='SAC'>SAC</option> \n" + 
                            "<option value='Diretoria'>Diretoria</option> \n" + 
                             "<option value='Markenting'>Markenting</option> \n" + 
                        "</select> \n" +
                        "<div class='select-dropdown'></div> \n" +
                    "</div> \n" +
                "</div> \n";
      				
      			}else{
      				
      				document.getElementById("selsetor").innerHTML = "";
      				//document.getElementById("selestab").innerHTML = "";
    				document.getElementById("selsetor").innerHTML = "   <div hidden class='form-group'>\n" +
                     "<input type='text' class='form-control item' name='setor' value='Administrativo'>\n" +
    	            "</div>";
      				
      			}
      		  
            
            });
            
			}else{
				document.getElementById("selsetor").innerHTML = "";
				document.getElementById("selestab").innerHTML = "";
				document.getElementById("selestab").innerHTML = "   <div hidden class='form-group'>\n" +
                 "<input type='text' class='form-control item' name='setor' value='Administrativo'>\n" +
	            "</div>";
			}
		  
	 });
	 
    let senha = document.getElementById('senha');
    let senhaC = document.getElementById('newsenha');

    function validarSenha() {
      if (senha.value != senhaC.value) {
        senhaC.setCustomValidity("Senhas diferentes!");
        senhaC.reportValidity();
        return false;
      } else {
        senhaC.setCustomValidity("");
        return true;
      }
    }

    // verificar também quando o campo for modificado, para que a mensagem suma quando as senhas forem iguais
    senhaC.addEventListener('input', validarSenha);
    
    
    </script>
    
        <!--

     <div class="social-media">
            <h5>Sign up with social media</h5>
            <div class="social-icons">
                <a href="#"><i class="icon-social-facebook" title="Facebook"></i></a>
                <a href="#"><i class="icon-social-google" title="Google"></i></a>
                <a href="#"><i class="icon-social-twitter" title="Twitter"></i></a>
            </div>
        </div>

        -->

    </div>
   <!-- <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script> -->
    <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
    <script src="assets/js/script.js"></script>
</body>

</html>