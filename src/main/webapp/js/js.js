
function add() {
 
   
   
var table = document.getElementById("tabela");
var row = table.insertRow(2);
let count = localStorage.getItem("u");

if(count == null){

    localStorage.setItem("u",2)
    count = localStorage.getItem("u");


}else{
    let aux =  parseInt(localStorage.getItem("u"))
    aux++;
    localStorage.clear()
    localStorage.setItem("u",aux)
    count = localStorage.getItem("u");
  console.log(aux)


}



row.insertCell(0).innerHTML = "Quantidade:";
row.insertCell(1).innerHTML = `<input type="number" class="form-control" id="inputEmail4"  name="quantidade${count}" required placeholder="Informe a quantidade"> `;
row.insertCell(2).innerHTML = "Item:";
row.insertCell(3).innerHTML = `<input type="text" class="form-control" id="inputEmail4"  name="item${count}" required placeholder="Informe o item">`;
row.insertCell(4).innerHTML = "Especificação:";
row.insertCell(5).innerHTML = `<input type="text" class="form-control" id="inputEmail4"  name="especificacao${count}" required placeholder="Informe a especificação">`;


};

