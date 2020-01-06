package ec.gob.dinardap.correo.util;

import org.apache.commons.io.FilenameUtils;

public class TipoArchivo {
	public static String obtenerTipoArchivo(String nombreArchivo){
		String extension = FilenameUtils.getExtension(nombreArchivo);
		String tipo = null;
		switch(extension){
		case "pdf":
			tipo = "application/pdf";
			break;
		case "jpg":
			tipo = "image/jpeg";
			break;
		case "png":
			tipo = "image/png";
			break;
		case "xls":
			tipo = "application/vnd.ms-excel";
			break;
		case "xlsx":
			tipo = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			break;
		case "xlsm":
			tipo = "application/vnd.ms-excel.sheet.macroEnabled.12";
			break;
		case "doc":
			tipo = "application/msword";
			break;
		case "docx":
			tipo ="application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			break;
		case "zip":
			tipo = "application/zip";
			break;
		case "rar":
			tipo = "application/x-rar-compressed";
			break;
		}
		return tipo;
	}
}
