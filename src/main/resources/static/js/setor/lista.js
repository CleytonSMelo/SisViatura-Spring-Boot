//$(document).ready(function() {
	
	$('.table #remover').click( function (event) {
       event.preventDefault();
       var href = $(this).attr('href');
       $('#exampleModal #btnRemover').attr('href', href);
        $('#exampleModal').modal();
    });
	
	$('.table #AddCriarServico').click( function (event) {
	       event.preventDefault();
	       var href = $(this).attr('href');
	       $('#criarServicoModal #btnPermitir').attr('href', href);
	        $('#criarServicoModal').modal();
	});
	
	$('.table #RemCriarServico').click( function (event) {
	       event.preventDefault();
	       var href = $(this).attr('href');
	       $('#remServicoModal #btnRevogar').attr('href', href);
	        $('#remServicoModal').modal();
    });
	
//});