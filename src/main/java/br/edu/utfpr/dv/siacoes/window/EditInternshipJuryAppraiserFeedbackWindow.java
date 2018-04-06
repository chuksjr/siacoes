﻿package br.edu.utfpr.dv.siacoes.window;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.ui.Notification;

import br.edu.utfpr.dv.siacoes.bo.InternshipJuryAppraiserBO;
import br.edu.utfpr.dv.siacoes.components.FileUploader;
import br.edu.utfpr.dv.siacoes.model.InternshipJuryAppraiser;
import br.edu.utfpr.dv.siacoes.model.Document.DocumentType;

public class EditInternshipJuryAppraiserFeedbackWindow extends EditWindow {
	
	private final InternshipJuryAppraiser appraiser;
	
	private final FileUploader uploadFile;
	
	public EditInternshipJuryAppraiserFeedbackWindow(InternshipJuryAppraiser appraiser){
		super("Enviar Feedback", null);
		
		if(appraiser == null){
			this.appraiser = new InternshipJuryAppraiser();
		}else{
			this.appraiser = appraiser;
		}
		
		this.uploadFile = new FileUploader("(Formato PDF, Tam. Máx. 5 MB)");
		this.uploadFile.getAcceptedDocumentTypes().add(DocumentType.PDF);
		this.uploadFile.setMaxBytesLength(6 * 1024 * 1024);
		
		this.addField(this.uploadFile);
	}
	
	@Override
	public void save() {
		if(this.uploadFile.getUploadedFile() != null) {
			this.appraiser.setFile(this.uploadFile.getUploadedFile());
		}
		
		if(this.appraiser.getFile() == null){
			Notification.show("Enviar Feedback", "É necessário submeter o arquivo.", Notification.Type.ERROR_MESSAGE);
		}else{
			try{
				InternshipJuryAppraiserBO bo = new InternshipJuryAppraiserBO();
				
				bo.save(this.appraiser);
				
				Notification.show("Enviar Feedback", "Feedback enviado com sucesso.", Notification.Type.HUMANIZED_MESSAGE);
				
				this.parentViewRefreshGrid();
				this.close();
			}catch(Exception e){
				Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
				
				Notification.show("Enviar Feedback", e.getMessage(), Notification.Type.ERROR_MESSAGE);
			}
		}
	}
	
}
