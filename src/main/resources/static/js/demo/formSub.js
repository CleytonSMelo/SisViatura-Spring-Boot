$(document).ready(function() {
		
	var $form = $('#formServico');
	
	
	$('#btnSalvar').attr("disabled", "disabled");
	
	$('#inputDataFechamento').change( function (event) {
    	validarinput();
    });
	
	$('#inputSolicitante').keyup( function (event) {
    	validarinput();
    });
    
    $('#setor').change( function (event) {
    	validarinput();
    });
    
    $('#exampleFormControlTextarea1').keyup( function (event) {
    	
    	if ($('#exampleFormControlTextarea1').val().length > 4) {
            $('#btnSalvar').removeAttr('disabled');
            validarinput();
        } else {
            $('#btnSalvar').attr('disabled', 'disabled');
            
        }
    });
    	
	function validarinput(){
		if($("#exampleFormControlTextarea1").val().length < 5 || $("#inputDataFechamento").val() == "" ||
		    $("#inputSolicitante").val()=="" || $("#setor").val()=="" || $("#equip-manutencao").val() == "" ){
			
			$('#btnSalvar').attr("disabled", "disabled");
			
		}else{
			$('#btnSalvar').removeAttr("disabled");
		}
	}
				
    //bloquear input do modal atualizar equipamento ao clicar em editar
	$(document).on('click', '#editar-equip', function(){	
		var texto = $("#equip-manutencao").val();
		
		//bloquear edicao de inputs ao atualizar
		$("#tombo-equipamento-atualizar").attr('disabled', 'disabled');
		$("#nserie-equipamento-atualizar").attr('disabled', 'disabled');  		
		$("#status-atualizar").attr('disabled', 'disabled');
		$("#ip-equipamento-atualizar").attr('disabled', 'disabled');
		
		var url = '/Sisint/manutencao/buscarEquipamentosPorId/'+texto;
		
		pesquisar(texto,url);
	});
	
	function pesquisar(texto,url) {
	       $.ajax({
	           dataType: 'json',
	           type: 'GET',
	           url: url
	       }).done(function (data) {
	           $('#equip-body').empty();
	           if (data.length == 0) {
	           	alert('nenhum equipamento encontrado')
	           
	           } else {
	        	   customizar(data);
	               function customizar(dado) {

	                    var status;
	                    if(dado.status == 'OK'){
	                    	status = "OK";
	                    }else if(dado.status == 'EM_CONSERTO'){
	                    	status = "EM_CONSERTO";
	                    }else if(dado.status == 'COM_DEFEITO'){
	                    	status = "COM_DEFEITO";
	                    }else if(dado.status == 'ALIENADO'){
	                    	status = "ALIENADO";
	                    }else if(dado.status == 'PARA_ALIENACAO'){
	                    	status = "PARA_ALIENACAO";
	                    }
	                    
	                    var tipos;
	                    if(dado.tipo == 'Mouse'){
	                    	tipos = "MOUSE";
	                    }else if(dado.tipo == 'Teclado'){
	                    	tipos = "TECLADO";
	                    }else if(dado.tipo == 'Monitor'){
	                    	tipos = "TECLADO";
	                    }else if(dado.tipo == 'No-Break'){
	                    	tipos = "NOBREAK";
	                    }else if(dado.tipo == 'Estabilizador'){
	                    	tipos = "ESTABILIZADOR";
	                    }else if(dado.tipo == 'CPU'){
	                    	tipos = "CPU";
	                    }else if(dado.tipo == 'Notebook'){
	                    	tipos = "NOTEBOOK";
	                    }else if(dado.tipo == 'Impressora'){
	                    	tipos = "IMPRESSORA";
	                    }else if(dado.tipo == 'USB'){
	                    	tipos = "USB";
	                    }else if(dado.tipo == 'Modem'){
	                    	tipos = "MODEM";
	                    }else if(dado.tipo == 'Switch'){
	                    	tipos = "SWITCH";
	                    }else if(dado.tipo == 'Webcam'){
	                    	tipos = "WEBCAM";
	                    }else if(dado.tipo == 'HD'){
	                    	tipos = "HD";
	                    }else if(dado.tipo == 'SSD'){
	                    	tipos = "SSD";
	                    }else if(dado.tipo == 'MicroSD'){
	                    	tipos = "MICROSD";
	                    }else if(dado.tipo == 'Outros'){
	                    	tipos = "OUTROS";
	                    }	                    	                    
	                    
	                    $("#modalAtualizarEquipamento").modal('show');	                    
	                                       
	                    $("#equip-manutencao").val(dado.id);
	                    $("#nome-equipamento-atualizar").val(dado.nome);
	                    $("#tombo-equipamento-atualizar").val(dado.tombo);	                    
	                    $("#tipo-atualizar").val(tipos);
	                    $("#nserie-equipamento-atualizar").val(dado.numeroSerie);	                  
	                    $("#status-atualizar").val(status);
	                    $("#equipamento-setor-atualizar").val(dado.setor.id);
	                    $("#equip-descricao-atualizar").val(dado.descricao);	                    
	                    $("#ip-equipamento-atualizar").val(dado.ip);
	                    
	               }
	           }
	       }).fail(function () {

	       }).always(function () {
	       });
	   }
	
	//atualizar equipamento por json
	$(document).on('click','#btnSalvarEquipe-atualizar',function () {
		salvarEquipEditar();
	});

	function salvarEquipEditar(){	       
	    var idEquip = $("#equip-manutencao").val();
        var nomeEquipamento = $("#nome-equipamento-atualizar").val();
        var tomboEquipamento = $("#tombo-equipamento-atualizar").val();
        var numSerieEquipamento = $("#nserie-equipamento-atualizar").val();
        var tipoEquipamento = $("#tipo-atualizar").val();
        var statusEquipamento = $("#status-atualizar").val();
        var ipEquipamento = $("#ip-equipamento-atualizar").val();
        var descricaoEquipamento = $("#equip-descricao-atualizar").val();
        var setorEquipamento = $("#equipamento-setor-atualizar").val();
        		       
        //Metodo Salvar por Json
        var url="/Sisint/Home/EquipamentoManutencao/salvar";
        
        xhr = new XMLHttpRequest();
        xhr.open("POST", url, true); 	       
        xhr.setRequestHeader("Content-type","application/json");
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4 && xhr.status == 200){                       
              
                var saida = xhr.responseText;
                var response = $.parseJSON(saida);	                
                $('#modalAtualizarEquipamento').modal('hide');
                limparFormularioEditar()
                atualizarCampoEquip(response);
            }
        }
        
        // dados equipamento	        
        //var dados = JSON.stringify({"id":""+idEquip+"", "nome":""+nomeEquipamento+"", "tombo":""+tomboEquipamento+"", "numeroSerie":""+numSerieEquipamento+""
        //	,"tipo":""+tipoEquipamento+"", "status":""+statusEquipamento+"", "ip":""+ipEquipamento+"", "descricao":""+descricaoEquipamento+"", "setor":{"id":""+setorEquipamento+""} });

        var dados = JSON.stringify({"id":""+idEquip+"", "nome":""+nomeEquipamento+"", "tombo":""+tomboEquipamento+"", "numeroSerie":""+numSerieEquipamento+""
        	,"tipo":""+tipoEquipamento+"", "status":""+statusEquipamento+"", "ip":""+ipEquipamento+"", "descricao":""+descricaoEquipamento+"", "setor":{"id":""+setorEquipamento+""} });
          xhr.send(dados); 

      }
	    
	 // limpar modal atualizar equipamento 
	 function limparFormularioEditar(){
			$("#equip-manutencao").val("");
            $("#nome-equipamento-atualizar").val("");
            $("#tombo-equipamento-atualizar").val("");	                    
            $("#tipo-atualizar").val("");
            $("#nserie-equipamento-atualizar").val("");	                  
            $("#status-atualizar").val("");
            $("#equipamento-setor-atualizar").val("");
            $("#equip-descricao-atualizar").val("");	                    
            $("#ip-equipamento-atualizar").val("");
		}
			
	//Salvar novo equipamento por json
	$('#btnSalvarEquip').click(function (e) {
		e.preventDefault();
		   	    
		var numTombo = $("#tombo-equipamento").val();
		verifica(numTombo);
	});
	
	// verifica se o tombo existe
	function verifica(numTombo) {
		
		var url  = "/Sisint/Home/manutencao/verifEquipamentosPorTombo/"+numTombo
		var xhr  = new XMLHttpRequest()
		xhr.open('GET', url , true)
		xhr.onload = function () {
			var users = JSON.parse(xhr.responseText);
			if (xhr.readyState == 4 && xhr.status == "200") {
				if(users.length != 0){
					alert("Tombo já Cadastrado!!!")
				}else{
					
					verificaNserie();
				}
				//console.log(users);
			} else {

				//console.error(users);	
			}
		}
		xhr.send(null);	
	}

	// verifica se o Numero de serie existe
	function verificaNserie() {
		
		var numSerie = $("#nserie-equipamento").val();
		
		var url  = "/Sisint/Home/manutencao/verifEquipamentosPorNserie/"+numSerie
		var xhr  = new XMLHttpRequest()
		xhr.open('GET', url , true)
		xhr.onload = function () {
			var users = JSON.parse(xhr.responseText);
			if (xhr.readyState == 4 && xhr.status == "200") {
				if(users.length != 0){
					alert("Numero de Serie já Cadastrado!!!")
				}else{
					salvarNovoEquipamento();
				}
				//console.log(users);
			} else {
				//console.error(users);
			}
		}
		xhr.send(null);		
	}
	
	function salvarNovoEquipamento(){	       		   
	        var nomeEquipamento = $("#nome-equipamento").val();
	        var tomboEquipamento = $("#tombo-equipamento").val();
	        var numSerieEquipamento = $("#nserie-equipamento").val();
	        var tipoEquipamento = $("#tipo").val();
	        var statusEquipamento = $("#status").val();
	        var ipEquipamento = $("#ip-equipamento").val();
	        var descricaoEquipamento = $("#equip-descricao").val();
	        var setorEquipamento = $("#equipamento-setor").val();
	        		       
	        //Metodo Salvar por Json
	        var url="/Sisint/Home/EquipamentoManutencao/salvar";
	        
	        xhr = new XMLHttpRequest();
	        xhr.open("POST", url, true); 	       
	        xhr.setRequestHeader("Content-type","application/json");
	        xhr.onreadystatechange = function(){
	            if(xhr.readyState == 4 && xhr.status == 200){                       
	              
	                var saida = xhr.responseText;
	                var response = $.parseJSON(saida);	                
	                $('#modalCriarEquipamento').modal('hide');
	                limparCampos();
	                atualizarCampoEquip(response);
	                
	                validarinput();
	                //console.log(response);
	            }
	        }
	        
	        // dados equipamento	        
	        var dados = JSON.stringify({"id":"", "nome":""+nomeEquipamento+"", "tombo":""+tomboEquipamento+"", "numeroSerie":""+numSerieEquipamento+""
	        	,"tipo":""+tipoEquipamento+"", "status":""+statusEquipamento+"", "ip":""+ipEquipamento+"", "descricao":""+descricaoEquipamento+"", "setor":{"id":""+setorEquipamento+""} });

	          xhr.send(dados); 
    
	}
	
	//-----------fim add equipamento--------------------
	var tbody = document.querySelector('#equipamentoCadastrado');
	function atualizarCampoEquip(equipamento) {
		
		var url  = "/Sisint/Home/manutencao/verifEquipamentosPorTombo/"+equipamento.tombo
		//var url  = "/Sisint/Home/manutencao/buscarEquipamentosPorTombo/"+equipamento.tombo
		var xhr  = new XMLHttpRequest()
		xhr.open('GET', url , true)
		xhr.onload = function () {
			var equipamento = JSON.parse(xhr.responseText);
			var i;
			if (xhr.readyState == 4 && xhr.status == "200") {			  
				function customizarStatus(equipamento) {
				    if(equipamento[0].status == 'OK') {
				        return "<span class='badge badge-success'>OK</span>";
				    } else if(equipamento[0].status == 'Em Conserto' || equipamento[0].status == 'EM_CONSERTO') {
				        return "<span class='badge badge-warning'>Em Conserto</span>";
				    } else if(equipamento[0].status == 'Para Alienação' || equipamento[0].status == 'PARA_ALIENACAO') {
				        return "<span class='badge badge-danger'> Para Alienação</span>";
				    } else if(equipamento[0].status == 'Alienado' || equipamento[0].status == 'ALIENADO'){
				    	return "<span class='badge badge-default'>Alienado</span>";
				    }
				}
				//serve para habilitar		
				validarinput();
				$("#equip-manutencao").val(equipamento[0].id);
				$("#nomeEquipamento").text(equipamento[0].nome);
				$("#descricaoEquipamento").text(equipamento[0].descricao);
				$('#equipamentoCadastrado').html("");	
			    tbody.innerHTML +=   			       
			        "<div id='list-tarefa' class='list-group-item border-left-primary'>" +			          			                
			                "<a id='editar-equip' class='editar-tarefa' href='#' posicao= '"+0+"' data-toggle='modal'  style='float: right;'>" +
	  	                    "<i class='fas fa-fw fa-edit'></i></a>" +
	  	                    "<h5 class='list-group-item-heading h5 mb-2 text-gray-800'>Equipamento</h5>" +			              
			                "<span class='list-group-item-text' style='size: 14px; margin-right: 16px;'>" +
			                        "Nome: " + equipamento[0].nome +"</span><br>"+
			                "<span class='list-group-item-text' style='size: 14px; margin-right: 16px;'>" +
			                        "Tombo: " + equipamento[0].tombo +"</span><br>"+
			                "<span class='list-group-item-text' style='size: 14px; margin-right: 16px;'>" + 
			                        "Num. Série: " + equipamento[0].numeroSerie +"</span><br>"+
			                "<span class='list-group-item-text' style='size: 14px; margin-right: 16px;'>" +
			                        "Status: " + customizarStatus(equipamento)+"</span>" +			                                                
			        "</div>";
			      
				   //console.log(equipamento);			
				 
			} else {
				//console.error(equipamento);
			}
		}
		xhr.send(null);				
	}
	
	function limparCampos(){
		    $("#nome-equipamento").val("");
	        $("#tombo-equipamento").val("");
	        $("#nserie-equipamento").val("");
	        $("#status").val("");
	        $("#tipo").val("");
	        $("#ip-equipamento").val("");
	        $("#equip-descricao").val("");
	        $("#equipamento-setor").val("");
	}
	
	//validar modal criar equipamento
	$('#btnSalvarEquip').attr("disabled", "disabled");


    $('#tombo-equipamento').keyup( function (event) {
    	verifcarInputsModal();
    });

    $('#nserie-equipamento').keyup( function (event) {
    	verifcarInputsModal();
     });
    
    $("#btn-gerarns").click( function(){
    	verifcarInputsModal();
    });
    
    $("#btn-gerartombo").click( function(){
    	verifcarInputsModal();
    });
    
    $('#nome-equipamento').keyup( function (event) {
    	verifcarInputsModal();
    });
    
    $('#ip-equipamento').keyup( function (event) {
    	verifcarInputsModal();
    });
    
    $('#status').change( function (event) {
    	verifcarInputsModal();
    });
    
    $('#tipo').change( function (event) {
    	verifcarInputsModal();
    });
    
    $('#equipamento-setor').change( function (event) {
    	verifcarInputsModal();
    });
    
    $('#equip-descricao').keyup( function (event) {
    	verifcarInputsModal();
    });
    
    function verifcarInputsModal() {
        if ($('#nserie-equipamento').val() == "" || $('#tombo-equipamento').val() == "" || $('#nome-equipamento').val() == "" || $('#equipamento-setor').val() == ""
        	|| $('#equip-descricao').val() == "" || $('#ip-equipamento').val() == "" || $('#tipo').val() == "" || $('#status').val() == ""){
            $('#btnSalvarEquip').attr("disabled", "disabled");
           // $('#campoObrigatorio').text("*Todos os campos devem ser preenchidos.");
        } else {
            $('#btnSalvarEquip').removeAttr("disabled");
            //$('#campoObrigatorio').text("");
        }
    }
    
    //validar modal atualizar equip
    //$('#btnSalvarEquip').attr("disabled", "disabled");


    
    
    $('#nome-equipamento-atualizar').keyup( function (event) {
    	verifcarInputsModal2();
    });
    
    $('#tipo-atualizar').change( function (event) {
    	verifcarInputsModal2();
    });
    
    $('#equipamento-setor-atualizar').change( function (event) {
    	verifcarInputsModal2();
    });
    
    $('#equip-descricao-atualizar').keyup( function (event) {
    	verifcarInputsModal2();
    });
    
    function verifcarInputsModal2() {
        if ($('#nome-equipamento-atualizar').val() == "" || $('#equipamento-setor-atualizar').val() == ""
        	|| $('#equip-descricao-atualizar').val() == "" || $('#tipo-atualizar').val() == ""){
            $('#btnSalvarEquipe-atualizar').attr("disabled", "disabled");
           // $('#campoObrigatorio').text("*Todos os campos devem ser preenchidos.");
        } else {
            $('#btnSalvarEquipe-atualizar').removeAttr("disabled");
            //$('#campoObrigatorio').text("");
        }
    }
	
});