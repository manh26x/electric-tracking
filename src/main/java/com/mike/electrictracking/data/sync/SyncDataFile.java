package com.mike.electrictracking.data.sync;

import com.mike.electrictracking.entity.ReceiveValue;
import com.mike.electrictracking.entity.Tags;
import com.mike.electrictracking.repository.ReceiveValueRepository;
import com.mike.electrictracking.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
public class SyncDataFile {

    @Value("${data.uri}")
    private String uriFolderData;
    @Value("${data.uri-backup}")
    private String uriFolderDataBackup;
    @Value("${data.date.format}")
    private String dateFormat;

    public static final String DELIMITER = ";";

    @Autowired
    private ReceiveValueRepository receiveValueRepository;
    @Autowired
    private TagsRepository tagsRepository;

    @Scheduled( initialDelay = 3 * 1000, fixedDelay =  30 * 60 * 1000)
    public void syncData() {
        File folder = new File(uriFolderData);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    Tags tags = new Tags();
                    String[] arrParamName = new String[0];
                    while ((line = br.readLine()) != null) {
                        if(line.trim().isEmpty()) {
                            break;
                        }
                        if(line.charAt(0) == '#') {
                            if(line.startsWith("#Time")) {
                                arrParamName = line.split(DELIMITER);
                            } else {
                                String rawTagName = line.split(" ")[0].trim();
                                tags.setName(rawTagName.substring(1, rawTagName.length()));
                                tags.setCode(line.split(" ")[1]);
                                if(tagsRepository.existsById(tags.getName())) {
                                    tags.setTotalError(tagsRepository.getTotalError(tags.getName()));
                                }
                            }
                        } else {
                            String[] values = line.split(";");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                            simpleDateFormat.applyPattern(dateFormat);
                            Date timeValue = simpleDateFormat.parse(values[0]);
                            for(int i =1; i < values.length; i++) {
                                String paramName = arrParamName[i].trim();
                                if(paramName.equals("Cycle Time")) {
                                    continue;
                                }
                                if(paramName.equals("Error")) {
                                    tags.setTotalError(tags.getTotalError() == null ?
                                            Long.parseLong(values[i]) : tags.getTotalError() + Long.parseLong(values[i]));
                                }
                                Double value = Double.valueOf(values[i]);
                                ReceiveValue receiveValue = new ReceiveValue();
                                receiveValue.setParamValue(value);
                                receiveValue.setTime(timeValue);
                                receiveValue.setParamName(paramName);
                                receiveValue.setTagsName(tags.getName());
                                if(!receiveValueRepository
                                        .findFirstByTimeAndParamNameAndTagsName(timeValue, paramName, tags.getName())
                                        .isPresent()) {
                                    receiveValueRepository.save(receiveValue);
                                } else {
                                    break;
                                }
                            }
                        }
                        tags = tagsRepository.save(tags);
                    }
                    br.close();
                    file.renameTo(new File(uriFolderDataBackup + "/" + file.getName()));
                }
                catch(IOException | ParseException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }


}
