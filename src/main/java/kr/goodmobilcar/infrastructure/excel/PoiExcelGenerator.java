package kr.goodmobilcar.infrastructure.excel;

import kr.goodmobilcar.application.port.ExcelGenerator;
import kr.goodmobilcar.domain.lead.Lead;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PoiExcelGenerator implements ExcelGenerator {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("Asia/Seoul"));

    private static final String[] HEADERS = {
            "ID", "이름", "연락처", "차량", "리스기간(개월)", "소득형태",
            "채널", "상태", "담당자ID", "메모", "신청일시"
    };

    @Override
    public byte[] generate(List<Lead> leads) {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("리드목록");

            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (Lead lead : leads) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(lead.getId());
                row.createCell(1).setCellValue(lead.getCustomerName());
                row.createCell(2).setCellValue(lead.getPhone().getValue());
                row.createCell(3).setCellValue(lead.getCarModel());
                row.createCell(4).setCellValue(lead.getLeasePeriod());
                row.createCell(5).setCellValue(lead.getIncomeType().name());
                row.createCell(6).setCellValue(lead.getChannel() != null ? lead.getChannel().name() : "");
                row.createCell(7).setCellValue(lead.getStatus().name());
                row.createCell(8).setCellValue(lead.getAssigneeId() != null ? lead.getAssigneeId() : 0);
                row.createCell(9).setCellValue(lead.getMemo() != null ? lead.getMemo() : "");
                row.createCell(10).setCellValue(lead.getCreatedAt() != null ? FMT.format(lead.getCreatedAt()) : "");
            }

            for (int i = 0; i < HEADERS.length; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("엑셀 생성 실패", e);
        }
    }
}
