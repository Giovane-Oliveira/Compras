/**
 * Validar campos obrigatorios
 * 
 * @author Professor Jose de Assis
 */



function verificar(){
	
	
	//compraservicos
let quantidade1 = compraservicosicos.quantidade1.value
let item1 = compraservicosicos.item1.value
let especificacao1 = compraservicos.especificacao1.value
let justificativa = compraservicos.justificativa.value

	if (quantidade1 === "") {
		alert('Preencha o campo Nome')
		compraservicos.quantidade1.focus()
		return false
	} else if (item1 === "") {
		alert('Preencha o campo Fone')
		compraservicos.item1.focus()
		return false
	}else if (especificacao1 === "") {
		alert('Preencha o campo Fone')
		compraservicos.especificacao1.focus()
		return false
	}else if (justificativa === "") {
		alert('Preencha o campo Fone')
		compraservicos.justificativa.focus()
		return false
	}   else {
		document.forms["compraservicos"].submit()
	}
	
}



	



										
										