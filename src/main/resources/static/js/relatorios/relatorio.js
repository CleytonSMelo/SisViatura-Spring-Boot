
$('#btnRelManut').click(function (e) {
	 e.preventDefault();
	$('#formRelatorio').attr("action", "/Sisint/Home/GerarRelatorioManutencao");
	$('#formRelatorio').submit();
        
});

$('#btnRelServico').click(function (e) {
	 e.preventDefault();
	$('#formRelatorio').attr("action", "/Sisint/Home/GerarRelatorioServico");
	$('#formRelatorio').submit();
       
});