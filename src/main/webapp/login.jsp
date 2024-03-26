<%@ page language="java" contentType="text/html; charset=utf-8"
        pageEncoding="utf-8"%>

<%@ page import="model.Usuario"%>
<%@ page import="util.Responses"%>

<%!Usuario user = new Usuario();%>

<%
user = (Usuario) session.getAttribute("usuario");

String retorno = (String) request.getAttribute("verificarUsuario");

String token = (String) request.getAttribute("token");

String usuarioCadastrado = (String) request.getAttribute("usuarioCadastrado");

String emailValido =  (String) request.getAttribute("email");

String recuperarSenha =  (String) request.getAttribute("recuperarSenha");

String recuperarSenhaToken =  (String) request.getAttribute("recuperarSenhaToken");

String recuperarSenhaEmail =  (String) request.getAttribute("recuperarSenhaEmail");

String senhaAlterada = (String) request.getAttribute("senhaAlterada");

%>


<% if (user != null) { %>
<jsp:forward page="index.jsp" />
<%
}
%>


<!doctype html>

<html lang="pt-br">
<head>
<meta name="description" content="">
<meta name="author" content="Giovane Oliveira">

<meta http-equiv="cleartype" content="on">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>

<link rel="icon" type="image/x-icon"
        href="https://grupoprovida.com.br/wp-content/uploads/2023/12/fav-icon2-150x150.png"
        sizes="32x32" />
<link
        href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'
        rel='stylesheet'>
<link
        href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'
        rel='stylesheet'>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

         <script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<style>
::-webkit-scrollbar {
        width: 8px;
}
/* Track */
::-webkit-scrollbar-track {
        background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
        background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
        background: #555;
}

body {
        color: #000;
        overflow-x: hidden;
        height: 100%;
        background-color: #B0BEC5;
        background-repeat: no-repeat;
}

.card0 {
        box-shadow: 0px 4px 8px 0px #757575;
        border-radius: 5px;
}

.card2 {
        margin: 0px 40px;
}

.logo {
        width: 100px;
        height: 100px;
        margin-top: 20px;
        margin-left: 50px;
}

.image {
        width: 360px;
        height: 280px;
}

.border-line {
        border-right: 1px solid #EEEEEE;
}

.facebook {
        background-color: #3b5998;
        color: #fff;
        font-size: 18px;
        padding-top: 5px;
        border-radius: 50%;
        width: 35px;
        height: 35px;
        cursor: pointer;
}

.twitter {
        background-color: #1DA1F2;
        color: #fff;
        font-size: 18px;
        padding-top: 5px;
        border-radius: 50%;
        width: 35px;
        height: 35px;
        cursor: pointer;
}

.linkedin {
        background-color: #2867B2;
        color: #fff;
        font-size: 18px;
        padding-top: 5px;
        border-radius: 50%;
        width: 35px;
        height: 35px;
        cursor: pointer;
}

.line {
        height: 1px;
        width: 45%;
        background-color: #E0E0E0;
        margin-top: 10px;
}

.or {
        width: 10%;
        font-weight: bold;
}

.text-sm {
        font-size: 14px !important;
}

::placeholder {
        color: #BDBDBD;
        opacity: 1;
        font-weight: 300
}

:-ms-input-placeholder {
        color: #BDBDBD;
        font-weight: 300
}

::-ms-input-placeholder {
        color: #BDBDBD;
        font-weight: 300
}

input, textarea {
        padding: 10px 12px 10px 12px;
        border: 1px solid lightgrey;
        border-radius: 2px;
        margin-bottom: 5px;
        margin-top: 2px;
        width: 100%;
        box-sizing: border-box;
        color: #2C3E50;
        font-size: 14px;
        letter-spacing: 1px;
}

input:focus, textarea:focus {
        -moz-box-shadow: none !important;
        -webkit-box-shadow: none !important;
        box-shadow: none !important;
        border: 1px solid #304FFE;
        outline-width: 0;
}

button:focus {
        -moz-box-shadow: none !important;
        -webkit-box-shadow: none !important;
        box-shadow: none !important;
        outline-width: 0;
}

a {
        color: inherit;
        cursor: pointer;
}

.btn-blue {
        background-color: #139299;
        width: 150px;
        color: #fff;
        border-radius: 2px;
}

.btn-blue:hover {
        background-color: #19696d;
        color: #fff;
        cursor: pointer;
}

.bg-blue {
        color: #fff;
        background-color: #139299;
}

@media screen and (max-width: 991px) {
        .logo {
                margin-left: 0px;
        }
        .image {
                width: 300px;
                height: 220px;
        }
        .border-line {
                border-right: none;
        }
        .card2 {
                border-top: 1px solid #EEEEEE !important;
                margin: 0px 15px;
        }
}
</style>
</head>
<body className='snippet-body'>
        <div class="container-fluid px-1 px-md-5 px-lg-1 px-xl-5 py-5 mx-auto">
                <div class="card card0 border-0">
                        <div class="row d-flex">
                                <div class="col-lg-6">
                                        <div class="card1 pb-5">
                                                <div>
                                                        <img src="./images/logo_oficial.jpg" class="logo">
                                                </div>
                                                <div class="row px-3 justify-content-center mt-4 mb-5 border-line">
                                                        <img src="https://i.imgur.com/uNGdWHi.png" class="image">
                                                </div>
                                        </div>
                                </div>
                                <div class="col-lg-6">
                                        <div class="card2 card border-0 px-4 py-5">
                                                <div hidden class="row mb-4 px-3">
                                                        <h6 class="mb-0 mr-4 mt-2">Sign in with</h6>
                                                        <div class="facebook text-center mr-3">
                                                                <div class="fa fa-facebook"></div>
                                                        </div>
                                                        <div class="twitter text-center mr-3">
                                                                <div class="fa fa-twitter"></div>
                                                        </div>
                                                        <div class="linkedin text-center mr-3">
                                                                <div class="fa fa-linkedin"></div>
                                                        </div>
                                                </div>
                                                <div hidden class="row px-3 mb-4">
                                                        <div class="line"></div>
                                                        <small class="or text-center">Or</small>
                                                        <div class="line"></div>
                                                </div>

                                                <form action="verificarLogin" method="post">
                                                        <div class="row px-3">
                                                                <label class="mb-1"><h6 class="mb-0 text-sm">Email</h6></label> <input class="mb-4" type="text" name="usuario" id="usuario"
                                                                        required placeholder="Informe seu email">
                                                        </div>
                                                        <div class="row px-3">
                                                                <label class="mb-1"><h6 class="mb-0 text-sm">Senha</h6></label>
                                                                <input type="password" name="pass" id="senha"
                                                                        required placeholder="Informe sua senha">
                                                        </div>
                                                         <div class="row px-3 mb-4">
                                                                <div hidden
                                                                        class="custom-control custom-checkbox custom-control-inline">
                                                                        <input id="chk1" type="checkbox" name="chk"
                                                                                class="custom-control-input"> <label for="chk1"
                                                                                class="custom-control-label text-sm">Lembrar-me</label>
                                                                </div>
                                                                <a  class="ml-auto mb-0 text-sm" data-toggle="modal" data-target="#validaEmail" data-whatever="@mdo">Esqueceu a senha?</a>
                                                        </div>
                                                        <div class="row mb-3 px-3">
                                                                <button type="submit" class="btn btn-blue text-center">Login</button>
                                                        </div>
                                                </form>

                                                <div class="row mb-4 px-3">
                                                        <small class="font-weight-bold">Ainda não tem uma conta?
                                                                <a class="text-danger" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Registre-se</a>
                                                        </small>
                                                </div>
                                        </div>
                                </div>
                        </div>
                        <div class="bg-blue py-4">
                                <div class="row px-3">
                                        <small class="ml-4 ml-sm-5 mb-2">Copyright &copy; 2024. All
                                                rights reserved.</small>
                                        <div class="social-contact ml-4 ml-sm-auto">
                                                <a href="https://www.facebook.com/grupoprovidaoficial"  target="_blank"><span  class="fa fa-facebook mr-4 text-sm"></span> </a>
                                                <!-- <a href="https://play.google.com/store/apps/details?id=br.com.infosoft.app.ilife.provida&hl=ln&gl=US&pli=1"><span  class="fa fa-google-plus mr-4 text-sm"></span> </a> -->
                                                <a href="https://www.linkedin.com/company/grupoprovidaoficial/mycompany/"  target="_blank"><span  class="fa fa-linkedin mr-4 text-sm"></span> </a>
                                                 <span hidden href="#" class="fa fa-twitter mr-4 mr-sm-5 text-sm"></span>
                                        </div>
                                </div>
                        </div>
                </div>
        </div>

        <% if(retorno != null) {%>
                <script>

                        $(document).ready(function() {
                         $('#notfound').modal('show');
                        })

                </script>
        <% } %>

        <%if(usuarioCadastrado != null){ %>

                <script>

                        $(document).ready(function() {
                         $('#cadastro').modal('show');
                        })

                </script>

        <% }  %>


                <%if(token == "Token Inválido"){ %>

                <script>

                        $(document).ready(function() {
                         $('#tokenInvalido').modal('show');
                        })

                </script>

        <% } %>

        <%if(recuperarSenha != null){ %>

                <script>

                        $(document).ready(function() {
                         $('#recuperacaoConta').modal('show');
                        })

                </script>

        <% } %>

        <%if(senhaAlterada == "senhaAlterada"){ %>

                <script>

                        $(document).ready(function() {
                         $('#senhaAlterada').modal('show');
                        })

                </script>

        <% }else if (senhaAlterada == "invalido"){ %>

                <script>

                        $(document).ready(function() {
                         $('#senhaAlteradaInvalida').modal('show');
                        })

                </script>

                <% } %>

                <%if(emailValido == "emailInvalido"){ %>

                <script>

                        $(document).ready(function() {
                         $('#emailInvalido').modal('show');
                        })

                </script>

        <% }else if(emailValido == "emailValido"){%>

                <script>

                        $(document).ready(function() {
                         $('#emailValido').modal('show');
                        })

                </script>

                <% } %>


<div class="modal fade" id="recuperacaoConta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Recuperação de conta</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
         <form action="alterarSenha" method="post">
      <div class="modal-body">
        <div  class="form-group">
            <label>Email: <%= recuperarSenhaEmail %></label>
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Nova senha:</label>
            <input type="password" class="form-control item"  id="pass" required placeholder="Informe uma senha">
          </div>
           <div class="form-group">
            <label for="recipient-name" class="col-form-label">Repita nova senha:</label>
            <input type="password" name="senha" class="form-control item"  id="pass2" required placeholder="Repita a senha">
          </div>
           <div hidden class="form-group">
            <input type="text" name="id" class="form-control" id="recipient-name" value='<%= recuperarSenha %>'>
          </div>
            <div hidden class="form-group">
            <input type="text" name="token" class="form-control" id="recipient-name" value='<%= recuperarSenhaToken %>'>
          </div>
      <!-- <div class="form-group">
            <label for="message-text" class="col-form-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
          </div> -->

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" onclick="return validarSenha()">Alterar</button>
      </div>
       </form>
    </div>
  </div>
</div>

        <script type="text/javascript">

    let senha = document.getElementById('pass');
    let senhaC = document.getElementById('pass2');

    function validarSenha() {
          //alert("senha: " + senha.value + " senha2: " + senhaC.value);
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

                 <!-- Modal -->
<div class="modal fade" id="cadastro" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Cadastro realizado com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>

                         <!-- Modal -->
<div class="modal fade" id="errocadastro" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Cadastro realizado com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
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
        Usuário ou senha incorretos!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>


         <!-- Modal -->
<div class="modal fade" id="senhaAlterada" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Senha alterada com sucesso!
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>


         <!-- Modal -->
<div class="modal fade" id="senhaAlteradaInvalida" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Link inválido! Faça uma nova solicitação.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>



         <!-- Modal -->
<div class="modal fade" id="emailValido" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Aviso</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Um link foi enviado para o seu email para a recuperação da conta.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <!--  <button type="button" class="btn btn-primary">Save changes</button>       -->
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="validaEmail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Recuperação de conta</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
          <form id="consultarEmail" action="consultarEmail" method="post">
      <div class="modal-body">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Email:</label>
            <input type="text" name="email" class="form-control" id="recipient-name" required placeholder="Informe o email cadastrado">
          </div>
       <div hidden id="divConsultarEmail" class="form-group">
           <span class="badge badge-info">Aguarde...</span>
         </div>
      </div>
             <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Validar</button>
      </div>
       </form>

    </div>
  </div>
</div>

<div class="modal fade" id="emailInvalido" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Recuperação de conta</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form action="consultarEmail" method="post">
      <div class="modal-body">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Email:</label>
            <input type="text" name="email" class="form-control" id="recipient-name" required placeholder="Informe o email cadastrado">
          </div>
          <div class="alert alert-warning" role="alert">
  Email inválido
</div>
      <!-- <div class="form-group">
            <label for="message-text" class="col-form-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
          </div> -->

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Validar</button>
      </div>
       </form>
    </div>
  </div>
</div>


<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Validar Token</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form action="consultarToken">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Token:</label>
            <input type="text" name="token" class="form-control" id="recipient-name" required placeholder="Informe o token">
          </div>
      <!-- <div class="form-group">
            <label for="message-text" class="col-form-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
          </div> -->

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Validar</button>
      </div>
       </form>
    </div>
  </div>
</div>

        <div class="modal fade" id="tokenInvalido" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Validar Token</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form action="consultarToken">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Token:</label>
            <input type="text" name="token" class="form-control" id="recipient-name" required placeholder="Informe o token">
          </div>
          <div class="alert alert-warning" role="alert">
  Token Inválido
</div>
      <!-- <div class="form-group">
            <label for="message-text" class="col-form-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
          </div> -->

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Validar</button>
      </div>
       </form>
    </div>
  </div>
</div>


        <script type="text/javascript">

$("#consultarEmail").submit(function(){


            $('#divConsultarEmail').removeAttr('hidden');


        });


</script>


        <script type='text/javascript'
                src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js'></script>
        <script type='text/javascript' src='./scripts/validador.js'></script>
        <script type='text/javascript' src='#'></script>




</body>
