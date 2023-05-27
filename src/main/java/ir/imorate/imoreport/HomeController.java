package ir.imorate.imoreport;

import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;


@Controller
@AllArgsConstructor
public class HomeController {

    private final Faker faker;
    private final ResourceLoader resourceLoader;

    @RequestMapping("/")
    public String home(Model model) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            employees.add(new Employee(
                    String.format("%s %s", faker.name().firstName(), faker.name().lastName()),
                    faker.country().name(),
                    faker.date().birthday(),
                    new BigDecimal((new Random().nextInt(10000) + 500)),
                    new BigDecimal((new Random().nextInt(10000) + 500)))
            );
        }
        model.addAttribute("employees", employees);
        try (InputStream is = Objects.requireNonNull(resourceLoader.getClassLoader()).getResourceAsStream("template.xlsx")) {
            try (OutputStream os = new FileOutputStream("./report/Report.xlsx")) {
                Context context = new Context();
                context.putVar("employees", employees);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "home";
    }

    @GetMapping("/write-excel")
    public String writeExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Excel sheet");
            Object[][] dataTypes = {
                    {"Datatype", "Type", "Size(in bytes)"},
                    {"int", "Primitive", 2},
                    {"float", "Primitive", 4},
                    {"double", "Primitive", 8},
                    {"char", "Primitive", 1},
                    {"String", "Non-Primitive", "No fixed size"}
            };
            int rowNum = 0;
            System.out.println("Creating excel");

            for (Object[] datatype : dataTypes) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : datatype) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }

            try {
                FileOutputStream outputStream = new FileOutputStream("excel.xlsx");
                workbook.write(outputStream);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "excel";
    }

    @GetMapping("/read-excel")
    public String readExcel(){
        try {
            FileInputStream excelFile = new FileInputStream("excel.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row currentRow : sheet) {
                Iterator<Cell> cellIterator = currentRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellType() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "\t");
                    } else if (currentCell.getCellType() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "\t");
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "excel";
    }

}