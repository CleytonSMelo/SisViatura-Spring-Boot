//$(document).ready(function() {
    $('.table #foto-vistoria').click( function (event) {
	       event.preventDefault();
	       var id = $(this).attr('vistoria-id');
	       //$('#exampleModal #btnRemover').attr('href', href);
	        //$('#exampleModal').modal();
	       $("#vistoria-id").val(id);
	 });	

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
	
	$(document).on('click','#motivo-vistoria',function () {	   
	       event.preventDefault();
	       var id = $(this).attr('vistoria-id');		       	      
	       listarMotivo(id);   	  
	 });	
	
	$('.table #nota').click( function (event) {
	       event.preventDefault();	 
	       
	       var id = $(this).attr('vistoria-id');
	       //alert(id);
	       $('#vistoria-id2').val(id);  
	});
	
	$("#btnSalvarNota").click( function (event) {
		$("#formModalNota").submit();
	});
	
	$("#btnSalvarFoto").click( function (event) {
		$("#formModalFoto").submit();
	});
	
    function listarMotivo(id) {
    	 $('#motivovistoria').html("");
    	var tbody = document.querySelector('#motivovistoria');
    	$.ajax({
    		  type: "GET",
    		  url: "/SisViatura/Home/Vistoria/buscarVistoriaPorId/"+id,
    		 // data: { tipos: $("#selectUser").val() },
    		  dataType: 'json',
    		  contentType: "application/json; charset=utf-8",
    		  success: function (data) {
    			  //console.log(data.motivo);
    			  //console.log(data);
    			  tbody.innerHTML += data.motivo;
    		           		         
    		  } 
         });
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
    
    var table = document.getElementById('example').rows.length;
	//console.log(table);
	for (var i = 1; i < table; i++) {
	var valor = $('#example > tbody > tr:nth-child('+i+') > td:nth-child(8)').text();
	
	if(valor === 'CONCLUIDO'){
		$('#example > tbody > tr:nth-child('+i+') > td:nth-child(8)').text('Concluido').addClass('badge badge-success');
    } else if (valor === 'EM_EXECUCAO') {
    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(8)').text('Em Execução').addClass('badge badge-danger');
    } else if (valor === 'EM_ANALISE') {
    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(8)').text('Em Análise').addClass('badge badge-secondary');
    } 
        
	
	}
//    var table = document.getElementById('example').rows.length;
//	//console.log(table);
//	for (var i = 1; i < table; i++) {
//	var valor = $('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text();
//	
//	if(valor === 'APROVADA'){
//		$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Aprovada').addClass('badge badge-success');
//    } else if (valor === 'RECUSADA') {
//    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Reprovada').addClass('badge badge-danger');
//    } else if (valor === 'EM_ANALISE') {
//    	$('#example > tbody > tr:nth-child('+i+') > td:nth-child(6)').text('Em Análise').addClass('badge badge-secondary');
//    } 
//        
//	
//	}
//});