package tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelGenerator {

    private static final String FILE_NAME = "PartitionProblem" + UUID.randomUUID().toString() + ".xlsx";
    private static FormulaEvaluator evaluator;
    private static String[] headers = {"Size, Complexity"};

    private ExcelGenerator() {
    }

    public static void write(Map<Integer, List<Long>> greedySizeTimes, Map<Integer, List<Long>> bruteForceSizeTimes,
                             Map<Integer, Long> greedySizeSpaces, Map<Integer, Long> bruteForceSizeSpaces) {
        Path targetPath = createDirectoryWithFile();
        XSSFWorkbook workbook = new XSSFWorkbook();
        createExcel(workbook, greedySizeTimes, bruteForceSizeTimes, greedySizeSpaces, bruteForceSizeSpaces);
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
        Path path = Paths.get(HOME + "/partition_problem/");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        path = Paths.get(path.toString(), FILE_NAME);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return path;
    }

    private static void createExcel(XSSFWorkbook workbook, Map<Integer, List<Long>> greedySizeTimes,
                                    Map<Integer, List<Long>> bruteForceSizeTimes,
                                    Map<Integer, Long> greedySizeSpaces,
                                    Map<Integer, Long> bruteForceSizeSpaces) {
        System.out.println("Creating excel...");
        XSSFSheet sheet = workbook.createSheet("Partition problem");
        evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        int rowNum = sheet.getLastRowNum() + 1;
        createHeaders(sheet, 0, "Greedy");
        rowNum = fillData(greedySizeTimes, greedySizeSpaces, sheet, rowNum);
        createHeaders(sheet, rowNum++, "BruteForce");
        fillData(bruteForceSizeTimes, bruteForceSizeSpaces, sheet, rowNum);
    }

    private static void createHeaders(XSSFSheet sheet, int rowNum, String name) {
        Row headerRow = sheet.createRow(rowNum);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i] + " " + name);
        }
    }

    private static int fillData(Map<Integer, List<Long>> sizeTimes, Map<Integer, Long> sizeSpaces,
                                XSSFSheet sheet, int rowNum) {
        Row row;
        int colNum;
        Cell cell;
        for (Map.Entry<Integer, List<Long>> integerListEntry : sizeTimes.entrySet()) {
            row = sheet.createRow(rowNum++);
            colNum = 0;
            cell = row.createCell(colNum++);
            cell.setCellValue(integerListEntry.getKey());
            List<Long> value = integerListEntry.getValue();
            for (Long aLong : value) {
                cell = row.createCell(colNum++);
                cell.setCellValue(aLong);
            }
            cell = row.createCell(colNum++ + 1);
            cell.setCellFormula("AVERAGE(" + CellReference.convertNumToColString(1) + (row.getRowNum() + 1) + ":"
                    + CellReference.convertNumToColString(sizeTimes.entrySet().iterator().next().getValue().size())
                    + (row.getRowNum() + 1) + ")");
            evaluator.evaluateFormulaCellEnum(cell);
            cell = row.createCell(colNum + 2);
            cell.setCellValue(sizeSpaces.get(integerListEntry.getKey()));
        }
        return rowNum;
    }
}
