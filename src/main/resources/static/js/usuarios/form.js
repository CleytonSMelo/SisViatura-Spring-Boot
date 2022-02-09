$(document).ready(function() {
	
	
	
//	$('#setor').change( function (event) {
//		var idsetor = $('#setor').val();
//		
//		var xmlhttp = new XMLHttpRequest();
//	      var url = "http://localhost:8080/telefone/"+ idsetor;
//	      
//	      xmlhttp.onreadystatechange = function(){
//	    	  if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
//	    		  ConectaServidor(xmlhttp.responseText)
//	    	  }
//	      }
//	      
//	      xmlhttp.open("GET", url, true);
//	      xmlhttp.send();
//	      
//	      function ConectaServidor(response){
//	    	  var dados =JSON.parse(response);
//	    	  	    	  
//	    	  $("#inputTelefoneRetorno").val(dados.telefone)	    	   	           
//	  	  		
//	      }
//	});
	
	
	$('#btnSalvar').attr("disabled", "disabled");
	
	$('#role').change( function (event) {
    	validarinput();
    });

    $('#setor').change( function (event) {
    	validarinput();
    });

    $('#inputNome').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputMatricula').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputLogin').keyup( function (event) {
    	validarinput();
    });
    
//    $('#inputSenha').keyup( function (event) {
//    	validarinput();
//    });
    
    $('#inputEmail').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputContato').keyup( function (event) {
    	validarinput();
    });
    
//    $('#inputTelefoneRetorno').keyup( function (event) {
//    	validarinput();
//    });
	
	function validarinput(){
		if($("#role").val()=="" ||
		   $("#inputNome").val()=="" || $("#inputMatricula").val()=="" || $("#inputLogin").val()=="" ||
		    $("#setor").val()=="" || $("#inputEmail").val()=="" || $("#inputContato").val()=="" ){
			//$("#inputSenha").val()=="" ||
			$('#btnSalvar').attr("disabled", "disabled");
			
		}else{
			$('#btnSalvar').removeAttr("disabled");
		}
	}
	
});