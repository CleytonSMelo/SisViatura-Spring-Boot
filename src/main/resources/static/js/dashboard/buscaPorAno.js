$(document).ready(function () {
	$('#select-option').change( function (event) {
    	var sdata = $('#select-option').val();
    	console.log(sdata);
        var url ='/SisVTR/filtrarServicoPorAno/'+sdata;
                       
        console.log(url);
        if ($('#select-option').val() != "") {
            $.ajax( {
            	dataType : 'json',
                type : 'GET',
                url : url 
            }).done(function (data) {
                 console.log(data);
                if (data.total == "") {
                	$("#totalconluido").text(data);
                } else {
                    $("#totalconluido").text(data);
                }
                
            }).fail(function (jqXHR, textStatus, errorThrown) {
             
            });
        } else {
            $('#totalconluido').val("");
           
        }
    });
	
	
	//---Tarefa
	$('#select-option2').change( function (event) {
    	var sdata = $('#select-option2').val();
    	console.log(sdata);
        var url ='/SisVTR/filtrarCotasPorAno/'+sdata;
                       
        console.log(url);
        if ($('#select-option2').val() != "") {
            $.ajax( {
            	dataType : 'json',
                type : 'GET',
                url : url 
            }).done(function (data) {
                 console.log(data);
                if (data.total == "") {
                	$("#totalTarefasConluida").text(data);
                } else {
                    $("#totalTarefasConluida").text(data);
                }
                
            }).fail(function (jqXHR, textStatus, errorThrown) {
             
            });
        } else {
            $('#totalTarefasConluida').val("");
           
        }
    });
	
	//--manutencao
	$('#select-option3').change( function (event) {
    	var sdata = $('#select-option3').val();
    	console.log(sdata);
        var url ='/SisVTR/filtrarVistoriasPorAno/'+sdata;
                       
        console.log(url);
        if ($('#select-option3').val() != "") {
            $.ajax( {
            	dataType : 'json',
                type : 'GET',
                url : url 
            }).done(function (data) {
                 console.log(data);
                if (data.total == "") {
                	$("#totalManutencoesConluida").text(data);
                } else {
                    $("#totalManutencoesConluida").text(data);
                }
                
            }).fail(function (jqXHR, textStatus, errorThrown) {
             
            });
        } else {
            $('#totalTarefasConluida').val("");
           
        }
    });
});

// document.querySelector("#totalconluido")