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

    $('#inputNome').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputEndereco').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputTelefone').keyup( function (event) {
    	validarinput();
    });
    
    $('#inputSenha').keyup( function (event) {
    	validarinput();
    });
    
    $('#exampleFormControlTextarea1').keyup( function (event) {
    	validarinput();
    });
    
//    $('#inputTelefoneRetorno').keyup( function (event) {
//    	validarinput();
//    });
    
    $('#exampleFormControlTextarea1').keyup( function () {
        if ($('#exampleFormControlTextarea1').val().length > 5) {
            $('#btnSalvar').removeAttr('disabled');
        } else {
            $('#btnSalvar').attr('disabled', 'disabled');
        }
     });
	
	function validarinput(){
		if($("#exampleFormControlTextarea1").val() == "" || 
		   $("#inputNome").val()=="" || $("#inputEndereco").val()=="" ||
		   $("#inputSenha").val()=="" || $("#inputTelefone").val()=="" ){
			
			$('#btnSalvar').attr("disabled", "disabled");
			
		}else{
			$('#btnSalvar').removeAttr("disabled");
		}
	}
	
});