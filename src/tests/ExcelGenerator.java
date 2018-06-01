package tests;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

    private static final String FILE_NAME = "PartitionProblem" + UUID.randomUUID().toString() + ".xlsx";
    private static FormulaEvaluator evaluator;
    private static String[] headers = {"Size"};

    private ExcelGenerator() {
    }

    public static void write(Map<Integer, List<Long>> greedySizeTimes, Map<Integer, List<Long>> bruteForceSizeTimes) {
        Path targetPath = createDirectoryWithFile();
        XSSFWorkbook workbook = new XSSFWorkbook();
        createExcel(workbook, greedySizeTimes, bruteForceSizeTimes);
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

    private static Path createDirectoryWithFile() {
        System.out.println("Creating directory...");
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

    private static void createExcel(XSSFWorkbook workbook, Map<Integer, List<Long>> greedySizeTimes, Map<Integer, List<Long>> bruteForceSizeTimes) {
        System.out.println("Creating excel...");
        XSSFSheet sheet = workbook.createSheet("Partition problem");
        evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        int rowNum = sheet.getLastRowNum() + 1;
        createHeaders(sheet, 0, "Greedy");
        rowNum = fillData(greedySizeTimes, sheet, rowNum);
        createHeaders(sheet, rowNum++, "BruteForce");
        fillData(bruteForceSizeTimes, sheet, rowNum);
    }

    private static void createHeaders(XSSFSheet sheet, int rowNum, String name) {
        Row headerRow = sheet.createRow(rowNum);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i] + " " + name);
        }
    }

    private static int fillData(Map<Integer, List<Long>> integerListMap, XSSFSheet sheet, int rowNum) {
        Row row;
        int colNum;
        Cell cell;
        for (Map.Entry<Integer, List<Long>> integerListEntry : integerListMap.entrySet()) {
            row = sheet.createRow(rowNum++);
            colNum = 0;
            cell = row.createCell(colNum++);
            cell.setCellValue(integerListEntry.getKey());
            List<Long> value = integerListEntry.getValue();
            for (Long aLong : value) {
                cell = row.createCell(colNum++);
                cell.setCellValue(aLong);
            }
            cell = row.createCell(colNum + 1);
            cell.setCellFormula("AVERAGE(" + CellReference.convertNumToColString(1) + (row.getRowNum() + 1) + ":"
                + CellReference.convertNumToColString(integerListMap.entrySet().iterator().next().getValue().size())
                + (row.getRowNum() + 1) + ")");
            evaluator.evaluateFormulaCellEnum(cell);
        }
        return rowNum;
    }
}
