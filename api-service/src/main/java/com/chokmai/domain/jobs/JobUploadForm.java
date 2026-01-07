package com.chokmai.domain.jobs;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class JobUploadForm {

    @FormParam("projectId")
    public String projectId;

    @FormParam("processorId")
    public String processorId;

    @FormParam("file")
    public FileUpload file;
}
