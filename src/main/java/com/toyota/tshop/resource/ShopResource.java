package com.toyota.tshop.resource;

import com.toyota.tshop.entity.Shop;
import com.toyota.tshop.service.ShopService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Component
@Path("/shops")
public class ShopResource {

    @Autowired
    private ShopService shopService;

    @GET
    @Produces("application/json")
    public List<Shop> getShops() {
        return shopService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Shop getShopWithID(@PathParam("id") int id) {
        return shopService.getByID(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Shop addShop(Shop c) {
        shopService.persistShop(c);
        return c;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Shop updateShop(Shop c) {
        return shopService.updateShop(c);
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public void removeShop(@PathParam("id") int id) {
        shopService.deleteShop(id);
    }

    @GET
    @Path("/download/{id}")
    @Produces("application/vnd.ms-excel")
    public Response getFile(@PathParam("id") int id) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(shopService.getByID(id).getName());
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(Font.COLOR_RED);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        Map<String, Object[]> data = new HashMap<String, Object[]>();
        data.put("2", new Object[] {"ID", "CODE", "NAME","UPDATED BY","UPDATED DATE"});
        data.put("1", new Object[] {
                shopService.getByID(id).getId(),
                shopService.getByID(id).getCode(),
                shopService.getByID(id).getName(),
                shopService.getByID(id).getUpdatedBy(),
                shopService.getByID(id).getUpdatedDate()
        });
        Set<String> keyset = data.keySet();
        int rownum = 0;
        int i=0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            i++;
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if(i==1) {
                    cell.setCellStyle(style);
                }
                if(obj instanceof Date)
                    cell.setCellValue((Date)obj);
                else if(obj instanceof Boolean)
                    cell.setCellValue((Boolean)obj);
                else if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Double)
                    cell.setCellValue((Double)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        try {
            FileOutputStream out = new FileOutputStream(new File("temp.xls"));
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File myFile = new File("temp.xls");
        Response.ResponseBuilder response = Response.ok((Object) myFile);
        response.header("Content-Disposition","attachment; filename="+shopService.getByID(id).getName()+".xls");
        return response.build();
    }

}
