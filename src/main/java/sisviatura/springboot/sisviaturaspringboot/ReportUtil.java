package sisviatura.springboot.sisviaturaspringboot;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;




import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {

	public byte[] gerarRelatorio(List listaDados,String relatorio, ServletContext context) throws Exception{
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		String caminhoJasper = context.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(), jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
	
//	public byte[] tombo(List listaDados,String relatorio, ServletContext context) throws Exception{
//		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
//		String caminhoJrxml = context.getRealPath("relatorios") + File.separator + relatorio + ".jrxml";
//		String caminhoJasper = context.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
//		JasperCompileManager.compileReportToFile(caminhoJrxml,caminhoJasper);
//		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(), jrbcds);
//		
//		return JasperExportManager.exportReportToPdf(impressoraJasper);
//	}
	
}
