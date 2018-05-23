import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    private static final String FILE_NAME = "PartitionProblem" + UUID.randomUUID().toString() + ".xlsx";
    private static String[] headers = {"BruteForce", "Greedy"};
    private static Path targetPath;

    public static void main(String[] args) {
        ExcelGenerator excelGenerator = new ExcelGenerator();
        excelGenerator.write();
    }

    public void write(){
        targetPath = createDirectoryWithFile();
        XSSFWorkbook workbook = new XSSFWorkbook();
        createExcel(workbook);
        try {
            FileOutputStream outputStream = new FileOutputStream(targetPath.toString());
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createExcel(XSSFWorkbook workbook) {
        System.out.println("Creating excel");
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        createHeaders(sheet);
        Object[][] datatypes = {
            {"Datatype", "Type", "Size(in bytes)"},
            {"int", "Primitive", Integer.BYTES},
            {"float", "Primitive", Float.BYTES},
            {"double", "Primitive", Double.BYTES},
            {"char", "Primitive", Character.BYTES},
            {"String", "Non-Primitive", "No fixed size"}
        };
        int rowNum = sheet.getLastRowNum() + 1;
        for (Object[] datatype : datatypes) {
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
    }

    private static void createHeaders(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private static Path createDirectoryWithFile() {
        System.out.println("Creating directory");
        final String HOME = System.getProperty("user.home");
        Path path = Paths.get(HOME + "/partition_problem/", FILE_NAME);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return path;
    }
}
