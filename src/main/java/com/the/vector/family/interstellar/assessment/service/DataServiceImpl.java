/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.service;

import com.the.vector.family.interstellar.assessment.entity.Planet;
import com.the.vector.family.interstellar.assessment.entity.Route;
import com.the.vector.family.interstellar.assessment.exception.InterstellarException;
import com.the.vector.family.interstellar.assessment.repository.PlanetRepository;
import com.the.vector.family.interstellar.assessment.repository.RouteRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vee
 */
@Component
@SuppressWarnings("deprecation")
public class DataServiceImpl implements DataService{
    
    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private RouteRepository routeRepository;
    
    @Value(value = "${xlsx.data.file.location}") //get file classpath
    private String XLSX_DATA_FILE;
    
    /**
     * When the Spring application context is started the below method will catch the life-cycle 
     * ContextStartedEvent and then invoke readXlsDataFile()
     * @param startEvent, it is an event fired when the Spring context is initialized
     */
    @EventListener
    public void onApplicationEvent(ContextStartedEvent startEvent) {
	getDataFromFile();
    }
        
    @Override
    public void getDataFromFile() {
        try{
            FileInputStream fis = new FileInputStream(new File(XLSX_DATA_FILE)); //reads the XLS data file
            Workbook workbook = new XSSFWorkbook(fis); //creates an XLS work book from specifed XLS file path
            
            processPlanetSheet(workbook); //process the XLS worksheets within PLANETS sheet
            processRoutesSheet(workbook); //process the XLS worksheets within ROUTES sheet
        }catch(FileNotFoundException ex){
            ex.getMessage();
        }catch(IOException ex){
            ex.getMessage();
        }
    }
    
    /**
     * Processes the first work sheet(planets) and then saves each record into the derby DB
     * @param workbook, The workbook containing the planets information
     */
    private void processPlanetSheet(Workbook workbook) throws FileNotFoundException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        
        Iterator<Row> it = sheet.iterator();
        while(it.hasNext()){
            Row row = it.next();
            Cell idCell = row.getCell(0);
            Cell nameCell = row.getCell(1);
            
            if ((idCell.getCellTypeEnum() == CellType.STRING) && (nameCell.getCellTypeEnum() == CellType.STRING)) {
		savePlanets(idCell.getStringCellValue(), nameCell.getStringCellValue());
            }
        }
    }
    
    /**
     * saves planets into the derby DB
     */
    private void savePlanets(String id, String name) {
	if(!id.contains("Node")) {
            planetRepository.save(new Planet(id, name));
	}
    }
    
    /**
     * Processes the second work sheet(routes) and then saves each record into the derby DB
     * @param workbook, The workbook containing the routes information
     */
    private void processRoutesSheet(Workbook workbook) throws FileNotFoundException, IOException {
        Sheet sheet = workbook.getSheetAt(1);
        
        Iterator<Row> it = sheet.iterator();
        while(it.hasNext()){
            Row row = it.next();
            Cell idCell = row.getCell(0);
            Cell sourceCell = row.getCell(1);
            Cell destinationCell = row.getCell(2);
            Cell weightCell = row.getCell(3);
            
            short id = 0;
            String source = "";
            String destination = "";
            double distance = 0.0d;
            
            if(idCell.getCellTypeEnum() == CellType.NUMERIC){
                id = (short) idCell.getNumericCellValue();
            }else{
                continue;
            }
            
            if (sourceCell.getCellTypeEnum() == CellType.STRING) {
		source = sourceCell.getStringCellValue();
            }
            
            if (destinationCell.getCellTypeEnum() == CellType.STRING) {
		destination = destinationCell.getStringCellValue();
            }
            
            if (weightCell.getCellTypeEnum() == CellType.NUMERIC) {
		distance = (double) weightCell.getNumericCellValue();
            }
            
            saveRoute(id, source, destination, distance);
        }
    }
    
    private void saveRoute(short id, String source, String destination, double distance) {
        try{
            if (!source.equals(destination))
                persistRoute(id, source, destination, distance);
        }catch(InterstellarException ex){
            ex.getLocalizedMessage();
        }
    }
    
    /**
     * saves routes into the derby DB
     */
    private void persistRoute(Short id, String origin, String planetDest, double distance) throws InterstellarException{
        /*Planet source = new Planet();
        Planet dest = new Planet();
        source.setId(origin);
        dest.setId(planetDest);*/
        Planet source = planetRepository.getOne(origin);
        Planet dest = planetRepository.getOne(planetDest);
        
        if ((source != null) && (dest != null)){
            routeRepository.save(new Route(id, source, dest, distance));
        }
    }
}
