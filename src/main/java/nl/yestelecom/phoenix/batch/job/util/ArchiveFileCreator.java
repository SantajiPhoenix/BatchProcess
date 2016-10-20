package nl.yestelecom.phoenix.batch.job.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ArchiveFileCreator {
	public void createArchiveFile(String currentPath) {
			Date today = new Date();
			SimpleDateFormat sds = new SimpleDateFormat("yyyyMMddHHmmSS");
			String date = sds.format(today);
			String archiveDir = currentPath+"archive/";
			File[] files = new File(currentPath).listFiles();

			for (File file : files) {
		        if (!file.isDirectory()){
		            System.out.println("File: " + (file.getName()+date).trim());
		            String archiveFileName = (file.getName()+"_"+date).trim();
		            Path src = Paths.get(currentPath+file.getName());
		            Path dest = Paths.get(archiveDir+archiveFileName);
		            try{
		            	Files.copy(src, dest);
		            }catch(Exception e){
		            	System.out.println("Error while copying >> "+e);
		            }
		            
		        }
		    }

			
			
			
	}

}
