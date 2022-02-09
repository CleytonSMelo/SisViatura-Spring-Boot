$(document).ready(function() {
	
	$('.table #remover').click( function (event) {
       event.preventDefault();
       var href = $(this).attr('href');
       $('#exampleModal #btnRemover').attr('href', href);
        $('#exampleModal').modal();
    });
	
	$('.table #foto-viatura').click( function (event) {
	       event.preventDefault();
	       var id = $(this).attr('viatura-id');
	       //$('#exampleModal #btnRemover').attr('href', href);
	        //$('#exampleModal').modal();
	       $("#viatura-id").val(id);
	 });
	
	$("#btnSalvarFoto").click( function (event) {
		$("#formModalFoto").submit();
	});	
});

function keypressed( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("valor-mercado").value
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
    var texto = document.getElementById("valor-inves").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

function keypressed2( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("km-litro").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

function keypressed3( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("km-inicial").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

function keypressed4( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("cota-semanal").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

function keypressed5( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("cota-extra").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

function keypressed6( obj , e ) {
    var tecla = ( window.event ) ? e.keyCode : e.which;
    var texto = document.getElementById("capacidadetanque").value
    var indexvir = texto.indexOf(",")
    var indexpon = texto.indexOf(".")
   
   if ( tecla == 8 || tecla == 0 )
       return true;
   if ( tecla != 44 && tecla != 46 && tecla < 48 || tecla > 57 )
       return false;
   if (tecla == 44) { if (indexvir !== -1 || indexpon !== -1) {return false} }
   if (tecla == 46) { if (indexvir !== -1 || indexpon !== -1) {return false} }
}

