(function($) {

	"use strict";

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	$('#sidebarCollapse').on('click', function () {
      $('#sidebar').toggleClass('active');
  });

})(jQuery);


function Home(nome, setor, unidade){
	document.querySelector("#content").innerHTML = "<div>" + 
	`<h2>${unidade}</h2>` +
	"<p>teste</p>" +
	"<p>teste</p>" +
	"<p>teste</p>" +
	"</div>"
   
}





/**
 *  <div class="form-group col-md-2">
      <label for="inputEmail4">Quantidade</label>
      <input type="text" class="form-control" id="inputEmail4" placeholder="Quantidade">
    </div>
    <div class="form-group col-md-3">
      <label for="inputPassword4">Item</label>
      <input type="text" class="form-control" id="inputPassword4" placeholder="Produto">
    </div>
      <div class="form-group col-md-3">
      <label for="inputPassword4">Especificações</label>
      <input type="text" class="form-control" id="inputPassword4" placeholder="Especificações">
    </div>
 * 
 * 
 * 
 */


function ReqComprasServicos(nome, setor, unidade){


 const data = new Date().toLocaleDateString('pt-BR');

	
  const teste = "<h2 class=mb-4>Requisição de Compras e Serviços</h2>\n" +
  "<form>\n" +
  "<div class='form-row'>\n" +
    "<div class='form-group col-md-6'>\n" +
      "<label for='inputEmail4'>Solicitante</label>\n" +
     `<input type='text' class='form-control' id='inputEmail4' placeholder='Nome' value='${nome}' readonly>\n`  +
    "</div>\n" +
    "<div class='form-group col-md-6'>\n" +
      "<label for='inputPassword4'>Unidade</label>\n" +
      `<input type='text' class='form-control' id='inputPassword4' placeholder='Unidade' value='${unidade}' readonly >\n` +
    "</div>\n" +
	"<div class='form-group col-md-6'>\n" +
	"<label for='inputPassword4'>Setor</label>\n" +
	`<input type='text' class='form-control' id='inputPassword4' placeholder='Password' value='${setor}' readonly >\n` +
  "</div>\n" +
  	"<div class='form-group col-md-6'>\n" +
	"<label for='inputPassword4'>Data</label>\n" +
	`<input type='text' class='form-control' id='inputPassword4' placeholder='data' value='${data}' readonly >\n` +
  "</div>\n" +
  "</div>\n" +
  
  "<div class='form-row'>\n" +
    "<div class='form-group col-md-2'>\n" +
      "<label for='inputZip'>Quantidade</label>\n" +
      "<input type='text' class='form-control' id='inputZip'>\n" +
    "</div>\n" +
       "<div class='form-group col-md-2'>\n" +
      "<label for='inputZip'>Item</label>\n" +
      "<input type='text' class='form-control' id='inputZip'>\n" +
    "</div>\n" +
    "<div class='form-group col-md-6'>\n" +
      "<label for='inputZip'>Especificações</label>\n" +
      "<input type='text' class='form-control' id='inputZip'>\n" +
    "</div>\n" +
  "</div>\n" +
  "<div class='form-group col-md-6'>\n" +
	"<label for='inputPassword4'>Justificativa</label>\n" +
	`<input type='text' class='form-control' id='inputPassword4' placeholder='Justificativa'  >\n` +
  "</div>\n" +
  "<div class='form-group'>\n" +

  "</div>\n" +
  "<button type='submit' class='btn btn-primary'>Enviar</button>\n" +
"</form>\n" 





  document.querySelector("#content").innerHTML = teste

}