//$(document).ready(function() {
	
	$('.table #remover').click( function (event) {
       event.preventDefault();
       var href = $(this).attr('href');
       $('#exampleModal #btnRemover').attr('href', href);
        $('#exampleModal').modal();
    });
		
//	$(document).on('click','#editar',function () {	   
//	       //event.preventDefault();
//	       //var id = $(this).attr('manutencao-id');		       	      
//	       //listarMotivo(id); 
//		  $("#btnSalvar").text("Atualizar");
//	 });
	
	$(document).on('click','#motivo-problema',function () {	   
	       event.preventDefault();
	       var id = $(this).attr('manutencao-id');		       	      
	       listarMotivo(id);   	  
	 });	
	
	$('.table #nota').click( function (event) {
	       event.preventDefault();
	       var id = $(this).attr('id-manutencao');
	       $('#manutencao-id').val(id);  
	});
	
	$("#btnSalvarNota").click( function (event) {
		$("#formModalNota").submit();
	});
	
	$('.table #arquivo-servico').click( function (event) {
	       event.preventDefault();
	       var id = $(this).attr('manutencao-id');
	       //$('#exampleModal #btnRemover').attr('href', href);
	        //$('#exampleModal').modal();
	       $("#manutencao-id2").val(id);
	 });
	
	$("#btnSalvarArquivo").click( function (event) {
		$("#formModalArquivo").submit();
	});
	
    function listarMotivo(id) {
    	 $('#motivomanute').html("");
    	var tbody = document.querySelector('#motivomanute');
    	$.ajax({
    		  type: "GET",
    		  url: "/SisViatura/Home/Manutencao/buscarManutencaoPorId/"+id,
    		 // data: { tipos: $("#selectUser").val() },
    		  dataType: 'json',
    		  contentType: "application/json; charset=utf-8",
    		  success: function (data) {
    			  //console.log(data.motivo);
    			  //console.log(data);
    			  tbody.innerHTML += data.problema;
    		           		         
    		  } 
         });
    }
    
    var table = document.getElementById('example').rows.length;
	//console.log(table);
	for (var i = 1; i < table; i++) {
	var valor = $('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text();
	
	if(valor === 'APROVADA'){
		$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Aprovada').addClass('badge badge-success');
    } else if (valor === 'RECUSADA') {
    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Reprovada').addClass('badge badge-danger');
    } else if (valor === 'EM_ANALISE') {
    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Em Análise').addClass('badge badge-secondary');
    } 
        
	
	}
	
	function keypressed( obj , e ) {
	    var tecla = ( window.event ) ? e.keyCode : e.which;
	    var texto = document.getElementById("kmatual").value
	    var indexvir = texto.indexOf(",")
	    var indexpon = texto.indexOf(".")
	   
	   if ( tecla == 8 || tecla == 0 )
	       return true;
	   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
	       return false;
	   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	}
	
	function keypressed1( obj , e ) {
	    var tecla = ( window.event ) ? e.keyCode : e.which;
	    var texto = document.getElementById("custoManut").value
	    var indexvir = texto.indexOf(",")
	    var indexpon = texto.indexOf(".")
	   
	   if ( tecla == 8 || tecla == 0 )
	       return true;
	   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
	       return false;
	   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
	}
//});