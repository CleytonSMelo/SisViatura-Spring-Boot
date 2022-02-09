$(document).ready(function () {
$('#dadosuser').click( function () {
	$('#setorUser').text('');
	var selectbox2 = $('#setorUser');
	selectbox2.append('<option value="">Selecione </option>');
	listarsetor();
});
	
	
function listarsetor() {
	
	$.ajax({
		  type: "GET",
		  url: "/Sisint/Home/AtualizarInformacoes",
		  data: { tipos: $("#setorUser").val() },
		  dataType: 'json',
		  contentType: "application/json; charset=utf-8",
		  success: function (data) {
			  //console.log(data);
		          var selectbox = $('#setorUser');
		          $.each(data, function (i, d) {
		              selectbox.append('<option value="' + d.id + '">' + d.nome + '</option>');
		          });
		  } });
}	
	

//atualizar informacoes usuario por json
$(document).on('click','#btnSalvarInfo',function () {
	atualizardados();
});

function atualizardados(){	       
    var lotacao = $("#setorUser").val();
    var fone = $("#contatoUser").val();
    
    		       
    //Metodo Salvar por Json
    var url="/Sisint/UsuarioAtualizar/salvar";
    
    xhr = new XMLHttpRequest();
    xhr.open("POST", url, true); 	       
    xhr.setRequestHeader("Content-type","application/json");
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){                       
          
            var saida = xhr.responseText;
            var response = $.parseJSON(saida);	                
            $('#modalUser').modal('hide');
            limparFormularioEditar();
            alert("Contato ou Lotação Atualizado com sucesso! Por favor faça login novamente no sistema para visualizar sua lotação corretamente!")
            window.location.replace("/Sisint/Home/logout");
        }
    }

    var dados = JSON.stringify({"id":"", "telefone":""+fone+"", "setor":{"id":""+lotacao+""}});
      xhr.send(dados); 

}

function limparFormularioEditar(){
	$("#setorUser").val("");
    $("#contatoUser").val("");
}

$('#btnSalvarInfo').attr("disabled", "disabled");

$('#setorUser').change( function (event) {
	blockbutao();
});

$('#contatoUser').keyup( function (event) {
	blockbutao();
});

function blockbutao(){
	if($("#contatoUser").val().length < 8 && $("#setorUser").val() == ""){
		
		$('#btnSalvarInfo').attr("disabled", "disabled");
		
	}else{
		$('#btnSalvarInfo').removeAttr("disabled");
	}
}

});